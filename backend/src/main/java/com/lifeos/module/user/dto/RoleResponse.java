package com.lifeos.module.user.dto;

import java.util.List;
import java.util.UUID;

/**
 * Record DTO for Role API response payload.
 */
public record RoleResponse(
        UUID id,
        String roleCode,
        String roleName,
        String description,
        Boolean isSystemRole,
        List<PermissionResponse> permissions
) {
}
