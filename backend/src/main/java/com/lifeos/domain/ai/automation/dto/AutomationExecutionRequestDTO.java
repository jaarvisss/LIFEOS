package com.lifeos.domain.ai.automation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for Triggering an Automation Rule.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationExecutionRequestDTO {
    private String automationName;
    private String triggerEvent; // TaskCompleted, ExpenseAdded, MeetingEnded, Cron8AM
    private Map<String, Object> payload;
}
