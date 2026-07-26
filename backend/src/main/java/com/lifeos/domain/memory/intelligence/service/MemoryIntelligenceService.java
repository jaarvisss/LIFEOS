package com.lifeos.domain.memory.intelligence.service;

import com.lifeos.domain.memory.intelligence.dto.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Service interface for AI-driven Memory Intelligence Engine.
 */
public interface MemoryIntelligenceService {

    ExtractedMemoryDTO extractMemory(String rawText);

    Map<String, Object> classifyMemory(String rawText);

    Map<String, Object> summarizeMemory(String rawText);

    List<String> generateTags(String rawText);

    List<ExtractedEntityDTO> extractEntities(String rawText);

    ExtractedMemoryDTO analyzeMemory(String rawText);

    DuplicateCheckResultDTO checkDuplicate(UUID userId, String rawText);

    Map<String, Object> enrichMemory(String rawText);
}
