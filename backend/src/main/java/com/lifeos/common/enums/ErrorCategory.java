package com.lifeos.common.enums;

/**
 * High-level classification categories for API errors.
 */
public enum ErrorCategory {
    VALIDATION_ERROR,
    AUTHENTICATION_ERROR,
    AUTHORIZATION_ERROR,
    RESOURCE_NOT_FOUND,
    BUSINESS_RULE_VIOLATION,
    CONFLICT_ERROR,
    DATABASE_ERROR,
    SYSTEM_ERROR
}
