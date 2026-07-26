-- V4.4.0__ai_workflow_agent_runtime.sql
-- Flyway migration for LifeOS Workflow Engine & Agent Runtime

CREATE TABLE IF NOT EXISTS ai.workflow_definitions (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    workflow_type VARCHAR(50) NOT NULL,
    version VARCHAR(20) NOT NULL DEFAULT '1.0.0',
    definition_json TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.workflow_instances (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    definition_id UUID REFERENCES ai.workflow_definitions(id),
    name VARCHAR(150) NOT NULL,
    current_state VARCHAR(40) NOT NULL,
    progress_percentage INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    completed_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS ai.workflow_steps (
    id UUID PRIMARY KEY,
    instance_id UUID NOT NULL REFERENCES ai.workflow_instances(id) ON DELETE CASCADE,
    step_number INT NOT NULL,
    step_name VARCHAR(100) NOT NULL,
    step_type VARCHAR(50) NOT NULL,
    step_status VARCHAR(30) NOT NULL,
    execution_result TEXT,
    started_at TIMESTAMP WITH TIME ZONE,
    completed_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS ai.agent_registry (
    id UUID PRIMARY KEY,
    agent_name VARCHAR(100) NOT NULL UNIQUE,
    agent_role VARCHAR(100) NOT NULL,
    description TEXT,
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_workflow_instances_user ON ai.workflow_instances(user_id);
CREATE INDEX IF NOT EXISTS idx_workflow_instances_state ON ai.workflow_instances(current_state);
