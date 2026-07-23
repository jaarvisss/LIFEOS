package com.lifeos.module.task.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.task.dto.TaskLabelRequest;
import com.lifeos.module.task.dto.TaskLabelResponse;
import com.lifeos.module.task.entity.TaskLabelEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct Mapper for TaskLabelEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface TaskLabelMapper {

    TaskLabelResponse toDto(TaskLabelEntity entity);

    TaskLabelEntity toEntity(TaskLabelRequest request);

    List<TaskLabelResponse> toDtoList(List<TaskLabelEntity> entityList);
}
