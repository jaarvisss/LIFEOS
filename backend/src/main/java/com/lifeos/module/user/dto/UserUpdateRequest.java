package com.lifeos.module.user.dto;

import jakarta.validation.constraints.Email;

/**
 * Record DTO for updating user details.
 */
public record UserUpdateRequest(
        @Email(message = "Email must be valid")
        String email,
        String status,
        Boolean mfaEnabled
) {
}
