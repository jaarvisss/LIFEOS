package com.lifeos.domain.ai.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Inter-Agent Delegation Trace.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentDelegationDTO {
    private UUID delegationId;
    private String parentAgentName;
    private String subAgentName;
    private String taskDescription;
    private String status; // COMPLETED
    private String resultSummary;
}
