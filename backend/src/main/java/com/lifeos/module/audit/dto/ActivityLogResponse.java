package com.lifeos.module.audit.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Activity Log response.
 */
public record ActivityLogResponse(
        UUID id,
        UUID userId,
        String activityType,
        String description,
        String metadataJsonb,
        Instant createdAt
) {
}
