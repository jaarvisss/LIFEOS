package com.lifeos.module.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Record DTO for Subtask creation request.
 */
public record SubtaskRequest(
        @NotNull(message = "Task ID is required")
        UUID taskId,

        @NotBlank(message = "Subtask title is required")
        String title,

        Boolean isCompleted,
        Integer sequenceOrder
) {
}
