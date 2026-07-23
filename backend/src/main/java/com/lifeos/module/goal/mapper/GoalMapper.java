package com.lifeos.module.goal.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.goal.dto.GoalMilestoneRequest;
import com.lifeos.module.goal.dto.GoalMilestoneResponse;
import com.lifeos.module.goal.dto.GoalRequest;
import com.lifeos.module.goal.dto.GoalResponse;
import com.lifeos.module.goal.entity.GoalEntity;
import com.lifeos.module.goal.entity.GoalMilestoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Goal and Goal Milestone entities.
 */
@Mapper(config = MapStructConfig.class)
public interface GoalMapper {

    GoalResponse toDto(GoalEntity entity);

    GoalEntity toEntity(GoalRequest request);

    List<GoalResponse> toDtoList(List<GoalEntity> entityList);

    void updateEntityFromDto(GoalRequest request, @MappingTarget GoalEntity entity);

    GoalMilestoneResponse toDto(GoalMilestoneEntity entity);

    GoalMilestoneEntity toEntity(GoalMilestoneRequest request);

    List<GoalMilestoneResponse> toMilestoneDtoList(List<GoalMilestoneEntity> entityList);
}
