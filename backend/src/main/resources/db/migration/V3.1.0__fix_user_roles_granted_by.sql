-- ============================================================================
-- LifeOS Database Migration: V3.1.0__fix_user_roles_granted_by.sql
-- Fix DEFAULT constraint on identity.user_roles.granted_by for JPA @JoinTable inserts
-- Engine Target: PostgreSQL 16+
-- ============================================================================

ALTER TABLE identity.user_roles 
  ALTER COLUMN granted_by SET DEFAULT '00000000-0000-0000-0000-000000000000'::uuid;
