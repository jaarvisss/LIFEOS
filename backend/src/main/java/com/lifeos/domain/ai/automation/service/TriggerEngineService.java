package com.lifeos.domain.ai.automation.service;

import com.lifeos.domain.ai.automation.dto.AutomationExecutionRequestDTO;
import com.lifeos.domain.ai.automation.dto.AutomationExecutionResultDTO;

import java.util.UUID;

/**
 * Service interface for Event-Driven and Time-Based Automation Triggers.
 */
public interface TriggerEngineService {

    AutomationExecutionResultDTO triggerAutomation(UUID userId, AutomationExecutionRequestDTO request);
}
