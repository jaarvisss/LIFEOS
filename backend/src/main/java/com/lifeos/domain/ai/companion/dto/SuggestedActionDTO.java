package com.lifeos.domain.ai.companion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Proactive Suggested Quick Actions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedActionDTO {
    private String actionType; // CREATE_TASK, CREATE_REMINDER, SAVE_NOTE, OPEN_CALENDAR, VIEW_TIMELINE
    private String title;
    private String reasoning;
    private double confidence;
}
