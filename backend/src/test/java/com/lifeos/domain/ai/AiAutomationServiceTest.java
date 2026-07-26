package com.lifeos.domain.ai;

import com.lifeos.domain.ai.agent.service.AgentCollaborationService;
import com.lifeos.domain.ai.automation.dto.*;
import com.lifeos.domain.ai.automation.service.AutomationEngineServiceImpl;
import com.lifeos.domain.ai.workflow.service.WorkflowExecutionEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for Intelligent Automation Engine & Event Triggers.
 */
class AiAutomationServiceTest {

    @Test
    @DisplayName("Should discover pre-packaged built-in automation rules and templates")
    void shouldDiscoverBuiltInAutomations() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        AgentCollaborationService mockAgentService = Mockito.mock(AgentCollaborationService.class);

        AutomationEngineServiceImpl service = new AutomationEngineServiceImpl(mockWorkflowEngine, mockAgentService);

        List<AutomationDefinitionDTO> rules = service.getRegisteredAutomations();

        assertNotNull(rules);
        assertTrue(rules.size() >= 4);
        assertTrue(rules.stream().anyMatch(r -> r.getName().equals("Morning AI Executive Briefing")));
        assertTrue(rules.stream().anyMatch(r -> r.getName().equals("Expense Variance Alert")));
    }

    @Test
    @DisplayName("Should trigger event-driven automation and execute Workflow Runtime & Agent Platform")
    void shouldTriggerAutomationRule() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        AgentCollaborationService mockAgentService = Mockito.mock(AgentCollaborationService.class);

        AutomationEngineServiceImpl service = new AutomationEngineServiceImpl(mockWorkflowEngine, mockAgentService);
        UUID userId = UUID.randomUUID();

        AutomationExecutionResultDTO result = service.triggerAutomation(userId, AutomationExecutionRequestDTO.builder()
                .automationName("Post-Meeting Summary & Task Extraction")
                .triggerEvent("MeetingEnded")
                .payload(Map.of("meetingId", UUID.randomUUID()))
                .build());

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("MeetingEnded", result.getTriggeredBy());
        assertNotNull(result.getExecutionSummary());
    }

    @Test
    @DisplayName("Should create and toggle enable/disable state for automation rules")
    void shouldManageAutomationRuleLifecycle() {
        WorkflowExecutionEngine mockWorkflowEngine = Mockito.mock(WorkflowExecutionEngine.class);
        AgentCollaborationService mockAgentService = Mockito.mock(AgentCollaborationService.class);

        AutomationEngineServiceImpl service = new AutomationEngineServiceImpl(mockWorkflowEngine, mockAgentService);

        AutomationDefinitionDTO created = service.createAutomation(AutomationDefinitionDTO.builder()
                .name("Custom Habit Automation")
                .description("Remind user if habit streak drops")
                .triggerType("CONDITIONAL")
                .triggerExpression("streak < 3")
                .actionType("NOTIFICATION")
                .build());

        assertNotNull(created);
        assertTrue(created.isEnabled());

        service.disableAutomation(created.getId());
        assertFalse(service.getRegisteredAutomations().stream().filter(r -> r.getId().equals(created.getId())).findFirst().get().isEnabled());
    }
}
