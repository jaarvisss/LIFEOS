package com.lifeos.domain.ai.search.service;

import com.lifeos.domain.ai.search.dto.SearchAnswerResponseDTO;
import com.lifeos.domain.ai.search.dto.SearchRequestDTO;

import java.util.UUID;

/**
 * Service interface for Direct AI Answer Mode.
 */
public interface SearchAnswerService {

    SearchAnswerResponseDTO generateAiAnswer(UUID userId, SearchRequestDTO request);
}
