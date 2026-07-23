package com.lifeos.module.user.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Record DTO for User API response payload.
 */
public record UserResponse(
        UUID id,
        String email,
        String userType,
        String status,
        Boolean emailVerified,
        Boolean mfaEnabled,
        List<String> roles,
        Instant createdAt,
        Instant updatedAt
) {
}
