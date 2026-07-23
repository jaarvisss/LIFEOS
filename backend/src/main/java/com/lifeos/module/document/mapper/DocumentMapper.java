package com.lifeos.module.document.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.document.dto.DocumentRequest;
import com.lifeos.module.document.dto.DocumentResponse;
import com.lifeos.module.document.dto.DocumentVersionResponse;
import com.lifeos.module.document.entity.DocumentEntity;
import com.lifeos.module.document.entity.DocumentVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Document and DocumentVersion entities.
 */
@Mapper(config = MapStructConfig.class)
public interface DocumentMapper {

    DocumentResponse toDto(DocumentEntity entity);

    DocumentEntity toEntity(DocumentRequest request);

    List<DocumentResponse> toDtoList(List<DocumentEntity> entityList);

    void updateEntityFromDto(DocumentRequest request, @MappingTarget DocumentEntity entity);

    DocumentVersionResponse toVersionDto(DocumentVersionEntity entity);

    List<DocumentVersionResponse> toVersionDtoList(List<DocumentVersionEntity> entityList);
}
