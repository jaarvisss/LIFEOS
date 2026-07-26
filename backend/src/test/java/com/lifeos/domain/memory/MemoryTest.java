package com.lifeos.domain.memory;

import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.exception.MemoryValidationException;
import com.lifeos.domain.memory.domain.valueobject.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for Memory Aggregate Root behavior & domain invariants.
 */
class MemoryTest {

    @Test
    @DisplayName("Should successfully create a active memory with valid inputs")
    void shouldCreateValidMemory() {
        UUID userId = UUID.randomUUID();
        MemoryTitle title = MemoryTitle.of("Architecture Plan");
        MemoryContent content = MemoryContent.of("Design patterns for clean architecture and DDD");

        Memory memory = Memory.create(
                userId,
                null,
                title,
                content,
                MemorySummary.of("Architecture summary"),
                MemoryType.KNOWLEDGE,
                ImportanceLevel.HIGH,
                SourceType.MANUAL,
                Language.defaultLanguage(),
                ConfidenceScore.defaultScore(),
                "{ \"tag\": \"arch\" }"
        );

        assertNotNull(memory.getId());
        assertEquals("Architecture Plan", memory.getTitle().value());
        assertEquals("Design patterns for clean architecture and DDD", memory.getContent().value());
        assertEquals(MemoryStatus.ACTIVE, memory.getStatus());
        assertEquals(ImportanceLevel.HIGH, memory.getImportance());
        assertEquals(0L, memory.getAccessCount());
        assertFalse(memory.isFavorite());
        assertFalse(memory.isPinned());
    }

    @Test
    @DisplayName("Should throw MemoryValidationException when creating title with blank text")
    void shouldThrowExceptionWhenTitleIsBlank() {
        assertThrows(MemoryValidationException.class, () -> MemoryTitle.of("   "));
    }

    @Test
    @DisplayName("Should throw MemoryValidationException when creating content exceeding max size")
    void shouldThrowExceptionWhenContentExceedsLimit() {
        String longContent = "A".repeat(50001);
        assertThrows(MemoryValidationException.class, () -> MemoryContent.of(longContent));
    }

    @Test
    @DisplayName("Should archive an active memory and record archive timestamp")
    void shouldArchiveMemory() {
        UUID userId = UUID.randomUUID();
        Memory memory = Memory.create(
                userId, null, MemoryTitle.of("Title"), MemoryContent.of("Content"),
                null, MemoryType.NOTE, ImportanceLevel.NORMAL, SourceType.MANUAL, null, null, null
        );

        memory.archive();

        assertEquals(MemoryStatus.ARCHIVED, memory.getStatus());
        assertNotNull(memory.getArchivedAt());
    }

    @Test
    @DisplayName("Should record access count and update last accessed timestamp")
    void shouldRecordAccess() {
        UUID userId = UUID.randomUUID();
        Memory memory = Memory.create(
                userId, null, MemoryTitle.of("Title"), MemoryContent.of("Content"),
                null, MemoryType.NOTE, ImportanceLevel.NORMAL, SourceType.MANUAL, null, null, null
        );

        memory.recordAccess();

        assertEquals(1L, memory.getAccessCount());
        assertNotNull(memory.getLastAccessedAt());
    }
}
