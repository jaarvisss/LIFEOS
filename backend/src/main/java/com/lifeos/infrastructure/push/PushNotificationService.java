package com.lifeos.infrastructure.push;

import java.util.UUID;

/**
 * Interface for Firebase Push Notification infrastructure.
 */
public interface PushNotificationService {

    void sendPushNotification(UUID userId, String targetDeviceToken, String title, String body);
}
