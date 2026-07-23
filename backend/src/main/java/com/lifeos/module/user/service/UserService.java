package com.lifeos.module.user.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.user.dto.ChangePasswordRequest;
import com.lifeos.module.user.dto.UserResponse;
import com.lifeos.module.user.dto.UserUpdateRequest;

import java.util.UUID;

/**
 * Service Interface for User Management operations.
 */
public interface UserService {

    UserResponse getUserById(UUID id);

    UserResponse getCurrentUser();

    PagedResponse<UserResponse> getAllUsers(PaginationRequest paginationRequest);

    UserResponse updateUser(UUID id, UserUpdateRequest request);

    void changePassword(UUID id, ChangePasswordRequest request);

    void deleteUser(UUID id);
}
