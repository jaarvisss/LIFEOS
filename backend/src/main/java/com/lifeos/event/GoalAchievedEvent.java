package com.lifeos.event;

import java.util.UUID;

/**
 * Event published when a goal reaches 100% progress.
 */
public record GoalAchievedEvent(
        UUID goalId,
        UUID userId,
        String goalTitle
) {
}
