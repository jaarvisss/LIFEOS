-- V4.6.0__ai_dashboard_intelligence.sql
-- Flyway migration for AI Dashboard Intelligence Platform & Life Score Engine

CREATE TABLE IF NOT EXISTS ai.dashboard_snapshots (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    overall_life_score INT NOT NULL DEFAULT 88,
    productivity_score INT NOT NULL DEFAULT 90,
    health_score INT NOT NULL DEFAULT 92,
    finance_score INT NOT NULL DEFAULT 85,
    learning_score INT NOT NULL DEFAULT 88,
    relationship_score INT NOT NULL DEFAULT 84,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.dashboard_insights (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    domain_type VARCHAR(50) NOT NULL, -- TASKS, FINANCE, HEALTH, RELATIONSHIPS, KNOWLEDGE
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    priority VARCHAR(30) NOT NULL DEFAULT 'NORMAL',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.dashboard_predictions (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    target_metric VARCHAR(100) NOT NULL,
    probability_percentage INT NOT NULL,
    explanation TEXT NOT NULL,
    suggested_mitigation TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_dashboard_snapshots_user ON ai.dashboard_snapshots(user_id);
CREATE INDEX IF NOT EXISTS idx_dashboard_insights_user ON ai.dashboard_insights(user_id);
