package com.lifeos.module.notification.dto;

import java.util.UUID;

/**
 * Record DTO for Notification Channel response.
 */
public record NotificationChannelResponse(
        UUID id,
        UUID userId,
        String channelType,
        String destination,
        Boolean isVerified
) {
}
