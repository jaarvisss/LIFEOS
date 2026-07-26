package com.lifeos.domain.memory.intelligence.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.domain.memory.intelligence.dto.*;
import com.lifeos.domain.memory.intelligence.gateway.LlmGateway;
import com.lifeos.domain.memory.intelligence.gateway.LlmRequest;
import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;
import com.lifeos.domain.memory.intelligence.prompt.PromptTemplateRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Enterprise implementation of MemoryIntelligenceService using Gemini LLM Gateway.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryIntelligenceServiceImpl implements MemoryIntelligenceService {

    private final LlmGateway llmGateway;
    private final ObjectMapper objectMapper;

    @Override
    public ExtractedMemoryDTO extractMemory(String rawText) {
        return analyzeMemory(rawText);
    }

    @Override
    public Map<String, Object> classifyMemory(String rawText) {
        String prompt = PromptTemplateRegistry.V1_CLASSIFICATION.formatted(rawText);
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.2).build());
        return parseJsonToMap(response.getContent());
    }

    @Override
    public Map<String, Object> summarizeMemory(String rawText) {
        String prompt = PromptTemplateRegistry.V1_SUMMARIZATION.formatted(rawText);
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.3).build());
        return parseJsonToMap(response.getContent());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> generateTags(String rawText) {
        String prompt = PromptTemplateRegistry.V1_TAGS.formatted(rawText);
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.3).build());
        Map<String, Object> map = parseJsonToMap(response.getContent());
        if (map.containsKey("tags") && map.get("tags") instanceof List) {
            return (List<String>) map.get("tags");
        }
        return List.of("memory", "note", "captured");
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ExtractedEntityDTO> extractEntities(String rawText) {
        String prompt = PromptTemplateRegistry.V1_ENTITIES.formatted(rawText);
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.2).build());
        Map<String, Object> map = parseJsonToMap(response.getContent());

        if (map.containsKey("entities") && map.get("entities") instanceof List) {
            List<Map<String, Object>> entityList = (List<Map<String, Object>>) map.get("entities");
            return entityList.stream()
                    .map(e -> ExtractedEntityDTO.builder()
                            .type((String) e.get("type"))
                            .value((String) e.get("value"))
                            .confidence(e.get("confidence") != null ? ((Number) e.get("confidence")).doubleValue() : 0.9)
                            .build())
                    .toList();
        }
        return List.of();
    }

    @Override
    public ExtractedMemoryDTO analyzeMemory(String rawText) {
        String prompt = PromptTemplateRegistry.V1_MEMORY_EXTRACTION.formatted(rawText);
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.2).build());
        
        try {
            return objectMapper.readValue(response.getContent(), ExtractedMemoryDTO.class);
        } catch (Exception e) {
            log.warn("Failed to deserialize LLM JSON directly, creating fallback ExtractedMemoryDTO: {}", e.getMessage());
            return ExtractedMemoryDTO.builder()
                    .title("Note: " + (rawText.length() > 30 ? rawText.substring(0, 30) + "..." : rawText))
                    .summary(rawText)
                    .content(rawText)
                    .memoryType("NOTE")
                    .importanceLevel("NORMAL")
                    .importanceScore(50)
                    .importanceReasoning("General captured note")
                    .confidence(0.9)
                    .language("en")
                    .tags(List.of("captured", "note"))
                    .source("MANUAL")
                    .build();
        }
    }

    @Override
    public DuplicateCheckResultDTO checkDuplicate(UUID userId, String rawText) {
        String prompt = PromptTemplateRegistry.V1_DUPLICATE_CHECK.formatted(rawText, "Existing user memories context");
        LlmResponse response = llmGateway.generateText(LlmRequest.builder().prompt(prompt).temperature(0.1).build());

        try {
            return objectMapper.readValue(response.getContent(), DuplicateCheckResultDTO.class);
        } catch (Exception e) {
            return DuplicateCheckResultDTO.builder()
                    .status("UNIQUE")
                    .similarityScore(0.1)
                    .explanation("Unique memory entry")
                    .build();
        }
    }

    @Override
    public Map<String, Object> enrichMemory(String rawText) {
        ExtractedMemoryDTO extracted = analyzeMemory(rawText);
        return Map.of(
                "extracted", extracted,
                "relatedTopics", extracted.getRelatedTopics() != null ? extracted.getRelatedTopics() : List.of("General"),
                "suggestedCategory", extracted.getSuggestedCategory() != null ? extracted.getSuggestedCategory() : "General",
                "suggestedPriority", extracted.getSuggestedPriority() != null ? extracted.getSuggestedPriority() : "NORMAL"
        );
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parseJsonToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Exception e) {
            log.warn("Failed to parse JSON string: {}", e.getMessage());
            return Map.of("result", json);
        }
    }
}
