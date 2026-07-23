package com.lifeos.module.ai.service;

import com.lifeos.module.ai.dto.AiSuggestionResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for AI Suggestions & Recommendations Engine.
 */
public interface AiSuggestionService {

    List<AiSuggestionResponse> getActiveSuggestions(UUID userId);

    AiSuggestionResponse respondToSuggestion(UUID userId, UUID suggestionId, boolean accept);
}
