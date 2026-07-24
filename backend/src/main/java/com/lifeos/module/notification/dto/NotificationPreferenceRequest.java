package com.lifeos.module.notification.dto;

/**
 * Record DTO for Notification Preferences update request.
 */
public record NotificationPreferenceRequest(
        Boolean emailEnabled,
        Boolean pushEnabled,
        Boolean whatsappEnabled,
        Boolean smsEnabled
) {
}
