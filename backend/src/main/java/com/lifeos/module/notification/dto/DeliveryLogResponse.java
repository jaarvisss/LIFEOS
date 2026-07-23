package com.lifeos.module.notification.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Record DTO for Delivery Log response.
 */
public record DeliveryLogResponse(
        UUID id,
        UUID userId,
        String channelType,
        String subject,
        String content,
        String status,
        String errorMessage,
        Instant sentAt,
        Instant createdAt
) {
}
