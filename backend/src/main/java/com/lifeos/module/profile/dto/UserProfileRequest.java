package com.lifeos.module.profile.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * Record DTO for creating/updating user profile.
 */
public record UserProfileRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        String displayName,
        String avatarUrl,
        String timeZone,
        String locale,
        LocalDate dateOfBirth,
        String bio
) {
}
