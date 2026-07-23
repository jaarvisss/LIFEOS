package com.lifeos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifeos.common.enums.ErrorCode;
import com.lifeos.common.filter.MdcLoggingFilter;
import com.lifeos.common.response.ApiError;
import com.lifeos.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom AuthenticationEntryPoint returning structured JSON 401 Unauthorized errors.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        log.warn("Unauthorized access attempt to {}: {}", request.getRequestURI(), authException.getMessage());

        String correlationId = MDC.get(MdcLoggingFilter.MDC_CORRELATION_ID_KEY);

        ApiError apiError = ApiError.of(
                ErrorCode.UNAUTHORIZED,
                "Full authentication is required to access this resource",
                request.getRequestURI(),
                correlationId
        );

        ApiResponse<Void> apiResponse = ApiResponse.error(apiError, HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
