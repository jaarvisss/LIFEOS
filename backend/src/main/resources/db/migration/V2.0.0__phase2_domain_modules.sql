-- ============================================================================
-- LIFEOS PHASE 2 COMPLETE DATABASE MIGRATION SCRIPT
-- Engine Target: PostgreSQL 16+
-- Modules: Planning, Calendar, Knowledge, Documents, Finance, Health
-- ============================================================================

-- ----------------------------------------------------------------------------
-- SECTION 1: PLANNING DOMAIN (goal, project, task schemas)
-- ----------------------------------------------------------------------------

-- Table: goal.goals
CREATE TABLE goal.goals (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT NULL,
  category VARCHAR(50) NOT NULL DEFAULT 'PERSONAL',
  status VARCHAR(50) NOT NULL DEFAULT 'IN_PROGRESS',
  target_date DATE NULL,
  progress_percentage NUMERIC(5,2) NOT NULL DEFAULT 0.00,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_goals_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_goals_status CHECK (status IN ('NOT_STARTED', 'IN_PROGRESS', 'ACHIEVED', 'ABANDONED')),
  CONSTRAINT chk_goals_progress CHECK (progress_percentage BETWEEN 0 AND 100)
);

CREATE INDEX idx_goals_user_status ON goal.goals (user_id, status) WHERE is_deleted = FALSE;

-- Table: goal.goal_milestones
CREATE TABLE goal.goal_milestones (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  goal_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  target_date DATE NULL,
  is_completed BOOLEAN NOT NULL DEFAULT FALSE,
  completed_at TIMESTAMPTZ NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_goal_milestones_goal FOREIGN KEY (goal_id) REFERENCES goal.goals (id) ON DELETE CASCADE
);

-- Table: project.projects
CREATE TABLE project.projects (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  goal_id UUID NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  color_code VARCHAR(10) NULL,
  start_date DATE NULL,
  target_end_date DATE NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_projects_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_projects_status CHECK (status IN ('PLANNING', 'ACTIVE', 'ON_HOLD', 'COMPLETED', 'ARCHIVED'))
);

CREATE INDEX idx_projects_user_status ON project.projects (user_id, status) WHERE is_deleted = FALSE;

-- Table: project.project_members
CREATE TABLE project.project_members (
  project_id UUID NOT NULL,
  contact_id UUID NOT NULL,
  role VARCHAR(50) NOT NULL DEFAULT 'CONTRIBUTOR',
  joined_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (project_id, contact_id),
  CONSTRAINT fk_project_members_project FOREIGN KEY (project_id) REFERENCES project.projects (id) ON DELETE CASCADE,
  CONSTRAINT fk_project_members_contact FOREIGN KEY (contact_id) REFERENCES people.contacts (id) ON DELETE CASCADE
);

-- Table: task.tasks
CREATE TABLE task.tasks (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  project_id UUID NULL,
  goal_id UUID NULL,
  title VARCHAR(500) NOT NULL,
  description TEXT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'TODO',
  priority VARCHAR(50) NOT NULL DEFAULT 'MEDIUM',
  due_date TIMESTAMPTZ NULL,
  estimated_duration_minutes INT NULL,
  completed_at TIMESTAMPTZ NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_tasks_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_tasks_status CHECK (status IN ('TODO', 'IN_PROGRESS', 'BLOCKED', 'COMPLETED', 'CANCELLED')),
  CONSTRAINT chk_tasks_priority CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'URGENT')),
  CONSTRAINT chk_tasks_duration CHECK (estimated_duration_minutes > 0)
);

CREATE INDEX idx_tasks_user_status_due ON task.tasks (user_id, status, due_date) WHERE is_deleted = FALSE;
CREATE INDEX idx_tasks_project ON task.tasks (project_id) WHERE project_id IS NOT NULL AND is_deleted = FALSE;
CREATE INDEX idx_tasks_goal ON task.tasks (goal_id) WHERE goal_id IS NOT NULL AND is_deleted = FALSE;

-- Table: task.subtasks
CREATE TABLE task.subtasks (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  task_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  is_completed BOOLEAN NOT NULL DEFAULT FALSE,
  sequence_order INT NOT NULL DEFAULT 0,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_subtasks_task FOREIGN KEY (task_id) REFERENCES task.tasks (id) ON DELETE CASCADE
);

-- Table: task.task_labels
CREATE TABLE task.task_labels (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(50) NOT NULL,
  color_code VARCHAR(10) NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_task_labels_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_task_labels_user_name UNIQUE (user_id, name)
);

-- ----------------------------------------------------------------------------
-- SECTION 2: CALENDAR DOMAIN (calendar & event schemas)
-- ----------------------------------------------------------------------------

-- Table: calendar.calendars
CREATE TABLE calendar.calendars (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT NULL,
  color_code VARCHAR(10) NULL,
  is_default BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_calendars_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: event.events
CREATE TABLE event.events (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  calendar_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT NULL,
  location VARCHAR(255) NULL,
  start_time TIMESTAMPTZ NOT NULL,
  end_time TIMESTAMPTZ NOT NULL,
  is_all_day BOOLEAN NOT NULL DEFAULT FALSE,
  event_status VARCHAR(50) NOT NULL DEFAULT 'CONFIRMED',
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_events_calendar FOREIGN KEY (calendar_id) REFERENCES calendar.calendars (id) ON DELETE CASCADE,
  CONSTRAINT chk_events_time_range CHECK (end_time >= start_time)
);

CREATE INDEX idx_events_calendar_start ON event.events (calendar_id, start_time) WHERE is_deleted = FALSE;

-- Table: event.recurring_rules
CREATE TABLE event.recurring_rules (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  event_id UUID NOT NULL,
  frequency VARCHAR(50) NOT NULL,
  interval_count INT NOT NULL DEFAULT 1,
  until_date DATE NULL,
  rrule_expression VARCHAR(255) NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_recurring_rules_event FOREIGN KEY (event_id) REFERENCES event.events (id) ON DELETE CASCADE,
  CONSTRAINT uk_recurring_rules_event UNIQUE (event_id)
);

-- Table: event.reminders
CREATE TABLE event.reminders (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  event_id UUID NOT NULL,
  minutes_before INT NOT NULL DEFAULT 15,
  channel VARCHAR(50) NOT NULL DEFAULT 'PUSH',
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_reminders_event FOREIGN KEY (event_id) REFERENCES event.events (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- SECTION 3: KNOWLEDGE DOMAIN (knowledge schema)
-- ----------------------------------------------------------------------------

-- Table: knowledge.notebooks
CREATE TABLE knowledge.notebooks (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_notebooks_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_notebooks_user_name UNIQUE (user_id, name)
);

-- Table: knowledge.notes
CREATE TABLE knowledge.notes (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  notebook_id UUID NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  is_favorite BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_notes_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_notes_notebook FOREIGN KEY (notebook_id) REFERENCES knowledge.notebooks (id) ON DELETE SET NULL
);

CREATE INDEX idx_notes_user_updated ON knowledge.notes (user_id, updated_at DESC) WHERE is_deleted = FALSE;
CREATE INDEX idx_gin_notes_fts ON knowledge.notes USING gin (to_tsvector('english', title || ' ' || content));

-- Table: knowledge.note_tags
CREATE TABLE knowledge.note_tags (
  note_id UUID NOT NULL,
  tag_name VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (note_id, tag_name),
  CONSTRAINT fk_note_tags_note FOREIGN KEY (note_id) REFERENCES knowledge.notes (id) ON DELETE CASCADE
);

-- Table: knowledge.knowledge_links
CREATE TABLE knowledge.knowledge_links (
  source_note_id UUID NOT NULL,
  target_note_id UUID NOT NULL,
  link_type VARCHAR(50) NOT NULL DEFAULT 'WIKI_LINK',
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (source_note_id, target_note_id),
  CONSTRAINT fk_klinks_source FOREIGN KEY (source_note_id) REFERENCES knowledge.notes (id) ON DELETE CASCADE,
  CONSTRAINT fk_klinks_target FOREIGN KEY (target_note_id) REFERENCES knowledge.notes (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- SECTION 4: DOCUMENTS DOMAIN (document schema)
-- ----------------------------------------------------------------------------

-- Table: document.documents
CREATE TABLE document.documents (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  file_extension VARCHAR(20) NOT NULL,
  mime_type VARCHAR(100) NOT NULL,
  storage_provider VARCHAR(50) NOT NULL DEFAULT 'S3',
  storage_key VARCHAR(500) NOT NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_documents_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: document.document_versions
CREATE TABLE document.document_versions (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  document_id UUID NOT NULL,
  version_number INT NOT NULL DEFAULT 1,
  file_size_bytes BIGINT NOT NULL,
  checksum_sha256 VARCHAR(64) NOT NULL,
  storage_key VARCHAR(500) NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_doc_versions_document FOREIGN KEY (document_id) REFERENCES document.documents (id) ON DELETE CASCADE,
  CONSTRAINT uk_doc_versions_num UNIQUE (document_id, version_number)
);

-- Table: document.document_tags
CREATE TABLE document.document_tags (
  document_id UUID NOT NULL,
  tag_name VARCHAR(50) NOT NULL,
  
  PRIMARY KEY (document_id, tag_name),
  CONSTRAINT fk_doc_tags_doc FOREIGN KEY (document_id) REFERENCES document.documents (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- SECTION 5: FINANCE DOMAIN (finance schema)
-- ----------------------------------------------------------------------------

-- Table: finance.accounts
CREATE TABLE finance.accounts (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(150) NOT NULL,
  account_type VARCHAR(50) NOT NULL,
  currency VARCHAR(3) NOT NULL DEFAULT 'USD',
  current_balance NUMERIC(19,4) NOT NULL DEFAULT 0.0000,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_accounts_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: finance.budgets
CREATE TABLE finance.budgets (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(150) NOT NULL,
  category VARCHAR(100) NOT NULL,
  allocated_amount NUMERIC(19,4) NOT NULL,
  currency VARCHAR(3) NOT NULL DEFAULT 'USD',
  period_type VARCHAR(50) NOT NULL DEFAULT 'MONTHLY',
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_budgets_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: finance.transactions
CREATE TABLE finance.transactions (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  account_id UUID NOT NULL,
  budget_id UUID NULL,
  amount NUMERIC(19,4) NOT NULL,
  currency VARCHAR(3) NOT NULL DEFAULT 'USD',
  transaction_type VARCHAR(50) NOT NULL,
  category VARCHAR(100) NOT NULL,
  transaction_timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  merchant_name VARCHAR(255) NULL,
  notes TEXT NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_transactions_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_transactions_account FOREIGN KEY (account_id) REFERENCES finance.accounts (id) ON DELETE RESTRICT,
  CONSTRAINT fk_transactions_budget FOREIGN KEY (budget_id) REFERENCES finance.budgets (id) ON DELETE SET NULL,
  CONSTRAINT chk_transactions_type CHECK (transaction_type IN ('INCOME', 'EXPENSE', 'TRANSFER'))
);

CREATE INDEX idx_transactions_user_timestamp ON finance.transactions (user_id, transaction_timestamp DESC) WHERE is_deleted = FALSE;

-- Table: finance.recurring_transactions
CREATE TABLE finance.recurring_transactions (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  account_id UUID NOT NULL,
  amount NUMERIC(19,4) NOT NULL,
  currency VARCHAR(3) NOT NULL DEFAULT 'USD',
  category VARCHAR(100) NOT NULL,
  frequency VARCHAR(50) NOT NULL DEFAULT 'MONTHLY',
  next_due_date DATE NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_rec_transactions_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_rec_transactions_account FOREIGN KEY (account_id) REFERENCES finance.accounts (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- SECTION 6: HEALTH DOMAIN (health schema)
-- ----------------------------------------------------------------------------

-- Table: health.habits
CREATE TABLE health.habits (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  name VARCHAR(150) NOT NULL,
  description TEXT NULL,
  target_frequency VARCHAR(50) NOT NULL DEFAULT 'DAILY',
  current_streak_count INT NOT NULL DEFAULT 0,
  best_streak_count INT NOT NULL DEFAULT 0,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_habits_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: health.habit_logs
CREATE TABLE health.habit_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  habit_id UUID NOT NULL,
  completed_date DATE NOT NULL,
  notes TEXT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_habit_logs_habit FOREIGN KEY (habit_id) REFERENCES health.habits (id) ON DELETE CASCADE,
  CONSTRAINT uk_habit_logs_date UNIQUE (habit_id, completed_date)
);

-- Table: health.workouts
CREATE TABLE health.workouts (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  workout_type VARCHAR(100) NOT NULL,
  duration_minutes INT NOT NULL,
  calories_burned INT NULL,
  logged_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_workouts_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: health.meals
CREATE TABLE health.meals (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  meal_type VARCHAR(50) NOT NULL,
  calories INT NULL,
  notes TEXT NULL,
  logged_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_meals_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

-- Table: health.sleep_logs
CREATE TABLE health.sleep_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  sleep_start TIMESTAMPTZ NOT NULL,
  sleep_end TIMESTAMPTZ NOT NULL,
  quality_score INT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_sleep_logs_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT chk_sleep_logs_score CHECK (quality_score BETWEEN 1 AND 10)
);

-- Table: health.body_metrics
CREATE TABLE health.body_metrics (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  weight_kg NUMERIC(5,2) NULL,
  height_cm NUMERIC(5,2) NULL,
  resting_heart_rate INT NULL,
  logged_date DATE NOT NULL DEFAULT CURRENT_DATE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_body_metrics_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_body_metrics_date UNIQUE (user_id, logged_date)
);

-- ----------------------------------------------------------------------------
-- SECTION 7: VIEWS & MATERIALIZED VIEWS
-- ----------------------------------------------------------------------------

-- View: task.v_todays_tasks
CREATE OR REPLACE VIEW task.v_todays_tasks AS
SELECT 
  t.id AS task_id,
  t.user_id,
  t.title,
  t.status,
  t.priority,
  t.due_date,
  p.name AS project_name,
  g.title AS goal_title
FROM task.tasks t
LEFT JOIN project.projects p ON t.project_id = p.id
LEFT JOIN goal.goals g ON t.goal_id = g.id
WHERE t.is_deleted = FALSE 
  AND t.status IN ('TODO', 'IN_PROGRESS')
  AND (t.due_date::date <= CURRENT_DATE OR t.due_date IS NULL);

-- View: finance.v_budget_summary
CREATE OR REPLACE VIEW finance.v_budget_summary AS
SELECT 
  b.id AS budget_id,
  b.user_id,
  b.name AS budget_name,
  b.category,
  b.allocated_amount,
  COALESCE(SUM(t.amount), 0.0000) AS spent_amount,
  (b.allocated_amount - COALESCE(SUM(t.amount), 0.0000)) AS remaining_amount
FROM finance.budgets b
LEFT JOIN finance.transactions t 
  ON b.id = t.budget_id 
 AND t.is_deleted = FALSE 
 AND t.transaction_type = 'EXPENSE'
WHERE b.is_deleted = FALSE
GROUP BY b.id, b.user_id, b.name, b.category, b.allocated_amount;

-- Materialized View: health.mv_habit_streak_summary
CREATE MATERIALIZED VIEW health.mv_habit_streak_summary AS
SELECT 
  h.id AS habit_id,
  h.user_id,
  h.name AS habit_name,
  h.current_streak_count,
  h.best_streak_count,
  MAX(hl.completed_date) AS last_completed_date
FROM health.habits h
LEFT JOIN health.habit_logs hl ON h.id = hl.habit_id
WHERE h.is_deleted = FALSE
GROUP BY h.id, h.user_id, h.name, h.current_streak_count, h.best_streak_count;

CREATE UNIQUE INDEX uk_mv_habit_streak ON health.mv_habit_streak_summary (habit_id);

-- ----------------------------------------------------------------------------
-- SECTION 8: TRIGGERS SETUP
-- ----------------------------------------------------------------------------
CREATE TRIGGER trg_goals_updated_at BEFORE UPDATE ON goal.goals FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_goals_version BEFORE UPDATE ON goal.goals FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_projects_updated_at BEFORE UPDATE ON project.projects FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_projects_version BEFORE UPDATE ON project.projects FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_tasks_updated_at BEFORE UPDATE ON task.tasks FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_tasks_version BEFORE UPDATE ON task.tasks FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_calendars_updated_at BEFORE UPDATE ON calendar.calendars FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_events_updated_at BEFORE UPDATE ON event.events FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();

CREATE TRIGGER trg_notes_updated_at BEFORE UPDATE ON knowledge.notes FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_notes_version BEFORE UPDATE ON knowledge.notes FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_transactions_updated_at BEFORE UPDATE ON finance.transactions FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_habits_updated_at BEFORE UPDATE ON health.habits FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
