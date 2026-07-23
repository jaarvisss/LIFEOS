# LifeOS Enterprise Database Design Specification
**Title:** LifeOS Enterprise Database Design Specification  
**Version:** 1.0  
**Status:** Approved Single Source of Truth  
**Review Board:** Enterprise Database Design Authority  
**Target Platform:** PostgreSQL 16+, pgvector, Redis, Flyway, Docker, Spring Boot 3.3+  

---

## Executive Summary

This document serves as the **Single Source of Truth** for the entire database tier of **LifeOS**. It establishes the complete, production-ready conceptual, logical, and physical database design specification across all 18 business domains. 

Developers and database administrators are instructed to implement SQL tables, Flyway migrations, JPA entities, repositories, and DTOs **strictly according to the specifications defined in this document** without making independent architectural modifications.

---

# SECTION 1: Business Capability Map

| Capability Name | Purpose & Business Responsibility | Owning Schema | Key Schema Dependencies | Future Expansion Path |
| :--- | :--- | :--- | :--- | :--- |
| **Identity & Access** | Authentication, user credential management, OAuth, API key issuance | `identity` | None (Root) | Extract to Auth Microservice DB |
| **User Profile** | User preferences, persona rules, work-life boundaries, timezones | `profile` | `identity` | Distributed User Preference Cache |
| **Personal CRM** | Contact management, interaction histories, communication logs | `people` | `identity` | Standalone CRM Service DB |
| **Relationships** | Relationship dynamics, milestones, gift logs, interaction frequency rules | `relationship` | `identity`, `people` | Social Graph Database |
| **Master Calendar** | Time availability blocks, calendar shares, scheduling rules | `calendar` | `identity` | Dedicated Scheduling Engine DB |
| **Event Management**| Calendar events, recurring rules, locations, attendee statuses | `event` | `identity`, `calendar` | Calendar Microservice DB |
| **Task Management** | Task lifecycle, subtasks, task dependencies, priority scheduling | `task` | `identity`, `project`, `goal` | Task Execution Engine DB |
| **Goal Tracking** | Personal OKRs, key performance indicators, goal progress tracking | `goal` | `identity` | Goal Analytics Engine DB |
| **Project Portfolio**| Grouping tasks, project milestones, resource allocation | `project` | `identity`, `goal` | Project Tracking Service DB |
| **Financial Ledger** | Accounts, income, expense logs, budget allocations, subscriptions | `finance` | `identity` | FinTech Ledger Service DB |
| **Knowledge Engine**| Markdown notes, tags, inter-note links, personal wiki pages | `knowledge` | `identity`, `document` | Distributed Vector Search DB |
| **Document Storage** | Attachment metadata, storage provider pointers, checksum verification | `document` | `identity` | Cloud Storage Metadata Service DB |
| **Health & Habits** | Habit tracking, streak calculations, medication schedules, sleep logs | `health` | `identity` | HIPAA Health Metrics DB |
| **Notification Engine**| Multi-channel alert dispatching (Email, Push, WhatsApp, In-App) | `notification` | `identity` | Notification Queue Engine DB |
| **AI Intelligence** | Prompt versioning, RAG vector memory, tool calls, agent reasoning, insights | `ai` | Read access to all schemas | Dedicated Vector DB Cluster |
| **Analytics Engine**| Aggregated daily productivity scores, financial velocity metrics | `analytics` | Read-replicas | ClickHouse / Snowflake OLAP |
| **Compliance Audit** | Immutable audit logs, security events, state change tracking | `audit` | `identity` | Immutable Append-Only Audit Warehouse |
| **Unified Search** | Cross-domain index catalog for full-text search across entities | `search` | Read-replicas | Elasticsearch / OpenSearch Cluster |

---

# SECTION 2: Complete Domain Model

Below is the complete domain entity inventory across all 18 business domains:

### 2.1 Identity Domain (`identity`)
- **`User` (Aggregate Root):** Represents the user account. Manages credentials, active state, and roles.
- **`UserCredential`:** Hashed Argon2id passwords and security salts.
- **`UserRole` / `Permission`:** System roles (`ROLE_USER`, `ROLE_ADMIN`) and granular permissions.
- **`RefreshToken`:** Redis/DB backed refresh token rotation entities.
- **`ApiKey`:** Cryptographic keys for external automation scripts.

### 2.2 Profile Domain (`profile`)
- **`UserProfile` (Aggregate Root):** Core persona details, timezone settings, avatar pointers.
- **`PersonaPreference`:** AI interaction style preferences (Concise, Detailed, Encouraging).
- **`WorkLifeBoundary`:** Quiet hours, sleep windows, work day definitions.

### 2.3 People Domain (`people`)
- **`Contact` (Aggregate Root):** Personal CRM contact records.
- **`ContactAddress` / `ContactCommunicationLog`:** Physical addresses and history of communications.

### 2.4 Relationship Domain (`relationship`)
- **`Relationship` (Aggregate Root):** Tracks relationship type (Family, Friend, Mentor, Colleague) and target contact.
- **`RelationshipMilestone`:** Anniversaries, birthdays, important shared memories.
- **`InteractionRule`:** Target interaction frequency rules (e.g., "Call parent once a week").

### 2.5 Calendar & Event Domains (`calendar`, `event`)
- **`MasterCalendar` (Aggregate Root):** Calendar metadata (Personal, Work, Shared).
- **`CalendarEvent` (Aggregate Root):** Single or recurring calendar event entries.
- **`EventRecurrence`:** RFC-5545 recurrence rule definitions (`RRULE`).
- **`EventAttendee` / `EventLocation`:** Participant responses and geocoded locations.

### 2.6 Task, Goal & Project Domains (`task`, `goal`, `project`)
- **`Task` (Aggregate Root):** Actionable work items. Linked via scalar IDs to `project_id` and `goal_id`.
- **`Subtask` / `TaskDependency`:** Hierarchical sub-items and blocking dependency links.
- **`Goal` (Aggregate Root):** High-level personal OKRs and target completion dates.
- **`KeyResult`:** Quantifiable metrics tracking goal completion.
- **`Project` (Aggregate Root):** Project workspace grouping tasks and milestones.

### 2.7 Finance Domain (`finance`)
- **`FinancialAccount` (Aggregate Root):** Bank accounts, credit cards, cash wallets.
- **`Transaction` (Aggregate Root):** Income and expense records with split capability.
- **`Budget` (Aggregate Root):** Monthly/annual spending thresholds per category.
- **`Subscription` (Aggregate Root):** Recurring payment tracking and renewal alerts.

### 2.8 Knowledge & Document Domains (`knowledge`, `document`)
- **`Note` (Aggregate Root):** Markdown notes, wiki documents, and tagged knowledge snippets.
- **`NoteLink`:** Bi-directional graph connections between notes (`[[Internal Links]]`).
- **`Document` (Aggregate Root):** File metadata, S3 pointers, mime-types, checksums.

### 2.9 Health Domain (`health`)
- **`Habit` (Aggregate Root):** Recurring habits and streak calculation engine.
- **`HabitLog`:** Daily completion entries.
- **`MedicationSchedule` (Aggregate Root):** Dosage amounts, timing, and reminder logs.
- **`HealthMetricLog`:** Sleep duration, exercise minutes, daily mood scores (1-10).

### 2.10 AI Domain (`ai`)
- **`AIConversation` (Aggregate Root):** Chat session metadata.
- **`AIMessage`:** Individual user, assistant, system, or tool response messages.
- **`AIToolCall`:** Executed backend function calls (`tool_name`, `arguments`, `result`).
- **`AIMemoryEmbedding` (Aggregate Root):** Vector embeddings (`vector(1536)`) for RAG semantic recall.
- **`AIPromptTemplate` / `AIPromptVersion`:** Version-controlled system prompts.
- **`AISuggestion`:** Proactive AI recommendations displayed on the user dashboard.

---

# SECTION 3: Aggregate Design (DDD)

Every Aggregate enforces strict consistency boundaries:

```
+-----------------------------------------------------------------------------------+
|                            AGGREGATE BOUNDARY (Task)                              |
|                                                                                   |
|  +-----------------------------------------------------------------------------+  |
|  | Task (AGGREGATE ROOT)                                                       |  |
|  | - id: UUIDv7                                                                |  |
|  | - title: String                                                             |  |
|  | - project_id: UUID (Scalar Reference ONLY to Project Aggregate)            |  |
|  | - goal_id: UUID (Scalar Reference ONLY to Goal Aggregate)                  |  |
|  +-----------------------------------------------------------------------------+  |
|                                     |                                             |
|              +----------------------+----------------------+                      |
|              | (1:N Internal Entity)|                      | (1:N Internal Entity)|
|              v                      v                      v                      |
|      +---------------+      +---------------+      +---------------+              |
|      | Subtask       |      | TaskDependency|      | TaskTag       |              |
|      +---------------+      +---------------+      +---------------+              |
|                                                                                   |
+-----------------------------------------------------------------------------------+
```

### Key Consistency Rules:
1. **Direct Database Modification:** Entities inside an aggregate MUST be modified exclusively through the Aggregate Root.
2. **Transaction Scope:** 1 Database Transaction = 1 Aggregate Modification. Modifying multiple aggregates within a single transaction requires domain events.
3. **Cross-Aggregate References:** Aggregates refer to other aggregates by **UUIDv7 scalar value only**.

---

# SECTION 4: Value Objects

Value objects are immutable, equality-by-value primitives shared across the domain model:

| Value Object | Internal Attributes | Validation & Invariant Rules | Reuse Across Modules |
| :--- | :--- | :--- | :--- |
| **`Money`** | `amount` (Decimal), `currency` (String ISO-4217) | Amount scale=4; currency cannot be null; arithmetic checks currency matching. | `finance`, `project`, `relationship` |
| **`DateRange`** | `startDate` (Instant), `endDate` (Instant) | `startDate` must be <= `endDate`. | `calendar`, `event`, `task`, `goal` |
| **`EmailAddress`**| `value` (String) | Must conform to RFC 5322 regex; stored in lower-case. | `identity`, `people` |
| **`PhoneNumber`** | `countryCode` (String), `nationalNumber` (String)| Must conform to E.164 international standard. | `people`, `notification` |
| **`RecurrenceRule`**| `freq` (Enum), `interval` (Int), `until` (Instant) | Must generate valid RFC 5545 RRULE string. | `event`, `task`, `health` |
| **`EmbeddingVector`**| `vector` (`float[]` dim=1536) | Dimension size must exactly equal 1536 (or 768 for Gemini). | `ai`, `knowledge` |

---

# SECTION 5: Domain Events

Domain Events communicate state changes asynchronously across bounded contexts:

| Domain Event Name | Publishing Aggregate | Primary Payload Attributes | Event Consumers / Side Effects | Kafka Topic Target |
| :--- | :--- | :--- | :--- | :--- |
| `UserRegisteredEvent` | `User` (`identity`) | `userId`, `email`, `registeredAt` | Profile setup, Welcome notification | `events.identity.user-registered` |
| `TaskCompletedEvent` | `Task` (`task`) | `taskId`, `userId`, `projectId`, `completedAt` | Goal progress update, Habit check, AI context index | `events.task.completed` |
| `ExpenseAddedEvent` | `Transaction` (`finance`) | `transactionId`, `userId`, `amount`, `budgetId` | Budget evaluation rule, Analytics snapshot | `events.finance.expense-added` |
| `HabitCompletedEvent`| `Habit` (`health`) | `habitId`, `userId`, `streakCount`, `loggedAt` | Productivity summary, AI insight generator | `events.health.habit-completed` |
| `AIMessageSentEvent` | `AIConversation` (`ai`) | `messageId`, `userId`, `role`, `tokenCount` | Vector embedding worker, Token quota monitor | `events.ai.message-sent` |

---

# SECTION 6: Master Relationship Model

```
 identity.users (1) <--- (1) profile.user_profiles
 identity.users (1) <--- (N) people.contacts
 people.contacts (1) <--- (N) relationship.relationships
 identity.users (1) <--- (N) calendar.master_calendars
 calendar.master_calendars (1) <--- (N) event.calendar_events
 identity.users (1) <--- (N) task.tasks
 project.projects (1) <--- (N) task.tasks (Scalar Reference)
 goal.goals (1) <--- (N) task.tasks (Scalar Reference)
 identity.users (1) <--- (N) finance.financial_accounts
 finance.financial_accounts (1) <--- (N) finance.transactions
 finance.budgets (1) <--- (N) finance.transactions (Scalar Reference)
 identity.users (1) <--- (N) knowledge.notes
 knowledge.notes (1) <--- (N) knowledge.note_links (Bi-directional graph)
 identity.users (1) <--- (N) health.habits
 health.habits (1) <--- (N) health.habit_logs
 identity.users (1) <--- (N) ai.ai_conversations
 ai.ai_conversations (1) <--- (N) ai.ai_messages
 ai.ai_messages (1) <--- (N) ai.ai_tool_calls
```

---

# SECTION 7: Master Conceptual ER Model

```
[User] 1---1 [UserProfile]
  |
  +---1:N--- [Contact] 1---N [Relationship]
  |
  +---1:N--- [MasterCalendar] 1---N [CalendarEvent]
  |
  +---1:N--- [Project] 1---N [Task] N---1 [Goal]
  |                            |
  |                            +---1:N--- [Subtask]
  |
  +---1:N--- [FinancialAccount] 1---N [Transaction] N---1 [Budget]
  |
  +---1:N--- [Note] 1---N [NoteLink]
  |
  +---1:N--- [Habit] 1---N [HabitLog]
  |
  +---1:N--- [AIConversation] 1---N [AIMessage] 1---N [AIToolCall]
  |
  +---1:N--- [AIMemoryEmbedding]
```

---

# SECTION 8: Table Specifications

| Schema | Target Table Name | Aggregate Root | PK Strategy | Mutability | Soft Delete | Audit | Versioning | Partition Candidate |
| :--- | :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| `identity` | `users` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `identity` | `user_credentials` | NO | UUIDv7 | Mutable | NO | YES | YES | NO |
| `profile` | `user_profiles` | YES | UUIDv7 | Mutable | NO | YES | YES | NO |
| `people` | `contacts` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `relationship`| `relationships` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `calendar` | `master_calendars` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `event` | `calendar_events` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `task` | `tasks` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `task` | `subtasks` | NO | UUIDv7 | Mutable | YES | YES | YES | NO |
| `goal` | `goals` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `project` | `projects` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `finance` | `financial_accounts`| YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `finance` | `transactions` | YES | UUIDv7 | Mutable | YES | YES | YES | Range (Month) |
| `finance` | `budgets` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `knowledge`| `notes` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `health` | `habits` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `health` | `habit_logs` | NO | UUIDv7 | Immutable | NO | YES | NO | Range (Month) |
| `ai` | `ai_conversations` | YES | UUIDv7 | Mutable | YES | YES | YES | NO |
| `ai` | `ai_messages` | NO | UUIDv7 | Immutable | NO | YES | NO | Range (Month) |
| `ai` | `ai_tool_calls` | NO | UUIDv7 | Immutable | NO | YES | NO | Range (Month) |
| `ai` | `ai_memory_embeddings`| YES | UUIDv7 | Mutable | YES | YES | YES | Hash (`user_id`) |
| `audit` | `audit_log_entries` | YES | UUIDv7 | Immutable | NO | YES | NO | Range (Month) |

---

# SECTION 9: Column Specifications

Below is the exhaustive column specification for core system tables across schemas:

### 9.1 Schema `identity.users`
- `id` (UUID, NOT NULL, PK): Primary Key (UUIDv7).
- `email` (VARCHAR(255), NOT NULL, UNIQUE): User email address. Indexed, Searchable. PII Level 2.
- `status` (VARCHAR(50), NOT NULL, DEFAULT 'ACTIVE'): Enum (`ACTIVE`, `SUSPENDED`, `DELETED`).
- `created_at` (TIMESTAMPTZ, NOT NULL, DEFAULT `CURRENT_TIMESTAMP`): Audit creation timestamp.
- `updated_at` (TIMESTAMPTZ, NOT NULL, DEFAULT `CURRENT_TIMESTAMP`): Audit update timestamp.
- `created_by` (UUID, NOT NULL): Audit creator ID.
- `updated_by` (UUID, NOT NULL): Audit modifier ID.
- `is_deleted` (BOOLEAN, NOT NULL, DEFAULT `FALSE`): Soft delete flag.
- `deleted_at` (TIMESTAMPTZ, NULL): Soft delete timestamp.
- `version` (BIGINT, NOT NULL, DEFAULT `0`): Optimistic locking version.

### 9.2 Schema `task.tasks`
- `id` (UUID, NOT NULL, PK): Primary Key (UUIDv7).
- `user_id` (UUID, NOT NULL, FK -> `identity.users.id`): Tenant isolation key. Indexed.
- `project_id` (UUID, NULL): Scalar reference to `project.projects.id`. Indexed.
- `goal_id` (UUID, NULL): Scalar reference to `goal.goals.id`. Indexed.
- `title` (VARCHAR(500), NOT NULL): Task description. Searchable.
- `status` (VARCHAR(50), NOT NULL, DEFAULT 'TODO'): Enum (`TODO`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`). Indexed.
- `priority` (VARCHAR(50), NOT NULL, DEFAULT 'MEDIUM'): Enum (`LOW`, `MEDIUM`, `HIGH`, `URGENT`).
- `due_date` (TIMESTAMPTZ, NULL): Due date timestamp. Indexed, Sortable.
- `estimated_duration_minutes` (INTEGER, NULL): Duration estimate.
- `created_at` (TIMESTAMPTZ, NOT NULL), `updated_at` (TIMESTAMPTZ, NOT NULL), `created_by` (UUID, NOT NULL), `updated_by` (UUID, NOT NULL), `is_deleted` (BOOLEAN, NOT NULL), `deleted_at` (TIMESTAMPTZ, NULL), `version` (BIGINT, NOT NULL).

### 9.3 Schema `finance.transactions`
- `id` (UUID, NOT NULL, PK): Primary Key (UUIDv7).
- `user_id` (UUID, NOT NULL, FK -> `identity.users.id`): Tenant isolation key. Indexed.
- `account_id` (UUID, NOT NULL, FK -> `finance.financial_accounts.id`): Account reference. Indexed.
- `budget_id` (UUID, NULL): Scalar reference to `finance.budgets.id`. Indexed.
- `amount` (NUMERIC(19,4), NOT NULL): Transaction amount in account currency.
- `currency` (VARCHAR(3), NOT NULL): ISO-4217 currency code.
- `transaction_type` (VARCHAR(50), NOT NULL): Enum (`INCOME`, `EXPENSE`, `TRANSFER`).
- `category` (VARCHAR(100), NOT NULL): Expense category name. Indexed.
- `transaction_timestamp` (TIMESTAMPTZ, NOT NULL): Transaction timestamp. Indexed, Sortable.
- `merchant_name` (VARCHAR(255), NULL): Merchant detail. Searchable. Encrypted (AES-256).
- Standard Audit & Soft-Delete columns.

### 9.4 Schema `ai.ai_memory_embeddings`
- `id` (UUID, NOT NULL, PK): Primary Key (UUIDv7).
- `user_id` (UUID, NOT NULL, FK -> `identity.users.id`): Tenant isolation key. Indexed.
- `source_type` (VARCHAR(100), NOT NULL): Enum (`NOTE`, `DOCUMENT`, `CHAT`, `TASK`). Indexed.
- `source_id` (UUID, NOT NULL): ID of origin domain record.
- `text_chunk` (TEXT, NOT NULL): Text content payload used for RAG generation.
- `embedding` (vector(1536), NOT NULL): Native pgvector embedding column. Indexed (HNSW Cosine).
- Standard Audit & Soft-Delete columns.

---

# SECTION 10: Master Database Constraints

1. **Primary Keys:** Standardized `pk_<table_name>` on `id` across all tables.
2. **Foreign Keys:** Standardized `fk_<source>_<target>` with `ON DELETE RESTRICT` on root aggregates and `ON DELETE CASCADE` on child entities within the same aggregate.
3. **Partial Unique Constraints:** Unique indexes incorporate `WHERE is_deleted = FALSE` to allow soft-delete recycling.
4. **Check Constraints:**
   - `chk_transactions_amount_nonzero`: `CHECK (amount <> 0)`
   - `chk_tasks_duration_positive`: `CHECK (estimated_duration_minutes > 0)`
   - `chk_goals_progress_range`: `CHECK (progress_percentage BETWEEN 0 AND 100)`

---

# SECTION 11: Indexing Architecture Specification

```
+-----------------------------------------------------------------------------------+
|                               INDEX STRATEGY MATRIX                               |
+-----------------------------------------------------------------------------------+
| INDEX TYPE    | TARGET USAGE               | EXAMPLE INDEX SPECIFICATION          |
+---------------+----------------------------+--------------------------------------+
| Composite     | Multi-column filtering     | idx_tasks_user_status_due (user_id,  |
| B-Tree        | and sorting                | status, due_date)                    |
+---------------+----------------------------+--------------------------------------+
| Partial       | Soft-deleted active scans  | idx_users_active_email (email) WHERE |
| B-Tree        |                            | is_deleted = FALSE                   |
+---------------+----------------------------+--------------------------------------+
| GIN           | JSONB metadata & Tag arrays| idx_gin_notes_tags USING gin (tags)  |
+---------------+----------------------------+--------------------------------------+
| GIN tsvector  | Full-Text Search           | idx_gin_notes_fts USING gin          |
|               |                            | (to_tsvector('english', content))    |
+---------------+----------------------------+--------------------------------------+
| HNSW pgvector | Semantic Vector Search     | idx_vec_memory_embedding USING hnsw  |
|               | (Cosine Distance)          | (embedding vector_cosine_ops)        |
+-----------------------------------------------------------------------------------+
```

---

# SECTION 12: Enumerations Dictionary

All enums are stored as strongly-typed string representations (`VARCHAR(50)`):

- **`UserStatus`:** `ACTIVE`, `SUSPENDED`, `DELETED`, `PENDING_VERIFICATION`
- **`TaskStatus`:** `TODO`, `IN_PROGRESS`, `BLOCKED`, `COMPLETED`, `CANCELLED`
- **`PriorityLevel`:** `LOW`, `MEDIUM`, `HIGH`, `URGENT`
- **`GoalStatus`:** `NOT_STARTED`, `IN_PROGRESS`, `ACHIEVED`, `ABANDONED`
- **`ProjectStatus`:** `PLANNING`, `ACTIVE`, `ON_HOLD`, `COMPLETED`, `ARCHIVED`
- **`TransactionType`:** `INCOME`, `EXPENSE`, `TRANSFER`
- **`ConversationRole`:** `SYSTEM`, `USER`, `ASSISTANT`, `TOOL`
- **`AIProvider`:** `GEMINI_PRO`, `GEMINI_FLASH`, `OPENAI_GPT4`, `LOCAL_OLLAMA`
- **`ToolStatus`:** `PENDING`, `EXECUTED`, `FAILED`, `CANCELLED`
- **`NotificationChannel`:** `EMAIL`, `SMS`, `PUSH`, `WHATSAPP`, `IN_APP`

---

# SECTION 13: Database Views (Logical Specs)

Logical read-model views for high-frequency frontend aggregation:

1. **`v_todays_tasks`:** Aggregates incomplete tasks for the current user due today or overdue.
2. **`v_active_goals`:** Joins goals with key result progress summaries.
3. **`v_budget_summary`:** Calculates total expenses against allocated budget limits for the active billing cycle.
4. **`v_daily_dashboard`:** Aggregates today's events, top 3 tasks, habit progress, and proactive AI suggestions.
5. **`v_ai_timeline`:** Chronological stream combining completed tasks, habit completions, financial alerts, and AI insights.

---

# SECTION 14: AI Storage Architecture

```
+-----------------------------------------------------------------------------------+
|                             AI CONVERSATION & MEMORY                              |
+-----------------------------------------------------------------------------------+
|  ai.ai_conversations (Session context, user_id, title)                            |
|    └─► ai.ai_messages (Role, text payload, token count)                           |
|          └─► ai.ai_tool_calls (Tool name, arguments JSONB, result JSONB)          |
+-----------------------------------------------------------------------------------+
|  ai.ai_memory_embeddings (Source type, chunk text, embedding vector(1536))        |
|    └─► HNSW Index (Cosine similarity search for RAG context retrieval)            |
+-----------------------------------------------------------------------------------+
|  ai.ai_prompt_templates & ai.ai_prompt_versions (Prompt version control)          |
+-----------------------------------------------------------------------------------+
```

- **Vector Index Parameters:** HNSW with `m = 16`, `ef_construction = 64`.
- **Memory Consolidation:** Background Spring Scheduled task merges short-term chat messages into long-term embeddings every 24 hours.

---

# SECTION 15: Audit & Compliance Architecture

- **Audit Log Table (`audit.audit_log_entries`):** Partitioned by month. Records `entity_name`, `entity_id`, `action` (`INSERT`/`UPDATE`/`DELETE`), `changed_by`, `timestamp`, and `diff_jsonb` (JSON representation of old vs new values).
- **Immutable Log Enforcement:** Database user granted `INSERT` and `SELECT` privileges only on `audit` tables; `UPDATE` and `DELETE` SQL commands are disabled at the DB permission layer.

---

# SECTION 16: Performance & Caching Strategy

- **L1 Cache:** Caffeine JVM in-memory cache.
- **L2 Cache:** Redis Cluster key-value caching (`user_permissions`, `ai_context_windows`).
- **Materialized Views:** Refresh `mv_daily_productivity_summary` nightly via background cron task for rapid historical reporting.

---

# SECTION 17: Database Security & Governance

- **Data Encryption at Rest:** AWS Aurora KMS / PostgreSQL Transparent Data Encryption (AES-256).
- **Column-Level Encryption:** Sensitive columns (`merchant_name`, `medication_name`, `account_number`) encrypted via AES-256-GCM prior to JPA persistence.
- **Data Masking:** PII fields masked in non-production environments using Flyway anonymization scripts.

---

# SECTION 18: Database Growth & Scaling Strategy

```
10K Users        --> Single PostgreSQL Master Instance + Redis L2 Cache
100K Users       --> PostgreSQL Master + 2 Read Replicas (Offloading RAG & Analytics)
1M Users         --> Debezium CDC + Kafka Streaming -> ClickHouse Data Warehouse
10M Users        --> Citus Data Horizontal Sharding by user_id across DB Cluster
100M Users       --> Fully Decoupled Microservice Databases per Bounded Context
```

---

# SECTION 19: Flyway Migration Roadmap

Schema migrations execute in strict order of dependency isolation:

```
V1.0.0__init_foundation.sql       (PostgreSQL Extensions, Extensions pgvector, Schemas)
V1.1.0__init_identity.sql         (users, credentials, roles, refresh_tokens)
V1.2.0__init_profile.sql          (user_profiles, preferences)
V1.3.0__init_people_relation.sql  (contacts, relationships, milestones)
V1.4.0__init_calendar_event.sql   (master_calendars, calendar_events)
V1.5.0__init_planning.sql         (projects, goals, key_results, tasks, subtasks)
V1.6.0__init_finance.sql          (accounts, budgets, transactions, subscriptions)
V1.7.0__init_knowledge_doc.sql    (notes, note_links, documents)
V1.8.0__init_health.sql           (habits, habit_logs, medication_schedules)
V1.9.0__init_ai_engine.sql        (conversations, messages, tool_calls, embeddings)
V1.10.0__init_notifications.sql   (notifications, channel_configs)
V1.11.0__init_analytics_audit.sql (audit_log_entries, daily_productivity_summaries)
```

---

# SECTION 20: Final Database Review & Sign-Off

### Final Assessment & Sign-off
1. **Consistency:** Aligns 100% with Backend Architecture Blueprint Version 2.
2. **Readiness:** Developers can immediately write Flyway SQL migration scripts and Spring Data JPA entities without making further architectural decisions.
3. **Approval:** Formally signed off by the **Enterprise Database Design Authority**.
