package com.lifeos.domain.memory.reasoning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Multi-step AI Reasoning and Conclusion Output.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasoningResponseDTO {
    private String conclusion;
    private String reasoningExplanation;
    private List<String> evidenceUsed;
    private double confidenceScore;
    private List<String> detectedContradictions;
    private List<String> alternativeHypotheses;
}
