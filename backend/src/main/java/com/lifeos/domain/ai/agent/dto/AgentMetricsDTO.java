package com.lifeos.domain.ai.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Agent Observability & Performance Metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentMetricsDTO {
    private String agentName;
    private long totalExecutions;
    private double successRate;
    private double avgLatencyMs;
    private long totalDelegations;
}
