package com.lifeos.domain.ai.workflow.service;

import com.lifeos.domain.ai.workflow.dto.AgentRegistrationDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Agent Runtime Management.
 */
public interface AgentRuntimeService {

    AgentRegistrationDTO registerAgent(String agentName, String agentRole, String description);

    List<AgentRegistrationDTO> getRegisteredAgents();
}
