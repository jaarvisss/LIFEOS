package com.lifeos.domain.memory.domain.valueobject;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;

import java.util.Objects;

/**
 * Value Object for Memory Content with invariant validation.
 */
public record MemoryContent(String value) {
    public MemoryContent {
        Objects.requireNonNull(value, "Content cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new MemoryValidationException("Memory content cannot be blank");
        }
        if (trimmed.length() > 50000) {
            throw new MemoryValidationException("Memory content cannot exceed 50,000 characters");
        }
        value = trimmed;
    }

    public static MemoryContent of(String content) {
        return new MemoryContent(content);
    }
}
