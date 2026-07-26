package com.lifeos.domain.ai.tool.tools;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;

import java.util.UUID;

/**
 * Interface contract for all executable LifeOS Tools.
 */
public interface ExecutableTool {

    String getToolName();

    String getDescription();

    String getCategory();

    boolean requiresConfirmation();

    ToolExecutionResultDTO execute(UUID userId, ToolExecutionRequestDTO request);

    boolean validate(UUID userId, ToolExecutionRequestDTO request);

    Object preview(UUID userId, ToolExecutionRequestDTO request);

    void rollback(UUID executionId);
}
