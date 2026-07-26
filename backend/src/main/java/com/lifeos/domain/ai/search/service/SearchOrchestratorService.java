package com.lifeos.domain.ai.search.service;

import com.lifeos.domain.ai.search.dto.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Hybrid Search Orchestration & Intent Analysis.
 */
public interface SearchOrchestratorService {

    SearchResultResponseDTO executeSearch(UUID userId, SearchRequestDTO request);

    List<SearchSuggestionDTO> getSearchSuggestions(UUID userId, String queryPrefix);

    List<String> getSearchProviders();
}
