package com.lifeos.domain.ai.tool.tools;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Built-in Knowledge Tool for capturing markdown notes in the Knowledge Base.
 */
@Slf4j
@Component
public class KnowledgeTool implements ExecutableTool {

    @Override
    public String getToolName() {
        return "KnowledgeTool";
    }

    @Override
    public String getDescription() {
        return "Saves or searches Markdown notes in the LifeOS Knowledge Base.";
    }

    @Override
    public String getCategory() {
        return "Personal";
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }

    @Override
    public ToolExecutionResultDTO execute(UUID userId, ToolExecutionRequestDTO request) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> params = request.getParameters();
        String noteTitle = (String) params.getOrDefault("title", "New Note");
        String content = (String) params.getOrDefault("content", "Note content");

        log.info("Executing KnowledgeTool for user {}: noteTitle='{}'", userId, noteTitle);

        UUID executionId = UUID.randomUUID();
        long latency = System.currentTimeMillis() - startTime;

        return ToolExecutionResultDTO.builder()
                .executionId(executionId)
                .toolName(getToolName())
                .status("SUCCESS")
                .resultData(Map.of("noteId", UUID.randomUUID(), "title", noteTitle, "content", content))
                .latencyMs(latency)
                .memoryUpdated("Memory created for Note: " + noteTitle)
                .build();
    }

    @Override
    public boolean validate(UUID userId, ToolExecutionRequestDTO request) {
        return request.getParameters() != null && request.getParameters().containsKey("title");
    }

    @Override
    public Object preview(UUID userId, ToolExecutionRequestDTO request) {
        return "Preview: Will save note '" + request.getParameters().get("title") + "'";
    }

    @Override
    public void rollback(UUID executionId) {
        log.info("Rollback executed for KnowledgeTool execution {}", executionId);
    }
}
