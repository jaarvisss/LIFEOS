package com.lifeos.module.auth.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.module.auth.dto.AuthResponse;
import com.lifeos.module.auth.dto.LoginRequest;
import com.lifeos.module.auth.dto.RegisterRequest;
import com.lifeos.module.auth.dto.TokenRefreshRequest;
import com.lifeos.module.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for User Registration, Authentication, and Token Operations.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration, authentication, login, and token refresh")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Creates a new user account and returns JWT access & refresh tokens.")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = authService.register(request);
        return ResponseBuilder.created(authResponse, "User registered successfully");
    }

    @PostMapping("/login")
    @Operation(summary = "Login User", description = "Authenticates user credentials and returns JWT access & refresh tokens.")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = authService.login(request);
        return ResponseBuilder.ok(authResponse, "Login successful");
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh JWT Access Token", description = "Issues a new JWT access token using a valid refresh token.")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        AuthResponse authResponse = authService.refreshToken(request);
        return ResponseBuilder.ok(authResponse, "Token refreshed successfully");
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout User", description = "Revokes the active refresh token.")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody(required = false) TokenRefreshRequest request) {
        if (request != null) {
            authService.logout(request.refreshToken());
        }
        return ResponseBuilder.ok(null, "Logout successful");
    }
}
