package com.lifeos.module.notification.mapper;

import com.lifeos.common.mapper.MapStructConfig;
import com.lifeos.module.notification.dto.DeliveryLogResponse;
import com.lifeos.module.notification.dto.NotificationChannelRequest;
import com.lifeos.module.notification.dto.NotificationChannelResponse;
import com.lifeos.module.notification.dto.NotificationPreferenceRequest;
import com.lifeos.module.notification.dto.NotificationPreferenceResponse;
import com.lifeos.module.notification.entity.DeliveryLogEntity;
import com.lifeos.module.notification.entity.NotificationChannelEntity;
import com.lifeos.module.notification.entity.NotificationPreferenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct Mapper for Notification entities and DTOs.
 */
@Mapper(config = MapStructConfig.class)
public interface NotificationMapper {

    NotificationPreferenceResponse toDto(NotificationPreferenceEntity entity);

    NotificationPreferenceEntity toEntity(NotificationPreferenceRequest request);

    void updateEntityFromDto(NotificationPreferenceRequest request, @MappingTarget NotificationPreferenceEntity entity);

    NotificationChannelResponse toDto(NotificationChannelEntity entity);

    NotificationChannelEntity toEntity(NotificationChannelRequest request);

    List<NotificationChannelResponse> toChannelDtoList(List<NotificationChannelEntity> entityList);

    DeliveryLogResponse toDto(DeliveryLogEntity entity);
}
