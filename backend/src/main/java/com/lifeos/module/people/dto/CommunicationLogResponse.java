package com.lifeos.module.people.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Communication Log response payload.
 */
public record CommunicationLogResponse(
        UUID id,
        UUID userId,
        UUID contactId,
        String channel,
        String summary,
        Instant interactionDate,
        Instant createdAt
) {
}
