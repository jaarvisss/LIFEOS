package com.lifeos.module.task.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for updating task status.
 */
public record TaskStatusUpdateRequest(
        @NotBlank(message = "Task status is required")
        String status
) {
}
