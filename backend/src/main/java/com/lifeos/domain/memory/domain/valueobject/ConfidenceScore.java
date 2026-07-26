package com.lifeos.domain.memory.domain.valueobject;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;

/**
 * Value Object for Confidence Score (0.0 to 1.0).
 */
public record ConfidenceScore(double value) {
    public ConfidenceScore {
        if (value < 0.0 || value > 1.0) {
            throw new MemoryValidationException("Confidence score must be between 0.0 and 1.0");
        }
    }

    public static ConfidenceScore of(double score) {
        return new ConfidenceScore(score);
    }

    public static ConfidenceScore defaultScore() {
        return new ConfidenceScore(1.0);
    }
}
