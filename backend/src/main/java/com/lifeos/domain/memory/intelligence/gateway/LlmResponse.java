package com.lifeos.domain.memory.intelligence.gateway;

import lombok.Builder;
import lombok.Data;

/**
 * Provider-agnostic LLM Response DTO.
 */
@Data
@Builder
public class LlmResponse {
    private String content;
    private String modelId;
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;
    private long latencyMs;
    private boolean successful;
    private String errorMessage;
}
