package com.lifeos.module.relationship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Milestone creation payload.
 */
public record MilestoneRequest(
        @NotNull(message = "Relationship ID is required")
        UUID relationshipId,

        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Event date is required")
        LocalDate eventDate,

        String description,
        Boolean recurringAnnual
) {
}
