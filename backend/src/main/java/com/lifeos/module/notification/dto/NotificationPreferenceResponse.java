package com.lifeos.module.notification.dto;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Record DTO for Notification Preferences response.
 */
public record NotificationPreferenceResponse(
        UUID userId,
        LocalTime quietHoursStart,
        LocalTime quietHoursEnd
) {
}
