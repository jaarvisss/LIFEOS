package com.lifeos.module.notification.dto;

import java.util.UUID;

/**
 * Record DTO for Notification Preferences response.
 */
public record NotificationPreferenceResponse(
        UUID userId,
        Boolean emailEnabled,
        Boolean pushEnabled,
        Boolean whatsappEnabled,
        Boolean smsEnabled
) {
}
