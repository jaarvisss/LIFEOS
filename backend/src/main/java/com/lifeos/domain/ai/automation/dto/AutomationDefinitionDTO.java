package com.lifeos.domain.ai.automation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Automation Rule Definitions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationDefinitionDTO {
    private UUID id;
    private String name;
    private String description;
    private String triggerType; // CRON, EVENT, CONDITIONAL
    private String triggerExpression;
    private String conditionRule;
    private String actionType; // WORKFLOW, AGENT, TOOL, NOTIFICATION
    private boolean enabled;
}
