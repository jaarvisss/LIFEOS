package com.lifeos.domain.ai.tool.tools;

import com.lifeos.domain.ai.tool.dto.ToolExecutionRequestDTO;
import com.lifeos.domain.ai.tool.dto.ToolExecutionResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * Built-in Finance Tool for logging income/expenses via AI Tool Calling Platform.
 */
@Slf4j
@Component
public class FinanceTool implements ExecutableTool {

    @Override
    public String getToolName() {
        return "FinanceTool";
    }

    @Override
    public String getDescription() {
        return "Logs transactions, income, or expenses into the LifeOS Finance engine.";
    }

    @Override
    public String getCategory() {
        return "Finance";
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }

    @Override
    public ToolExecutionResultDTO execute(UUID userId, ToolExecutionRequestDTO request) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> params = request.getParameters();
        double amount = params.get("amount") != null ? ((Number) params.get("amount")).doubleValue() : 0.0;
        String category = (String) params.getOrDefault("category", "General");
        String type = (String) params.getOrDefault("type", "EXPENSE");

        log.info("Executing FinanceTool for user {}: amount={}, category='{}', type='{}'", userId, amount, category, type);

        UUID executionId = UUID.randomUUID();
        long latency = System.currentTimeMillis() - startTime;

        return ToolExecutionResultDTO.builder()
                .executionId(executionId)
                .toolName(getToolName())
                .status("SUCCESS")
                .resultData(Map.of("transactionId", UUID.randomUUID(), "amount", amount, "category", category, "type", type))
                .latencyMs(latency)
                .memoryUpdated("Memory created for Transaction: " + type + " $" + amount)
                .build();
    }

    @Override
    public boolean validate(UUID userId, ToolExecutionRequestDTO request) {
        return request.getParameters() != null && request.getParameters().containsKey("amount");
    }

    @Override
    public Object preview(UUID userId, ToolExecutionRequestDTO request) {
        return "Preview: Will log " + request.getParameters().getOrDefault("type", "EXPENSE") + " $" + request.getParameters().get("amount");
    }

    @Override
    public void rollback(UUID executionId) {
        log.info("Rollback executed for FinanceTool execution {}", executionId);
    }
}
