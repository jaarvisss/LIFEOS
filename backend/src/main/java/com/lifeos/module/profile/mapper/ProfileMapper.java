package com.lifeos.module.profile.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.profile.dto.PersonaPreferencesRequest;
import com.lifeos.module.profile.dto.PersonaPreferencesResponse;
import com.lifeos.module.profile.dto.UserProfileRequest;
import com.lifeos.module.profile.dto.UserProfileResponse;
import com.lifeos.module.profile.dto.WorkLifeBoundariesRequest;
import com.lifeos.module.profile.dto.WorkLifeBoundariesResponse;
import com.lifeos.module.profile.entity.PersonaPreferencesEntity;
import com.lifeos.module.profile.entity.UserProfileEntity;
import com.lifeos.module.profile.entity.WorkLifeBoundariesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * MapStruct Mapper for Profile entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface ProfileMapper {

    UserProfileResponse toDto(UserProfileEntity entity);

    UserProfileEntity toEntity(UserProfileRequest request);

    void updateEntityFromDto(UserProfileRequest request, @MappingTarget UserProfileEntity entity);

    PersonaPreferencesResponse toDto(PersonaPreferencesEntity entity);

    void updateEntityFromDto(PersonaPreferencesRequest request, @MappingTarget PersonaPreferencesEntity entity);

    WorkLifeBoundariesResponse toDto(WorkLifeBoundariesEntity entity);

    void updateEntityFromDto(WorkLifeBoundariesRequest request, @MappingTarget WorkLifeBoundariesEntity entity);
}
