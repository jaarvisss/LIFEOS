package com.lifeos.module.auth.dto;

import com.lifeos.module.user.dto.UserResponse;

/**
 * Record DTO for authentication success response.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresInMs,
        UserResponse user
) {
}
