package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for Tool Execution Request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionRequestDTO {
    private String toolName;
    private Map<String, Object> parameters;
    private boolean confirmExecution = false;
}
