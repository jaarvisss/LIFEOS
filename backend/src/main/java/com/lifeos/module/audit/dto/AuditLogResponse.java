package com.lifeos.module.audit.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Audit Log response.
 */
public record AuditLogResponse(
        UUID id,
        UUID userId,
        String entityName,
        UUID entityId,
        String action,
        String diffJsonb,
        String ipAddress,
        Instant createdAt
) {
}
