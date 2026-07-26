package com.lifeos.domain.ai.agent.service;

import com.lifeos.domain.ai.agent.dto.AgentExecutionRequestDTO;
import com.lifeos.domain.ai.agent.dto.AgentExecutionResponseDTO;

import java.util.UUID;

/**
 * Service interface for Multi-Agent Collaboration and Execution.
 */
public interface AgentCollaborationService {

    AgentExecutionResponseDTO executeAgentPipeline(UUID userId, AgentExecutionRequestDTO request);
}
