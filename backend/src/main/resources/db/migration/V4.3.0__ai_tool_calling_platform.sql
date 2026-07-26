-- V4.3.0__ai_tool_calling_platform.sql
-- Flyway migration for AI Tool Calling & Action Orchestration Platform

CREATE TABLE IF NOT EXISTS ai.tool_registry (
    id UUID PRIMARY KEY,
    tool_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    version VARCHAR(20) NOT NULL DEFAULT '1.0.0',
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    requires_confirmation BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.tool_executions (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    tool_name VARCHAR(100) NOT NULL,
    parameters_json TEXT,
    result_json TEXT,
    execution_status VARCHAR(30) NOT NULL, -- PENDING, EXECUTING, SUCCESS, FAILED, CANCELLED
    latency_ms BIGINT,
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.tool_audit_logs (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    tool_name VARCHAR(100) NOT NULL,
    execution_id UUID NOT NULL,
    action_performed VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_tool_executions_user ON ai.tool_executions(user_id);
CREATE INDEX IF NOT EXISTS idx_tool_executions_status ON ai.tool_executions(execution_status);
