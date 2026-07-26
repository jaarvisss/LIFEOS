package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * DTO for Knowledge Graph Canonical Entity Node.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphNodeDTO {
    private UUID id;
    private String canonicalName;
    private String entityType; // PERSON, TASK, PROJECT, GOAL, CALENDAR_EVENT, MEMORY, KNOWLEDGE_NOTE
    private List<String> aliases;
    private int importanceScore;
}
