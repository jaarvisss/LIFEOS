package com.lifeos.domain.ai.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Planned DAG Execution Strategy.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowExecutionPlanDTO {
    private String workflowName;
    private String workflowType;
    private List<WorkflowStepDTO> steps;
    private double confidenceScore;
    private int estimatedDurationSeconds;
}
