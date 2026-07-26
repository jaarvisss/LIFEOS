package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.semantic.dto.HybridSearchRequestDTO;
import com.lifeos.domain.memory.semantic.dto.SemanticSearchResultDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Vector Semantic and Hybrid Search Engine.
 */
public interface MemorySemanticSearchService {

    List<SemanticSearchResultDTO> semanticSearch(UUID userId, String query, int topK, double minSimilarity);

    List<SemanticSearchResultDTO> hybridSearch(UUID userId, HybridSearchRequestDTO request);
}
