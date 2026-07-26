package com.lifeos.domain.ai.graph.service;

import com.lifeos.domain.ai.graph.dto.*;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for Relationship Discovery, Graph Traversal, and Reasoning.
 */
public interface RelationshipEngineService {

    GraphOverviewDTO getGraphOverview(UUID userId);

    GraphTraversalPathDTO findPathBetweenEntities(UUID userId, String sourceEntity, String targetEntity);

    List<GraphClusterDTO> getClusters(UUID userId);

    GraphAnalyticsDTO getAnalytics(UUID userId);
}
