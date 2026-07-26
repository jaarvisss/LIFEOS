package com.lifeos.domain.memory.infrastructure.mapper;

import com.lifeos.domain.memory.domain.aggregate.Memory;
import com.lifeos.domain.memory.domain.valueobject.*;
import com.lifeos.domain.memory.infrastructure.entity.MemoryDomainEntity;

/**
 * Mapper between Memory Aggregate Root and MemoryDomainEntity JPA Entity.
 */
public class MemoryEntityMapper {

    public static MemoryDomainEntity toEntity(Memory domain) {
        if (domain == null) return null;

        return MemoryDomainEntity.builder()
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
                .deleted(domain.isDeleted())
                .build();
    }

    public static Memory toDomain(MemoryDomainEntity entity) {
        if (entity == null) return null;

        return Memory.builder()
                .id(MemoryId.of(entity.getId()))
                .title(MemoryTitle.of(entity.getTitle()))
                .content(MemoryContent.of(entity.getContent()))
                .summary(entity.getSummary() != null ? MemorySummary.of(entity.getSummary()) : MemorySummary.empty())
                .type(MemoryType.valueOf(entity.getMemoryType()))
                .importance(ImportanceLevel.valueOf(entity.getImportanceLevel()))
                .status(MemoryStatus.valueOf(entity.getMemoryStatus()))
                .source(SourceType.valueOf(entity.getSourceType()))
                .userId(entity.getUserId())
                .lifeProfileId(entity.getLifeProfileId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .archivedAt(entity.getArchivedAt())
                .deletedAt(entity.getDeletedAt())
                .lastAccessedAt(entity.getLastAccessedAt())
                .accessCount(entity.getAccessCount())
                .language(Language.of(entity.getLanguage()))
                .confidenceScore(ConfidenceScore.of(entity.getConfidenceScore()))
                .favorite(entity.isFavorite())
                .pinned(entity.isPinned())
                .metadata(entity.getMetadata())
                .version(entity.getVersion())
                .deleted(entity.isDeleted())
                .build();
    }
}
