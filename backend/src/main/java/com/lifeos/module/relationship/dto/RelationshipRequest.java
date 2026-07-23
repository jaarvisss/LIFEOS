package com.lifeos.module.relationship.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Record DTO for creating/updating Relationship payload.
 */
public record RelationshipRequest(
        @NotNull(message = "Contact ID is required")
        UUID contactId,

        @NotBlank(message = "Relationship type is required")
        String relationshipType,

        @Min(value = 1, message = "Closeness level must be at least 1")
        @Max(value = 10, message = "Closeness level cannot exceed 10")
        Integer closenessLevel
) {
}
