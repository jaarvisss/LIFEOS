package com.lifeos.common.exception;

import com.lifeos.common.enums.ErrorCode;
import com.lifeos.common.filter.MdcLoggingFilter;
import com.lifeos.common.response.ApiError;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.FieldErrorItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enterprise Centralized Global Exception Handler Advice.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex, HttpServletRequest request) {
        log.error("BaseException occurred [Code: {}, Status: {}]: {}", ex.getErrorCode().getCode(), ex.getHttpStatus(), ex.getMessage(), ex);
        ApiError apiError = ApiError.builder()
                .code(ex.getErrorCode().getCode())
                .category(ex.getErrorCode().getCategory())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(ex.getTimestamp())
                .correlationId(getCorrelationId())
                .build();

        if (ex instanceof ValidationException valEx) {
            apiError.setFieldErrors(valEx.getFieldErrors());
        }

        return ResponseEntity.status(ex.getHttpStatus())
                .body(ApiResponse.error(apiError, ex.getHttpStatus().value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Malformed JSON request on {}: {}", request.getRequestURI(), ex.getMessage());
        ApiError apiError = ApiError.of(
                ErrorCode.MALFORMED_JSON,
                "Malformed JSON request body: Ensure request JSON contains valid single-brace syntax",
                request.getRequestURI(),
                getCorrelationId()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(apiError, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("Validation error on request to {}: {}", request.getRequestURI(), ex.getMessage());

        List<FieldErrorItem> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .code(ErrorCode.VALIDATION_FAILED.getCode())
                .category(ErrorCode.VALIDATION_FAILED.getCategory())
                .message(ErrorCode.VALIDATION_FAILED.getDefaultMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .correlationId(getCorrelationId())
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(apiError, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        log.warn("Constraint violation on request to {}: {}", request.getRequestURI(), ex.getMessage());

        List<FieldErrorItem> fieldErrors = ex.getConstraintViolations().stream()
                .map(violation -> FieldErrorItem.builder()
                        .field(violation.getPropertyPath().toString())
                        .rejectedValue(violation.getInvalidValue())
                        .message(violation.getMessage())
                        .build())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .code(ErrorCode.VALIDATION_FAILED.getCode())
                .category(ErrorCode.VALIDATION_FAILED.getCategory())
                .message("Constraint validation failed")
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .correlationId(getCorrelationId())
                .fieldErrors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(apiError, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        log.error("Data integrity violation on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        ApiError apiError = ApiError.of(
                ErrorCode.RESOURCE_ALREADY_EXISTS,
                "Database constraint violation: " + ex.getMostSpecificCause().getMessage(),
                request.getRequestURI(),
                getCorrelationId()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(apiError, HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied on {}: {}", request.getRequestURI(), ex.getMessage());
        ApiError apiError = ApiError.of(
                ErrorCode.FORBIDDEN,
                "Access denied: You do not have permission to perform this action",
                request.getRequestURI(),
                getCorrelationId()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error(apiError, HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        log.warn("Authentication failed on {}: {}", request.getRequestURI(), ex.getMessage());
        ApiError apiError = ApiError.of(
                ErrorCode.UNAUTHORIZED,
                "Authentication failed: " + ex.getMessage(),
                request.getRequestURI(),
                getCorrelationId()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error(apiError, HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnhandledException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception occurred on request to {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        ApiError apiError = ApiError.of(
                ErrorCode.INTERNAL_SERVER_ERROR,
                "An unexpected internal error occurred",
                request.getRequestURI(),
                getCorrelationId()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(apiError, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    private FieldErrorItem mapFieldError(FieldError fieldError) {
        return FieldErrorItem.builder()
                .field(fieldError.getField())
                .rejectedValue(fieldError.getRejectedValue())
                .message(fieldError.getDefaultMessage())
                .build();
    }

    private String getCorrelationId() {
        return MDC.get(MdcLoggingFilter.MDC_CORRELATION_ID_KEY);
    }
}
