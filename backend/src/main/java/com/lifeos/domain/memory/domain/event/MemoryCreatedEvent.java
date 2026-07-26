package com.lifeos.domain.memory.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain Event emitted when a Memory is created.
 */
public record MemoryCreatedEvent(
        UUID memoryId,
        UUID userId,
        String title,
        String memoryType,
        LocalDateTime occurredAt
) {
    public MemoryCreatedEvent(UUID memoryId, UUID userId, String title, String memoryType) {
        this(memoryId, userId, title, memoryType, LocalDateTime.now());
    }
}
