package com.lifeos.module.project.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Project response payload.
 */
public record ProjectResponse(
        UUID id,
        UUID userId,
        UUID goalId,
        String name,
        String description,
        String status,
        String colorCode,
        LocalDate startDate,
        LocalDate targetEndDate,
        Instant createdAt,
        Instant updatedAt
) {
}
