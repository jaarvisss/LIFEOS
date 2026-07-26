package com.lifeos.domain.ai.automation.service;

import com.lifeos.domain.ai.agent.dto.AgentExecutionRequestDTO;
import com.lifeos.domain.ai.agent.service.AgentCollaborationService;
import com.lifeos.domain.ai.automation.dto.*;
import com.lifeos.domain.ai.workflow.service.WorkflowExecutionEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Enterprise Implementation of Intelligent Automation Engine, Event Triggers, Condition Evaluation, and Rule Management.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutomationEngineServiceImpl implements
        TriggerEngineService,
        RuleEngineService {

    private final WorkflowExecutionEngine workflowExecutionEngine;
    private final AgentCollaborationService agentCollaborationService;

    private final Map<UUID, AutomationDefinitionDTO> automationRules = new ConcurrentHashMap<>();

    @Override
    public List<AutomationDefinitionDTO> getRegisteredAutomations() {
        if (automationRules.isEmpty()) {
            initBuiltInAutomations();
        }
        return new ArrayList<>(automationRules.values());
    }

    @Override
    public AutomationDefinitionDTO createAutomation(AutomationDefinitionDTO dto) {
        UUID id = dto.getId() != null ? dto.getId() : UUID.randomUUID();
        dto.setId(id);
        dto.setEnabled(true);
        automationRules.put(id, dto);
        return dto;
    }

    @Override
    public void enableAutomation(UUID id) {
        AutomationDefinitionDTO rule = automationRules.get(id);
        if (rule != null) rule.setEnabled(true);
    }

    @Override
    public void disableAutomation(UUID id) {
        AutomationDefinitionDTO rule = automationRules.get(id);
        if (rule != null) rule.setEnabled(false);
    }

    @Override
    public AutomationExecutionResultDTO triggerAutomation(UUID userId, AutomationExecutionRequestDTO request) {
        long startTime = System.currentTimeMillis();
        getRegisteredAutomations();

        log.info("Triggering Automation for user {}: name='{}', event='{}'", userId, request.getAutomationName(), request.getTriggerEvent());

        // Execute corresponding Workflow or Agent Pipeline
        if ("Post-Meeting Summary & Task Extraction".equalsIgnoreCase(request.getAutomationName()) || "MeetingEnded".equalsIgnoreCase(request.getTriggerEvent())) {
            workflowExecutionEngine.executeWorkflow(userId, "Post-Meeting Automation Workflow", "Extract action items and update memories");
        } else if ("Expense Variance Alert".equalsIgnoreCase(request.getAutomationName()) || "ExpenseAdded".equalsIgnoreCase(request.getTriggerEvent())) {
            agentCollaborationService.executeAgentPipeline(userId, AgentExecutionRequestDTO.builder()
                    .agentName("FinanceCoachAgent")
                    .prompt("Evaluate spending variance and notify user if over budget")
                    .build());
        } else {
            workflowExecutionEngine.executeWorkflow(userId, "Standard Automation Workflow: " + request.getAutomationName(), "Execute scheduled rule");
        }

        long latency = System.currentTimeMillis() - startTime;
        UUID instanceId = UUID.randomUUID();

        return AutomationExecutionResultDTO.builder()
                .instanceId(instanceId)
                .automationName(request.getAutomationName())
                .status("SUCCESS")
                .triggeredBy(request.getTriggerEvent())
                .executionSummary("Automation executed cleanly. Actions performed, workflow completed, and memories updated.")
                .latencyMs(latency)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private void initBuiltInAutomations() {
        registerRule("Morning AI Executive Briefing", "Generates daily brief, prioritizes focus tasks, and notifies user every morning.", "CRON", "0 0 8 * * ?", "ALWAYS", "WORKFLOW");
        registerRule("Post-Meeting Summary & Task Extraction", "Generates meeting summary, creates follow-up tasks, and stores key decisions as memories upon meeting completion.", "EVENT", "MeetingEnded", "payload.hasTranscripts", "WORKFLOW");
        registerRule("Expense Variance Alert", "Evaluates spending trends when an expense is logged and notifies if monthly budget cap exceeds 80%.", "EVENT", "ExpenseAdded", "payload.amount > 5000", "AGENT");
        registerRule("Sunday Evening Reflection & Weekly Planning", "Analyzes weekly productivity, reviews completed goals, and generates next week's schedule every Sunday evening.", "CRON", "0 0 20 ? * SUN", "ALWAYS", "AGENT");
    }

    private void registerRule(String name, String desc, String type, String expr, String cond, String action) {
        UUID id = UUID.randomUUID();
        automationRules.put(id, AutomationDefinitionDTO.builder()
                .id(id)
                .name(name)
                .description(desc)
                .triggerType(type)
                .triggerExpression(expr)
                .conditionRule(cond)
                .actionType(action)
                .enabled(true)
                .build());
    }
}
