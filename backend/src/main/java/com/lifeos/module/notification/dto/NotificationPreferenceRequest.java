package com.lifeos.module.notification.dto;

import java.time.LocalTime;

/**
 * Record DTO for Notification Preferences update request.
 */
public record NotificationPreferenceRequest(
        LocalTime quietHoursStart,
        LocalTime quietHoursEnd
) {
}
