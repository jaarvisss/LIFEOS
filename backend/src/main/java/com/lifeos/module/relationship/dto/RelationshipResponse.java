package com.lifeos.module.relationship.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Relationship response payload.
 */
public record RelationshipResponse(
        UUID id,
        UUID userId,
        UUID contactId,
        String relationshipType,
        Integer closenessLevel,
        Instant createdAt,
        Instant updatedAt
) {
}
