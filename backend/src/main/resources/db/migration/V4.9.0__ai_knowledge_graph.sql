-- V4.9.0__ai_knowledge_graph.sql
-- Flyway migration for Personal Knowledge Graph & Entity Resolution Engine

CREATE TABLE IF NOT EXISTS ai.graph_nodes (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    canonical_name VARCHAR(150) NOT NULL,
    entity_type VARCHAR(50) NOT NULL, -- PERSON, TASK, PROJECT, GOAL, CALENDAR_EVENT, MEMORY, CONVERSATION, KNOWLEDGE_NOTE, EXPENSE
    aliases_json TEXT,
    importance_score INT NOT NULL DEFAULT 5,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.graph_edges (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    source_node_id UUID NOT NULL REFERENCES ai.graph_nodes(id) ON DELETE CASCADE,
    target_node_id UUID NOT NULL REFERENCES ai.graph_nodes(id) ON DELETE CASCADE,
    relation_type VARCHAR(50) NOT NULL, -- WORKS_ON, BELONGS_TO, MENTIONS, RELATED_TO, ABOUT, CREATED_FROM
    confidence_score DOUBLE PRECISION NOT NULL DEFAULT 0.95,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.graph_clusters (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    cluster_name VARCHAR(100) NOT NULL,
    node_ids_json TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_graph_nodes_user ON ai.graph_nodes(user_id);
CREATE INDEX IF NOT EXISTS idx_graph_edges_source ON ai.graph_edges(source_node_id);
CREATE INDEX IF NOT EXISTS idx_graph_edges_target ON ai.graph_edges(target_node_id);
