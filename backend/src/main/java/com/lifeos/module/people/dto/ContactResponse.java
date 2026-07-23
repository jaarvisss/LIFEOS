package com.lifeos.module.people.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for Contact API response.
 */
public record ContactResponse(
        UUID id,
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String company,
        String jobTitle,
        LocalDate dateOfBirth,
        String notes,
        Instant createdAt,
        Instant updatedAt
) {
}
