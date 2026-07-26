package com.lifeos.domain.ai.automation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Automation Execution Output.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationExecutionResultDTO {
    private UUID instanceId;
    private String automationName;
    private String status; // SUCCESS, FAILED, SKIPPED_CONDITION
    private String triggeredBy;
    private String executionSummary;
    private long latencyMs;
    private LocalDateTime timestamp;
}
