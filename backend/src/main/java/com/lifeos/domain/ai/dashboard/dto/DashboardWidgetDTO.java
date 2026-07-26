package com.lifeos.domain.ai.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Dashboard Smart Widgets.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardWidgetDTO {
    private String widgetId;
    private String widgetName;
    private String widgetType; // DAILY_BRIEF, LIFE_SCORE, TIMELINE, INSIGHTS, PREDICTIONS
    private int positionOrder;
    private boolean visible;
}
