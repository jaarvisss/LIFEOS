package com.lifeos.domain.memory.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value Object representing a unique Memory Identifier.
 */
public record MemoryId(UUID value) {
    public MemoryId {
        Objects.requireNonNull(value, "MemoryId UUID cannot be null");
    }

    public static MemoryId generate() {
        return new MemoryId(UUID.randomUUID());
    }

    public static MemoryId of(UUID id) {
        return new MemoryId(id);
    }
}
