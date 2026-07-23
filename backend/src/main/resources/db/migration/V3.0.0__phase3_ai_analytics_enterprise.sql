-- ============================================================================
-- LIFEOS PHASE 3 COMPLETE DATABASE MIGRATION SCRIPT
-- Engine Target: PostgreSQL 16+
-- Modules: AI Engine, Analytics, Audit Trail, Search, Enterprise & Security
-- ============================================================================

-- ----------------------------------------------------------------------------
-- SECTION 1: AI ENGINE DOMAIN (ai schema)
-- ----------------------------------------------------------------------------

-- Table: ai.ai_conversations
CREATE TABLE ai.ai_conversations (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  system_instruction TEXT NULL,
  is_pinned BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_ai_conversations_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

CREATE INDEX idx_ai_conversations_user ON ai.ai_conversations (user_id, updated_at DESC) WHERE is_deleted = FALSE;

-- Table: ai.ai_messages
CREATE TABLE ai.ai_messages (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  conversation_id UUID NOT NULL,
  role VARCHAR(50) NOT NULL,
  content TEXT NOT NULL,
  token_count INT NOT NULL DEFAULT 0,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_ai_messages_conversation FOREIGN KEY (conversation_id) REFERENCES ai.ai_conversations (id) ON DELETE CASCADE,
  CONSTRAINT chk_ai_messages_role CHECK (role IN ('SYSTEM', 'USER', 'ASSISTANT', 'TOOL'))
);

CREATE INDEX idx_ai_messages_conversation ON ai.ai_messages (conversation_id, created_at ASC);

-- Table: ai.ai_memory
CREATE TABLE ai.ai_memory (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  memory_key VARCHAR(150) NOT NULL,
  memory_value TEXT NOT NULL,
  category VARCHAR(50) NOT NULL DEFAULT 'GENERAL',
  importance_score INT NOT NULL DEFAULT 5,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_ai_memory_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_ai_memory_importance CHECK (importance_score BETWEEN 1 AND 10)
);

-- Table: ai.embeddings (pgvector HNSW Vector Search)
CREATE TABLE ai.embeddings (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  source_type VARCHAR(100) NOT NULL,
  source_id UUID NOT NULL,
  chunk_content TEXT NOT NULL,
  embedding vector(1536) NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_ai_embeddings_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- HNSW Vector Index for Cosine Distance Similarity Search
CREATE INDEX idx_vec_ai_embeddings ON ai.embeddings USING hnsw (embedding vector_cosine_ops) WITH (m = 16, ef_construction = 64);
CREATE INDEX idx_ai_embeddings_user_source ON ai.embeddings (user_id, source_type);

-- Table: ai.reasoning_logs
CREATE TABLE ai.reasoning_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  conversation_id UUID NULL,
  agent_name VARCHAR(100) NOT NULL,
  reasoning_chain TEXT NOT NULL,
  execution_time_ms INT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_ai_reasoning_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: ai.ai_tasks
CREATE TABLE ai.ai_tasks (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  task_name VARCHAR(255) NOT NULL,
  parameters_jsonb JSONB NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
  result_summary TEXT NULL,
  scheduled_for TIMESTAMPTZ NULL,
  executed_at TIMESTAMPTZ NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_ai_tasks_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_ai_tasks_status CHECK (status IN ('PENDING', 'RUNNING', 'COMPLETED', 'FAILED', 'CANCELLED'))
);

-- Table: ai.ai_suggestions
CREATE TABLE ai.ai_suggestions (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  suggestion_text TEXT NOT NULL,
  category VARCHAR(50) NOT NULL DEFAULT 'PRODUCTIVITY',
  confidence_score NUMERIC(3,2) NOT NULL DEFAULT 0.85,
  is_accepted BOOLEAN NULL,
  dismissed_at TIMESTAMPTZ NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_ai_suggestions_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- SECTION 2: ANALYTICS DOMAIN (analytics schema)
-- ----------------------------------------------------------------------------

-- Table: analytics.dashboards
CREATE TABLE analytics.dashboards (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  dashboard_name VARCHAR(150) NOT NULL,
  layout_config_jsonb JSONB NOT NULL DEFAULT '{}'::jsonb,
  is_default BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_dashboards_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: analytics.productivity_metrics
CREATE TABLE analytics.productivity_metrics (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  metric_date DATE NOT NULL,
  tasks_completed_count INT NOT NULL DEFAULT 0,
  habits_completed_count INT NOT NULL DEFAULT 0,
  focus_minutes_total INT NOT NULL DEFAULT 0,
  productivity_score NUMERIC(5,2) NOT NULL DEFAULT 0.00,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_prod_metrics_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_prod_metrics_user_date UNIQUE (user_id, metric_date)
);

-- Table: analytics.life_scores
CREATE TABLE analytics.life_scores (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  score_date DATE NOT NULL DEFAULT CURRENT_DATE,
  overall_score NUMERIC(5,2) NOT NULL,
  health_subscore NUMERIC(5,2) NOT NULL,
  finance_subscore NUMERIC(5,2) NOT NULL,
  productivity_subscore NUMERIC(5,2) NOT NULL,
  relationship_subscore NUMERIC(5,2) NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_life_scores_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_life_scores_date UNIQUE (user_id, score_date)
);

-- Table: analytics.usage_statistics
CREATE TABLE analytics.usage_statistics (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  feature_name VARCHAR(100) NOT NULL,
  action_count INT NOT NULL DEFAULT 1,
  last_used_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_usage_stats_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_usage_stats_feature UNIQUE (user_id, feature_name)
);

-- ----------------------------------------------------------------------------
-- SECTION 3: AUDIT DOMAIN (audit schema - Partitioned)
-- ----------------------------------------------------------------------------

-- Table: audit.audit_logs (Partitioned by Month)
CREATE TABLE audit.audit_logs (
  id UUID DEFAULT generate_uuid_v7(),
  user_id UUID NULL,
  entity_name VARCHAR(100) NOT NULL,
  entity_id UUID NOT NULL,
  action VARCHAR(50) NOT NULL,
  diff_jsonb JSONB NULL,
  ip_address VARCHAR(45) NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id, created_at)
) PARTITION BY RANGE (created_at);

-- Partitions for 2026
CREATE TABLE audit.audit_logs_2026_07 PARTITION OF audit.audit_logs FOR VALUES FROM ('2026-07-01 00:00:00+00') TO ('2026-08-01 00:00:00+00');
CREATE TABLE audit.audit_logs_2026_08 PARTITION OF audit.audit_logs FOR VALUES FROM ('2026-08-01 00:00:00+00') TO ('2026-09-01 00:00:00+00');
CREATE TABLE audit.audit_logs_default PARTITION OF audit.audit_logs DEFAULT;

-- Table: audit.entity_history
CREATE TABLE audit.entity_history (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  entity_name VARCHAR(100) NOT NULL,
  entity_id UUID NOT NULL,
  version_number BIGINT NOT NULL,
  snapshot_jsonb JSONB NOT NULL,
  changed_by UUID NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_entity_history_lookup ON audit.entity_history (entity_name, entity_id, version_number DESC);

-- Table: audit.activity_logs
CREATE TABLE audit.activity_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  activity_type VARCHAR(100) NOT NULL,
  description TEXT NOT NULL,
  metadata_jsonb JSONB NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_activity_logs_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

CREATE INDEX idx_activity_logs_user_date ON audit.activity_logs (user_id, created_at DESC);

-- ----------------------------------------------------------------------------
-- SECTION 4: SEARCH DOMAIN (search schema)
-- ----------------------------------------------------------------------------

-- Table: search.search_index
CREATE TABLE search.search_index (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  entity_type VARCHAR(50) NOT NULL,
  entity_id UUID NOT NULL,
  title VARCHAR(500) NOT NULL,
  content_summary TEXT NULL,
  tags VARCHAR(500) NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_search_index_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_search_index_entity UNIQUE (user_id, entity_type, entity_id)
);

-- Table: search.search_vectors (Full-Text Search Engine)
CREATE TABLE search.search_vectors (
  search_index_id UUID PRIMARY KEY,
  document_vector tsvector NOT NULL,
  
  CONSTRAINT fk_search_vectors_index FOREIGN KEY (search_index_id) REFERENCES search.search_index (id) ON DELETE CASCADE
);

CREATE INDEX idx_gin_search_vectors ON search.search_vectors USING gin (document_vector);

-- ----------------------------------------------------------------------------
-- SECTION 5: ENTERPRISE FUNCTIONS, PROCEDURES & VECTOR SEARCH
-- ----------------------------------------------------------------------------

-- Function: Semantic Vector Search
CREATE OR REPLACE FUNCTION ai.ai_semantic_search(
  p_user_id UUID,
  p_query_embedding vector(1536),
  p_match_threshold FLOAT DEFAULT 0.70,
  p_match_count INT DEFAULT 10
)
RETURNS TABLE (
  id UUID,
  source_type VARCHAR,
  source_id UUID,
  chunk_content TEXT,
  similarity FLOAT
) AS $$
BEGIN
  RETURN QUERY
  SELECT 
    e.id,
    e.source_type,
    e.source_id,
    e.chunk_content,
    (1 - (e.embedding <=> p_query_embedding))::FLOAT AS similarity
  FROM ai.embeddings e
  WHERE e.user_id = p_user_id
    AND (1 - (e.embedding <=> p_query_embedding)) >= p_match_threshold
  ORDER BY e.embedding <=> p_query_embedding
  LIMIT p_match_count;
END;
$$ LANGUAGE plpgsql STABLE;

-- Stored Procedure: Refresh All Materialized Views
CREATE OR REPLACE PROCEDURE proc_refresh_materialized_views()
AS $$
BEGIN
  REFRESH MATERIALIZED VIEW CONCURRENTLY health.mv_habit_streak_summary;
END;
$$ LANGUAGE plpgsql;

-- Stored Procedure: Maintenance & Soft-Delete Purging
CREATE OR REPLACE PROCEDURE proc_purge_soft_deleted_records(p_retention_days INT DEFAULT 90)
AS $$
BEGIN
  DELETE FROM task.tasks WHERE is_deleted = TRUE AND deleted_at < CURRENT_TIMESTAMP - (p_retention_days * INTERVAL '1 day');
  DELETE FROM people.contacts WHERE is_deleted = TRUE AND deleted_at < CURRENT_TIMESTAMP - (p_retention_days * INTERVAL '1 day');
  DELETE FROM finance.transactions WHERE is_deleted = TRUE AND deleted_at < CURRENT_TIMESTAMP - (p_retention_days * INTERVAL '1 day');
  DELETE FROM knowledge.notes WHERE is_deleted = TRUE AND deleted_at < CURRENT_TIMESTAMP - (p_retention_days * INTERVAL '1 day');
END;
$$ LANGUAGE plpgsql;

-- ----------------------------------------------------------------------------
-- SECTION 6: ROW-LEVEL SECURITY (RLS) POLICIES
-- ----------------------------------------------------------------------------
ALTER TABLE identity.users ENABLE ROW LEVEL SECURITY;
ALTER TABLE task.tasks ENABLE ROW LEVEL SECURITY;
ALTER TABLE finance.transactions ENABLE ROW LEVEL SECURITY;
ALTER TABLE knowledge.notes ENABLE ROW LEVEL SECURITY;
ALTER TABLE ai.ai_memory ENABLE ROW LEVEL SECURITY;

CREATE POLICY user_isolation_policy_tasks ON task.tasks
  FOR ALL USING (user_id = NULLIF(current_setting('app.current_user_id', TRUE), '')::UUID);

CREATE POLICY user_isolation_policy_finance ON finance.transactions
  FOR ALL USING (user_id = NULLIF(current_setting('app.current_user_id', TRUE), '')::UUID);

CREATE POLICY user_isolation_policy_notes ON knowledge.notes
  FOR ALL USING (user_id = NULLIF(current_setting('app.current_user_id', TRUE), '')::UUID);

CREATE POLICY user_isolation_policy_ai_memory ON ai.ai_memory
  FOR ALL USING (user_id = NULLIF(current_setting('app.current_user_id', TRUE), '')::UUID);

-- ----------------------------------------------------------------------------
-- SECTION 7: TRIGGERS SETUP
-- ----------------------------------------------------------------------------
CREATE TRIGGER trg_ai_conversations_updated_at BEFORE UPDATE ON ai.ai_conversations FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_ai_conversations_version BEFORE UPDATE ON ai.ai_conversations FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_ai_memory_updated_at BEFORE UPDATE ON ai.ai_memory FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_ai_suggestions_updated_at BEFORE UPDATE ON ai.ai_suggestions FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
