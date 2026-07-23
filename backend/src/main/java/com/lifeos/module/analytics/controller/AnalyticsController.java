package com.lifeos.module.analytics.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.analytics.dto.DashboardRequest;
import com.lifeos.module.analytics.dto.DashboardResponse;
import com.lifeos.module.analytics.dto.LifeScoreResponse;
import com.lifeos.module.analytics.dto.ProductivityMetricResponse;
import com.lifeos.module.analytics.dto.UsageStatisticResponse;
import com.lifeos.module.analytics.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Analytics, Dashboards, Productivity Trends, and Life Scores.
 */
@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@Tag(name = "Analytics & Intelligence", description = "Endpoints for dashboard layout customization, productivity trends, holistic life scores, and feature usage analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @PostMapping("/dashboards")
    @Operation(summary = "Create Dashboard Layout", description = "Creates a customized widget dashboard layout.")
    public ResponseEntity<ApiResponse<DashboardResponse>> createDashboard(@Valid @RequestBody DashboardRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(analyticsService.createDashboard(userId, request), "Dashboard created");
    }

    @GetMapping("/dashboards")
    @Operation(summary = "List User Dashboards", description = "Retrieves user's configured dashboard layouts.")
    public ResponseEntity<ApiResponse<List<DashboardResponse>>> getDashboards() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(analyticsService.getDashboards(userId));
    }

    @GetMapping("/productivity/today")
    @Operation(summary = "Get Today Productivity Metrics", description = "Retrieves daily completed tasks, habits, and productivity score.")
    public ResponseEntity<ApiResponse<ProductivityMetricResponse>> getTodayProductivity() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(analyticsService.getTodayProductivity(userId));
    }

    @GetMapping("/productivity/range")
    @Operation(summary = "Get Productivity Range", description = "Retrieves productivity metrics across a date range.")
    public ResponseEntity<ApiResponse<List<ProductivityMetricResponse>>> getProductivityRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(analyticsService.getProductivityRange(userId, start, end));
    }

    @GetMapping("/life-scores/latest")
    @Operation(summary = "Get Latest Life Score", description = "Retrieves composite LifeOS overall score and category subscores.")
    public ResponseEntity<ApiResponse<LifeScoreResponse>> getLatestLifeScore() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(analyticsService.getLatestLifeScore(userId));
    }

    @GetMapping("/usage")
    @Operation(summary = "Get Usage Statistics", description = "Retrieves feature usage counts and timestamps.")
    public ResponseEntity<ApiResponse<List<UsageStatisticResponse>>> getUsageStatistics() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(analyticsService.getUsageStatistics(userId));
    }
}
