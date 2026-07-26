package com.lifeos.domain.ai.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Agent Execution Request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentExecutionRequestDTO {
    private String agentName;
    private String prompt;
    private boolean enableMultiAgentCollaboration = true;
}
