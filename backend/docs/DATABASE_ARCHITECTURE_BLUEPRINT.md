# LifeOS Enterprise Database Architecture Blueprint
**Document Version:** 1.0.0  
**Status:** Final Architectural Baseline  
**Review Board:** Enterprise Database Architecture Review Board  
**Target Technologies:** PostgreSQL 16+, pgvector, Redis, Flyway, Docker, Spring Boot 3.3+  

---

## Executive Summary

**LifeOS** is an AI-first Personal Operating System. It synthesizes user data across calendar management, project tracking, personal finance, health metrics, relationship management, knowledge bases, and proactive AI context engines.

This document establishes the official **Enterprise Database Architecture Blueprint** for LifeOS. It defines the database philosophy, storage standards, domain entity models, relationship rules, AI data storage architectures, indexing strategies, security policies, and 10+ year scalability roadmaps required to support millions of active users while remaining 100% aligned with the approved **Backend Architecture Blueprint Version 2**.

---

# SECTION 1: Database Philosophy

## 1.1 Core Storage Stack & Justification

| Technology Component | Architectural Role | Justification for LifeOS |
| :--- | :--- | :--- |
| **PostgreSQL 16+** | Core Relational Data Store (OLTP) | LifeOS features deeply interconnected domains (Tasks, Budgets, Calendar, Health) requiring strict ACID transactions, complex relational queries, foreign key enforcement, declarative partitioning, and Row-Level Security (RLS). |
| **pgvector Extension** | Native Vector Similarity Engine | Enables semantic embedding storage and vector similarity searches directly inside PostgreSQL. Eliminates the operational complexity and cost of maintaining a separate vector database (e.g., Pinecone/Weaviate) on Day 1 while keeping embeddings co-located with user relational context. |
| **Redis** | In-Memory Data Store & Cache (L2) | Serves as high-speed cache, token blacklist, rate-limiting counter, active AI context window buffer, and pub/sub message broker for real-time Server-Sent Events (SSE). |
| **Flyway** | Database Schema Version Control | Guarantees repeatable, immutable, version-controlled schema migrations (`V1.0.0__...sql`) across all developer environments, staging pipelines, and production clusters. |

```
+------------------------------------------------------------------------------------+
|                               APPLICATION LAYER                                    |
|               (Spring Boot 3.3+ Application Services / Repositories)              |
+------------------------------------------------------------------------------------+
                         |                                   |
           (Relational / Vector Data)                      (Caching / Sessions)
                         v                                   v
+----------------------------------------------------+  +----------------------------+
|                POSTGRESQL 16+ ENGINE               |  |       REDIS CLUSTER        |
|                                                    |  |                            |
|  +---------------------+  +---------------------+  |  |  * Active AI Context Cache |
|  |  Relational Schemas |  |   pgvector Storage  |  |  |  * JWT Token Blacklist     |
|  | (identity, task,    |  | (HNSW Indexing for  |  |  |  * Rate Limiting Counters  |
|  |  finance, health)   |  |  AI Vector Memory)  |  |  |  * L2 Entity/Query Cache   |
|  +---------------------+  +---------------------+  |  +----------------------------+
|                           |                        |
|  +----------------------------------------------+  |
|  | Flyway Migration Control & Row-Level Security|  |
|  +----------------------------------------------+  |
+----------------------------------------------------+
```

---

# SECTION 2: Database Standards

## 2.1 Primary Keys & Identifier Strategy
- **Standard Identifier:** **128-bit UUID Version 7 (UUIDv7)** across all database tables.
- **Justification for UUIDv7 over UUIDv4 and Auto-Increment BigInt:**
  1. **Time-Ordered Sequential B-Tree Performance:** UUIDv7 combines a 48-bit UNIX millisecond timestamp prefix with random bits. This eliminates the random page-split and index fragmentation performance penalty of UUIDv4 while maintaining global uniqueness.
  2. **Microservice Extraction Ready:** UUIDs prevent primary key collision during future database sharding or microservice splitting.
  3. **Security:** Obfuscates total record counts and prevents sequential enumeration attacks on REST API endpoints.

## 2.2 Standard Audit Fields
Every domain table MUST contain the following audit columns:

| Column Name | Data Type | Nullable | Default Value | Purpose |
| :--- | :--- | :---: | :--- | :--- |
| `created_at` | `TIMESTAMPTZ` | NO | `CURRENT_TIMESTAMP` | UTC timestamp of record creation |
| `updated_at` | `TIMESTAMPTZ` | NO | `CURRENT_TIMESTAMP` | UTC timestamp of last modification |
| `created_by` | `UUID` | NO | System/User ID | Identifier of user or service creating record |
| `updated_by` | `UUID` | NO | System/User ID | Identifier of user or service updating record |
| `version` | `BIGINT` | NO | `0` | Optimistic locking counter for Spring Data JPA |

## 2.3 Soft Delete Strategy
- **Columns:** `deleted_at` (`TIMESTAMPTZ`, NULLable) and `is_deleted` (`BOOLEAN DEFAULT FALSE`).
- **Rules:** Active queries filter on `WHERE is_deleted = FALSE`.
- **Partial Unique Indexes:** Unique constraints must incorporate partial indexing to allow re-creation of soft-deleted records:  
  `CREATE UNIQUE INDEX uk_users_email ON identity.users (email) WHERE is_deleted = FALSE;`

## 2.4 Timestamp & Time Zone Standards
- All database timestamps MUST be defined as `TIMESTAMPTZ` (Timestamp with Time Zone).
- PostgreSQL engine time zone MUST be enforced to `UTC`.
- Time zone localization occurs strictly at the presentation layer using the user's profile time zone setting.

## 2.5 Naming Conventions

```
Tables:                 snake_case, plural           (identity.users, task.tasks)
Columns:                snake_case, singular         (user_id, task_title, created_at)
Primary Keys:           pk_<table_name>              (pk_users, pk_tasks)
Foreign Keys:           fk_<source>_<target>         (fk_tasks_projects)
Unique Constraints:     uk_<table_name>_<column(s)>  (uk_users_email)
B-Tree Indexes:         idx_<table_name>_<column(s)> (idx_tasks_user_status)
GIN Indexes:            idx_gin_<table_name>_<col>   (idx_gin_notes_content)
pgvector Indexes:       idx_vec_<table_name>_<col>   (idx_vec_embeddings_vector)
Schemas:                snake_case                   (identity, finance, ai)
Enums:                  UPPER_SNAKE_CASE             (varchar string representations)
```

## 2.6 JSONB Column Usage Rules
- JSONB columns are **strictly restricted** to non-queryable payload metadata, third-party webhook payloads, and flexible AI parameter bags.
- Core business entities, foreign keys, and queryable search fields MUST NEVER be hidden inside JSONB columns.

---

# SECTION 3: Database Modules (Schemas)

The database is divided into 18 logical PostgreSQL schemas corresponding 1:1 with backend Bounded Contexts.

| Module / Schema | Primary Purpose | Core Data Managed | Key Schema Dependencies | Future Scalability |
| :--- | :--- | :--- | :--- | :--- |
| **`identity`** | Auth & Access Control | Users, Credentials, Refresh Tokens, Roles, Permissions | None (Root Schema) | Extract to Auth Service DB |
| **`profile`** | User Preferences | Life profiles, Persona settings, Work-life balance rules | `identity` | User preferences cache |
| **`people`** | Personal CRM | Contacts, Interaction logs, Personal relationship entries | `identity` | CRM Microservice |
| **`relationship`**| Relationship Dynamics | Milestones, Relationship types, Frequency rules | `identity`, `people` | Social Graph DB |
| **`calendar`** | Master Calendar | Calendar definitions, Time slots, Availability blocks | `identity` | High-throughput scheduling DB |
| **`event`** | Single & Recurring Events| Events, Recurrence rules, Locations, Attendees | `identity`, `calendar` | Calendar Microservice |
| **`task`** | Execution Management | Tasks, Subtasks, Task dependencies, Execution state | `identity`, `project`, `goal` | Task Engine DB |
| **`goal`** | Goals & OKRs | Personal goals, Key results, OKR progress metrics | `identity` | Goal Analytics Engine |
| **`project`** | Portfolio Management | Projects, Milestones, Project resources | `identity`, `goal` | Project Service DB |
| **`finance`** | Ledger & Budgets | Accounts, Income, Expenses, Budgets, Subscriptions | `identity` | Financial Ledger DB |
| **`knowledge`** | Personal Wiki & Notes | Markdown notes, Inter-note links, Tags, Folders | `identity`, `document` | Vector/Search DB |
| **`document`** | Storage Attachments | Document metadata, Attachment storage references | `identity` | Storage Metadata DB |
| **`health`** | Health & Habits | Habits, Habit logs, Medication schedules, Sleep logs | `identity` | HIPAA Health DB |
| **`notification`**| Multi-Channel Alerts | Notifications, Subscriptions, Delivery logs | `identity` | Notification Queue DB |
| **`ai`** | AI Memory & Reasoning | Prompts, Embeddings, Contexts, Conversations, Tools | All schemas (Read-only) | Dedicated AI Engine Vector DB |
| **`analytics`** | Aggregated Insights | Daily productivity scores, Financial velocity metrics| Read-replica projections | ClickHouse / OLAP Warehouse |
| **`audit`** | Compliance Trail | Immutable state change logs, Security events | `identity` | Immutable Append-Only DB |
| **`search`** | Unified Index Catalog | Cross-entity text indexes, Entity references | Read projections | Elasticsearch / OpenSearch |

---

# SECTION 4: Complete Domain Model (Entities & Aggregates)

Below is the complete domain model listing Aggregate Roots, Entities, Value Objects, and Domain Events across all 18 modules.

```
====================================================================================
MODULE 1: identity
====================================================================================
Aggregate Root:
  - User (Entities: User, UserCredential, UserRole, RefreshToken, ApiKey)
Value Objects:
  - EmailAddress, HashedPassword, UserStatus
Domain Events:
  - UserRegisteredEvent, UserLoggedInEvent, PasswordResetRequestedEvent

====================================================================================
MODULE 2: profile
====================================================================================
Aggregate Root:
  - UserProfile (Entities: UserProfile, PersonaPreference, WorkLifeBoundary)
Value Objects:
  - TimeZone, AvatarUrl, WorkHoursRange
Domain Events:
  - ProfileUpdatedEvent, WorkHoursChangedEvent

====================================================================================
MODULE 3: people
====================================================================================
Aggregate Root:
  - Contact (Entities: Contact, ContactAddress, ContactCommunicationLog)
Value Objects:
  - PhoneNumber, SocialHandle, RelationshipTier
Domain Events:
  - ContactCreatedEvent, ContactInteractionLoggedEvent

====================================================================================
MODULE 4: relationship
====================================================================================
Aggregate Root:
  - Relationship (Entities: Relationship, RelationshipMilestone, ImportantDate)
Value Objects:
  - RelationshipType, InteractionFrequency
Domain Events:
  - MilestoneReachedEvent, RelationshipReminderTriggeredEvent

====================================================================================
MODULE 5: calendar
====================================================================================
Aggregate Root:
  - MasterCalendar (Entities: MasterCalendar, CalendarShare, AvailabilityBlock)
Value Objects:
  - CalendarColor, AccessLevel
Domain Events:
  - CalendarCreatedEvent, AvailabilityUpdatedEvent

====================================================================================
MODULE 6: event
====================================================================================
Aggregate Root:
  - CalendarEvent (Entities: CalendarEvent, EventRecurrence, EventAttendee, EventLocation)
Value Objects:
  - DateTimeRange, RecurrenceRule, EventStatus
Domain Events:
  - EventScheduledEvent, EventCancelledEvent, RecurrenceExpandedEvent

====================================================================================
MODULE 7: task
====================================================================================
Aggregate Root:
  - Task (Entities: Task, Subtask, TaskDependency, TaskTag)
Value Objects:
  - TaskPriority, TaskStatus, EstimatedDuration
Domain Events:
  - TaskCreatedEvent, TaskCompletedEvent, TaskOverdueEvent

====================================================================================
MODULE 8: goal
====================================================================================
Aggregate Root:
  - Goal (Entities: Goal, KeyResult, GoalMilestone)
Value Objects:
  - TargetMetric, GoalCategory, ProgressPercentage
Domain Events:
  - GoalSetEvent, KeyResultUpdatedEvent, GoalAchievedEvent

====================================================================================
MODULE 9: project
====================================================================================
Aggregate Root:
  - Project (Entities: Project, ProjectMilestone, ProjectMember)
Value Objects:
  - ProjectStatus, ProjectBudgetRef
Domain Events:
  - ProjectCreatedEvent, ProjectCompletedEvent

====================================================================================
MODULE 10: finance
====================================================================================
Aggregate Root 1:
  - FinancialAccount (Entities: FinancialAccount, InstitutionDetail)
Aggregate Root 2:
  - Transaction (Entities: Transaction, ExpenseDetail, IncomeDetail, TransactionSplit)
Aggregate Root 3:
  - Budget (Entities: Budget, BudgetCategoryAllocation)
Aggregate Root 4:
  - Subscription (Entities: Subscription, BillingCycle)
Value Objects:
  - Money (Amount, Currency), PaymentMethod, TransactionType
Domain Events:
  - TransactionLoggedEvent, BudgetExceededEvent, SubscriptionRenewalAlertEvent

====================================================================================
MODULE 11: knowledge
====================================================================================
Aggregate Root:
  - Note (Entities: Note, NoteLink, NoteTag, Folder)
Value Objects:
  - MarkdownContent, NoteVisibility
Domain Events:
  - NoteCreatedEvent, NoteUpdatedEvent, NoteLinkedEvent

====================================================================================
MODULE 12: document
====================================================================================
Aggregate Root:
  - Document (Entities: Document, DocumentAttachment, StorageReference)
Value Objects:
  - FileChecksum, MimeType, FileSizeBytes
Domain Events:
  - DocumentUploadedEvent, DocumentDeletedEvent

====================================================================================
MODULE 13: health
====================================================================================
Aggregate Root 1:
  - Habit (Entities: Habit, HabitLog, HabitStreak)
Aggregate Root 2:
  - MedicationSchedule (Entities: MedicationSchedule, DosageLog)
Aggregate Root 3:
  - HealthMetricLog (Entities: SleepLog, WorkoutLog, MoodLog)
Value Objects:
  - DosageUnit, HabitFrequency, MoodScore
Domain Events:
  - HabitCompletedEvent, HabitStreakBrokenEvent, MedicationDueEvent

====================================================================================
MODULE 14: notification
====================================================================================
Aggregate Root:
  - Notification (Entities: Notification, NotificationChannelConfig, DeliveryAttempt)
Value Objects:
  - ChannelType, DeliveryStatus
Domain Events:
  - NotificationDispatchedEvent, NotificationFailedEvent

====================================================================================
MODULE 15: ai
====================================================================================
Aggregate Root 1:
  - AIConversation (Entities: AIConversation, AIMessage, AIToolCall)
Aggregate Root 2:
  - AIMemoryEmbedding (Entities: AIMemoryEmbedding, VectorChunk)
Aggregate Root 3:
  - AIPromptTemplate (Entities: AIPromptTemplate, AIPromptVersion)
Aggregate Root 4:
  - AISuggestion (Entities: AISuggestion, AIInsight, AITimelineEntry)
Value Objects:
  - EmbeddingVector(1536), TokenUsage, ConfidenceScore
Domain Events:
  - AIMessageReceivedEvent, AISuggestionGeneratedEvent, MemoryIndexedEvent

====================================================================================
MODULE 16: analytics
====================================================================================
Aggregate Root:
  - DailyProductivitySummary (Entities: DailyProductivitySummary, FinancialVelocitySnapshot)
Value Objects:
  - ProductivityScore, TaskCompletionRate
Domain Events:
  - DailySummaryCalculatedEvent

====================================================================================
MODULE 17: audit
====================================================================================
Aggregate Root:
  - AuditLogEntry (Entities: AuditLogEntry, StateChangeDiff)
Value Objects:
  - AuditAction, IpAddress, UserAgent
Domain Events:
  - SecurityAuditEventLoggedEvent

====================================================================================
MODULE 18: search
====================================================================================
Aggregate Root:
  - SearchIndexEntry (Entities: SearchIndexEntry)
Value Objects:
  - SearchTokenType, RelevanceWeight
Domain Events:
  - EntityIndexedEvent
```

---

# SECTION 5: Relationship & Aggregate Boundary Design

## 5.1 DDD Aggregate Boundaries & Reference Rules
1. **Intra-Aggregate Relationships:** Entities within the SAME Aggregate Root use strict foreign key constraints and can be loaded via JPA cascading (`CascadeType.ALL`).
2. **Inter-Aggregate Relationships:** Cross-Aggregate references MUST use **ID values only** (e.g., `Task` contains `project_id: UUID`, NOT a `@ManyToOne ProjectEntity`).
3. **Cross-Module Boundaries:** Modules NEVER join tables across schemas directly in application query repositories. Cross-module data aggregation occurs in the Service layer using read DTOs or events.

```
+------------------------------------+       +------------------------------------+
|        AGGREGATE ROOT 1            |       |        AGGREGATE ROOT 2            |
|       (task.tasks schema)          |       |      (project.projects schema)     |
|                                    |       |                                    |
|  TaskEntity (ID: UUIDv7)           |       |  ProjectEntity (ID: UUIDv7)        |
|  - title: String                   |       |  - name: String                    |
|  - project_id: UUID -------------->|======>|  - status: String                  |
|    (Scalar ID reference ONLY,      |       |                                    |
|     NO JPA Entity Association)     |       |                                    |
+------------------------------------+       +------------------------------------+
```

## 5.2 Cascade & Delete Strategies
- `CascadeType.PERSIST` and `CascadeType.MERGE` are allowed ONLY within the same Aggregate Root.
- `CascadeType.REMOVE` is **STRICTLY FORBIDDEN**. Deletions must be handled explicitly via application domain service methods enforcing soft deletion (`deleted_at = NOW()`).
- Database FK Delete Rules: `ON DELETE RESTRICT` for root aggregates; `ON DELETE CASCADE` for dependent child entities within the same aggregate (e.g., deleting a `Task` cascades to its `subtasks`).

---

# SECTION 6: Database Architecture Rules

1. **No Circular Schema References:** Dependencies between schemas must flow downwards without cycles.
2. **Mandatory Tenant & User Isolation:** Every entity table MUST include a indexed `user_id` column.
3. **No Direct Cross-Module Joins:** SQL `JOIN` statements across schema boundaries are prohibited in production APIs.
4. **Soft Delete Filter Enforcement:** All SELECT queries MUST append `WHERE is_deleted = FALSE` (automated via Hibernate `@SQLRestriction` annotations).
5. **JSONB Storage Limit:** Maximum 1 JSONB column per table, capped at 10KB payload size. Core business fields must remain strongly typed relational columns.
6. **Optimistic Locking:** Every mutable table MUST include `version BIGINT DEFAULT 0` to prevent lost updates under high concurrency.

---

# SECTION 7: AI Data Architecture (pgvector & Memory)

The `ai` schema encapsulates vector embeddings, context memory, and conversation histories.

```
+-----------------------------------------------------------------------------------+
|                                  ai.ai_conversations                              |
|   (id, user_id, title, system_instruction, created_at, updated_at)                |
+-----------------------------------------------------------------------------------+
                                         |
                                         v 1:N
+-----------------------------------------------------------------------------------+
|                                    ai.ai_messages                                 |
|   (id, conversation_id, role, content, token_count, created_at)                   |
+-----------------------------------------------------------------------------------+
                                         |
                                         v 1:N
+-----------------------------------------------------------------------------------+
|                                  ai.ai_tool_calls                                 |
|   (id, message_id, tool_name, arguments_jsonb, result_jsonb, status)              |
+-----------------------------------------------------------------------------------+

+-----------------------------------------------------------------------------------+
|                               ai.ai_memory_embeddings                             |
|   (id, user_id, source_type, source_id, text_chunk, embedding vector(1536))      |
|   * HNSW Index on embedding column for fast cosine distance vector lookup         |
+-----------------------------------------------------------------------------------+
```

## 7.1 Memory Storage & Vector Retrieval Strategy
- **Embedding Column:** `embedding vector(1536)` (OpenAI) or `vector(768)` (Google Gemini text-embedding-004).
- **pgvector Index:** **HNSW (Hierarchical Navigable Small World)** index using Cosine Distance (`vector_cosine_ops`):  
  `CREATE INDEX idx_vec_memory_embedding ON ai.ai_memory_embeddings USING hnsw (embedding vector_cosine_ops) WITH (m = 16, ef_construction = 64);`
- **Short-Term Memory:** Active chat messages stored in Redis key `ai:chat:session:{id}` for sub-millisecond retrieval.
- **Long-Term Memory:** Background worker chunks notes, documents, and past interactions into `ai.ai_memory_embeddings` for semantic RAG search.

---

# SECTION 8: Database Performance Strategy

## 8.1 Indexing Matrix

| Index Type | Target Usage | Example Application |
| :--- | :--- | :--- |
| **B-Tree (Standard)** | Primary Keys, Foreign Keys, Equality Filters | `idx_tasks_user_id`, `idx_expenses_budget_id` |
| **Composite B-Tree** | Multi-column dynamic filtering & sorting | `idx_tasks_user_status_due_date` (`user_id`, `status`, `due_date`) |
| **Partial B-Tree** | Active soft-deleted records | `idx_users_active_email` `WHERE is_deleted = FALSE` |
| **GIN Index** | JSONB payload search & Tag arrays | `idx_gin_notes_tags` USING gin (`tags`) |
| **GIN + tsvector** | Full-Text Search across Notes & Documents | `idx_gin_notes_fts` USING gin (`to_tsvector('english', content)`) |
| **HNSW (pgvector)** | Fast Approximate Nearest Neighbor vector search | `idx_vec_memory_embedding` USING hnsw (`embedding vector_cosine_ops`) |

## 8.2 Declarative Table Partitioning
High-growth tables MUST use PostgreSQL **Range Partitioning by Month**:
- `audit.audit_log_entries` (Partitioned by `created_at`)
- `ai.ai_messages` (Partitioned by `created_at`)
- `analytics.daily_productivity_summaries` (Partitioned by `created_at`)

## 8.3 Redis Caching Strategy
- **L1 Cache:** Caffeine in-memory cache inside JVM for ultra-fast static lookups.
- **L2 Cache:** Redis Cluster key-value store:
  - User Permissions: `user:perm:{user_id}` (TTL: 15 mins)
  - Active AI Context Window: `ai:context:{user_id}` (TTL: 5 mins)
  - Exchange Rates & Constants: `ref:exchange_rates` (TTL: 24 hrs)

---

# SECTION 9: Database Security & Encryption

## 9.1 Data Encryption Standards
- **Encryption at Rest:** AWS Aurora KMS / PostgreSQL Transparent Data Encryption (AES-256).
- **Encryption in Transit:** TLS 1.3 enforced for all application-to-database connections.
- **Column-Level Encryption:** Sensitive fields (OAuth tokens, financial account numbers, health logs) encrypted using AES-256-GCM before database write via Spring Data JPA `AttributeConverter`.

## 9.2 Row-Level Security (RLS)
PostgreSQL RLS policies enforced on multi-tenant schemas to prevent cross-user data leaks at the DB engine layer:  
`CREATE POLICY user_isolation_policy ON task.tasks FOR ALL USING (user_id = current_setting('app.current_user_id')::uuid);`

---

# SECTION 10: Future Scalability & Evolution

```
                           +-------------------------------+
                           |     SPRING BOOT MONOLITH      |
                           +-------------------------------+
                                     |           |
                        (Write DB)   |           | (Read-Only Queries)
                                     v           v
                          +--------------+   +--------------+
                          | MASTER DB    |   | READ REPLICA |
                          | (PostgreSQL) |---| (PostgreSQL) |
                          +--------------+   +--------------+
                                 |
                                 | (CDC via Debezium)
                                 v
                          +--------------+
                          | APACHE KAFKA |
                          +--------------+
                           /            \
                          v              v
               +---------------+    +-------------------+
               | OLAP READ DB  |    | MICROSERVICES DBs |
               | (ClickHouse)  |    | (Citus Sharding)  |
               +---------------+    +-------------------+
```

1. **Read-Replicas:** Offload search, analytics, and AI context query traffic to PostgreSQL Read Replicas.
2. **Citus Horizontal Sharding:** If single-node storage exceeds 2TB, migrate to **Citus Data extension** to shard tables horizontally across a distributed cluster by `user_id`.
3. **CDC Data Warehouse Pipeline:** Use Debezium Change Data Capture (CDC) to stream PostgreSQL WAL logs into Apache Kafka for real-time aggregation into an OLAP warehouse (ClickHouse/Snowflake).

---

# SECTION 11: Self-Critical Database Risk Assessment

| Identified Risk Area | Impact Level | Architectural Mitigation |
| :--- | :---: | :--- |
| **pgvector RAM Consumption** | High | HNSW indexes require holding vector nodes in memory.  <br>*Mitigation:* Enforce `vector(768)` dimension limits and restrict embedding generation to active user items. |
| **JSONB Schema Drift** | Medium | Developers may store relational fields in JSONB.  <br>*Mitigation:* Enforce strict DB Architecture Rule #5 (Max 1 JSONB column per entity; mandatory code review). |
| **High Concurrent Habit Log Writes** | Medium | Frequent habit/workout logging creates write locks.  <br>*Mitigation:* Buffer frequent habit streak writes in Redis before flushing to PostgreSQL asynchronously. |
| **Soft Delete Index Bloat** | Medium | Accumulated soft-deleted rows slow down index scans.  <br>*Mitigation:* Enforce partial indexes (`WHERE is_deleted = FALSE`) and monthly background purge jobs. |

---

# SECTION 12: Final Database Blueprint Summary

| Blueprint Dimension | Architectural Standard Approved |
| :--- | :--- |
| **Database Engine** | PostgreSQL 16+ with pgvector extension |
| **In-Memory Store** | Redis Cluster (L2 Cache, Sessions, Token Blacklist, AI Buffers) |
| **Migration Control** | Flyway declarative SQL versioning (`V<Major>.<Minor>.<Patch>__<desc>.sql`) |
| **Primary Keys** | 128-bit UUID Version 7 (Time-Ordered Sequential B-Tree optimized) |
| **Schema Isolation** | 18 DDD Schemas (`identity`, `task`, `finance`, `health`, `ai`, etc.) |
| **Aggregate Boundaries** | Inter-Aggregate links via scalar IDs only; Zero cross-schema SQL joins |
| **AI Vector Storage** | `embedding vector(1536)` with Cosine HNSW Indexing (`m = 16`, `ef = 64`) |
| **Security Standards** | AES-256 Column Encryption, PostgreSQL RLS, Password Argon2id, UTC `TIMESTAMPTZ` |
| **Scalability Path** | Read Replicas -> Debezium CDC + Kafka -> Citus Horizontal Sharding by `user_id` |

---

## Conclusion

This Enterprise Database Architecture Blueprint establishes the complete data tier baseline for LifeOS. Engineering teams are instructed to execute database migrations, JPA mapping strategies, and repository implementations strictly adhering to these architectural standards.
