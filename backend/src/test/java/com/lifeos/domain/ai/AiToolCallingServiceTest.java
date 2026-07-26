package com.lifeos.domain.ai;

import com.lifeos.domain.ai.tool.dto.*;
import com.lifeos.domain.ai.tool.service.ToolCallingServiceImpl;
import com.lifeos.domain.ai.tool.tools.CalendarTool;
import com.lifeos.domain.ai.tool.tools.FinanceTool;
import com.lifeos.domain.ai.tool.tools.KnowledgeTool;
import com.lifeos.domain.ai.tool.tools.TaskTool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for AI Tool Calling Platform & Action Orchestration.
 */
class AiToolCallingServiceTest {

    @Test
    @DisplayName("Should register and discover capabilities across built-in tools")
    void shouldDiscoverRegisteredTools() {
        TaskTool taskTool = new TaskTool();
        CalendarTool calendarTool = new CalendarTool();
        KnowledgeTool knowledgeTool = new KnowledgeTool();
        FinanceTool financeTool = new FinanceTool();

        ToolCallingServiceImpl service = new ToolCallingServiceImpl(List.of(taskTool, calendarTool, knowledgeTool, financeTool));

        List<ToolDefinitionDTO> tools = service.getRegisteredTools();
        assertNotNull(tools);
        assertEquals(4, tools.size());

        List<String> categories = service.getToolCategories();
        assertTrue(categories.contains("Productivity"));
        assertTrue(categories.contains("Finance"));
    }

    @Test
    @DisplayName("Should generate multi-tool action plan for complex user intent")
    void shouldGenerateMultiToolActionPlan() {
        TaskTool taskTool = new TaskTool();
        CalendarTool calendarTool = new CalendarTool();
        KnowledgeTool knowledgeTool = new KnowledgeTool();
        FinanceTool financeTool = new FinanceTool();

        ToolCallingServiceImpl service = new ToolCallingServiceImpl(List.of(taskTool, calendarTool, knowledgeTool, financeTool));
        UUID userId = UUID.randomUUID();

        ActionPlanDTO plan = service.planAction(userId, "Schedule a project meeting tomorrow and add ₹500 grocery expense");

        assertNotNull(plan);
        assertTrue(plan.getSteps().size() >= 2);
        assertTrue(plan.getConfidenceScore() > 0.9);
    }

    @Test
    @DisplayName("Should execute TaskTool and return success status with memory updated")
    void shouldExecuteTaskTool() {
        TaskTool taskTool = new TaskTool();
        CalendarTool calendarTool = new CalendarTool();

        ToolCallingServiceImpl service = new ToolCallingServiceImpl(List.of(taskTool, calendarTool));
        UUID userId = UUID.randomUUID();

        ToolExecutionResultDTO result = service.executeTool(userId, ToolExecutionRequestDTO.builder()
                .toolName("TaskTool")
                .parameters(Map.of("title", "Call Rahul tomorrow", "priority", "HIGH"))
                .build());

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("TaskTool", result.getToolName());
        assertNotNull(result.getMemoryUpdated());
    }
}
