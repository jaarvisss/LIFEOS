package com.lifeos.domain.ai.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for AI Life Operating Score metrics.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LifeScoreDTO {
    private int overallLifeScore;
    private int productivityScore;
    private int healthScore;
    private int financeScore;
    private int learningScore;
    private int relationshipScore;
    private String scoreSummaryExplanation;
}
