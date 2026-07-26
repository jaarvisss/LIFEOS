package com.lifeos.domain.ai.agent.service;

import com.lifeos.domain.ai.agent.dto.AgentDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Agent Registry & Discovery.
 */
public interface AgentRegistryService {

    List<AgentDTO> getRegisteredAgents();

    AgentDTO getAgentByName(String agentName);

    AgentDTO getAgentById(UUID id);

    void enableAgent(String agentName);

    void disableAgent(String agentName);
}
