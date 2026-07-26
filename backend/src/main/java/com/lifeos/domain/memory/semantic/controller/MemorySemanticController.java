package com.lifeos.domain.memory.semantic.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.memory.semantic.dto.*;
import com.lifeos.domain.memory.semantic.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Semantic Memory Engine, Vector Search & RAG Pipeline.
 */
@RestController
@RequestMapping("/api/v1/memory")
@RequiredArgsConstructor
@Tag(name = "Semantic Memory & RAG Engine", description = "Endpoints for pgvector semantic search, hybrid search, context builder, relationship graph, and timeline")
public class MemorySemanticController {

    private final MemorySemanticSearchService searchService;
    private final ContextBuilderService contextBuilderService;
    private final MemoryRelationshipService relationshipService;
    private final MemoryTimelineService timelineService;
    private final EmbeddingService embeddingService;

    @Data
    public static class SemanticSearchRequest {
        private String query;
        private int topK = 10;
        private double minSimilarity = 0.6;
    }

    @PostMapping("/search/semantic")
    @Operation(summary = "Semantic Vector Search", description = "Executes natural language semantic search using pgvector cosine similarity.")
    public ResponseEntity<ApiResponse<List<SemanticSearchResultDTO>>> semanticSearch(@RequestBody SemanticSearchRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(searchService.semanticSearch(userId, request.getQuery(), request.getTopK(), request.getMinSimilarity()));
    }

    @PostMapping("/search/hybrid")
    @Operation(summary = "Hybrid Search", description = "Combines keyword search, vector distance, metadata, and tags.")
    public ResponseEntity<ApiResponse<List<SemanticSearchResultDTO>>> hybridSearch(@RequestBody HybridSearchRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(searchService.hybridSearch(userId, request));
    }

    @PostMapping("/context")
    @Operation(summary = "Build Prompt Context", description = "Retrieves relevant memories, ranks, and formats optimized prompt context block for LLM.")
    public ResponseEntity<ApiResponse<ContextBuildResponseDTO>> buildContext(@RequestBody ContextBuildRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(contextBuilderService.buildContext(userId, request));
    }

    @GetMapping("/timeline")
    @Operation(summary = "Memory Timeline", description = "Retrieves chronological timeline view of memories.")
    public ResponseEntity<ApiResponse<List<TimelineEventDTO>>> getTimeline(@RequestParam(value = "timeframe", defaultValue = "all") String timeframe) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(timelineService.getTimeline(userId, timeframe));
    }

    @GetMapping("/relationships")
    @Operation(summary = "Memory Relationship Graph", description = "Retrieves relationship links for a specific memory.")
    public ResponseEntity<ApiResponse<List<MemoryRelationshipDTO>>> getRelationships(@RequestParam UUID memoryId) {
        return ResponseBuilder.ok(relationshipService.getRelationshipsForMemory(memoryId));
    }

    @GetMapping("/{id}/related")
    @Operation(summary = "Get Related Memories", description = "Retrieves related memories from memory relationship graph.")
    public ResponseEntity<ApiResponse<List<MemoryRelationshipDTO>>> getRelatedMemories(@PathVariable UUID id) {
        return ResponseBuilder.ok(relationshipService.getRelatedMemories(id));
    }

    @PostMapping("/reindex")
    @Operation(summary = "Reindex Vector Embeddings", description = "Triggers background re-indexing of all user memories.")
    public ResponseEntity<ApiResponse<Void>> reindexAll() {
        UUID userId = SecurityUtils.getCurrentUserId();
        embeddingService.reindexAllMemories(userId);
        return ResponseBuilder.ok(null, "Re-indexing initiated successfully");
    }

    @PostMapping("/regenerate-embeddings")
    @Operation(summary = "Regenerate Embeddings", description = "Forces re-generation of vector embeddings for selected memory.")
    public ResponseEntity<ApiResponse<Void>> regenerateEmbeddings(@RequestParam UUID memoryId) {
        embeddingService.indexMemoryEmbedding(memoryId, "Memory content text");
        return ResponseBuilder.ok(null, "Embeddings regenerated successfully");
    }
}
