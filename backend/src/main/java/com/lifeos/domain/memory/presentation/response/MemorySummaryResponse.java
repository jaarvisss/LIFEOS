package com.lifeos.domain.memory.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Response DTO for Memory Summary listing.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemorySummaryResponse {

    private UUID id;
    private String title;
    private String summary;
    private String memoryType;
    private String importanceLevel;
    private String memoryStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorite;
    private boolean pinned;
}
