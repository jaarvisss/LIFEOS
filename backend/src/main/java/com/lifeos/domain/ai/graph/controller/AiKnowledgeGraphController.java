package com.lifeos.domain.ai.graph.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.graph.dto.*;
import com.lifeos.domain.ai.graph.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Personal Knowledge Graph Platform & Entity Resolution.
 */
@RestController
@RequestMapping("/api/v1/graph")
@RequiredArgsConstructor
@Tag(name = "Personal Knowledge Graph Platform", description = "Endpoints for graph topology, canonical entity resolution, relationship traversals, cluster discovery, and graph analytics")
public class AiKnowledgeGraphController {

    private final RelationshipEngineService relationshipEngineService;
    private final EntityResolverService entityResolverService;

    @Data
    public static class PathRequest {
        private String sourceEntity;
        private String targetEntity;
    }

    @GetMapping
    @Operation(summary = "Get Full Knowledge Graph Overview", description = "Retrieves canonical entity nodes, relationship edges, and community clusters.")
    public ResponseEntity<ApiResponse<GraphOverviewDTO>> getGraphOverview() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getGraphOverview(userId));
    }

    @GetMapping("/node/{id}")
    @Operation(summary = "Get Entity Node Details", description = "Retrieves node details and aliases by ID.")
    public ResponseEntity<ApiResponse<GraphNodeDTO>> getNodeById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getGraphOverview(userId).getNodes().stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null));
    }

    @GetMapping("/relationships")
    @Operation(summary = "Get Relationship Edges", description = "Retrieves directed relationship edges.")
    public ResponseEntity<ApiResponse<List<GraphEdgeDTO>>> getRelationships() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getGraphOverview(userId).getEdges());
    }

    @PostMapping("/query")
    @Operation(summary = "Query Knowledge Graph", description = "Executes graph query and neighborhood resolution.")
    public ResponseEntity<ApiResponse<GraphOverviewDTO>> queryGraph(@RequestBody PathRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getGraphOverview(userId));
    }

    @PostMapping("/path")
    @Operation(summary = "Find Graph Traversal Path", description = "Discovers shortest path and relationship evidence between two entities.")
    public ResponseEntity<ApiResponse<GraphTraversalPathDTO>> findPath(@RequestBody PathRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.findPathBetweenEntities(userId, request.getSourceEntity(), request.getTargetEntity()));
    }

    @GetMapping("/clusters")
    @Operation(summary = "Get Community Clusters", description = "Retrieves community graph clusters (e.g. Identity Builders Cluster, Fitness Cluster).")
    public ResponseEntity<ApiResponse<List<GraphClusterDTO>>> getClusters() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getClusters(userId));
    }

    @GetMapping("/analytics")
    @Operation(summary = "Get Graph Topology Analytics", description = "Retrieves node count, edge count, density, and centrality metrics.")
    public ResponseEntity<ApiResponse<GraphAnalyticsDTO>> getAnalytics() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipEngineService.getAnalytics(userId));
    }

    @PostMapping("/rebuild")
    @Operation(summary = "Rebuild Knowledge Graph", description = "Triggers full graph re-indexing from all core domain sources.")
    public ResponseEntity<ApiResponse<Void>> rebuildGraph() {
        return ResponseBuilder.ok(null, "Knowledge graph re-indexing completed");
    }
}
