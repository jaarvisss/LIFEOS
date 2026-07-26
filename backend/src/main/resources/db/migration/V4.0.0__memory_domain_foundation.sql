-- V4.0.0__memory_domain_foundation.sql
-- Flyway migration for Memory Domain Foundation

CREATE SCHEMA IF NOT EXISTS memory;

CREATE TABLE IF NOT EXISTS memory.memories (
    id UUID PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    summary VARCHAR(1000),
    memory_type VARCHAR(50) NOT NULL,
    importance_level VARCHAR(30) NOT NULL,
    memory_status VARCHAR(30) NOT NULL,
    source_type VARCHAR(30) NOT NULL,
    user_id UUID NOT NULL,
    life_profile_id UUID,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    archived_at TIMESTAMP WITH TIME ZONE,
    deleted_at TIMESTAMP WITH TIME ZONE,
    last_accessed_at TIMESTAMP WITH TIME ZONE,
    access_count BIGINT NOT NULL DEFAULT 0,
    language VARCHAR(10) NOT NULL DEFAULT 'en',
    confidence_score DOUBLE PRECISION NOT NULL DEFAULT 1.0,
    is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
    is_pinned BOOLEAN NOT NULL DEFAULT FALSE,
    metadata TEXT,
    version BIGINT NOT NULL DEFAULT 0,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX IF NOT EXISTS idx_memories_user_id ON memory.memories(user_id);
CREATE INDEX IF NOT EXISTS idx_memories_status ON memory.memories(memory_status);
CREATE INDEX IF NOT EXISTS idx_memories_user_pinned ON memory.memories(user_id, is_pinned) WHERE is_pinned IS TRUE;
CREATE INDEX IF NOT EXISTS idx_memories_user_favorite ON memory.memories(user_id, is_favorite) WHERE is_favorite IS TRUE;
CREATE INDEX IF NOT EXISTS idx_memories_user_type ON memory.memories(user_id, memory_type);
