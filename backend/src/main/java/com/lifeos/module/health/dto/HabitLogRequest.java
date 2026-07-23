package com.lifeos.module.health.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Habit execution logging request.
 */
public record HabitLogRequest(
        @NotNull(message = "Habit ID is required")
        UUID habitId,

        @NotNull(message = "Completed date is required")
        LocalDate completedDate,

        String notes
) {
}
