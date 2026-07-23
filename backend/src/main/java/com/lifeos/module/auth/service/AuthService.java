package com.lifeos.module.auth.service;

import com.lifeos.module.auth.dto.AuthResponse;
import com.lifeos.module.auth.dto.LoginRequest;
import com.lifeos.module.auth.dto.RegisterRequest;
import com.lifeos.module.auth.dto.TokenRefreshRequest;

/**
 * Service Interface for Authentication Operations.
 */
public interface AuthService {

    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(TokenRefreshRequest refreshRequest);

    void logout(String refreshToken);
}
