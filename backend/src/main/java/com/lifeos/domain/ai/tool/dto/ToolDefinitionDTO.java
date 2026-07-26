package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Registered Tool Definition & Capability Discovery.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolDefinitionDTO {
    private String toolName;
    private String description;
    private String category; // Productivity, Personal, Finance, Health, System
    private String version;
    private boolean enabled;
    private boolean requiresConfirmation;
    private String inputSchema;
    private String outputSchema;
}
