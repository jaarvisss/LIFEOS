package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for Individual Action Step in a Multi-Tool Workflow.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionStepDTO {
    private int stepNumber;
    private String toolName;
    private String stepDescription;
    private Map<String, Object> parameters;
    private boolean requiresConfirmation;
}
