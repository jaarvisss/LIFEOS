package com.lifeos.domain.ai.tool.service;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;

import java.util.UUID;

/**
 * Service interface for Tool Execution Security and Confirmation Validation.
 */
public interface ToolSecurityService {

    boolean authorizeExecution(UUID userId, ToolExecutionRequestDTO request);
}
