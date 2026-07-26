package com.lifeos.domain.memory.reasoning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Adaptive AI Planning & Roadmaps.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponseDTO {
    private String planTitle;
    private String timeframe; // daily, weekly, monthly
    private List<String> priorityActions;
    private List<String> scheduleBlocks;
    private List<String> contingencyNotes;
    private double expectedSuccessScore;
}
