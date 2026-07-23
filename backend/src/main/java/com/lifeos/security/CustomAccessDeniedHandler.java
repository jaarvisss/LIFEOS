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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom AccessDeniedHandler returning structured JSON 403 Forbidden errors.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        log.warn("Access denied for request to {}: {}", request.getRequestURI(), accessDeniedException.getMessage());

        String correlationId = MDC.get(MdcLoggingFilter.MDC_CORRELATION_ID_KEY);

        ApiError apiError = ApiError.of(
                ErrorCode.FORBIDDEN,
                "You do not have permission to access this resource",
                request.getRequestURI(),
                correlationId
        );

        ApiResponse<Void> apiResponse = ApiResponse.error(apiError, HttpServletResponse.SC_FORBIDDEN);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        objectMapper.writeValue(response.getOutputStream(), apiResponse);
    }
}
