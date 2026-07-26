package com.lifeos.domain.ai.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * DTO for Autonomous AI Agent metadata and capabilities.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {
    private UUID id;
    private String agentName;
    private String agentRole;
    private String description;
    private String communicationStyle;
    private String expertise;
    private boolean enabled;
    private List<String> supportedTools;
}
