package com.lifeos.domain.memory.application.mapper;

import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.presentation.response.MemoryResponse;
import com.lifeos.domain.memory.presentation.response.MemorySummaryResponse;

/**
 * Mapper to convert Memory Aggregate Root to API Response DTOs.
 */
public class MemoryDtoMapper {

    public static MemoryResponse toResponse(Memory domain) {
        if (domain == null) return null;

        return MemoryResponse.builder()
                .id(domain.getId().value())
                .title(domain.getTitle().value())
                .content(domain.getContent().value())
                .summary(domain.getSummary() != null ? domain.getSummary().value() : null)
                .memoryType(domain.getType().name())
                .importanceLevel(domain.getImportance().name())
                .memoryStatus(domain.getStatus().name())
                .sourceType(domain.getSource().name())
                .userId(domain.getUserId())
                .lifeProfileId(domain.getLifeProfileId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .archivedAt(domain.getArchivedAt())
                .deletedAt(domain.getDeletedAt())
                .lastAccessedAt(domain.getLastAccessedAt())
                .accessCount(domain.getAccessCount())
                .language(domain.getLanguage().code())
                .confidenceScore(domain.getConfidenceScore().value())
                .favorite(domain.isFavorite())
                .pinned(domain.isPinned())
                .metadata(domain.getMetadata())
                .version(domain.getVersion())
                .build();
    }

    public static MemorySummaryResponse toSummaryResponse(Memory domain) {
        if (domain == null) return null;

        return MemorySummaryResponse.builder()
                .id(domain.getId().value())
                .title(domain.getTitle().value())
                .summary(domain.getSummary() != null ? domain.getSummary().value() : null)
                .memoryType(domain.getType().name())
                .importanceLevel(domain.getImportance().name())
                .memoryStatus(domain.getStatus().name())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .favorite(domain.isFavorite())
                .pinned(domain.isPinned())
                .build();
    }
}
