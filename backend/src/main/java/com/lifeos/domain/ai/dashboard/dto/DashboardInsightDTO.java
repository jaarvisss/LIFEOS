package com.lifeos.domain.ai.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Proactive AI Dashboard Insights.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardInsightDTO {
    private UUID id;
    private String domainType; // TASKS, FINANCE, HEALTH, RELATIONSHIPS, KNOWLEDGE
    private String title;
    private String description;
    private String priority; // CRITICAL, HIGH, NORMAL
    private LocalDateTime createdAt;
}
