package com.lifeos.domain.memory.reasoning.service;

import com.lifeos.domain.memory.reasoning.dto.ProactiveInsightDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Unprompted Proactive Insights.
 */
public interface InsightService {

    List<ProactiveInsightDTO> generateProactiveInsights(UUID userId);
}
