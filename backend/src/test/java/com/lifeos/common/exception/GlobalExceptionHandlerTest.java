package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;
import com.lifeos.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/v1/test");
    }

    @Test
    @DisplayName("Should handle BusinessException and return 400 Bad Request response")
    void testHandleBusinessException() {
        BusinessException ex = new BusinessException("Business validation error");

        ResponseEntity<ApiResponse<Void>> response = globalExceptionHandler.handleBaseException(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Business validation error", response.getBody().getMessage());
        assertEquals("/api/v1/test", response.getBody().getError().getPath());
    }

    @Test
    @DisplayName("Should handle ResourceNotFoundException and return 404 Not Found response")
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("User", "id", "123");

        ResponseEntity<ApiResponse<Void>> response = globalExceptionHandler.handleBaseException(ex, request);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorCode.RESOURCE_NOT_FOUND.getCode(), response.getBody().getError().getCode());
    }
}
