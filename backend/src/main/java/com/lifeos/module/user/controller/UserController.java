package com.lifeos.module.user.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.module.user.dto.ChangePasswordRequest;
import com.lifeos.module.user.dto.UserResponse;
import com.lifeos.module.user.dto.UserUpdateRequest;
import com.lifeos.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for User Account Lifecycle Management.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "Endpoints for user profile retrieval, account updates, and password changes")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Retrieves profile information for the authenticated user.")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        return ResponseBuilder.ok(userService.getCurrentUser());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(summary = "Get User by ID", description = "Retrieves user details by unique identifier.")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        return ResponseBuilder.ok(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List Users", description = "Retrieves paginated list of active users (Admin only).")
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getAllUsers(@Valid PaginationRequest paginationRequest) {
        return ResponseBuilder.ok(userService.getAllUsers(paginationRequest));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(summary = "Update User", description = "Updates user account attributes.")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseBuilder.ok(userService.updateUser(id, request), "User updated successfully");
    }

    @PostMapping("/{id}/change-password")
    @PreAuthorize("authentication.principal.id == #id")
    @Operation(summary = "Change Password", description = "Changes the user password.")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable UUID id,
            @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseBuilder.ok(null, "Password updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    @Operation(summary = "Delete User", description = "Soft-deletes a user account.")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseBuilder.ok(null, "User deleted successfully");
    }
}
