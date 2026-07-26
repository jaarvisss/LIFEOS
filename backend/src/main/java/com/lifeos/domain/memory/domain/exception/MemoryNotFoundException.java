package com.lifeos.domain.memory.domain.exception;

import java.util.UUID;

/**
 * Exception thrown when a memory entity is not found.
 */
public class MemoryNotFoundException extends RuntimeException {
    public MemoryNotFoundException(UUID id) {
        super("Memory not found with ID: " + id);
    }

    public MemoryNotFoundException(String message) {
        super(message);
    }
}
