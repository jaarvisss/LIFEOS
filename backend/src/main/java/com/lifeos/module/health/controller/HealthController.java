package com.lifeos.module.health.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.health.dto.BodyMetricsRequest;
import com.lifeos.module.health.dto.BodyMetricsResponse;
import com.lifeos.module.health.dto.HabitLogRequest;
import com.lifeos.module.health.dto.HabitLogResponse;
import com.lifeos.module.health.dto.HabitRequest;
import com.lifeos.module.health.dto.HabitResponse;
import com.lifeos.module.health.dto.MealRequest;
import com.lifeos.module.health.dto.MealResponse;
import com.lifeos.module.health.dto.SleepLogRequest;
import com.lifeos.module.health.dto.SleepLogResponse;
import com.lifeos.module.health.dto.WorkoutRequest;
import com.lifeos.module.health.dto.WorkoutResponse;
import com.lifeos.module.health.service.HealthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Health, Fitness, Habit Tracking, and Body Metrics.
 */
@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
@Tag(name = "Health & Vitality", description = "Endpoints for habit tracking, workout logging, meal plans, sleep quality, and body metrics")
public class HealthController {

    private final HealthService healthService;

    // --- Habits ---

    @PostMapping("/habits")
    @Operation(summary = "Create Habit", description = "Defines a new habit for tracking.")
    public ResponseEntity<ApiResponse<HabitResponse>> createHabit(@Valid @RequestBody HabitRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.createHabit(userId, request), "Habit created successfully");
    }

    @GetMapping("/habits")
    @Operation(summary = "List Habits", description = "Retrieves user habits and streak counts.")
    public ResponseEntity<ApiResponse<List<HabitResponse>>> getHabits() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getHabits(userId));
    }

    @PostMapping("/habits/logs")
    @Operation(summary = "Log Habit Execution", description = "Logs habit completion for a specific date and updates streak.")
    public ResponseEntity<ApiResponse<HabitLogResponse>> logHabitCompletion(@Valid @RequestBody HabitLogRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.logHabitCompletion(userId, request), "Habit execution logged");
    }

    @GetMapping("/habits/{habitId}/logs")
    @Operation(summary = "Get Habit Execution Logs", description = "Retrieves completion history for a habit.")
    public ResponseEntity<ApiResponse<List<HabitLogResponse>>> getHabitLogs(@PathVariable UUID habitId) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getHabitLogs(userId, habitId));
    }

    // --- Fitness Workouts ---

    @PostMapping("/workouts")
    @Operation(summary = "Log Workout", description = "Records a workout session.")
    public ResponseEntity<ApiResponse<WorkoutResponse>> logWorkout(@Valid @RequestBody WorkoutRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.logWorkout(userId, request), "Workout logged successfully");
    }

    @GetMapping("/workouts")
    @Operation(summary = "List Workouts", description = "Retrieves paginated workout logs.")
    public ResponseEntity<ApiResponse<PagedResponse<WorkoutResponse>>> getWorkouts(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getWorkouts(userId, paginationRequest));
    }

    // --- Nutrition Meals ---

    @PostMapping("/meals")
    @Operation(summary = "Log Meal", description = "Records a meal and estimated calories.")
    public ResponseEntity<ApiResponse<MealResponse>> logMeal(@Valid @RequestBody MealRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.logMeal(userId, request), "Meal logged successfully");
    }

    @GetMapping("/meals")
    @Operation(summary = "List Meals", description = "Retrieves paginated meal history.")
    public ResponseEntity<ApiResponse<PagedResponse<MealResponse>>> getMeals(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getMeals(userId, paginationRequest));
    }

    // --- Sleep ---

    @PostMapping("/sleep")
    @Operation(summary = "Log Sleep", description = "Records sleep start/end times and quality score.")
    public ResponseEntity<ApiResponse<SleepLogResponse>> logSleep(@Valid @RequestBody SleepLogRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.logSleep(userId, request), "Sleep log created successfully");
    }

    @GetMapping("/sleep")
    @Operation(summary = "List Sleep Logs", description = "Retrieves paginated sleep logs.")
    public ResponseEntity<ApiResponse<PagedResponse<SleepLogResponse>>> getSleepLogs(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getSleepLogs(userId, paginationRequest));
    }

    // --- Body Metrics ---

    @PostMapping("/metrics")
    @Operation(summary = "Log Body Metrics", description = "Records weight, height, or heart rate entry.")
    public ResponseEntity<ApiResponse<BodyMetricsResponse>> logBodyMetrics(@Valid @RequestBody BodyMetricsRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(healthService.logBodyMetrics(userId, request), "Body metrics logged successfully");
    }

    @GetMapping("/metrics")
    @Operation(summary = "List Body Metrics", description = "Retrieves paginated body metric history.")
    public ResponseEntity<ApiResponse<PagedResponse<BodyMetricsResponse>>> getBodyMetrics(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(healthService.getBodyMetrics(userId, paginationRequest));
    }
}
