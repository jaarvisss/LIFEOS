package com.lifeos.security;

import com.lifeos.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private JwtProperties jwtProperties;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecretKey("dGVzdF9zZWNyZXRfa2V5X2Zvcl9saWZlb3NfYmFja2VuZF9lbnRlcnByaXNlX3NlY3VyaXR5X2ZyYW1ld29ya18yMDI2X2p3dF90b2tlbl9zcmluZ19ib290XzM1");
        jwtProperties.setExpirationMs(3600000);
        jwtProperties.setRefreshExpirationMs(86400000);
        jwtProperties.setIssuer("LifeOS-Test");
        jwtProperties.setAudience("LifeOS-Audience");

        jwtTokenProvider = new JwtTokenProvider(jwtProperties);
    }

    @Test
    @DisplayName("Should generate valid JWT access token and extract claims correctly")
    void testGenerateAndValidateAccessToken() {
        UUID userId = UUID.randomUUID();
        UserPrincipal userPrincipal = UserPrincipal.create(
                userId, "user@lifeos.io", "testuser", "password", List.of("USER"), List.of("READ_DATA")
        );

        String token = jwtTokenProvider.generateAccessToken(userPrincipal);

        assertNotNull(token);
        assertTrue(jwtTokenProvider.validateToken(token));

        UUID extractedUserId = jwtTokenProvider.getUserIdFromToken(token);
        assertEquals(userId, extractedUserId);

        String extractedEmail = jwtTokenProvider.getEmailFromToken(token);
        assertEquals("user@lifeos.io", extractedEmail);

        List<String> roles = jwtTokenProvider.getRolesFromToken(token);
        assertNotNull(roles);
        assertTrue(roles.contains("USER"));
    }

    @Test
    @DisplayName("Should generate valid refresh token")
    void testGenerateRefreshToken() {
        UUID userId = UUID.randomUUID();
        UserPrincipal userPrincipal = UserPrincipal.create(
                userId, "user@lifeos.io", "testuser", "password", List.of("USER"), List.of()
        );

        String refreshToken = jwtTokenProvider.generateRefreshToken(userPrincipal);

        assertNotNull(refreshToken);
        assertTrue(jwtTokenProvider.validateToken(refreshToken));

        Claims claims = jwtTokenProvider.getClaimsFromToken(refreshToken);
        assertEquals(SecurityConstants.TOKEN_TYPE_REFRESH, claims.get(SecurityConstants.CLAIM_TOKEN_TYPE));
    }

    @Test
    @DisplayName("Should return false for invalid token")
    void testInvalidToken() {
        assertFalse(jwtTokenProvider.validateToken("invalid.jwt.token"));
    }
}
