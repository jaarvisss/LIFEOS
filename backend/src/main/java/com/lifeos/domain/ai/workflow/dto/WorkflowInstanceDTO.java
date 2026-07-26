package com.lifeos.domain.ai.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for Active or Completed Workflow Instance.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowInstanceDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private String currentState; // CREATED, PLANNED, RUNNING, PAUSED, WAITING_APPROVAL, COMPLETED, FAILED
    private int progressPercentage;
    private List<WorkflowStepDTO> steps;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;
}
