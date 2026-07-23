package com.lifeos.module.task.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Task response payload.
 */
public record TaskResponse(
        UUID id,
        UUID userId,
        UUID projectId,
        UUID goalId,
        String title,
        String description,
        String status,
        String priority,
        Instant dueDate,
        Integer estimatedDurationMinutes,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
