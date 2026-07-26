package com.lifeos.domain.memory.domain.exception;

/**
 * Exception thrown when domain validation rules are violated.
 */
public class MemoryValidationException extends RuntimeException {
    public MemoryValidationException(String message) {
        super(message);
    }
}
