package com.lifeos.module.user.dto;

import java.util.UUID;

/**
 * Record DTO for Permission API response payload.
 */
public record PermissionResponse(
        UUID id,
        String permissionCode,
        String resource,
        String action,
        String description
) {
}
