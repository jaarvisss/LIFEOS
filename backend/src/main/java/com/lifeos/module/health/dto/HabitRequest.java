package com.lifeos.module.health.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Habit creation/update request.
 */
public record HabitRequest(
        @NotBlank(message = "Habit name is required")
        String name,

        String description,
        String targetFrequency
) {
}
