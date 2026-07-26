package com.lifeos.domain.ai.tool.service;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;

import java.util.UUID;

/**
 * Service interface for Tool Execution and Multi-Tool Workflows.
 */
public interface ToolExecutorService {

    ToolExecutionResultDTO executeTool(UUID userId, ToolExecutionRequestDTO request);
}
