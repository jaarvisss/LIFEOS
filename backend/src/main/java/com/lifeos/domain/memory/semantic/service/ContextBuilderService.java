package com.lifeos.domain.memory.semantic.service;

import com.lifeos.domain.memory.semantic.dto.ContextBuildRequestDTO;
import com.lifeos.domain.memory.semantic.dto.ContextBuildResponseDTO;

import java.util.UUID;

/**
 * Service interface for building optimized prompt context for LLM.
 */
public interface ContextBuilderService {

    ContextBuildResponseDTO buildContext(UUID userId, ContextBuildRequestDTO request);
}
