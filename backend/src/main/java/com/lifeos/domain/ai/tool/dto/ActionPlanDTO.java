package com.lifeos.domain.ai.tool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Multi-Tool Action Execution Plan.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionPlanDTO {
    private String goal;
    private List<ActionStepDTO> steps;
    private double confidenceScore;
    private int estimatedTotalTimeMs;
}
