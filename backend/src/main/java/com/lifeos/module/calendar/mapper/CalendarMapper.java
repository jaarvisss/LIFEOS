package com.lifeos.module.calendar.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.calendar.dto.CalendarRequest;
import com.lifeos.module.calendar.dto.CalendarResponse;
import com.lifeos.module.calendar.entity.CalendarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for CalendarEntity.
 */
@Mapper(config = MapStructConfig.class)
public interface CalendarMapper {

    CalendarResponse toDto(CalendarEntity entity);

    CalendarEntity toEntity(CalendarRequest request);

    List<CalendarResponse> toDtoList(List<CalendarEntity> entityList);

    void updateEntityFromDto(CalendarRequest request, @MappingTarget CalendarEntity entity);
}
