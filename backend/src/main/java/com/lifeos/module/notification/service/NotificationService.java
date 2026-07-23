package com.lifeos.module.notification.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.notification.dto.DeliveryLogResponse;
import com.lifeos.module.notification.dto.NotificationChannelRequest;
import com.lifeos.module.notification.dto.NotificationChannelResponse;
import com.lifeos.module.notification.dto.NotificationPreferenceRequest;
import com.lifeos.module.notification.dto.NotificationPreferenceResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Notification Preferences, Channels, and Delivery Logs.
 */
public interface NotificationService {

    NotificationPreferenceResponse getPreferences(UUID userId);

    NotificationPreferenceResponse updatePreferences(UUID userId, NotificationPreferenceRequest request);

    NotificationChannelResponse createChannel(UUID userId, NotificationChannelRequest request);

    List<NotificationChannelResponse> getChannels(UUID userId);

    void deleteChannel(UUID userId, UUID channelId);

    PagedResponse<DeliveryLogResponse> getDeliveryLogs(UUID userId, PaginationRequest paginationRequest);
}
