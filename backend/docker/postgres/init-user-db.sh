#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE EXTENSION IF NOT EXISTS "pgcrypto";
    CREATE EXTENSION IF NOT EXISTS "vector";
    
    DO \$$
    BEGIN
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'lifeos_app') THEN
        CREATE ROLE lifeos_app WITH LOGIN PASSWORD 'lifeos_secret_password' SUPERUSER;
      END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'lifeos_app_role') THEN
        CREATE ROLE lifeos_app_role NOLOGIN;
      END IF;
      IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'lifeos_readonly_role') THEN
        CREATE ROLE lifeos_readonly_role NOLOGIN;
      END IF;
    END
    \$$;

    GRANT ALL PRIVILEGES ON DATABASE lifeos_db TO lifeos_app;
EOSQL
