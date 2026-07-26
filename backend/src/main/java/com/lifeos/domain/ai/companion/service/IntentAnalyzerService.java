package com.lifeos.domain.ai.companion.service;

import java.util.Map;

/**
 * Service interface for Intent Analysis.
 */
public interface IntentAnalyzerService {

    Map<String, Object> analyzeIntent(String userMessage);
}
