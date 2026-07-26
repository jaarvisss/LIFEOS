package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO for Directed Knowledge Graph Relationship Edge.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphEdgeDTO {
    private UUID id;
    private UUID sourceNodeId;
    private String sourceNodeName;
    private UUID targetNodeId;
    private String targetNodeName;
    private String relationType; // WORKS_ON, BELONGS_TO, MENTIONS, RELATED_TO, ABOUT, CREATED_FROM
    private double confidenceScore;
}
