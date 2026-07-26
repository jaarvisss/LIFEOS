package com.lifeos.domain.memory.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain Event emitted when a Memory is updated.
 */
public record MemoryUpdatedEvent(
        UUID memoryId,
        UUID userId,
        LocalDateTime occurredAt
) {
    public MemoryUpdatedEvent(UUID memoryId, UUID userId) {
        this(memoryId, userId, LocalDateTime.now());
    }
}
