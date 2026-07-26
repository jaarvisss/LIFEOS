package com.lifeos.domain.ai.tool.service;

import com.lifeos.domain.ai.tool.dto.ActionPlanDTO;

import java.util.UUID;

/**
 * Service interface for Multi-Tool Action Planning.
 */
public interface ActionPlannerService {

    ActionPlanDTO planAction(UUID userId, String userIntent);
}
