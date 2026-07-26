package com.lifeos.domain.memory.intelligence.gateway;

import lombok.Builder;
import lombok.Data;

/**
 * Provider-agnostic LLM Request DTO.
 */
@Data
@Builder
public class LlmRequest {
    private String prompt;
    private String systemInstruction;
    private double temperature;
    private int maxTokens;
}
