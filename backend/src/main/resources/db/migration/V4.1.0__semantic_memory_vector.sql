-- V4.1.0__semantic_memory_vector.sql
-- Flyway migration for Semantic Memory Vector & Relationship Engine

CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS memory.memory_embeddings (
    id UUID PRIMARY KEY,
    memory_id UUID NOT NULL REFERENCES memory.memories(id) ON DELETE CASCADE,
    embedding_model VARCHAR(100) NOT NULL DEFAULT 'text-embedding-3-small',
    embedding_dimension INT NOT NULL DEFAULT 1536,
    embedding vector(1536),
    checksum VARCHAR(64),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_memory_embeddings_memory_id ON memory.memory_embeddings(memory_id);

-- Create HNSW Cosine Index for sub-50ms vector similarity search
CREATE INDEX IF NOT EXISTS idx_memory_embeddings_vector_hnsw 
ON memory.memory_embeddings USING hnsw (embedding vector_cosine_ops);

-- Memory Relationships Graph Table
CREATE TABLE IF NOT EXISTS memory.memory_relationships (
    id UUID PRIMARY KEY,
    source_memory_id UUID NOT NULL REFERENCES memory.memories(id) ON DELETE CASCADE,
    target_memory_id UUID NOT NULL REFERENCES memory.memories(id) ON DELETE CASCADE,
    relationship_type VARCHAR(50) NOT NULL,
    confidence_score DOUBLE PRECISION NOT NULL DEFAULT 1.0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_memory_rel_source ON memory.memory_relationships(source_memory_id);
CREATE INDEX IF NOT EXISTS idx_memory_rel_target ON memory.memory_relationships(target_memory_id);
