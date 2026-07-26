package com.lifeos.domain.ai.automation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for Automation History Record.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AutomationInstanceDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private String status;
    private String triggeredBy;
    private String executionResultJson;
    private LocalDateTime createdAt;
}
