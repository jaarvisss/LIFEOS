-- ============================================================================
-- LifeOS Database Migration: V3.2.0__enable_vector_extension.sql
-- Ensure pgvector extension is installed in public schema
-- Engine Target: PostgreSQL 16+
-- ============================================================================

CREATE EXTENSION IF NOT EXISTS vector WITH SCHEMA public;
