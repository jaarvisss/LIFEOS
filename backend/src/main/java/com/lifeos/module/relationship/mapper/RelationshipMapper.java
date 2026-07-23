package com.lifeos.module.relationship.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.relationship.dto.MilestoneRequest;
import com.lifeos.module.relationship.dto.MilestoneResponse;
import com.lifeos.module.relationship.dto.RelationshipRequest;
import com.lifeos.module.relationship.dto.RelationshipResponse;
import com.lifeos.module.relationship.entity.MilestoneEntity;
import com.lifeos.module.relationship.entity.RelationshipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Relationship and Milestone entities.
 */
@Mapper(config = MapStructConfig.class)
public interface RelationshipMapper {

    RelationshipResponse toDto(RelationshipEntity entity);

    RelationshipEntity toEntity(RelationshipRequest request);

    List<RelationshipResponse> toDtoList(List<RelationshipEntity> entityList);

    void updateEntityFromDto(RelationshipRequest request, @MappingTarget RelationshipEntity entity);

    MilestoneResponse toDto(MilestoneEntity entity);

    MilestoneEntity toEntity(MilestoneRequest request);

    List<MilestoneResponse> toMilestoneDtoList(List<MilestoneEntity> entityList);
}
