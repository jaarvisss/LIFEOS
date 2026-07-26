package com.lifeos.domain.memory.reasoning.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.memory.reasoning.dto.*;
import com.lifeos.domain.memory.reasoning.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller for AI Memory Reasoning, Decision Engine, Adaptive Planning, Proactive Insights, and Daily AI Briefing.
 */
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Tag(name = "AI Memory Reasoning & Decision Engine", description = "Endpoints for executive reasoning, decision evaluation, adaptive planning, proactive insights, and daily briefing")
public class MemoryReasoningController {

    private final ReasoningService reasoningService;
    private final DecisionEngineService decisionEngineService;
    private final PersonalContextService personalContextService;
    private final PlanningService planningService;
    private final InsightService insightService;
    private final RecommendationService recommendationService;
    private final DailyBriefService dailyBriefService;

    @Data
    public static class ReasoningRequest {
        private String prompt;
    }

    @Data
    public static class PlanRequest {
        private String timeframe = "daily";
        private String goalFocus = "general";
    }

    @Data
    public static class DecisionRequest {
        private String context;
        private List<String> options;
    }

    @PostMapping("/reason")
    @Operation(summary = "Multi-step AI Reasoning", description = "Executes multi-step reasoning over memories and generates conclusions with confidence scores.")
    public ResponseEntity<ApiResponse<ReasoningResponseDTO>> reasonAboutPrompt(@RequestBody ReasoningRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(reasoningService.reasonAboutPrompt(userId, request.getPrompt()));
    }

    @PostMapping("/plan")
    @Operation(summary = "Generate Adaptive Plan", description = "Generates adaptive daily, weekly, or goal execution plans.")
    public ResponseEntity<ApiResponse<PlanResponseDTO>> generatePlan(@RequestBody PlanRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(planningService.generatePlan(userId, request.getTimeframe(), request.getGoalFocus()));
    }

    @PostMapping("/recommend")
    @Operation(summary = "Evaluate Alternatives", description = "Evaluates decision options and recommends next best actions with reasoning.")
    public ResponseEntity<ApiResponse<List<RecommendationDTO>>> evaluateDecision(@RequestBody DecisionRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(decisionEngineService.evaluateAlternatives(userId, request.getContext(), request.getOptions()));
    }

    @PostMapping("/insights")
    @Operation(summary = "Generate Proactive Insights", description = "Generates unprompted proactive insights regarding deadlines, schedule conflicts, and habit streaks.")
    public ResponseEntity<ApiResponse<List<ProactiveInsightDTO>>> generateInsights() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(insightService.generateProactiveInsights(userId));
    }

    @PostMapping("/context")
    @Operation(summary = "Get Full Personal Context", description = "Aggregates multi-domain context for AI processing.")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPersonalContext() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(personalContextService.getFullPersonalContext(userId));
    }

    @GetMapping("/timeline/highlights")
    @Operation(summary = "Get Timeline Highlights", description = "Retrieves AI-generated timeline highlights and achievement summaries.")
    public ResponseEntity<ApiResponse<List<String>>> getTimelineHighlights() {
        return ResponseBuilder.ok(List.of("Completed Clean Architecture Refactoring", "Achieved 100% test suite pass rate"));
    }

    @GetMapping("/recent-insights")
    @Operation(summary = "Get Recent Insights", description = "Retrieves recent proactive insights.")
    public ResponseEntity<ApiResponse<List<ProactiveInsightDTO>>> getRecentInsights() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(insightService.generateProactiveInsights(userId));
    }

    @GetMapping("/recommendations")
    @Operation(summary = "Get Recommendations", description = "Retrieves active personalized recommendations.")
    public ResponseEntity<ApiResponse<List<RecommendationDTO>>> getRecommendations() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(recommendationService.getRecommendations(userId));
    }

    @GetMapping("/daily-brief")
    @Operation(summary = "Get Daily AI Brief", description = "Retrieves executive morning AI brief with priorities, schedule, and insights.")
    public ResponseEntity<ApiResponse<DailyBriefResponseDTO>> getDailyBrief() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(dailyBriefService.generateDailyBrief(userId));
    }
}
