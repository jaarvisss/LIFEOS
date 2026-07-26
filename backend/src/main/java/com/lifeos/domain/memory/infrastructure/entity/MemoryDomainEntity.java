package com.lifeos.domain.memory.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * JPA Entity for memory.memories table.
 */
@Entity
@Table(schema = "memory", name = "memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoryDomainEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "summary", length = 1000)
    private String summary;

    @Column(name = "memory_type", nullable = false, length = 50)
    private String memoryType;

    @Column(name = "importance_level", nullable = false, length = 30)
    private String importanceLevel;

    @Column(name = "memory_status", nullable = false, length = 30)
    private String memoryStatus;

    @Column(name = "source_type", nullable = false, length = 30)
    private String sourceType;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "life_profile_id")
    private UUID lifeProfileId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "archived_at")
    private LocalDateTime archivedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "access_count", nullable = false)
    private long accessCount;

    @Column(name = "language", nullable = false, length = 10)
    private String language;

    @Column(name = "confidence_score", nullable = false)
    private double confidenceScore;

    @Column(name = "is_favorite", nullable = false)
    private boolean favorite;

    @Column(name = "is_pinned", nullable = false)
    private boolean pinned;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted;
}
