package com.lifeos.domain.ai.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Individual Workflow Step Execution.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStepDTO {
    private UUID id;
    private int stepNumber;
    private String stepName;
    private String stepType; // REASONING, TOOL_EXECUTION, CONDITION, APPROVAL, NOTIFICATION
    private String stepStatus; // PENDING, RUNNING, COMPLETED, FAILED, SKIPPED
    private String executionResult;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}
