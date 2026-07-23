package com.lifeos.event;

import java.util.UUID;

/**
 * Event published when a task is marked completed.
 */
public record TaskCompletedEvent(
        UUID taskId,
        UUID userId,
        String taskTitle
) {
}
