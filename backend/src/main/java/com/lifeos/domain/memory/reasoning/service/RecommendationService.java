package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.RecommendationDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Personalized Recommendations.
 */
public interface RecommendationService {

    List<RecommendationDTO> getRecommendations(UUID userId);
}
