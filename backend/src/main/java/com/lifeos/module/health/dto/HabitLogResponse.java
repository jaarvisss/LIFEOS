package com.lifeos.module.health.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Habit Log response.
 */
public record HabitLogResponse(
        UUID id,
        UUID habitId,
        LocalDate completedDate,
        String notes,
        Instant createdAt
) {
}
