-- ============================================================================
-- LifeOS Database Migration: V1.2.1__seed_core_data.sql
-- Core Seed Data: Initial Roles, Permissions, Admin Account
-- Engine Target: PostgreSQL 16+
-- ============================================================================

-- System ID Constants
DO $$
DECLARE
  v_admin_role_id UUID := '018f0000-0000-7000-8000-000000000001'::uuid;
  v_user_role_id UUID  := '018f0000-0000-7000-8000-000000000002'::uuid;
  v_system_user_id UUID := '00000000-0000-0000-0000-000000000000'::uuid;
  v_perm_user_read UUID := '018f0000-0000-7000-8000-000000000010'::uuid;
  v_perm_user_write UUID := '018f0000-0000-7000-8000-000000000011'::uuid;
  v_perm_admin_all UUID := '018f0000-0000-7000-8000-000000000099'::uuid;
BEGIN

  -- 1. Insert Core Roles
  INSERT INTO identity.roles (id, role_code, role_name, description, is_system_role, created_by, updated_by)
  VALUES 
    (v_admin_role_id, 'ROLE_ADMIN', 'Administrator', 'System Administrator with full access', TRUE, v_system_user_id, v_system_user_id),
    (v_user_role_id, 'ROLE_USER', 'Standard User', 'Standard registered individual user', TRUE, v_system_user_id, v_system_user_id)
  ON CONFLICT (role_code) DO NOTHING;

  -- 2. Insert Core Permissions
  INSERT INTO identity.permissions (id, permission_code, resource, action, description, created_by, updated_by)
  VALUES
    (v_perm_user_read, 'user:read', 'user', 'read', 'Ability to view user profiles', v_system_user_id, v_system_user_id),
    (v_perm_user_write, 'user:write', 'user', 'write', 'Ability to modify user profiles', v_system_user_id, v_system_user_id),
    (v_perm_admin_all, 'admin:all', 'system', 'all', 'Superadmin full control permission', v_system_user_id, v_system_user_id)
  ON CONFLICT (permission_code) DO NOTHING;

  -- 3. Map Role Permissions
  INSERT INTO identity.role_permissions (role_id, permission_id)
  VALUES
    (v_admin_role_id, v_perm_admin_all),
    (v_admin_role_id, v_perm_user_read),
    (v_admin_role_id, v_perm_user_write),
    (v_user_role_id, v_perm_user_read),
    (v_user_role_id, v_perm_user_write)
  ON CONFLICT DO NOTHING;

END
$$;
