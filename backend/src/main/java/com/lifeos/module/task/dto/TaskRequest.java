package com.lifeos.module.task.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Task creation/update payload.
 */
public record TaskRequest(
        @NotBlank(message = "Task title is required")
        String title,

        String description,
        UUID projectId,
        UUID goalId,
        String status,
        String priority,
        Instant dueDate,
        Integer estimatedDurationMinutes
) {
}
