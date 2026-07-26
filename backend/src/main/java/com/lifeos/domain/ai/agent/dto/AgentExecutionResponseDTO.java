package com.lifeos.domain.ai.agent.dto;

import com.lifeos.domain.ai.workflow.dto.WorkflowInstanceDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Agent Execution Output.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentExecutionResponseDTO {
    private String primaryAgentName;
    private String responseSummary;
    private double confidenceScore;
    private WorkflowInstanceDTO workflowInstance;
    private List<AgentDelegationDTO> delegations;
    private List<String> proactiveRecommendations;
}
