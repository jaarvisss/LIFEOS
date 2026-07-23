package com.lifeos.module.health.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
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

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Health, Fitness, Habits, and Vitality metrics.
 */
public interface HealthService {

    HabitResponse createHabit(UUID userId, HabitRequest request);

    List<HabitResponse> getHabits(UUID userId);

    HabitLogResponse logHabitCompletion(UUID userId, HabitLogRequest request);

    List<HabitLogResponse> getHabitLogs(UUID userId, UUID habitId);

    WorkoutResponse logWorkout(UUID userId, WorkoutRequest request);

    PagedResponse<WorkoutResponse> getWorkouts(UUID userId, PaginationRequest paginationRequest);

    MealResponse logMeal(UUID userId, MealRequest request);

    PagedResponse<MealResponse> getMeals(UUID userId, PaginationRequest paginationRequest);

    SleepLogResponse logSleep(UUID userId, SleepLogRequest request);

    PagedResponse<SleepLogResponse> getSleepLogs(UUID userId, PaginationRequest paginationRequest);

    BodyMetricsResponse logBodyMetrics(UUID userId, BodyMetricsRequest request);

    PagedResponse<BodyMetricsResponse> getBodyMetrics(UUID userId, PaginationRequest paginationRequest);
}
