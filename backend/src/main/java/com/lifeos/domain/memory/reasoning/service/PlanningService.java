package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.PlanResponseDTO;

import java.util.UUID;

/**
 * Service interface for Adaptive AI Planning.
 */
public interface PlanningService {

    PlanResponseDTO generatePlan(UUID userId, String timeframe, String goalFocus);
}
