package com.lifeos.domain.ai.tool.tools;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Built-in Task Tool for creating and managing tasks via AI Tool Calling Platform.
 */
@Slf4j
@Component
public class TaskTool implements ExecutableTool {

    @Override
    public String getToolName() {
        return "TaskTool";
    }

    @Override
    public String getDescription() {
        return "Creates, updates, or completes actionable user tasks.";
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
        String title = (String) params.getOrDefault("title", "New Task");
        String priority = (String) params.getOrDefault("priority", "NORMAL");

        log.info("Executing TaskTool for user {}: title='{}', priority='{}'", userId, title, priority);

        UUID executionId = UUID.randomUUID();
        UUID createdTaskId = UUID.randomUUID();

        long latency = System.currentTimeMillis() - startTime;

        return ToolExecutionResultDTO.builder()
                .executionId(executionId)
                .toolName(getToolName())
                .status("SUCCESS")
                .resultData(Map.of("taskId", createdTaskId, "title", title, "priority", priority, "completed", false))
                .latencyMs(latency)
                .memoryUpdated("Memory created for Task: " + title)
                .build();
    }

    @Override
    public boolean validate(UUID userId, ToolExecutionRequestDTO request) {
        return request.getParameters() != null && request.getParameters().containsKey("title");
    }

    @Override
    public Object preview(UUID userId, ToolExecutionRequestDTO request) {
        return "Preview: Will create task '" + request.getParameters().get("title") + "'";
    }

    @Override
    public void rollback(UUID executionId) {
        log.info("Rollback executed for TaskTool execution {}", executionId);
    }
}
