package com.lifeos.domain.memory.reasoning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for Explainable AI Recommendations.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {
    private String title;
    private String category; // Task, Goal, Health, Finance, Relationship
    private String action;
    private String reasoning;
    private double confidence;
    private String expectedImpact; // HIGH, MEDIUM, LOW
}
