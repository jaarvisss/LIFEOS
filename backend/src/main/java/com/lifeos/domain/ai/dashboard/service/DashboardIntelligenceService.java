package com.lifeos.domain.ai.dashboard.service;

import com.lifeos.domain.ai.dashboard.dto.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for AI Dashboard Intelligence, Insights, and Predictions.
 */
public interface DashboardIntelligenceService {

    DashboardOverviewDTO getDashboardOverview(UUID userId);

    String getDailyBriefing(UUID userId);

    List<DashboardInsightDTO> getProactiveInsights(UUID userId);

    List<DashboardPredictionDTO> getPredictions(UUID userId);

    List<DashboardWidgetDTO> getWidgets(UUID userId);
}
