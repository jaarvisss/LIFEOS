package com.lifeos.domain.ai;

import com.lifeos.domain.ai.graph.dto.*;
import com.lifeos.domain.ai.graph.service.KnowledgeGraphServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Suite for Personal Knowledge Graph Platform & Entity Resolution.
 */
class AiKnowledgeGraphServiceTest {

    @Test
    @DisplayName("Should resolve duplicate entity aliases to canonical graph node")
    void shouldResolveCanonicalEntity() {
        KnowledgeGraphServiceImpl service = new KnowledgeGraphServiceImpl();
        UUID userId = UUID.randomUUID();

        GraphNodeDTO resolved1 = service.resolveOrCreateEntity(userId, "Rahul", "PERSON", List.of("Rahul Sharma"));
        assertNotNull(resolved1);
        assertEquals("Rahul", resolved1.getCanonicalName());

        GraphNodeDTO resolved2 = service.resolveOrCreateEntity(userId, "Rahul Sharma", "PERSON", List.of());
        assertEquals(resolved1.getId(), resolved2.getId());
    }

    @Test
    @DisplayName("Should discover traversal path and relationship evidence between two entities")
    void shouldFindTraversalPath() {
        KnowledgeGraphServiceImpl service = new KnowledgeGraphServiceImpl();
        UUID userId = UUID.randomUUID();

        GraphTraversalPathDTO path = service.findPathBetweenEntities(userId, "Rahul", "Identity Builders");

        assertNotNull(path);
        assertFalse(path.getPathNodes().isEmpty());
        assertTrue(path.getReasoningExplanation().contains("WORKS_ON"));
    }

    @Test
    @DisplayName("Should compute graph topology analytics and community clusters")
    void shouldGetAnalyticsAndClusters() {
        KnowledgeGraphServiceImpl service = new KnowledgeGraphServiceImpl();
        UUID userId = UUID.randomUUID();

        GraphAnalyticsDTO analytics = service.getAnalytics(userId);
        assertNotNull(analytics);
        assertTrue(analytics.getTotalNodes() >= 4);
        assertTrue(analytics.getTotalEdges() >= 3);

        List<GraphClusterDTO> clusters = service.getClusters(userId);
        assertNotNull(clusters);
        assertFalse(clusters.isEmpty());
    }
}
