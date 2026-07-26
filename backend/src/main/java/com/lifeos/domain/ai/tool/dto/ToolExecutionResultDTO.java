package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Tool Execution Output.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionResultDTO {
    private UUID executionId;
    private String toolName;
    private String status; // SUCCESS, FAILED, PENDING_CONFIRMATION
    private Object resultData;
    private long latencyMs;
    private String errorMessage;
    private String memoryUpdated;
}
