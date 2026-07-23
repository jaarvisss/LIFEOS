package com.lifeos.module.user.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.module.user.dto.PermissionResponse;
import com.lifeos.module.user.dto.RoleResponse;
import com.lifeos.module.user.entity.RoleEntity;
import com.lifeos.module.user.mapper.RoleMapper;
import com.lifeos.module.user.repository.PermissionRepository;
import com.lifeos.module.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of RoleService interface.
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleMapper.toDtoList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(UUID id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return roleMapper.toDto(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {
        return roleMapper.toPermissionDtoList(permissionRepository.findAll());
    }
}
