package com.lifeos.domain.ai.graph.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for Graph Traversal Path & Reasoning Evidence Chain.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphTraversalPathDTO {
    private String query;
    private List<GraphNodeDTO> pathNodes;
    private List<GraphEdgeDTO> pathEdges;
    private String reasoningExplanation;
}
