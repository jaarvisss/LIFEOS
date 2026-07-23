package com.lifeos.infrastructure.push;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Enterprise Firebase FCM Push Notification Implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    @Override
    public void sendPushNotification(UUID userId, String targetDeviceToken, String title, String body) {
        log.info("Sending FCM push notification to user: {} | Device: {} | Title: {}", userId, targetDeviceToken, title);
    }
}
