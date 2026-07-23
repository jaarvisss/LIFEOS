package com.lifeos.module.people.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.people.dto.ContactRequest;
import com.lifeos.module.people.dto.ContactResponse;
import com.lifeos.module.people.entity.ContactEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for ContactEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface ContactMapper {

    ContactResponse toDto(ContactEntity entity);

    ContactEntity toEntity(ContactRequest request);

    List<ContactResponse> toDtoList(List<ContactEntity> entityList);

    void updateEntityFromDto(ContactRequest request, @MappingTarget ContactEntity entity);
}
