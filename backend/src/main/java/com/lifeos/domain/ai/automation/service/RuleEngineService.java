package com.lifeos.domain.ai.automation.service;

import com.lifeos.domain.ai.automation.dto.AutomationDefinitionDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Automation Rule Management and Condition Evaluation.
 */
public interface RuleEngineService {

    List<AutomationDefinitionDTO> getRegisteredAutomations();

    AutomationDefinitionDTO createAutomation(AutomationDefinitionDTO dto);

    void enableAutomation(UUID id);

    void disableAutomation(UUID id);
}
