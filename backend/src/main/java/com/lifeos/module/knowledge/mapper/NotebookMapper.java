package com.lifeos.module.knowledge.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.knowledge.dto.NotebookRequest;
import com.lifeos.module.knowledge.dto.NotebookResponse;
import com.lifeos.module.knowledge.entity.NotebookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for NotebookEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface NotebookMapper {

    NotebookResponse toDto(NotebookEntity entity);

    NotebookEntity toEntity(NotebookRequest request);

    List<NotebookResponse> toDtoList(List<NotebookEntity> entityList);

    void updateEntityFromDto(NotebookRequest request, @MappingTarget NotebookEntity entity);
}
