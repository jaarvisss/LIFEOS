package com.lifeos.domain.memory.intelligence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Extracted Memory payload.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractedMemoryDTO {
    private String title;
    private String summary;
    private String content;
    private String memoryType;
    private String importanceLevel;
    private int importanceScore;
    private String importanceReasoning;
    private List<ExtractedEntityDTO> entities;
    private double confidence;
    private String language;
    private List<String> tags;
    private String source;
    private List<String> relatedTopics;
    private String suggestedCategory;
    private String suggestedPriority;
    private List<String> suggestedReminders;
    private List<String> followUpActions;
}
