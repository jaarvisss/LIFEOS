package com.lifeos.domain.memory.domain.valueobject;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;

import java.util.Objects;

/**
 * Value Object for Memory Title with invariant validation.
 */
public record MemoryTitle(String value) {
    public MemoryTitle {
        Objects.requireNonNull(value, "Title cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new MemoryValidationException("Memory title cannot be blank");
        }
        if (trimmed.length() > 200) {
            throw new MemoryValidationException("Memory title cannot exceed 200 characters");
        }
        value = trimmed;
    }

    public static MemoryTitle of(String title) {
        return new MemoryTitle(title);
    }
}
