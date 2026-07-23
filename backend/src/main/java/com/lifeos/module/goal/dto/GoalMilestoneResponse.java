package com.lifeos.module.goal.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Goal Milestone response.
 */
public record GoalMilestoneResponse(
        UUID id,
        UUID goalId,
        String title,
        LocalDate targetDate,
        Boolean isCompleted,
        Instant completedAt
) {
}
