package com.lifeos.module.notification.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.notification.dto.DeliveryLogResponse;
import com.lifeos.module.notification.dto.NotificationChannelRequest;
import com.lifeos.module.notification.dto.NotificationChannelResponse;
import com.lifeos.module.notification.dto.NotificationPreferenceRequest;
import com.lifeos.module.notification.dto.NotificationPreferenceResponse;
import com.lifeos.module.notification.entity.DeliveryLogEntity;
import com.lifeos.module.notification.entity.NotificationChannelEntity;
import com.lifeos.module.notification.entity.NotificationPreferenceEntity;
import com.lifeos.module.notification.mapper.NotificationMapper;
import com.lifeos.module.notification.repository.DeliveryLogRepository;
import com.lifeos.module.notification.repository.NotificationChannelRepository;
import com.lifeos.module.notification.repository.NotificationPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of NotificationService interface.
 */
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final NotificationChannelRepository notificationChannelRepository;
    private final DeliveryLogRepository deliveryLogRepository;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional(readOnly = true)
    public NotificationPreferenceResponse getPreferences(UUID userId) {
        NotificationPreferenceEntity entity = notificationPreferenceRepository.findById(userId)
                .orElseGet(() -> NotificationPreferenceEntity.builder().userId(userId).build());
        return notificationMapper.toDto(entity);
    }

    @Override
    @Transactional
    public NotificationPreferenceResponse updatePreferences(UUID userId, NotificationPreferenceRequest request) {
        NotificationPreferenceEntity entity = notificationPreferenceRepository.findById(userId)
                .orElseGet(() -> NotificationPreferenceEntity.builder().userId(userId).build());

        notificationMapper.updateEntityFromDto(request, entity);
        entity = notificationPreferenceRepository.save(entity);
        return notificationMapper.toDto(entity);
    }

    @Override
    @Transactional
    public NotificationChannelResponse createChannel(UUID userId, NotificationChannelRequest request) {
        NotificationChannelEntity channel = notificationMapper.toEntity(request);
        channel.setUserId(userId);
        channel = notificationChannelRepository.save(channel);
        return notificationMapper.toDto(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationChannelResponse> getChannels(UUID userId) {
        return notificationMapper.toChannelDtoList(notificationChannelRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public void deleteChannel(UUID userId, UUID channelId) {
        NotificationChannelEntity channel = notificationChannelRepository.findById(channelId)
                .filter(c -> c.getUserId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("NotificationChannel", "id", channelId));
        notificationChannelRepository.delete(channel);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<DeliveryLogResponse> getDeliveryLogs(UUID userId, PaginationRequest paginationRequest) {
        Page<DeliveryLogEntity> page = deliveryLogRepository.findByUserId(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(notificationMapper::toDto));
    }
}
