package com.lifeos.module.auth.service;

import com.lifeos.common.exception.ConflictException;
import com.lifeos.common.exception.UnauthorizedException;
import com.lifeos.config.JwtProperties;
import com.lifeos.module.auth.dto.AuthResponse;
import com.lifeos.module.auth.dto.LoginRequest;
import com.lifeos.module.auth.dto.RegisterRequest;
import com.lifeos.module.auth.dto.TokenRefreshRequest;
import com.lifeos.module.auth.entity.RefreshTokenEntity;
import com.lifeos.module.auth.entity.UserCredentialsEntity;
import com.lifeos.module.auth.repository.RefreshTokenRepository;
import com.lifeos.module.auth.repository.UserCredentialsRepository;
import com.lifeos.module.user.entity.RoleEntity;
import com.lifeos.module.user.entity.UserEntity;
import com.lifeos.module.user.mapper.UserMapper;
import com.lifeos.module.user.repository.RoleRepository;
import com.lifeos.module.user.repository.UserRepository;
import com.lifeos.security.JwtTokenProvider;
import com.lifeos.security.SecurityConstants;
import com.lifeos.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collections;

import java.util.Set;

/**
 * Implementation of AuthService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String email = request.email().toLowerCase();
        if (userRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new ConflictException("User with email '" + email + "' already exists");
        }

        RoleEntity userRole = roleRepository.findByRoleCode("USER")
                .orElseGet(() -> roleRepository.save(RoleEntity.builder()
                        .roleCode("USER")
                        .roleName("Standard User")
                        .description("Default application user role")
                        .isSystemRole(true)
                        .build()));

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .userType("INDIVIDUAL")
                .status("ACTIVE")
                .emailVerified(true)
                .roles(Set.of(userRole))
                .build();

        userEntity = userRepository.save(userEntity);

        UserCredentialsEntity credentials = UserCredentialsEntity.builder()
                .userId(userEntity.getId())
                .passwordHash(passwordEncoder.encode(request.password()))
                .passwordAlgo("BCRYPT")
                .passwordChangedAt(Instant.now())
                .failedLoginAttempts(0)
                .build();

        userCredentialsRepository.save(credentials);

        UserPrincipal principal = UserPrincipal.create(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getEmail(),
                credentials.getPasswordHash(),
                Collections.singletonList("USER"),
                Collections.emptyList()
        );

        String accessToken = jwtTokenProvider.generateAccessToken(principal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal);

        saveRefreshToken(userEntity.getId(), refreshToken);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE,
                jwtProperties.getExpirationMs(),
                userMapper.toDto(userEntity)
        );
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        String email = request.email().toLowerCase();
        UserEntity user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        UserCredentialsEntity credentials = userCredentialsRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), credentials.getPasswordHash())) {
            credentials.setFailedLoginAttempts(credentials.getFailedLoginAttempts() + 1);
            userCredentialsRepository.save(credentials);
            throw new UnauthorizedException("Invalid email or password");
        }

        credentials.setFailedLoginAttempts(0);
        userCredentialsRepository.save(credentials);

        UserPrincipal principal = UserPrincipal.create(
                user.getId(),
                user.getEmail(),
                user.getEmail(),
                credentials.getPasswordHash(),
                userMapper.mapRolesToStringList(user.getRoles()),
                Collections.emptyList()
        );

        String accessToken = jwtTokenProvider.generateAccessToken(principal);
        String refreshToken = jwtTokenProvider.generateRefreshToken(principal);

        saveRefreshToken(user.getId(), refreshToken);

        return new AuthResponse(
                accessToken,
                refreshToken,
                SecurityConstants.TOKEN_TYPE,
                jwtProperties.getExpirationMs(),
                userMapper.toDto(user)
        );
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.refreshToken();
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new UnauthorizedException("Invalid or expired refresh token");
        }

        RefreshTokenEntity tokenEntity = refreshTokenRepository.findByTokenHashAndRevokedFalse(refreshToken)
                .orElseThrow(() -> new UnauthorizedException("Refresh token not found or revoked"));

        UserEntity user = userRepository.findById(tokenEntity.getUserId())
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        tokenEntity.setRevoked(true);
        tokenEntity.setRevokedAt(Instant.now());
        refreshTokenRepository.save(tokenEntity);

        UserPrincipal principal = UserPrincipal.create(
                user.getId(),
                user.getEmail(),
                user.getEmail(),
                null,
                userMapper.mapRolesToStringList(user.getRoles()),
                Collections.emptyList()
        );

        String newAccessToken = jwtTokenProvider.generateAccessToken(principal);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(principal);

        saveRefreshToken(user.getId(), newRefreshToken);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                SecurityConstants.TOKEN_TYPE,
                jwtProperties.getExpirationMs(),
                userMapper.toDto(user)
        );
    }

    @Override
    @Transactional
    public void logout(String refreshToken) {
        if (refreshToken != null) {
            refreshTokenRepository.findByTokenHashAndRevokedFalse(refreshToken)
                    .ifPresent(token -> {
                        token.setRevoked(true);
                        token.setRevokedAt(Instant.now());
                        refreshTokenRepository.save(token);
                    });
        }
    }

    private void saveRefreshToken(java.util.UUID userId, String refreshToken) {
        RefreshTokenEntity entity = RefreshTokenEntity.builder()
                .userId(userId)
                .tokenHash(refreshToken)
                .expiresAt(Instant.now().plusMillis(jwtProperties.getRefreshExpirationMs()))
                .revoked(false)
                .build();
        refreshTokenRepository.save(entity);
    }
}
