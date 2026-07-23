package com.lifeos.module.profile.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Record DTO for User Profile response.
 */
public record UserProfileResponse(
        UUID id,
        UUID userId,
        String firstName,
        String lastName,
        String displayName,
        String avatarUrl,
        String timeZone,
        String locale,
        LocalDate dateOfBirth,
        String bio,
        Instant createdAt,
        Instant updatedAt
) {
}
