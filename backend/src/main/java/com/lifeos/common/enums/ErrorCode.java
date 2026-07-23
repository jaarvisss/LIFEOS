package com.lifeos.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Enterprise Error Code Catalog mapping specific domain errors to HTTP status codes and categories.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    BAD_REQUEST("ERR-400-001", "Invalid request body or parameters", HttpStatus.BAD_REQUEST, ErrorCategory.BUSINESS_RULE_VIOLATION),
    VALIDATION_FAILED("ERR-400-002", "Validation failed for request fields", HttpStatus.BAD_REQUEST, ErrorCategory.VALIDATION_ERROR),
    MALFORMED_JSON("ERR-400-003", "Malformed JSON request payload", HttpStatus.BAD_REQUEST, ErrorCategory.VALIDATION_ERROR),
    INVALID_PARAMETER("ERR-400-004", "Invalid parameter value", HttpStatus.BAD_REQUEST, ErrorCategory.VALIDATION_ERROR),

    // 401 Unauthorized
    UNAUTHORIZED("ERR-401-001", "Authentication failure or missing token", HttpStatus.UNAUTHORIZED, ErrorCategory.AUTHENTICATION_ERROR),
    EXPIRED_TOKEN("ERR-401-002", "JWT token has expired", HttpStatus.UNAUTHORIZED, ErrorCategory.AUTHENTICATION_ERROR),
    INVALID_TOKEN("ERR-401-003", "Invalid or tampered JWT token", HttpStatus.UNAUTHORIZED, ErrorCategory.AUTHENTICATION_ERROR),
    INVALID_CREDENTIALS("ERR-401-004", "Invalid username or password", HttpStatus.UNAUTHORIZED, ErrorCategory.AUTHENTICATION_ERROR),

    // 403 Forbidden
    FORBIDDEN("ERR-403-001", "Access denied due to insufficient permissions", HttpStatus.FORBIDDEN, ErrorCategory.AUTHORIZATION_ERROR),
    ACCOUNT_DISABLED("ERR-403-002", "User account is disabled or locked", HttpStatus.FORBIDDEN, ErrorCategory.AUTHORIZATION_ERROR),

    // 404 Not Found
    RESOURCE_NOT_FOUND("ERR-404-001", "Requested resource was not found", HttpStatus.NOT_FOUND, ErrorCategory.RESOURCE_NOT_FOUND),
    USER_NOT_FOUND("ERR-404-002", "User account not found", HttpStatus.NOT_FOUND, ErrorCategory.RESOURCE_NOT_FOUND),

    // 409 Conflict
    RESOURCE_ALREADY_EXISTS("ERR-409-001", "Resource with given key already exists", HttpStatus.CONFLICT, ErrorCategory.CONFLICT_ERROR),
    OPTIMISTIC_LOCK_FAILURE("ERR-409-002", "Resource was updated by another transaction", HttpStatus.CONFLICT, ErrorCategory.CONFLICT_ERROR),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR("ERR-500-001", "An unexpected internal system error occurred", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCategory.SYSTEM_ERROR),
    DATABASE_ERROR("ERR-500-002", "Database persistence or connection failure", HttpStatus.INTERNAL_SERVER_ERROR, ErrorCategory.DATABASE_ERROR);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;
    private final ErrorCategory category;
}
