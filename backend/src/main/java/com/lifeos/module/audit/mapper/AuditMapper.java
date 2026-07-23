package com.lifeos.module.audit.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.audit.dto.ActivityLogResponse;
import com.lifeos.module.audit.dto.AuditLogResponse;
import com.lifeos.module.audit.dto.EntityHistoryResponse;
import com.lifeos.module.audit.entity.ActivityLogEntity;
import com.lifeos.module.audit.entity.AuditLogEntity;
import com.lifeos.module.audit.entity.EntityHistoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct Mapper for Audit entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface AuditMapper {

    AuditLogResponse toDto(AuditLogEntity entity);

    List<AuditLogResponse> toAuditDtoList(List<AuditLogEntity> entityList);

    EntityHistoryResponse toDto(EntityHistoryEntity entity);

    List<EntityHistoryResponse> toHistoryDtoList(List<EntityHistoryEntity> entityList);

    ActivityLogResponse toDto(ActivityLogEntity entity);

    List<ActivityLogResponse> toActivityDtoList(List<ActivityLogEntity> entityList);
}
