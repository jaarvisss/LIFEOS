package com.lifeos.module.health.service;

import com.lifeos.common.exception.ResourceNotFoundException;
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
import com.lifeos.module.health.entity.BodyMetricsEntity;
import com.lifeos.module.health.entity.HabitEntity;
import com.lifeos.module.health.entity.HabitLogEntity;
import com.lifeos.module.health.entity.MealEntity;
import com.lifeos.module.health.entity.SleepLogEntity;
import com.lifeos.module.health.entity.WorkoutEntity;
import com.lifeos.module.health.mapper.HealthMapper;
import com.lifeos.module.health.repository.BodyMetricsRepository;
import com.lifeos.module.health.repository.HabitLogRepository;
import com.lifeos.module.health.repository.HabitRepository;
import com.lifeos.module.health.repository.MealRepository;
import com.lifeos.module.health.repository.SleepLogRepository;
import com.lifeos.module.health.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of HealthService interface.
 */
@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final WorkoutRepository workoutRepository;
    private final MealRepository mealRepository;
    private final SleepLogRepository sleepLogRepository;
    private final BodyMetricsRepository bodyMetricsRepository;
    private final HealthMapper healthMapper;

    @Override
    @Transactional
    public HabitResponse createHabit(UUID userId, HabitRequest request) {
        HabitEntity habit = healthMapper.toEntity(request);
        habit.setUserId(userId);
        if (habit.getTargetFrequency() == null || habit.getTargetFrequency().isBlank()) {
            habit.setTargetFrequency("DAILY");
        }
        if (habit.getCurrentStreakCount() == null) {
            habit.setCurrentStreakCount(0);
        }
        if (habit.getBestStreakCount() == null) {
            habit.setBestStreakCount(0);
        }
        habit = habitRepository.save(habit);
        return healthMapper.toDto(habit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitResponse> getHabits(UUID userId) {
        return healthMapper.toHabitDtoList(habitRepository.findByUserIdAndIsDeletedFalse(userId));
    }

    @Override
    @Transactional
    public HabitLogResponse logHabitCompletion(UUID userId, HabitLogRequest request) {
        HabitEntity habit = habitRepository.findByIdAndUserIdAndIsDeletedFalse(request.habitId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit", "id", request.habitId()));

        HabitLogEntity log = habitLogRepository.findByHabitIdAndCompletedDate(request.habitId(), request.completedDate())
                .orElseGet(() -> healthMapper.toEntity(request));

        if (log.getId() == null) {
            int currentStreak = habit.getCurrentStreakCount() != null ? habit.getCurrentStreakCount() : 0;
            int bestStreak = habit.getBestStreakCount() != null ? habit.getBestStreakCount() : 0;
            
            habit.setCurrentStreakCount(currentStreak + 1);
            if (habit.getCurrentStreakCount() > bestStreak) {
                habit.setBestStreakCount(habit.getCurrentStreakCount());
            }
            habitRepository.save(habit);
        }

        log = habitLogRepository.save(log);
        return healthMapper.toDto(log);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitLogResponse> getHabitLogs(UUID userId, UUID habitId) {
        habitRepository.findByIdAndUserIdAndIsDeletedFalse(habitId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Habit", "id", habitId));

        return healthMapper.toHabitLogDtoList(habitLogRepository.findByHabitIdOrderByCompletedDateDesc(habitId));
    }

    @Override
    @Transactional
    public WorkoutResponse logWorkout(UUID userId, WorkoutRequest request) {
        WorkoutEntity workout = healthMapper.toEntity(request);
        workout.setUserId(userId);
        if (workout.getLoggedAt() == null) {
            workout.setLoggedAt(Instant.now());
        }
        workout = workoutRepository.save(workout);
        return healthMapper.toDto(workout);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<WorkoutResponse> getWorkouts(UUID userId, PaginationRequest paginationRequest) {
        Page<WorkoutEntity> page = workoutRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(healthMapper::toDto));
    }

    @Override
    @Transactional
    public MealResponse logMeal(UUID userId, MealRequest request) {
        MealEntity meal = healthMapper.toEntity(request);
        meal.setUserId(userId);
        if (meal.getLoggedAt() == null) {
            meal.setLoggedAt(Instant.now());
        }
        meal = mealRepository.save(meal);
        return healthMapper.toDto(meal);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<MealResponse> getMeals(UUID userId, PaginationRequest paginationRequest) {
        Page<MealEntity> page = mealRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(healthMapper::toDto));
    }

    @Override
    @Transactional
    public SleepLogResponse logSleep(UUID userId, SleepLogRequest request) {
        SleepLogEntity sleep = healthMapper.toEntity(request);
        sleep.setUserId(userId);
        sleep = sleepLogRepository.save(sleep);
        return healthMapper.toDto(sleep);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<SleepLogResponse> getSleepLogs(UUID userId, PaginationRequest paginationRequest) {
        Page<SleepLogEntity> page = sleepLogRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(healthMapper::toDto));
    }

    @Override
    @Transactional
    public BodyMetricsResponse logBodyMetrics(UUID userId, BodyMetricsRequest request) {
        BodyMetricsEntity metrics = healthMapper.toEntity(request);
        metrics.setUserId(userId);
        if (metrics.getLoggedDate() == null) {
            metrics.setLoggedDate(LocalDate.now());
        }
        metrics = bodyMetricsRepository.save(metrics);
        return healthMapper.toDto(metrics);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<BodyMetricsResponse> getBodyMetrics(UUID userId, PaginationRequest paginationRequest) {
        Page<BodyMetricsEntity> page = bodyMetricsRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(healthMapper::toDto));
    }
}
