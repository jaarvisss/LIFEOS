package com.lifeos.module.task.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.task.dto.SubtaskRequest;
import com.lifeos.module.task.dto.SubtaskResponse;
import com.lifeos.module.task.dto.TaskRequest;
import com.lifeos.module.task.dto.TaskResponse;
import com.lifeos.module.task.entity.SubtaskEntity;
import com.lifeos.module.task.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Task and Subtask entities.
 */
@Mapper(config = MapStructConfig.class)
public interface TaskMapper {

    TaskResponse toDto(TaskEntity entity);

    TaskEntity toEntity(TaskRequest request);

    List<TaskResponse> toDtoList(List<TaskEntity> entityList);

    void updateEntityFromDto(TaskRequest request, @MappingTarget TaskEntity entity);

    SubtaskResponse toDto(SubtaskEntity entity);

    SubtaskEntity toEntity(SubtaskRequest request);

    List<SubtaskResponse> toSubtaskDtoList(List<SubtaskEntity> entityList);
}
