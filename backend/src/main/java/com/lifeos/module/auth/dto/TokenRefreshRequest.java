package com.lifeos.module.auth.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for token refresh payload.
 */
public record TokenRefreshRequest(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
}
