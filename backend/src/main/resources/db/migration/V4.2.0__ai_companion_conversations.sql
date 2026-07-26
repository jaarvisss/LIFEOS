-- V4.2.0__ai_companion_conversations.sql
-- Flyway migration for AI Companion Conversation Engine

CREATE TABLE IF NOT EXISTS ai.conversations (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    title VARCHAR(200) NOT NULL,
    summary VARCHAR(1000),
    is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS ai.conversation_messages (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL REFERENCES ai.conversations(id) ON DELETE CASCADE,
    sender_type VARCHAR(20) NOT NULL, -- USER, ASSISTANT, SYSTEM
    content TEXT NOT NULL,
    intent_type VARCHAR(50),
    confidence_score DOUBLE PRECISION,
    token_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE IF NOT EXISTS ai.conversation_feedback (
    id UUID PRIMARY KEY,
    conversation_id UUID NOT NULL REFERENCES ai.conversations(id) ON DELETE CASCADE,
    message_id UUID NOT NULL REFERENCES ai.conversation_messages(id) ON DELETE CASCADE,
    user_id UUID NOT NULL,
    feedback_type VARCHAR(20) NOT NULL, -- THUMBS_UP, THUMBS_DOWN, HALLUCINATION
    feedback_comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_conversations_user ON ai.conversations(user_id);
CREATE INDEX IF NOT EXISTS idx_messages_conv ON ai.conversation_messages(conversation_id);
