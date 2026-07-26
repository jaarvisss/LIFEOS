package com.lifeos.domain.memory.domain.aggregate;

import com.lifeos.domain.memory.domain.exception.MemoryValidationException;
import com.lifeos.domain.memory.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Memory Aggregate Root containing rich domain behavior and invariant enforcement.
 */
@Getter
@Builder
public class Memory {

    private final MemoryId id;
    private MemoryTitle title;
    private MemoryContent content;
    private MemorySummary summary;
    private MemoryType type;
    private ImportanceLevel importance;
    private MemoryStatus status;
    private SourceType source;
    private final UUID userId;
    private UUID lifeProfileId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime lastAccessedAt;
    private long accessCount;
    private Language language;
    private ConfidenceScore confidenceScore;
    private boolean favorite;
    private boolean pinned;
    private String metadata;
    private Long version;
    private boolean deleted;

    public static Memory create(
            UUID userId,
            UUID lifeProfileId,
            MemoryTitle title,
            MemoryContent content,
            MemorySummary summary,
            MemoryType type,
            ImportanceLevel importance,
            SourceType source,
            Language language,
            ConfidenceScore confidenceScore,
            String metadata) {
        
        Objects.requireNonNull(userId, "UserId cannot be null");
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(content, "Content cannot be null");

        LocalDateTime now = LocalDateTime.now();

        return Memory.builder()
                .id(MemoryId.generate())
                .title(title)
                .content(content)
                .summary(summary != null ? summary : MemorySummary.empty())
                .type(type != null ? type : MemoryType.NOTE)
                .importance(importance != null ? importance : ImportanceLevel.NORMAL)
                .status(MemoryStatus.ACTIVE)
                .source(source != null ? source : SourceType.MANUAL)
                .userId(userId)
                .lifeProfileId(lifeProfileId)
                .createdAt(now)
                .updatedAt(now)
                .accessCount(0L)
                .language(language != null ? language : Language.defaultLanguage())
                .confidenceScore(confidenceScore != null ? confidenceScore : ConfidenceScore.defaultScore())
                .favorite(false)
                .pinned(false)
                .metadata(metadata)
                .version(0L)
                .deleted(false)
                .build();
    }

    public void update(
            MemoryTitle newTitle,
            MemoryContent newContent,
            MemorySummary newSummary,
            MemoryType newType,
            ImportanceLevel newImportance,
            Language newLanguage,
            String newMetadata) {

        if (this.deleted || this.status == MemoryStatus.DELETED) {
            throw new MemoryValidationException("Cannot update a deleted memory");
        }

        if (newTitle != null) this.title = newTitle;
        if (newContent != null) this.content = newContent;
        if (newSummary != null) this.summary = newSummary;
        if (newType != null) this.type = newType;
        if (newImportance != null) this.importance = newImportance;
        if (newLanguage != null) this.language = newLanguage;
        if (newMetadata != null) this.metadata = newMetadata;

        this.updatedAt = LocalDateTime.now();
    }

    public void archive() {
        if (this.deleted || this.status == MemoryStatus.DELETED) {
            throw new MemoryValidationException("Cannot archive a deleted memory");
        }
        if (this.status == MemoryStatus.ARCHIVED) {
            return;
        }
        this.status = MemoryStatus.ARCHIVED;
        this.archivedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void restore() {
        if (this.status == MemoryStatus.ACTIVE && !this.deleted) {
            return;
        }
        this.status = MemoryStatus.ACTIVE;
        this.deleted = false;
        this.archivedAt = null;
        this.deletedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        if (this.deleted && this.status == MemoryStatus.DELETED) {
            return;
        }
        this.status = MemoryStatus.DELETED;
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
        this.updatedAt = LocalDateTime.now();
    }

    public void toggleFavorite() {
        this.favorite = !this.favorite;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
        this.updatedAt = LocalDateTime.now();
    }

    public void recordAccess() {
        this.accessCount++;
        this.lastAccessedAt = LocalDateTime.now();
    }
}
