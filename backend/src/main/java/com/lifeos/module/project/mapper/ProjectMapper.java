package com.lifeos.module.project.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.project.dto.ProjectRequest;
import com.lifeos.module.project.dto.ProjectResponse;
import com.lifeos.module.project.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for ProjectEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface ProjectMapper {

    ProjectResponse toDto(ProjectEntity entity);

    ProjectEntity toEntity(ProjectRequest request);

    List<ProjectResponse> toDtoList(List<ProjectEntity> entityList);

    void updateEntityFromDto(ProjectRequest request, @MappingTarget ProjectEntity entity);
}
