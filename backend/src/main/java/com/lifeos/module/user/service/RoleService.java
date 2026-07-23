package com.lifeos.module.user.service;

import com.lifeos.module.user.dto.PermissionResponse;
import com.lifeos.module.user.dto.RoleResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Roles and Permissions Management.
 */
public interface RoleService {

    List<RoleResponse> getAllRoles();

    RoleResponse getRoleById(UUID id);

    List<PermissionResponse> getAllPermissions();
}
