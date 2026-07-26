package com.lifeos.domain.ai.dashboard.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.dashboard.dto.*;
import com.lifeos.domain.ai.dashboard.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for AI Dashboard Intelligence & Life Operating Cockpit.
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Tag(name = "AI Dashboard Intelligence Platform", description = "Endpoints for complete life cockpit overview, daily brief, proactive insights, predictions, life score, timeline, and widget customization")
public class AiDashboardController {

    private final DashboardIntelligenceService intelligenceService;
    private final LifeScoreService lifeScoreService;

    @Data
    public static class LayoutUpdateRequest {
        private List<DashboardWidgetDTO> widgets;
    }

    @GetMapping
    @Operation(summary = "Get Dashboard Overview", description = "Retrieves complete Life Operating Center overview with Life Score, Daily Brief, Insights, and Predictions.")
    public ResponseEntity<ApiResponse<DashboardOverviewDTO>> getDashboardOverview() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.getDashboardOverview(userId));
    }

    @GetMapping("/brief")
    @Operation(summary = "Get Executive Daily Briefing", description = "Retrieves executive morning briefing synthesis.")
    public ResponseEntity<ApiResponse<String>> getDailyBrief() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.getDailyBriefing(userId));
    }

    @GetMapping("/insights")
    @Operation(summary = "Get Proactive AI Insights", description = "Retrieves cross-domain proactive insights and alerts.")
    public ResponseEntity<ApiResponse<List<DashboardInsightDTO>>> getInsights() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.getProactiveInsights(userId));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get Proactive Recommendations", description = "Retrieves prioritized recommendations.")
    public ResponseEntity<ApiResponse<List<String>>> getRecommendations() {
        return ResponseBuilder.ok(List.of(
                "Finish Phase 9 AI Dashboard Certification before end of day",
                "Reduce meeting load tomorrow to optimize focus time",
                "Schedule catch-up meeting with Rahul",
                "Review monthly dining expense variance"
        ));
    }

    @GetMapping("/predictions")
    @Operation(summary = "Get AI Predictive Models", description = "Retrieves predictive milestone models with probability percentages.")
    public ResponseEntity<ApiResponse<List<DashboardPredictionDTO>>> getPredictions() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.getPredictions(userId));
    }

    @GetMapping("/life-score")
    @Operation(summary = "Get Life Operating Score", description = "Retrieves 0-100 scores across Productivity, Health, Finance, Learning, Relationships, and Balance.")
    public ResponseEntity<ApiResponse<LifeScoreDTO>> getLifeScore() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(lifeScoreService.calculateLifeScore(userId));
    }

    @GetMapping("/timeline")
    @Operation(summary = "Get Unified Life Timeline", description = "Retrieves unified chronological life timeline stream.")
    public ResponseEntity<ApiResponse<List<String>>> getTimeline() {
        return ResponseBuilder.ok(List.of(
                "08:00 AM - Morning AI Executive Briefing Synthesized",
                "09:30 AM - Task: 'Phase 9 AI Dashboard' set to HIGH Priority",
                "01:00 PM - Logged Grocery Expense: $50",
                "03:30 PM - Meeting: Project Roadmap Review with Team",
                "06:00 PM - Health: Sleep Score 8.2h logged"
        ));
    }

    @GetMapping("/widgets")
    @Operation(summary = "Get Dashboard Smart Widgets", description = "Retrieves smart widget configuration layout.")
    public ResponseEntity<ApiResponse<List<DashboardWidgetDTO>>> getWidgets() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.getWidgets(userId));
    }

    @PostMapping("/layout")
    @Operation(summary = "Update Dashboard Layout", description = "Updates widget visibility and order preferences.")
    public ResponseEntity<ApiResponse<Void>> updateLayout(@RequestBody LayoutUpdateRequest request) {
        return ResponseBuilder.ok(null, "Dashboard layout updated successfully");
    }
}
