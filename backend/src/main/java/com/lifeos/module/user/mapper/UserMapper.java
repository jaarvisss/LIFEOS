package com.lifeos.module.user.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.user.dto.UserResponse;
import com.lifeos.module.user.entity.RoleEntity;
import com.lifeos.module.user.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * MapStruct Mapper for UserEntity <-> UserResponse.
 */
@Mapper(config = MapStructConfig.class)
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStringList")
    UserResponse toDto(UserEntity entity);

    List<UserResponse> toDtoList(List<UserEntity> entityList);

    @Named("mapRolesToStringList")
    default List<String> mapRolesToStringList(Set<RoleEntity> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }
        return roles.stream()
                .map(RoleEntity::getRoleCode)
                .collect(Collectors.toList());
    }
}
