package com.lifeos.module.notification.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Record DTO for Notification Channel creation request.
 */
public record NotificationChannelRequest(
        @NotBlank(message = "Channel type is required")
        String channelType,

        @NotBlank(message = "Destination is required")
        String destination,

        Boolean isEnabled
) {
}
