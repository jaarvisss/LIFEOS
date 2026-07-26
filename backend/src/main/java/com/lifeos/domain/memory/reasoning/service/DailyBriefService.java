package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.DailyBriefResponseDTO;

import java.util.UUID;

/**
 * Service interface for Daily AI Briefing generation.
 */
public interface DailyBriefService {

    DailyBriefResponseDTO generateDailyBrief(UUID userId);
}
