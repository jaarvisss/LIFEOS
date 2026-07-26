-- V4.7.0__ai_intelligent_automation.sql
-- Flyway migration for Intelligent Automation Engine & Rules Engine

CREATE TABLE IF NOT EXISTS ai.automation_definitions (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    trigger_type VARCHAR(50) NOT NULL, -- CRON, EVENT, RECURRING, CONDITIONAL
    trigger_expression VARCHAR(200) NOT NULL,
    condition_rule TEXT,
    action_type VARCHAR(50) NOT NULL, -- WORKFLOW, AGENT, TOOL, NOTIFICATION
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.automation_instances (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    definition_id UUID REFERENCES ai.automation_definitions(id),
    name VARCHAR(150) NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'SUCCESS',
    triggered_by VARCHAR(100) NOT NULL,
    execution_result_json TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.automation_history (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    automation_name VARCHAR(150) NOT NULL,
    status VARCHAR(30) NOT NULL,
    latency_ms BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_automation_instances_user ON ai.automation_instances(user_id);
CREATE INDEX IF NOT EXISTS idx_automation_history_user ON ai.automation_history(user_id);
