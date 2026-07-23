package com.lifeos.module.audit.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Entity History revision response.
 */
public record EntityHistoryResponse(
        UUID id,
        String entityName,
        UUID entityId,
        Long versionNumber,
        String snapshotJsonb,
        UUID changedBy,
        Instant createdAt
) {
}
