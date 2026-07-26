package com.lifeos.domain.memory.domain.valueobject;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;

/**
 * Value Object for Memory Summary.
 */
public record MemorySummary(String value) {
    public MemorySummary {
        if (value != null) {
            String trimmed = value.trim();
            if (trimmed.length() > 1000) {
                throw new MemoryValidationException("Memory summary cannot exceed 1000 characters");
            }
            value = trimmed;
        }
    }

    public static MemorySummary of(String summary) {
        return new MemorySummary(summary);
    }

    public static MemorySummary empty() {
        return new MemorySummary(null);
    }
}
