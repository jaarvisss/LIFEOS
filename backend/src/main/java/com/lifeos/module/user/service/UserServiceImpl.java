package com.lifeos.module.user.service;

import com.lifeos.common.exception.BusinessException;
import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.auth.entity.UserCredentialsEntity;
import com.lifeos.module.auth.repository.UserCredentialsRepository;
import com.lifeos.module.user.dto.ChangePasswordRequest;
import com.lifeos.module.user.dto.UserResponse;
import com.lifeos.module.user.dto.UserUpdateRequest;
import com.lifeos.module.user.entity.UserEntity;
import com.lifeos.module.user.mapper.UserMapper;
import com.lifeos.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of UserService interface.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        UserEntity user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        UUID userId = SecurityUtils.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("No authenticated user in security context");
        }
        return getUserById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserResponse> getAllUsers(PaginationRequest paginationRequest) {
        Page<UserEntity> page = userRepository.findByIsDeletedFalse(paginationRequest.toPageable());
        return PagedResponse.from(page.map(userMapper::toDto));
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UserUpdateRequest request) {
        UserEntity user = userRepository.findById(id)
                .filter(u -> !u.getIsDeleted())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (request.email() != null && !request.email().equalsIgnoreCase(user.getEmail())) {
            user.setEmail(request.email().toLowerCase());
        }
        if (request.status() != null) {
            user.setStatus(request.status());
        }
        if (request.mfaEnabled() != null) {
            user.setMfaEnabled(request.mfaEnabled());
        }

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void changePassword(UUID id, ChangePasswordRequest request) {
        UserCredentialsEntity credentials = userCredentialsRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Credentials", "userId", id));

        if (!passwordEncoder.matches(request.currentPassword(), credentials.getPasswordHash())) {
            throw new BusinessException("Current password does not match");
        }

        credentials.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        credentials.setPasswordChangedAt(Instant.now());
        userCredentialsRepository.save(credentials);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.markDeleted();
        userRepository.save(user);
    }
}
