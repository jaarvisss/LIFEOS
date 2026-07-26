package com.lifeos.domain.ai.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Individual Search Result Record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResultItemDTO {
    private UUID id;
    private String domainType; // MEMORY, TASK, CALENDAR, KNOWLEDGE, FINANCE, HEALTH, CONVERSATION
    private String title;
    private String snippet;
    private double relevanceScore;
    private LocalDateTime timestamp;
}
