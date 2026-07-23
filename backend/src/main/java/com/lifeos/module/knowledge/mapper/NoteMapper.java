package com.lifeos.module.knowledge.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.knowledge.dto.NoteRequest;
import com.lifeos.module.knowledge.dto.NoteResponse;
import com.lifeos.module.knowledge.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for NoteEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface NoteMapper {

    NoteResponse toDto(NoteEntity entity);

    NoteEntity toEntity(NoteRequest request);

    List<NoteResponse> toDtoList(List<NoteEntity> entityList);

    void updateEntityFromDto(NoteRequest request, @MappingTarget NoteEntity entity);
}
