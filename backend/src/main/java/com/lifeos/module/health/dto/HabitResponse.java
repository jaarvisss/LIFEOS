package com.lifeos.module.health.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Habit response payload.
 */
public record HabitResponse(
        UUID id,
        UUID userId,
        String name,
        String description,
        String targetFrequency,
        Integer currentStreakCount,
        Integer bestStreakCount,
        Instant createdAt,
        Instant updatedAt
) {
}
