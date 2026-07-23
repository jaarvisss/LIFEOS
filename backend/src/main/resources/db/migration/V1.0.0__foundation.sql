-- ============================================================================
-- LifeOS Database Migration: V1.0.0__foundation.sql
-- Milestone 1: Database Foundation & Core Infrastructure
-- Engine Target: PostgreSQL 16+
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. PostgreSQL Extensions
-- ----------------------------------------------------------------------------
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS "vector";

-- ----------------------------------------------------------------------------
-- 2. Domain Schema Creation (18 DDD Bounded Context Schemas)
-- ----------------------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS identity;
CREATE SCHEMA IF NOT EXISTS profile;
CREATE SCHEMA IF NOT EXISTS people;
CREATE SCHEMA IF NOT EXISTS relationship;
CREATE SCHEMA IF NOT EXISTS calendar;
CREATE SCHEMA IF NOT EXISTS event;
CREATE SCHEMA IF NOT EXISTS task;
CREATE SCHEMA IF NOT EXISTS goal;
CREATE SCHEMA IF NOT EXISTS project;
CREATE SCHEMA IF NOT EXISTS finance;
CREATE SCHEMA IF NOT EXISTS knowledge;
CREATE SCHEMA IF NOT EXISTS document;
CREATE SCHEMA IF NOT EXISTS health;
CREATE SCHEMA IF NOT EXISTS notification;
CREATE SCHEMA IF NOT EXISTS ai;
CREATE SCHEMA IF NOT EXISTS analytics;
CREATE SCHEMA IF NOT EXISTS audit;
CREATE SCHEMA IF NOT EXISTS search;

-- ----------------------------------------------------------------------------
-- 3. Utility Functions: Native PostgreSQL UUIDv7 Generator
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION generate_uuid_v7()
RETURNS uuid AS $$
DECLARE
  v_time double precision;
  v_epoch_ms bigint;
  v_bytes bytea;
BEGIN
  v_time := extract(epoch from clock_timestamp());
  v_epoch_ms := (v_time * 1000)::bigint;
  
  -- Encode 48-bit timestamp prefix (6 bytes)
  v_bytes := decode(lpad(to_hex(v_epoch_ms), 12, '0'), 'hex') || gen_random_bytes(10);
  
  -- Set version bits to 7 (bits 4-7 of 7th byte -> 0111_xxxx)
  v_bytes := set_byte(v_bytes, 6, (get_byte(v_bytes, 6) & 15) | 112);
  -- Set variant bits to RFC 4122 (bits 6-7 of 9th byte -> 10xx_xxxx)
  v_bytes := set_byte(v_bytes, 8, (get_byte(v_bytes, 8) & 63) | 128);
  
  RETURN encode(v_bytes, 'hex')::uuid;
END;
$$ LANGUAGE plpgsql VOLATILE;

COMMENT ON FUNCTION generate_uuid_v7() IS 'Generates sequential, time-ordered 128-bit UUID Version 7 optimized for B-Tree indexing.';

-- ----------------------------------------------------------------------------
-- 4. Shared Audit & Version Triggers
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION trigger_update_updated_at()
RETURNS trigger AS $$
BEGIN
  NEW.updated_at = CURRENT_TIMESTAMP;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION trigger_update_updated_at() IS 'Trigger function to automatically set updated_at column to current UTC timestamp.';

CREATE OR REPLACE FUNCTION trigger_increment_version()
RETURNS trigger AS $$
BEGIN
  NEW.version = OLD.version + 1;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION trigger_increment_version() IS 'Trigger function to enforce optimistic locking version increments.';

-- ----------------------------------------------------------------------------
-- 5. Database Roles & Privilege Setup (Production Role Strategy)
-- ----------------------------------------------------------------------------
DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'lifeos_app_role') THEN
    CREATE ROLE lifeos_app_role NOLOGIN;
  END IF;
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'lifeos_readonly_role') THEN
    CREATE ROLE lifeos_readonly_role NOLOGIN;
  END IF;
END
$$;

-- Grant Schema Usage Privileges
GRANT USAGE ON SCHEMA identity, profile, people, relationship, calendar, event, task, goal, project, finance, knowledge, document, health, notification, ai, analytics, audit, search TO lifeos_app_role;
GRANT USAGE ON SCHEMA identity, profile, people, relationship, calendar, event, task, goal, project, finance, knowledge, document, health, notification, ai, analytics, audit, search TO lifeos_readonly_role;
