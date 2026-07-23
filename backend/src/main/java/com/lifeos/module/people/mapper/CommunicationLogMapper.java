package com.lifeos.module.people.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.people.dto.CommunicationLogRequest;
import com.lifeos.module.people.dto.CommunicationLogResponse;
import com.lifeos.module.people.entity.CommunicationLogEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct Mapper for CommunicationLogEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface CommunicationLogMapper {

    CommunicationLogResponse toDto(CommunicationLogEntity entity);

    CommunicationLogEntity toEntity(CommunicationLogRequest request);

    List<CommunicationLogResponse> toDtoList(List<CommunicationLogEntity> entityList);
}
