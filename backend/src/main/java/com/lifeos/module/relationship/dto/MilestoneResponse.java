package com.lifeos.module.relationship.dto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Milestone response.
 */
public record MilestoneResponse(
        UUID id,
        UUID relationshipId,
        String title,
        LocalDate eventDate,
        String description,
        Boolean recurringAnnual
) {
}
