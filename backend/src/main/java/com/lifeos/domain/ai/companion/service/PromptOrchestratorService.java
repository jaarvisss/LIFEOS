package com.lifeos.domain.ai.companion.service;

import java.util.UUID;

/**
 * Service interface for Prompt Orchestration.
 */
public interface PromptOrchestratorService {

    String assembleOrchestratedPrompt(UUID userId, String userMessage, String intentType);
}
