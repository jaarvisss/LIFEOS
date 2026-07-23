package com.lifeos.module.user.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.user.dto.PermissionResponse;
import com.lifeos.module.user.dto.RoleResponse;
import com.lifeos.module.user.entity.PermissionEntity;
import com.lifeos.module.user.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * MapStruct Mapper for RoleEntity and PermissionEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface RoleMapper {

    RoleResponse toDto(RoleEntity entity);

    PermissionResponse toDto(PermissionEntity entity);

    List<RoleResponse> toDtoList(List<RoleEntity> entityList);

    List<PermissionResponse> toPermissionDtoList(List<PermissionEntity> entityList);
}
