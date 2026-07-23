package com.lifeos.common.util;

import com.lifeos.security.UserPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUtilsTest {

    @BeforeEach
    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should retrieve authenticated user ID and principal from SecurityContext")
    void testSecurityUtilsWithAuthenticatedUser() {
        UUID userId = UUID.randomUUID();
        UserPrincipal principal = UserPrincipal.create(userId, "admin@lifeos.io", "admin", "pwd", List.of("ADMIN"), List.of());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        assertTrue(SecurityUtils.isAuthenticated());
        assertEquals(userId, SecurityUtils.getCurrentUserId());
        assertEquals("admin", SecurityUtils.getCurrentUsername());
        assertTrue(SecurityUtils.getCurrentUserPrincipal().isPresent());
    }

    @Test
    @DisplayName("Should return null and false when SecurityContext is empty")
    void testSecurityUtilsWithUnauthenticatedUser() {
        assertFalse(SecurityUtils.isAuthenticated());
        assertNull(SecurityUtils.getCurrentUserId());
        assertNull(SecurityUtils.getCurrentUsername());
        assertTrue(SecurityUtils.getCurrentUserPrincipal().isEmpty());
    }
}
