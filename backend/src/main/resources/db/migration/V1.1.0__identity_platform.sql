-- ============================================================================
-- LifeOS Database Migration: V1.1.0__identity_platform.sql
-- Milestone 2: Identity & Authentication Platform
-- Engine Target: PostgreSQL 16+
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. Table: identity.users
-- ----------------------------------------------------------------------------
CREATE TABLE identity.users (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  email VARCHAR(255) NOT NULL,
  user_type VARCHAR(50) NOT NULL DEFAULT 'INDIVIDUAL',
  status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  email_verified BOOLEAN NOT NULL DEFAULT FALSE,
  mfa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  -- Soft Delete Columns
  is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
  deleted_at TIMESTAMPTZ NULL,
  
  -- Check Constraints
  CONSTRAINT chk_users_email_lowercase CHECK (email = lower(email)),
  CONSTRAINT chk_users_status CHECK (status IN ('ACTIVE', 'SUSPENDED', 'PENDING_VERIFICATION', 'DELETED')),
  CONSTRAINT chk_users_type CHECK (user_type IN ('INDIVIDUAL', 'SYSTEM_ADMIN', 'SERVICE_ACCOUNT'))
);

-- Partial Unique Index (Allows email re-registration after soft deletion)
CREATE UNIQUE INDEX uk_users_email_active ON identity.users (email) WHERE is_deleted = FALSE;
CREATE INDEX idx_users_status ON identity.users (status) WHERE is_deleted = FALSE;

-- ----------------------------------------------------------------------------
-- 2. Table: identity.user_credentials
-- ----------------------------------------------------------------------------
CREATE TABLE identity.user_credentials (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  password_algo VARCHAR(50) NOT NULL DEFAULT 'ARGON2ID',
  password_salt VARCHAR(255) NULL,
  password_changed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  failed_login_attempts INT NOT NULL DEFAULT 0,
  locked_until TIMESTAMPTZ NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_user_credentials_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_user_credentials_user UNIQUE (user_id)
);

-- ----------------------------------------------------------------------------
-- 3. Tables: identity.roles & identity.permissions
-- ----------------------------------------------------------------------------
CREATE TABLE identity.roles (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  role_code VARCHAR(100) NOT NULL,
  role_name VARCHAR(150) NOT NULL,
  description VARCHAR(500) NULL,
  is_system_role BOOLEAN NOT NULL DEFAULT TRUE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT uk_roles_code UNIQUE (role_code)
);

CREATE TABLE identity.permissions (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  permission_code VARCHAR(150) NOT NULL,
  resource VARCHAR(100) NOT NULL,
  action VARCHAR(100) NOT NULL,
  description VARCHAR(500) NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT uk_permissions_code UNIQUE (permission_code)
);

-- Join Tables: user_roles & role_permissions
CREATE TABLE identity.user_roles (
  user_id UUID NOT NULL,
  role_id UUID NOT NULL,
  granted_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  granted_by UUID NOT NULL,
  
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES identity.roles (id) ON DELETE CASCADE
);

CREATE TABLE identity.role_permissions (
  role_id UUID NOT NULL,
  permission_id UUID NOT NULL,
  granted_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (role_id, permission_id),
  CONSTRAINT fk_role_permissions_role FOREIGN KEY (role_id) REFERENCES identity.roles (id) ON DELETE CASCADE,
  CONSTRAINT fk_role_permissions_permission FOREIGN KEY (permission_id) REFERENCES identity.permissions (id) ON DELETE CASCADE
);

-- ----------------------------------------------------------------------------
-- 4. Table: identity.refresh_tokens
-- ----------------------------------------------------------------------------
CREATE TABLE identity.refresh_tokens (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  token_hash VARCHAR(255) NOT NULL,
  device_id VARCHAR(255) NULL,
  ip_address VARCHAR(45) NULL,
  user_agent VARCHAR(500) NULL,
  expires_at TIMESTAMPTZ NOT NULL,
  revoked BOOLEAN NOT NULL DEFAULT FALSE,
  revoked_at TIMESTAMPTZ NULL,
  replaced_by_token UUID NULL,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_refresh_tokens_hash UNIQUE (token_hash)
);

CREATE INDEX idx_refresh_tokens_user ON identity.refresh_tokens (user_id) WHERE revoked = FALSE;
CREATE INDEX idx_refresh_tokens_expires ON identity.refresh_tokens (expires_at);

-- ----------------------------------------------------------------------------
-- 5. Table: identity.api_keys
-- ----------------------------------------------------------------------------
CREATE TABLE identity.api_keys (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  key_name VARCHAR(150) NOT NULL,
  key_prefix VARCHAR(16) NOT NULL,
  key_hash VARCHAR(255) NOT NULL,
  scopes VARCHAR(500) NOT NULL DEFAULT 'read,write',
  expires_at TIMESTAMPTZ NULL,
  last_used_at TIMESTAMPTZ NULL,
  revoked BOOLEAN NOT NULL DEFAULT FALSE,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_api_keys_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_api_keys_hash UNIQUE (key_hash)
);

-- ----------------------------------------------------------------------------
-- 6. Table: identity.user_devices
-- ----------------------------------------------------------------------------
CREATE TABLE identity.user_devices (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  device_fingerprint VARCHAR(255) NOT NULL,
  device_name VARCHAR(150) NOT NULL,
  device_type VARCHAR(50) NOT NULL DEFAULT 'MOBILE',
  os_version VARCHAR(100) NULL,
  push_token VARCHAR(500) NULL,
  is_trusted BOOLEAN NOT NULL DEFAULT FALSE,
  last_active_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  -- Audit Columns
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  version BIGINT NOT NULL DEFAULT 0,
  
  CONSTRAINT fk_user_devices_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE,
  CONSTRAINT uk_user_devices_fingerprint UNIQUE (user_id, device_fingerprint)
);

-- ----------------------------------------------------------------------------
-- 7. Table: identity.login_history
-- ----------------------------------------------------------------------------
CREATE TABLE identity.login_history (
  id UUID PRIMARY KEY DEFAULT generate_uuid_v7(),
  user_id UUID NOT NULL,
  login_status VARCHAR(50) NOT NULL,
  ip_address VARCHAR(45) NOT NULL,
  user_agent VARCHAR(500) NULL,
  failure_reason VARCHAR(255) NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT fk_login_history_user FOREIGN KEY (user_id) REFERENCES identity.users (id) ON DELETE CASCADE
);

CREATE INDEX idx_login_history_user_date ON identity.login_history (user_id, created_at DESC);

-- ----------------------------------------------------------------------------
-- 8. Triggers Setup
-- ----------------------------------------------------------------------------
CREATE TRIGGER trg_users_updated_at BEFORE UPDATE ON identity.users FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_users_version BEFORE UPDATE ON identity.users FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_credentials_updated_at BEFORE UPDATE ON identity.user_credentials FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_credentials_version BEFORE UPDATE ON identity.user_credentials FOR EACH ROW EXECUTE FUNCTION trigger_increment_version();

CREATE TRIGGER trg_refresh_tokens_updated_at BEFORE UPDATE ON identity.refresh_tokens FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
CREATE TRIGGER trg_api_keys_updated_at BEFORE UPDATE ON identity.api_keys FOR EACH ROW EXECUTE FUNCTION trigger_update_updated_at();
