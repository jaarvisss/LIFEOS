-- ============================================================================
-- LifeOS Database Migration: V1.2.0__personal_foundation.sql
-- Milestone 3: Personal Foundation (Profile, People, Relationship, Notification)
-- Engine Target: PostgreSQL 16+
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. Profile Domain Tables
-- ----------------------------------------------------------------------------
CREATE TABLE profile.user_profiles (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  display_name VARCHAR(150) NULL,
  avatar_url VARCHAR(500) NULL,
  time_zone VARCHAR(100) NOT NULL DEFAULT 'UTC',
  locale VARCHAR(10) NOT NULL DEFAULT 'en_US',
  date_of_birth DATE NULL,
  bio TEXT NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_user_profiles_user UNIQUE (user_id)
);

CREATE TABLE profile.persona_preferences (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  ai_tone VARCHAR(50) NOT NULL DEFAULT 'BALANCED',
  verbosity_level VARCHAR(50) NOT NULL DEFAULT 'CONCISE',
  proactive_suggestions BOOLEAN NOT NULL DEFAULT TRUE,
  preferred_ai_provider VARCHAR(50) NOT NULL DEFAULT 'GEMINI_PRO',
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_persona_preferences_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_persona_preferences_user UNIQUE (user_id)
);

CREATE TABLE profile.work_life_boundaries (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  work_start_time TIME NOT NULL DEFAULT '09:00:00',
  work_end_time TIME NOT NULL DEFAULT '17:00:00',
  work_days VARCHAR(50) NOT NULL DEFAULT 'MON,TUE,WED,THU,FRI',
  quiet_hours_start TIME NULL,
  quiet_hours_end TIME NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_work_life_boundaries_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_work_life_boundaries_user UNIQUE (user_id)
);

-- ----------------------------------------------------------------------------
-- 2. People Domain Tables
-- ----------------------------------------------------------------------------
CREATE TABLE people.contacts (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NULL,
  email VARCHAR(255) NULL,
  phone_number VARCHAR(50) NULL,
  company VARCHAR(150) NULL,
  job_title VARCHAR(150) NULL,
  date_of_birth DATE NULL,
  notes TEXT NULL,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_contacts_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

CREATE INDEX idx_contacts_user_name ON people.contacts (user_id, last_name, first_name) WHERE is_deleted = FALSE;

CREATE TABLE people.contact_addresses (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  contact_id UUID NOT NULL,
  address_type VARCHAR(50) NOT NULL DEFAULT 'HOME',
  street_address VARCHAR(255) NOT NULL,
  city VARCHAR(100) NOT NULL,
  state_province VARCHAR(100) NULL,
  postal_code VARCHAR(20) NULL,
  country VARCHAR(100) NOT NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_contact_addresses_contact FOREIGN KEY (contact_id) REFERENCES people.contacts (id) ON DELETE CASCADE
);

CREATE TABLE people.communication_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  contact_id UUID NOT NULL,
  channel VARCHAR(50) NOT NULL,
  summary TEXT NOT NULL,
  interaction_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_communication_logs_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_communication_logs_contact FOREIGN KEY (contact_id) REFERENCES people.contacts (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- 3. Relationship Domain Tables
-- ----------------------------------------------------------------------------
CREATE TABLE relationship.relationships (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  contact_id UUID NOT NULL,
  relationship_type VARCHAR(50) NOT NULL,
  closeness_level INT NOT NULL DEFAULT 5,
  
  -- Audit & Soft-Delete Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  CONSTRAINT fk_relationships_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_relationships_contact FOREIGN KEY (contact_id) REFERENCES people.contacts (id) ON DELETE CASCADE,
  CONSTRAINT chk_relationships_closeness CHECK (closeness_level BETWEEN 1 AND 10),
  CONSTRAINT uk_relationships_user_contact UNIQUE (user_id, contact_id)
);

CREATE TABLE relationship.milestones (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  relationship_id UUID NOT NULL,
  title VARCHAR(255) NOT NULL,
  event_date DATE NOT NULL,
  description TEXT NULL,
  recurring_annual BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_milestones_relationship FOREIGN KEY (relationship_id) REFERENCES relationship.relationships (id) ON DELETE CASCADE
);

CREATE TABLE relationship.interaction_rules (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  relationship_id UUID NOT NULL,
  target_frequency_days INT NOT NULL DEFAULT 7,
  last_contacted_at TIMESTAMPTZ NULL,
  alert_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_interaction_rules_relationship FOREIGN KEY (relationship_id) REFERENCES relationship.relationships (id) ON DELETE CASCADE,
  CONSTRAINT uk_interaction_rules_relationship UNIQUE (relationship_id)
);

-- ----------------------------------------------------------------------------
-- 4. Notification Domain Tables
-- ----------------------------------------------------------------------------
CREATE TABLE notification.notification_preferences (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  email_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  push_enabled BOOLEAN NOT NULL DEFAULT TRUE,
  whatsapp_enabled BOOLEAN NOT NULL DEFAULT FALSE,
  sms_enabled BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_notif_pref_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_notif_pref_user UNIQUE (user_id)
);

CREATE TABLE notification.notification_channels (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  channel_type VARCHAR(50) NOT NULL,
  destination VARCHAR(255) NOT NULL,
  is_verified BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_notif_channel_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_notif_channel_dest UNIQUE (user_id, channel_type, destination)
);

CREATE TABLE notification.delivery_logs (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  channel_type VARCHAR(50) NOT NULL,
  subject VARCHAR(255) NULL,
  content TEXT NOT NULL,
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
  error_message TEXT NULL,
  sent_at TIMESTAMPTZ NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_delivery_logs_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

CREATE INDEX idx_delivery_logs_user_status ON notification.delivery_logs (user_id, status, created_at DESC);

-- ----------------------------------------------------------------------------
-- 5. Read-Model Views
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW people.v_contact_summary AS
SELECT 
  c.id AS contact_id,
  c.user_id,
  c.first_name,
  c.last_name,
  c.email,
  c.phone_number,
  c.company,
  r.relationship_type,
  r.closeness_level,
  ir.target_frequency_days,
  ir.last_contacted_at
FROM people.contacts c
LEFT JOIN relationship.relationships r ON c.id = r.contact_id AND r.is_deleted = FALSE
LEFT JOIN relationship.interaction_rules ir ON r.id = ir.relationship_id
WHERE c.is_deleted = FALSE;

CREATE OR REPLACE VIEW relationship.v_upcoming_birthdays AS
SELECT 
  c.id AS contact_id,
  c.user_id,
  c.first_name,
  c.last_name,
  c.date_of_birth,
  to_char(c.date_of_birth, 'Mon DD') AS birthday_formatted,
  (c.date_of_birth + (extract(year from age(c.date_of_birth)) + 1) * interval '1 year')::date AS next_birthday
FROM people.contacts c
WHERE c.date_of_birth IS NOT NULL AND c.is_deleted = FALSE;

CREATE OR REPLACE VIEW relationship.v_relationship_dashboard AS
SELECT 
  r.id AS relationship_id,
  r.user_id,
  c.first_name || ' ' || COALESCE(c.last_name, '') AS contact_name,
  r.relationship_type,
  r.closeness_level,
  ir.target_frequency_days,
  ir.last_contacted_at,
  CASE 
    WHEN ir.last_contacted_at IS NULL THEN TRUE
    WHEN (CURRENT_TIMESTAMP - ir.last_contacted_at) > (ir.target_frequency_days * interval '1 day') THEN TRUE
    ELSE FALSE
  END AS needs_attention
FROM relationship.relationships r
JOIN people.contacts c ON r.contact_id = c.id
LEFT JOIN relationship.interaction_rules ir ON r.id = ir.relationship_id
WHERE r.is_deleted = FALSE AND c.is_deleted = FALSE;

-- ----------------------------------------------------------------------------
-- 6. Triggers Setup
-- ----------------------------------------------------------------------------
CREATE TRIGGER trg_user_profiles_updated_at BEFORE UPDATE ON profile.user_profiles FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_contacts_updated_at BEFORE UPDATE ON people.contacts FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_relationships_updated_at BEFORE UPDATE ON relationship.relationships FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
