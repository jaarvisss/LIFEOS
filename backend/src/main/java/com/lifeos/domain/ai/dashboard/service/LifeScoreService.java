package com.lifeos.domain.ai.dashboard.service;

import com.lifeos.domain.ai.dashboard.dto.LifeScoreDTO;

import java.util.UUID;

/**
 * Service interface for AI Life Operating Score Calculations.
 */
public interface LifeScoreService {

    LifeScoreDTO calculateLifeScore(UUID userId);
}
