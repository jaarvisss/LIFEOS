package com.lifeos.module.health.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Workout response.
 */
public record WorkoutResponse(
        UUID id,
        UUID userId,
        String workoutType,
        Integer durationMinutes,
        Integer caloriesBurned,
        Instant loggedAt
) {
}
