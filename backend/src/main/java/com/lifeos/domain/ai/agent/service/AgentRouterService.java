package com.lifeos.domain.ai.agent.service;

import com.lifeos.domain.ai.agent.dto.AgentDTO;

import java.util.UUID;

/**
 * Service interface for Agent Routing.
 */
public interface AgentRouterService {

    AgentDTO routeUserRequest(UUID userId, String requestPrompt);
}
