package com.lifeos.module.goal.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.goal.dto.GoalMilestoneRequest;
import com.lifeos.module.goal.dto.GoalMilestoneResponse;
import com.lifeos.module.goal.dto.GoalProgressRequest;
import com.lifeos.module.goal.dto.GoalRequest;
import com.lifeos.module.goal.dto.GoalResponse;
import com.lifeos.module.goal.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Life Goals, Goal Milestones, and OKR Tracking.
 */
@RestController
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@Tag(name = "Goal Management", description = "Endpoints for personal & career goals, progress tracking, and milestones")
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    @Operation(summary = "Create Goal", description = "Creates a new personal or career goal.")
    public ResponseEntity<ApiResponse<GoalResponse>> createGoal(@Valid @RequestBody GoalRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(goalService.createGoal(userId, request), "Goal created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Goal by ID", description = "Retrieves goal details by identifier.")
    public ResponseEntity<ApiResponse<GoalResponse>> getGoalById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(goalService.getGoalById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Goals", description = "Retrieves paginated list of goals with optional status filter.")
    public ResponseEntity<ApiResponse<PagedResponse<GoalResponse>>> getAllGoals(
            @RequestParam(value = "status", required = false) String status,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(goalService.getAllGoals(userId, status, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Goal", description = "Updates goal title, description, category, and target date.")
    public ResponseEntity<ApiResponse<GoalResponse>> updateGoal(
            @PathVariable UUID id,
            @Valid @RequestBody GoalRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(goalService.updateGoal(userId, id, request), "Goal updated successfully");
    }

    @PatchMapping("/{id}/progress")
    @Operation(summary = "Update Progress", description = "Updates goal progress percentage and status.")
    public ResponseEntity<ApiResponse<GoalResponse>> updateProgress(
            @PathVariable UUID id,
            @Valid @RequestBody GoalProgressRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(goalService.updateProgress(userId, id, request), "Goal progress updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Goal", description = "Soft-deletes a goal record.")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        goalService.deleteGoal(userId, id);
        return ResponseBuilder.ok(null, "Goal deleted successfully");
    }

    @PostMapping("/milestones")
    @Operation(summary = "Add Goal Milestone", description = "Creates a new milestone for a goal.")
    public ResponseEntity<ApiResponse<GoalMilestoneResponse>> addMilestone(@Valid @RequestBody GoalMilestoneRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(goalService.addMilestone(userId, request), "Milestone added successfully");
    }

    @GetMapping("/{id}/milestones")
    @Operation(summary = "Get Goal Milestones", description = "Retrieves all milestones for a goal.")
    public ResponseEntity<ApiResponse<List<GoalMilestoneResponse>>> getMilestones(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(goalService.getMilestones(userId, id));
    }
}
