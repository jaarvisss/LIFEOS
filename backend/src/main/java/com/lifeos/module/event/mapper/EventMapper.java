package com.lifeos.module.event.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.event.dto.EventRequest;
import com.lifeos.module.event.dto.EventResponse;
import com.lifeos.module.event.dto.ReminderRequest;
import com.lifeos.module.event.dto.ReminderResponse;
import com.lifeos.module.event.entity.EventEntity;
import com.lifeos.module.event.entity.ReminderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Event and Reminder entities.
 */
@Mapper(config = MapStructConfig.class)
public interface EventMapper {

    EventResponse toDto(EventEntity entity);

    EventEntity toEntity(EventRequest request);

    List<EventResponse> toDtoList(List<EventEntity> entityList);

    void updateEntityFromDto(EventRequest request, @MappingTarget EventEntity entity);

    ReminderResponse toDto(ReminderEntity entity);

    ReminderEntity toEntity(ReminderRequest request);

    List<ReminderResponse> toReminderDtoList(List<ReminderEntity> entityList);
}
