package com.lifeos.domain.ai.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for AI Predictive Modeling Output.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardPredictionDTO {
    private UUID id;
    private String targetMetric;
    private int probabilityPercentage;
    private String explanation;
    private String suggestedMitigation;
}
