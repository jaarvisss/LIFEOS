package com.lifeos.domain.ai.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Complete AI Dashboard Life Operating Cockpit overview.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOverviewDTO {
    private LifeScoreDTO lifeScore;
    private String dailyBriefingSummary;
    private List<DashboardInsightDTO> proactiveInsights;
    private List<DashboardPredictionDTO> predictions;
    private List<String> topPrioritiesToday;
    private List<DashboardWidgetDTO> widgets;
}
