package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.ReasoningResponseDTO;

import java.util.UUID;

/**
 * Service interface for Multi-step AI Reasoning.
 */
public interface ReasoningService {

    ReasoningResponseDTO reasonAboutPrompt(UUID userId, String prompt);
}
