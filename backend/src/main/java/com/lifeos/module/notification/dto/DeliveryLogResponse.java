package com.lifeos.module.notification.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Delivery Log response.
 */
public record DeliveryLogResponse(
        UUID id,
        UUID userId,
        UUID channelId,
        String notificationType,
        String title,
        String body,
        String status,
        Instant sentAt,
        Instant createdAt
) {
}
