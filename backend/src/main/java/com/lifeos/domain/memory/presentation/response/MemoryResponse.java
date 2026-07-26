package com.lifeos.domain.memory.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for Memory details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemoryResponse {

    private UUID id;
    private String title;
    private String content;
    private String summary;
    private String memoryType;
    private String importanceLevel;
    private String memoryStatus;
    private String sourceType;
    private UUID userId;
    private UUID lifeProfileId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime archivedAt;
    private LocalDateTime deletedAt;
    private LocalDateTime lastAccessedAt;
    private long accessCount;
    private String language;
    private double confidenceScore;
    private boolean favorite;
    private boolean pinned;
    private String metadata;
    private Long version;
}
