package com.lifeos.module.analytics.service;

import com.lifeos.module.analytics.dto.DashboardRequest;
import com.lifeos.module.analytics.dto.DashboardResponse;
import com.lifeos.module.analytics.dto.LifeScoreResponse;
import com.lifeos.module.analytics.dto.ProductivityMetricResponse;
import com.lifeos.module.analytics.dto.UsageStatisticResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Dashboards, Productivity Trends, Life Scores, and Feature Usage Analytics.
 */
public interface AnalyticsService {

    DashboardResponse createDashboard(UUID userId, DashboardRequest request);

    List<DashboardResponse> getDashboards(UUID userId);

    ProductivityMetricResponse getTodayProductivity(UUID userId);

    List<ProductivityMetricResponse> getProductivityRange(UUID userId, LocalDate start, LocalDate end);

    LifeScoreResponse getLatestLifeScore(UUID userId);

    List<UsageStatisticResponse> getUsageStatistics(UUID userId);

    void recordFeatureUsage(UUID userId, String featureName);
}
