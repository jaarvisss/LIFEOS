package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.RecommendationDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Explainable Decision Engine.
 */
public interface DecisionEngineService {

    List<RecommendationDTO> evaluateAlternatives(UUID userId, String decisionContext, List<String> options);
}
