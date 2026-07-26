-- V4.8.0__ai_universal_search.sql
-- Flyway migration for Universal Search Platform & Global Activity Index

CREATE TABLE IF NOT EXISTS ai.search_activity_index (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    domain_type VARCHAR(50) NOT NULL, -- MEMORY, TASK, CALENDAR, KNOWLEDGE, FINANCE, HEALTH, CONVERSATION
    source_id UUID NOT NULL,
    title VARCHAR(250) NOT NULL,
    content_text TEXT NOT NULL,
    importance_score INT NOT NULL DEFAULT 5,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.search_queries (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    query_text VARCHAR(300) NOT NULL,
    intent_type VARCHAR(50) NOT NULL DEFAULT 'LOOKUP',
    results_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_search_activity_user ON ai.search_activity_index(user_id);
CREATE INDEX IF NOT EXISTS idx_search_activity_domain ON ai.search_activity_index(domain_type);
