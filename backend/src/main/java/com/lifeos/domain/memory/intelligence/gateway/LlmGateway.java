package com.lifeos.domain.memory.intelligence.gateway;

/**
 * Provider-agnostic LLM Gateway interface supporting Gemini, OpenAI, Anthropic, Ollama, etc.
 */
public interface LlmGateway {

    /**
     * Sends a text prompt request to the underlying LLM provider.
     */
    LlmResponse generateText(LlmRequest request);

    /**
     * Gets the active provider identifier (e.g. "GEMINI", "OPENAI").
     */
    String getProviderName();
}
