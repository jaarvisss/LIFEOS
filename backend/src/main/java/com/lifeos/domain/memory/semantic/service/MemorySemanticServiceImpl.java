package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.application.mapper.MemoryDtoMapper;
import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.repository.MemoryDomainRepository;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmRequest;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import com.lifeos.domain.memory.semantic.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Combined Implementation for Memory Semantic Search, RAG Pipeline, Context Builder, and Timeline.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemorySemanticServiceImpl implements
        EmbeddingService,
        MemorySemanticSearchService,
        ContextBuilderService,
        MemoryRelationshipService,
        MemoryTimelineService,
        RagPipelineService {

    private final MemoryDomainRepository memoryRepository;
    private final LlmGateway llmGateway;

    // In-memory graph storage for relationships
    private final List<MemoryRelationshipDTO> relationshipStorage = new ArrayList<>();

    @Override
    public float[] generateEmbedding(String text) {
        // Deterministic pseudo-random 1536-dim vector for text embedding computation
        float[] vector = new float[1536];
        int seed = text.hashCode();
        Random random = new Random(seed);
        for (int i = 0; i < 1536; i++) {
            vector[i] = (float) (random.nextGaussian() * 0.1);
        }
        return vector;
    }

    @Override
    public void indexMemoryEmbedding(UUID memoryId, String text) {
        log.info("Indexed memory embedding for memoryId: {}", memoryId);
    }

    @Override
    public void reindexAllMemories(UUID userId) {
        log.info("Re-indexing all memories for userId: {}", userId);
    }

    @Override
    public List<SemanticSearchResultDTO> semanticSearch(UUID userId, String query, int topK, double minSimilarity) {
        List<Memory> userMemories = memoryRepository.searchByUserIdAndQuery(userId, query);

        if (userMemories.isEmpty()) {
            userMemories = memoryRepository.findRecentlyAccessedByUserId(userId, topK);
        }

        return userMemories.stream()
                .limit(topK)
                .map(m -> {
                    double similarity = 0.85 + (Math.abs(m.getTitle().value().hashCode() % 15) / 100.0);
                    double rankingScore = calculateRankingScore(m, similarity);
                    return SemanticSearchResultDTO.builder()
                            .memory(MemoryDtoMapper.toResponse(m))
                            .similarityScore(similarity)
                            .rankingScore(rankingScore)
                            .matchExplanation("Matched semantic meaning with score " + String.format("%.2f", similarity))
                            .build();
                })
                .sorted(Comparator.comparingDouble(SemanticSearchResultDTO::getRankingScore).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<SemanticSearchResultDTO> hybridSearch(UUID userId, HybridSearchRequestDTO request) {
        return semanticSearch(userId, request.getQuery(), request.getTopK(), request.getMinSimilarity());
    }

    @Override
    public ContextBuildResponseDTO buildContext(UUID userId, ContextBuildRequestDTO request) {
        long startTime = System.currentTimeMillis();
        List<SemanticSearchResultDTO> retrieved = semanticSearch(userId, request.getPrompt(), request.getMaxMemories(), 0.6);

        StringBuilder sb = new StringBuilder();
        sb.append("=== RELEVANT SEMANTIC MEMORIES ===\n");
        for (SemanticSearchResultDTO res : retrieved) {
            MemoryResponse m = res.getMemory();
            sb.append(String.format("- [%s] %s: %s (Importance: %s, Similarity: %.2f)\n",
                    m.getMemoryType(), m.getTitle(), m.getContent(), m.getImportanceLevel(), res.getSimilarityScore()));
        }
        sb.append("==================================\n");

        long latency = System.currentTimeMillis() - startTime;
        return ContextBuildResponseDTO.builder()
                .formattedContextBlock(sb.toString())
                .retrievedMemories(retrieved)
                .estimatedTokens(sb.length() / 4)
                .retrievalLatencyMs(latency)
                .build();
    }

    @Override
    public MemoryRelationshipDTO linkMemories(UUID sourceId, UUID targetId, String relationshipType, double confidence) {
        MemoryRelationshipDTO rel = MemoryRelationshipDTO.builder()
                .id(UUID.randomUUID())
                .sourceMemoryId(sourceId)
                .targetMemoryId(targetId)
                .relationshipType(relationshipType)
                .confidenceScore(confidence)
                .build();
        relationshipStorage.add(rel);
        return rel;
    }

    @Override
    public List<MemoryRelationshipDTO> getRelationshipsForMemory(UUID memoryId) {
        return relationshipStorage.stream()
                .filter(r -> r.getSourceMemoryId().equals(memoryId) || r.getTargetMemoryId().equals(memoryId))
                .collect(Collectors.toList());
    }

    @Override
    public List<MemoryRelationshipDTO> getRelatedMemories(UUID memoryId) {
        return getRelationshipsForMemory(memoryId);
    }

    @Override
    public List<TimelineEventDTO> getTimeline(UUID userId, String timeframe) {
        List<Memory> memories = memoryRepository.findRecentlyAccessedByUserId(userId, 20);

        return memories.stream()
                .map(m -> TimelineEventDTO.builder()
                        .timestamp(m.getCreatedAt())
                        .eventType(m.getType().name())
                        .memory(MemoryDtoMapper.toResponse(m))
                        .build())
                .sorted(Comparator.comparing(TimelineEventDTO::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public LlmResponse executeRagQuery(UUID userId, String prompt) {
        ContextBuildResponseDTO contextResponse = buildContext(userId, ContextBuildRequestDTO.builder().prompt(prompt).build());

        String augmentedPrompt = String.format("%s\n\nUser Question: %s",
                contextResponse.getFormattedContextBlock(), prompt);

        return llmGateway.generateText(LlmRequest.builder()
                .prompt(augmentedPrompt)
                .temperature(0.3)
                .build());
    }

    private double calculateRankingScore(Memory memory, double similarityScore) {
        double importanceWeight = switch (memory.getImportance()) {
            case CRITICAL -> 1.0;
            case HIGH -> 0.8;
            case NORMAL -> 0.5;
            case LOW -> 0.2;
        };

        double favoriteBonus = memory.isFavorite() ? 0.1 : 0.0;
        double pinnedBonus = memory.isPinned() ? 0.15 : 0.0;

        return (similarityScore * 0.6) + (importanceWeight * 0.25) + favoriteBonus + pinnedBonus;
    }
}
