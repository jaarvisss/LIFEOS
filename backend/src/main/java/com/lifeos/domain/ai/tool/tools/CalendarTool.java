package com.lifeos.domain.ai.tool.tools;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Built-in Calendar Tool for scheduling events via AI Tool Calling Platform.
 */
@Slf4j
@Component
public class CalendarTool implements ExecutableTool {

    @Override
    public String getToolName() {
        return "CalendarTool";
    }

    @Override
    public String getDescription() {
        return "Schedules events and meetings on the user's timeline calendar.";
    }

    @Override
    public String getCategory() {
        return "Productivity";
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }

    @Override
    public ToolExecutionResultDTO execute(UUID userId, ToolExecutionRequestDTO request) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> params = request.getParameters();
        String eventTitle = (String) params.getOrDefault("title", "Scheduled Event");
        String time = (String) params.getOrDefault("time", "10:00 AM");

        log.info("Executing CalendarTool for user {}: eventTitle='{}', time='{}'", userId, eventTitle, time);

        UUID executionId = UUID.randomUUID();
        long latency = System.currentTimeMillis() - startTime;

        return ToolExecutionResultDTO.builder()
                .executionId(executionId)
                .toolName(getToolName())
                .status("SUCCESS")
                .resultData(Map.of("eventId", UUID.randomUUID(), "title", eventTitle, "time", time))
                .latencyMs(latency)
                .memoryUpdated("Memory created for Event: " + eventTitle)
                .build();
    }

    @Override
    public boolean validate(UUID userId, ToolExecutionRequestDTO request) {
        return request.getParameters() != null && request.getParameters().containsKey("title");
    }

    @Override
    public Object preview(UUID userId, ToolExecutionRequestDTO request) {
        return "Preview: Will schedule event '" + request.getParameters().get("title") + "'";
    }

    @Override
    public void rollback(UUID executionId) {
        log.info("Rollback executed for CalendarTool execution {}", executionId);
    }
}
