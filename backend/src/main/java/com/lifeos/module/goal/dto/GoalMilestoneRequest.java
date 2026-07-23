package com.lifeos.module.goal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Goal Milestone request.
 */
public record GoalMilestoneRequest(
        @NotNull(message = "Goal ID is required")
        UUID goalId,

        @NotBlank(message = "Title is required")
        String title,

        LocalDate targetDate,
        Boolean isCompleted
) {
}
