package com.lifeos.module.goal.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Record DTO for Goal creation payload.
 */
public record GoalRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,
        String category,
        String status,
        LocalDate targetDate
) {
}
