package com.lifeos.module.goal.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Goal response payload.
 */
public record GoalResponse(
        UUID id,
        UUID userId,
        String title,
        String description,
        String category,
        String status,
        LocalDate targetDate,
        BigDecimal progressPercentage,
        Instant createdAt,
        Instant updatedAt
) {
}
