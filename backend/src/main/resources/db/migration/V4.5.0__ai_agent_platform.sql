-- V4.5.0__ai_agent_platform.sql
-- Flyway migration for LifeOS Autonomous AI Agent Platform

CREATE TABLE IF NOT EXISTS ai.agents (
    id UUID PRIMARY KEY,
    agent_name VARCHAR(100) NOT NULL UNIQUE,
    agent_role VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    communication_style VARCHAR(50) NOT NULL DEFAULT 'EXECUTIVE',
    expertise VARCHAR(150) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.agent_delegations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    parent_agent_name VARCHAR(100) NOT NULL,
    sub_agent_name VARCHAR(100) NOT NULL,
    task_description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'COMPLETED',
    result_summary TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.agent_messages (
    id UUID PRIMARY KEY,
    sender_agent VARCHAR(100) NOT NULL,
    recipient_agent VARCHAR(100) NOT NULL,
    message_type VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_agents_name ON ai.agents(agent_name);
CREATE INDEX IF NOT EXISTS idx_delegations_user ON ai.agent_delegations(user_id);
