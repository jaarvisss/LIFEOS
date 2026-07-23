package com.lifeos.module.health.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Record DTO for Workout log request.
 */
public record WorkoutRequest(
        @NotBlank(message = "Workout type is required")
        String workoutType,

        @NotNull(message = "Duration in minutes is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationMinutes,

        Integer caloriesBurned,
        Instant loggedAt
) {
}
