package com.lifeos.module.task.dto;

import java.util.UUID;

/**
 * Record DTO for Subtask response.
 */
public record SubtaskResponse(
        UUID id,
        UUID taskId,
        String title,
        Boolean isCompleted,
        Integer sequenceOrder
) {
}
