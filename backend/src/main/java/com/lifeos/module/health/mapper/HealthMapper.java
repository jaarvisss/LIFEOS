package com.lifeos.module.health.mapper;

import com.lifeos.common.mapper.MapStructConfig;
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
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Health & Vitality entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface HealthMapper {

    HabitResponse toDto(HabitEntity entity);

    HabitEntity toEntity(HabitRequest request);

    List<HabitResponse> toHabitDtoList(List<HabitEntity> entityList);

    void updateEntityFromDto(HabitRequest request, @MappingTarget HabitEntity entity);

    HabitLogResponse toDto(HabitLogEntity entity);

    HabitLogEntity toEntity(HabitLogRequest request);

    List<HabitLogResponse> toHabitLogDtoList(List<HabitLogEntity> entityList);

    WorkoutResponse toDto(WorkoutEntity entity);

    WorkoutEntity toEntity(WorkoutRequest request);

    MealResponse toDto(MealEntity entity);

    MealEntity toEntity(MealRequest request);

    SleepLogResponse toDto(SleepLogEntity entity);

    SleepLogEntity toEntity(SleepLogRequest request);

    BodyMetricsResponse toDto(BodyMetricsEntity entity);

    BodyMetricsEntity toEntity(BodyMetricsRequest request);
}
