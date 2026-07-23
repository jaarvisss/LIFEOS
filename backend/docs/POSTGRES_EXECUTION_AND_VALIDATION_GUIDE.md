# LifeOS PostgreSQL Execution, Flyway Migration & Database Validation Guide
**Document Version:** 1.0.0  
**Target Environment:** PostgreSQL 16+, pgvector, Redis, Docker, Spring Boot 3.3+, Flyway  
**Role:** Lead DevOps Engineer & Principal PostgreSQL DBA  

---

## Executive Overview

This document provides the operational execution playbook to start the PostgreSQL 16 database container, execute all version-controlled Flyway migrations (`V1.0.0` through `V3.0.0`), perform automated SQL verification against all 18 domain schemas, and navigate the instantiated database objects inside **pgAdmin 4**.

---

# STEP 1: Flyway Migration Verification & Naming Order

The Flyway migration scripts are located at `src/main/resources/db/migration/`:

```
src/main/resources/db/migration/
├── V1.0.0__foundation.sql                       # Milestone 1: Schemas, Extensions, UUIDv7, Audit triggers
├── V1.1.0__identity_platform.sql                # Milestone 2: Identity & Auth tables, RBAC, tokens
├── V1.2.0__personal_foundation.sql              # Milestone 3: Profiles, Contacts, Relationships, Notifications
├── V1.2.1__seed_core_data.sql                   # Milestone 3: Core Roles & Permissions seed data
├── V2.0.0__phase2_domain_modules.sql            # Phase 2: Tasks, Goals, Projects, Calendar, Notes, Finance, Health
└── V3.0.0__phase3_ai_analytics_enterprise.sql   # Phase 3: AI Vector Memory, Embeddings, Analytics, Audit, RLS
```

---

# STEP 2: Docker Environment & Database Startup

All Docker configurations are provisioned inside the `docker/` folder.

### 2.1 Starting PostgreSQL, Redis, and pgAdmin 4
Execute the following commands in PowerShell or Terminal from the project root (`c:\Users\jagad\OneDrive\Desktop\LIFEOS\backend`):

```bash
# Navigate to docker directory
cd docker

# Start PostgreSQL 16 (with pgvector), Redis, and pgAdmin 4 containers
docker-compose up -d
```

### 2.2 Verify Container Health
```bash
# Check status of containers
docker-compose ps
```

*Expected Container Output:*
- `lifeos-postgres`: Running on port `5432` (Health: healthy)
- `lifeos-redis`: Running on port `6379` (Health: healthy)
- `lifeos-pgadmin`: Running on port `5050`

---

# STEP 3: Executing Flyway Migrations

Migrations can be executed via Spring Boot application launch or standalone Maven / Docker commands.

### Option A: Via Spring Boot Application Startup (Recommended)
Run the application from the `backend/` directory:
```bash
mvn spring-boot:run
```

*Expected Console Log Output:*
```text
INFO --- o.f.c.i.s.JdbcTableSchema : Creating Schema History table "identity"."flyway_schema_history" ...
INFO --- o.f.c.i.c.DbMigrate : Current version of schema "identity": << Empty Schema >>
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "1.0.0 - foundation"
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "1.1.0 - identity platform"
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "1.2.0 - personal foundation"
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "1.2.1 - seed core data"
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "2.0.0 - phase2 domain modules"
INFO --- o.f.c.i.c.DbMigrate : Migrating schema "identity" to version "3.0.0 - phase3 ai analytics enterprise"
INFO --- o.f.c.i.c.DbMigrate : Successfully applied 6 migrations to schema "identity", now at version v3.0.0
```

### Option B: Via Maven Flyway Plugin Direct Command
```bash
mvn flyway:migrate
```

---

# STEP 4: PostgreSQL Automated Database Validation Queries

To verify that every schema, table, primary key, foreign key, index, trigger, function, and view exists physically in PostgreSQL, connect to PostgreSQL using `psql` or pgAdmin:

```bash
docker exec -it lifeos-postgres psql -U lifeos_app -d lifeos_db
```

### 4.1 Schema Verification Query
```sql
SELECT schema_name 
FROM information_schema.schemata 
WHERE schema_name IN ('identity','profile','people','relationship','calendar','event','task','goal','project','finance','knowledge','document','health','notification','ai','analytics','audit','search')
ORDER BY schema_name;
```
*Expected Result:* Returns 18 rows matching all 18 DDD Bounded Context schemas.

### 4.2 Table Inventory & Row Count Query
```sql
SELECT table_schema, count(table_name) AS table_count
FROM information_schema.tables 
WHERE table_schema IN ('identity','profile','people','relationship','calendar','event','task','goal','project','finance','knowledge','document','health','notification','ai','analytics','audit','search')
GROUP BY table_schema
ORDER BY table_schema;
```
*Expected Result:* Tables exist across all 18 schemas.

### 4.3 UUIDv7 Function Verification Query
```sql
SELECT generate_uuid_v7() AS test_uuid_v7;
```
*Expected Result:* Generates a valid 128-bit UUID (e.g., `018f3a8b-4f92-7000-8000-123456789abc`).

### 4.4 Vector Extension & Index Verification Query
```sql
SELECT indexname, indexdef 
FROM pg_indexes 
WHERE schemaname = 'ai' AND indexname = 'idx_vec_ai_embeddings';
```
*Expected Result:* Returns HNSW vector index definition using `vector_cosine_ops`.

### 4.5 Seed Data Verification Query
```sql
SELECT role_code, role_name FROM identity.roles ORDER BY role_code;
```
*Expected Result:* Returns `ROLE_ADMIN` and `ROLE_USER`.

---

# STEP 5: pgAdmin 4 Interactive Navigation Checklist

1. Open your browser and navigate to: `http://localhost:5050`
2. Login with pgAdmin credentials:
   - **Email:** `admin@lifeos.com`
   - **Password:** `admin_secret_password`
3. Click **Add New Server**:
   - **Name:** `LifeOS PostgreSQL 16`
   - **Connection Tab:**
     - **Host name/address:** `postgres` (inside docker) or `localhost` (from host machine)
     - **Port:** `5432`
     - **Maintenance database:** `lifeos_db`
     - **Username:** `lifeos_app`
     - **Password:** `lifeos_secret_password`
4. Expand the Database Tree:

```text
Servers
└── LifeOS PostgreSQL 16
    └── Databases
        └── lifeos_db
            └── Schemas (18 Schemas)
                ├── identity
                │   ├── Tables (users, user_credentials, roles, permissions, user_roles, refresh_tokens, api_keys, user_devices, login_history)
                │   ├── Views
                │   └── Functions
                ├── profile (user_profiles, persona_preferences, work_life_boundaries)
                ├── people (contacts, contact_addresses, communication_logs, v_contact_summary)
                ├── relationship (relationships, milestones, interaction_rules, v_upcoming_birthdays, v_relationship_dashboard)
                ├── calendar (calendars)
                ├── event (events, reminders, recurring_rules)
                ├── task (tasks, subtasks, task_labels, v_todays_tasks)
                ├── goal (goals, goal_milestones)
                ├── project (projects, project_members)
                ├── finance (accounts, budgets, transactions, recurring_transactions, v_budget_summary)
                ├── knowledge (notebooks, notes, note_tags, knowledge_links)
                ├── document (documents, document_versions, document_tags)
                ├── health (habits, habit_logs, workouts, meals, sleep_logs, body_metrics, mv_habit_streak_summary)
                ├── notification (notification_preferences, notification_channels, delivery_logs)
                ├── ai (ai_conversations, ai_messages, ai_memory, embeddings [HNSW Index], reasoning_logs, ai_tasks, ai_suggestions)
                ├── analytics (dashboards, productivity_metrics, life_scores, usage_statistics)
                ├── audit (audit_logs [Partitioned], entity_history, activity_logs)
                └── search (search_index, search_vectors [GIN tsvector Index])
```

---

# STEP 6: Error Recovery & Troubleshooting

If a Flyway migration fails due to database locks or bad SQL:

1. Repair Flyway schema history:
   ```bash
   mvn flyway:repair
   ```
2. Reset container environment (destroys data volume for clean restart):
   ```bash
   cd docker
   docker-compose down -v
   docker-compose up -d
   ```

---

## Final Validation Sign-off

With Docker launched and `mvn spring-boot:run` executed, the LifeOS PostgreSQL 16 database physically exists on disk, fully migrated up to `v3.0.0`, seeded, and ready for production Spring Data JPA interactions.
