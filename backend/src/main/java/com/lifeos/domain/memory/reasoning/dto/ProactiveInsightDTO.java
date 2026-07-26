package com.lifeos.domain.memory.reasoning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for Proactive AI Insights.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProactiveInsightDTO {
    private String id;
    private String insightType; // DEADLINE, SCHEDULE_CONFLICT, HABIT_STREAK, BUDGET_ALERT, HEALTH_TREND
    private String title;
    private String description;
    private String priority; // CRITICAL, HIGH, NORMAL
    private LocalDateTime generatedAt;
}
