package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.intelligence.gateway.LlmResponse;

import java.util.UUID;

/**
 * Service interface for the end-to-end RAG Pipeline.
 */
public interface RagPipelineService {

    LlmResponse executeRagQuery(UUID userId, String prompt);
}
