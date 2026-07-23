# LifeOS Backend Architecture Blueprint
**Document Version:** 1.0.0  
**Status:** Approved for Core Development  
**Target Platform:** Java 21 / Spring Boot 3.3+ / PostgreSQL / Redis / Docker / Gemini AI  
**Author:** Chief Enterprise Software Architect  

---

## Executive Summary

**LifeOS** is an AI-powered Personal Operating System designed to synthesize, orchestrate, and intelligently assist across every domain of human life—including calendar management, project tracking, personal finance, health metrics, relationship management, knowledge bases, and proactive decision-support systems.

This document presents the complete, enterprise-grade backend architecture for LifeOS. It defines the structural boundaries, package hierarchies, internal module paradigms, security enforcement mechanisms, AI context orchestration pipelines, integration adapters, dependency rules, and scalability strategies required to sustain a high-performing system over a 10+ year lifespan.

---

# SECTION 1: High-Level Backend Architecture

## 1.1 Architectural Paradigm: Modular Monolith to Microservices-Ready Distributed Architecture
LifeOS adopts a **Modular Monolith** architecture pattern with strict domain boundaries enforced via Spring Application Events and package-private access modifiers. 

### Why Modular Monolith for LifeOS?
1. **Domain Complexity Alignment:** LifeOS manages diverse domains (Finance, Health, Tasks, AI Context) that share common transactional boundaries (e.g., creating a goal triggers an event, updates finance allocations, and indexes into AI memory). A distributed microservices setup on Day 1 introduces distributed transactions (Saga Pattern), network latency overhead, and complex deployment pipelines prematurely.
2. **High Cohesion, Low Coupling:** Modules are designed as self-contained business capabilities. Cross-module communication occurs strictly via explicit public interface contracts or asynchronous domain events.
3. **Zero-Friction Microservices Extraction Path:** Because package boundaries strictly mirror Domain-Driven Design (DDD) Bounded Contexts, any feature module (e.g., `com.lifeos.ai` or `com.lifeos.module.finance`) can be extracted into an independent microservice with zero changes to internal business logic.

```
+-----------------------------------------------------------------------------------+
|                                  CLIENT LAYER                                     |
|               (Mobile Apps, Web Client, Wearables, AI Assistants)                 |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                        API GATEWAY / SECURITY LAYER                               |
|        Stateless JWT Authentication | Rate Limiting | MDC Logging Filter           |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                            APPLICATION CONTAINER                                  |
|                                                                                   |
|  +--------------------+  +--------------------+  +--------------------+           |
|  |     User & Auth    |  |  Calendar & Task   |  | Finance & Budgets  |           |
|  |      Module        |  |       Module       |  |       Module       |           |
|  +--------------------+  +--------------------+  +--------------------+           |
|           |                        |                        |                     |
|           +------------------------+------------------------+                     |
|                                    | (Asynchronous Events)                        |
|                                    v                                              |
|  +-----------------------------------------------------------------------------+  |
|  |                          AI CONTEXT ENGINE MODULE                           |  |
|  |  (Context Aggregator | Vector Memory | Prompt Builder | Gemini AI Client)   |  |
|  +-----------------------------------------------------------------------------+  |
|                                                                                   |
+-----------------------------------------------------------------------------------+
                                    |         |
                  +-----------------+         +-----------------+
                  v                                             v
+-----------------------------------+         +-----------------------------------+
|       PERSISTENCE LAYER           |         |          CACHE & AGGREGATION          |
|    PostgreSQL (Relational Data)   |         |        Redis (Tokens, Sessions,     |
|   Flyway Schema Version Control   |         |       Context Cache, Rate Limits) |
+-----------------------------------+         +-----------------------------------+
```

## 1.2 Core Architectural Principles
- **SOLID Principles:** Strict adherence to Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, and Dependency Inversion.
- **Domain-Driven Design (DDD):** Every feature package represents an isolated Bounded Context with distinct entities, value objects, domain services, and repository interfaces.
- **Ports and Adapters (Hexagonal Architecture):** Domain logic is insulated from external dependencies (DB frameworks, AI LLMs, Cloud Providers) via interface adapters.
- **Strict Entity Encapsulation:** JPA Entities NEVER cross the Service boundary into Controllers or API responses. DTOs (Java Records) are used exclusively for input/output payloads.
- **Asynchronous Event-Driven Decoupling:** Non-critical side-effects (indexing for search, updating AI context memory, sending emails/notifications) are published as Spring `ApplicationEvent` objects and handled asynchronously.

## 1.3 Package-by-Layer vs. Package-by-Feature Analysis

| Feature Criterion | Package-by-Layer (`com.lifeos.controller`) | Package-by-Feature (`com.lifeos.module.task`) | Chosen Architecture |
| :--- | :--- | :--- | :--- |
| **Cohesion** | Low (Related feature code is scattered across directories) | High (All code for a feature lives in one location) | **Package-by-Feature** |
| **Coupling** | High (Layer boundaries encourage cross-feature imports) | Low (Features communicate via strict public interfaces) | **Package-by-Feature** |
| **Code Navigation** | Difficult (Developers jump across folders to edit 1 feature) | Intuitive (Everything for 'Task' is in one folder) | **Package-by-Feature** |
| **Microservice Readiness** | Extremely Hard (Requires refactoring entire layers) | Trivially Easy (Copy folder out to a new Spring application) | **Package-by-Feature** |
| **Encapsulation** | Mandatory `public` access modifiers across layers | Package-private classes allowed for internal logic | **Package-by-Feature** |

### Architectural Decision Record (ADR-001)
- **Decision:** Adopt **Hybrid Package-by-Feature with Standardized Layering Inside Features**.
- **Package Hierarchy:** `com.lifeos.module.<feature_name>.<layer>`
- **Justification:** Combines the microservice readiness and domain cohesion of Package-by-Feature with the structural consistency of layered software design across all team members.

---

# SECTION 2: Complete Folder Structure

Below is the complete physical folder tree for the `backend/` directory root.

```
backend/
├── .dockerignore
├── .gitignore
├── README.md
├── ARCHITECTURE_BLUEPRINT.md
├── pom.xml
├── docker/
│   ├── docker-compose.yml
│   ├── docker-compose.dev.yml
│   ├── docker-compose.prod.yml
│   ├── Dockerfile
│   └── postgres/
│       └── init-user-db.sh
├── scripts/
│   ├── setup_folders.ps1
│   ├── dev-setup.sh
│   ├── run-migrations.sh
│   └── backup-db.sh
├── docs/
│   ├── ARCHITECTURE.md
│   ├── API_SPEC.md
│   ├── DATABASE_SCHEMA.md
│   └── AI_ENGINE_SPEC.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── lifeos/
    │   │           ├── LifeOsApplication.java
    │   │           ├── common/
    │   │           │   ├── response/
    │   │           │   ├── exception/
    │   │           │   ├── validation/
    │   │           │   ├── annotation/
    │   │           │   ├── constants/
    │   │           │   ├── mapper/
    │   │           │   ├── base/
    │   │           │   ├── helper/
    │   │           │   ├── util/
    │   │           │   ├── enums/
    │   │           │   └── events/
    │   │           ├── config/
    │   │           ├── security/
    │   │           │   ├── filter/
    │   │           │   ├── provider/
    │   │           │   ├── user/
    │   │           │   ├── permission/
    │   │           │   ├── entrypoint/
    │   │           │   └── handler/
    │   │           ├── ai/
    │   │           │   ├── prompt/
    │   │           │   ├── context/
    │   │           │   ├── memory/
    │   │           │   ├── reasoning/
    │   │           │   ├── suggestion/
    │   │           │   ├── insight/
    │   │           │   ├── timeline/
    │   │           │   ├── chat/
    │   │           │   └── provider/
    │   │           │       └── gemini/
    │   │           ├── integration/
    │   │           │   ├── firebase/
    │   │           │   ├── email/
    │   │           │   ├── sms/
    │   │           │   ├── whatsapp/
    │   │           │   ├── gcalendar/
    │   │           │   ├── gdrive/
    │   │           │   └── storage/
    │   │           └── module/
    │   │               ├── auth/
    │   │               ├── user/
    │   │               ├── profile/
    │   │               ├── people/
    │   │               ├── relationship/
    │   │               ├── calendar/
    │   │               ├── event/
    │   │               ├── task/
    │   │               ├── goal/
    │   │               ├── project/
    │   │               ├── finance/
    │   │               ├── document/
    │   │               ├── knowledge/
    │   │               ├── health/
    │   │               ├── notification/
    │   │               ├── scheduler/
    │   │               ├── analytics/
    │   │               ├── audit/
    │   │               └── search/
    │   └── resources/
    │       ├── application.yml
    │       ├── application-local.yml
    │       ├── application-dev.yml
    │       ├── application-prod.yml
    │       ├── logback-spring.xml
    │       ├── db/
    │       │   └── migration/
    │       ├── messages/
    │       ├── templates/
    │       └── static/
    └── test/
        └── java/
            └── com/
                └── lifeos/
                    ├── common/
                    ├── config/
                    ├── security/
                    ├── ai/
                    ├── integration/
                    └── module/
                        ├── auth/
                        ├── user/
                        ├── profile/
                        ├── people/
                        ├── relationship/
                        ├── calendar/
                        ├── event/
                        ├── task/
                        ├── goal/
                        ├── project/
                        ├── finance/
                        ├── document/
                        ├── knowledge/
                        ├── health/
                        ├── notification/
                        ├── scheduler/
                        ├── analytics/
                        ├── audit/
                        └── search/
```

---

# SECTION 3: Complete Java Package Architecture

| Package Name | Primary Purpose | Key Responsibilities | Allowed Dependencies | Forbidden Dependencies | Future Scalability |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `com.lifeos.common` | Shared Cross-Cutting Utilities | Unified responses, global exception handlers, base entities, custom annotations, general helpers. | Standard Java 21 SDK, Spring Framework Core annotations. | ANY feature module (`com.lifeos.module.*`), AI, Security, Integration packages. | Stays lightweight; published as a shared library (`lifeos-common.jar`) if split. |
| `com.lifeos.config` | Application-wide Infrastructure Setup | Beans for Database, Caching, Async, Swagger, OpenAPI, Jackson, Redis. | `com.lifeos.common`, Spring Auto-configurations, Third-party SDKs. | Feature modules, Controllers, DTOs. | Config server integration (Spring Cloud Config). |
| `com.lifeos.security` | Authentication & Authorization Engine | JWT token validation, Spring Security Filter Chains, Role/Permission evaluators, Custom UserDetails. | `com.lifeos.common`, `com.lifeos.config`, Spring Security, JWT libraries. | Directly importing domain entities outside `user`/`auth` module. | Auth0 / Keycloak delegation adapter. |
| `com.lifeos.ai` | AI Intelligence & Context Core | Prompt building, RAG context synthesis, vector memory management, Gemini LLM communication, suggestion generators. | `com.lifeos.common`, `com.lifeos.module.*` (via query APIs), Gemini SDK. | Direct JPA DB mutation of domain entities. | Extraction into isolated `lifeos-ai-engine` microservice with gRPC interface. |
| `com.lifeos.integration` | External Gateway Adapters | Adapters for WhatsApp, Google Calendar, S3, Firebase, Email, SMS. | `com.lifeos.common`, Third-party SDKs (Google API client, AWS S3 SDK). | Feature module business logic. | Serverless workers / Lambda handlers for third-party webhooks. |
| `com.lifeos.module.auth` | Security & Token Operations | User login, registration, token refresh, password resets, MFA. | `com.lifeos.common`, `com.lifeos.security`, `com.lifeos.module.user`. | Other domain modules (`finance`, `health`, etc.). | Extraction to Identity Provider (IdP) service. |
| `com.lifeos.module.user` | Account Lifecycle Management | User profile management, settings, account state transitions. | `com.lifeos.common`, Spring Data JPA. | `finance`, `health`, `ai`. | Central user data repository. |
| `com.lifeos.module.profile` | Life Profiles & Persona Engine | Core persona settings, life preferences, work-life balance rules. | `com.lifeos.common`, `com.lifeos.module.user`. | Feature-specific entities. | User preferences micro-service. |
| `com.lifeos.module.people` | Personal CRM & Contacts | Managing contacts, social circles, personal CRM data. | `com.lifeos.common`, `com.lifeos.module.user`. | `finance`, `health`. | Standalone CRM service. |
| `com.lifeos.module.relationship` | Relationship Dynamics & History | Tracks relationship milestones, interaction frequency, gift logs. | `com.lifeos.common`, `com.lifeos.module.people`. | Direct `finance` or `health` access. | Microservice for social graph. |
| `com.lifeos.module.calendar` | Master Calendar Aggregator | Aggregating time slots, availability calculation, multi-calendar sync. | `com.lifeos.common`, `com.lifeos.module.event`. | Direct DB mutations in `task` or `project`. | High-throughput scheduling engine. |
| `com.lifeos.module.event` | Single Event Management | Recurring event rules, location management, event participant state. | `com.lifeos.common`, `com.lifeos.module.calendar`. | External HTTP Clients (use `integration`). | Calendar microservice. |
| `com.lifeos.module.task` | Task Lifecycle & Execution | Task creation, status machines, subtask hierarchies, deadline timers. | `com.lifeos.common`, `com.lifeos.module.project`, `com.lifeos.module.goal`. | Direct AI prompt calls (publish events instead). | Task execution engine. |
| `com.lifeos.module.goal` | Goal Setting & OKR Tracking | Key performance tracking, personal OKRs, goal milestone progress. | `com.lifeos.common`, `com.lifeos.module.project`, `com.lifeos.module.task`. | Health/Finance details (use events). | Goal analytics service. |
| `com.lifeos.module.project` | Project Portfolio Management | Grouping tasks, milestone definitions, resource allocations. | `com.lifeos.common`, `com.lifeos.module.user`. | Detailed health or financial records. | Project tracking service. |
| `com.lifeos.module.finance` | Income, Expense & Budget Core | Expense tracking, income logs, budget thresholds, subscription renewal alerts. | `com.lifeos.common`, `com.lifeos.module.user`. | AI internal components. | FinTech compliant isolated ledger service. |
| `com.lifeos.module.document` | File Attachment & Metadata | Document metadata, file categorization, cloud storage linkages. | `com.lifeos.common`, `com.lifeos.integration.storage`. | Raw byte processing inside controllers. | Document storage service. |
| `com.lifeos.module.knowledge` | Notes & Personal Wiki Engine | Markdown note management, inter-note links, tag trees. | `com.lifeos.common`, `com.lifeos.module.document`. | AI prompt building. | Vector search backend. |
| `com.lifeos.module.health` | Health & Habit Metrics | Medication schedules, habit streaks, sleep/workout logs. | `com.lifeos.common`, `com.lifeos.module.user`. | External messaging integrations. | HIPAA-compliant health data service. |
| `com.lifeos.module.notification` | Alert Orchestration | Multi-channel dispatching (Email, Push, WhatsApp, In-App). | `com.lifeos.common`, `com.lifeos.integration.*`. | Feature business logic. | Dedicated notification engine (Kafka backed). |
| `com.lifeos.module.scheduler` | Recurring System Tasks | Scheduled job definitions, CRON execution triggers. | `com.lifeos.common`, Domain services. | Direct database query execution. | Distributed Quartz / ShedLock cluster. |
| `com.lifeos.module.analytics` | System Metrics & User Insights | Calculating usage metrics, productivity indices, financial velocity. | `com.lifeos.common`, Read-only query repositories. | Mutation of feature tables. | Analytics & Data Lake pipeline. |
| `com.lifeos.module.audit` | Compliance & Security Audit | Immutable audit logging of sensitive state transitions and access. | `com.lifeos.common`, Spring Security Context. | Feature service logic. | Immutable append-only audit warehouse. |
| `com.lifeos.module.search` | Unified Search Orchestrator | Cross-module indexing and multi-entity text searching. | `com.lifeos.common`, Feature Read-DTOs. | Feature entity mutations. | Dedicated Elasticsearch / OpenSearch cluster. |

---

# SECTION 4: Internal Module Structure

Every feature module (`com.lifeos.module.<feature>`) follows a standardized, enforced internal architecture:

```
com.lifeos.module.<feature_name>/
├── controller/
├── service/
├── repository/
├── entity/
├── dto/
├── mapper/
├── validator/
├── exception/
├── event/
├── config/
├── util/
├── specification/
└── client/
```

## 4.1 Folder Responsibilities & Strict Boundaries

### 1. `controller/`
- **Why it exists:** Provides RESTful API endpoints for client applications.
- **What belongs:** `@RestController`, `@RequestMapping`, `@PreAuthorize`, OpenAPI `@Operation`, HTTP request parsing, calling service methods, returning `ResponseEntity<ApiResponse<T>>`.
- **What NEVER belongs:** Business logic, entity references, raw database queries, direct external HTTP calls.

### 2. `service/`
- **Why it exists:** Enforces domain logic, transactional boundaries, and orchestrates operations.
- **What belongs:** Interfaces (`FeatureService`) and implementations (`FeatureServiceImpl`), `@Transactional`, publishing domain events, enforcing domain security rules.
- **What NEVER belongs:** `HttpServletRequest`/`HttpServletResponse`, returning raw JPA Entities to external callers, direct SQL string construction.

### 3. `repository/`
- **Why it exists:** Handles object-relational persistence operations against PostgreSQL.
- **What belongs:** Interfaces extending `JpaRepository<T, ID>`, `JpaSpecificationExecutor<T>`, custom JPQL query annotations (`@Query`).
- **What NEVER belongs:** DTO instantiation logic, business validations, controller calls.

### 4. `entity/`
- **Why it exists:** Maps database tables into Java objects via JPA/Hibernate.
- **What belongs:** `@Entity`, `@Table`, `@Id`, `@Column`, domain lifecycle hooks (`@PrePersist`, `@PreUpdate`), JPA relationship annotations (`@ManyToOne`, `@OneToMany`).
- **What NEVER belongs:** Jackson serialization annotations for HTTP APIs (`@JsonProperty`), DTO interfaces, API validation logic.

### 5. `dto/`
- **Why it exists:** Transfers data between clients, controllers, and services immutably.
- **What belongs:** Java `record` declarations for Requests (`CreateExpenseRequest`), Responses (`ExpenseResponse`), and Search Filters (`TaskSearchCriteria`).
- **What NEVER belongs:** Database ORM annotations (`@Entity`), mutable setter methods, business rules altering entity state.

### 6. `mapper/`
- **Why it exists:** Translates between Entities, DTOs, and Domain Events without boilerplate.
- **What belongs:** MapStruct interfaces (`@Mapper(componentModel = "spring")`), explicit conversion mapping logic.
- **What NEVER belongs:** Business state checks, repository calls, database persistence actions.

### 7. `validator/`
- **Why it exists:** Enforces domain-specific validation constraints that cannot be expressed via standard bean annotations.
- **What belongs:** Classes implementing `ConstraintValidator<A, T>`, complex multi-field validator logic.
- **What NEVER belongs:** Data retrieval from external microservices, database transactions.

### 8. `exception/`
- **Why it exists:** Isolates domain-specific runtime errors for centralized handling.
- **What belongs:** Custom runtime exceptions (`ExpenseNotFoundException`, `BudgetExceededException`) extending `BaseException`.
- **What NEVER belongs:** Catching generic `Exception` without context, returning HTTP response wrappers directly.

### 9. `event/`
- **Why it exists:** Decouples intra-system side-effects using Spring `ApplicationEvent` objects.
- **What belongs:** Immutable event records (`TaskCompletedEvent`, `ExpenseCreatedEvent`), event listener beans (`@EventListener`, `@TransactionalEventListener`).
- **What NEVER belongs:** Synchronous blocking network calls inside listeners without `@Async`.

### 10. `config/`
- **Why it exists:** Configures feature-specific beans, local cache regions, or feature toggles.
- **What belongs:** `@Configuration` classes, `@Bean` definitions specific exclusively to this module.
- **What NEVER belongs:** Global security filter chains, database connection configurations.

### 11. `util/`
- **Why it exists:** Houses pure helper utilities specific to the domain.
- **What belongs:** Stateless helper methods (e.g., `FinancialCalculationUtils`).
- **What NEVER belongs:** Stateful beans, `@Autowired` dependencies.

### 12. `specification/`
- **Why it exists:** Enables dynamic, type-safe database queries.
- **What belongs:** `Specification<T>` factory classes for filtering entity properties dynamically.
- **What NEVER belongs:** Direct SQL strings, modification operations (`INSERT`/`UPDATE`/`DELETE`).

### 13. `client/`
- **Why it exists:** Encapsulates RPC or HTTP communications required by this module from external subsystems or microservices.
- **What belongs:** Feign clients, RestTemplate/WebClient wrappers.
- **What NEVER belongs:** DB persistence code.

---

# SECTION 5: Common Package Design

The `com.lifeos.common` package contains shared primitives. It must remain completely independent of all domain modules.

```
com.lifeos.common/
├── response/
│   ├── ApiResponse.java
│   ├── PagedResponse.java
│   ├── ErrorResponse.java
│   └── ResponseStatus.java
├── exception/
│   ├── BaseException.java
│   ├── ResourceNotFoundException.java
│   ├── UnauthorizedException.java
│   ├── ForbiddenException.java
│   ├── ValidationException.java
│   ├── ConflictException.java
│   └── GlobalExceptionHandler.java
├── validation/
│   ├── ValidEnum.java
│   ├── ValidEnumValidator.java
│   ├── ValidPhoneNumber.java
│   └── ValidCurrency.java
├── annotation/
│   ├── Auditable.java
│   ├── RequiresPermission.java
│   ├── AITraceable.java
│   └── RateLimited.java
├── constants/
│   ├── SecurityConstants.java
│   ├── DateTimeConstants.java
│   ├── ErrorConstants.java
│   └── PaginationConstants.java
├── mapper/
│   └── CentralMapperConfig.java
├── base/
│   ├── BaseEntity.java
│   ├── BaseDto.java
│   └── BaseService.java
├── helper/
│   ├── SecurityHelper.java
│   ├── DateTimeHelper.java
│   └── StringHelper.java
├── util/
│   ├── JsonUtil.java
│   ├── CryptoUtil.java
│   └── PaginationUtil.java
├── enums/
│   ├── SortDirection.java
│   ├── Environment.java
│   └── PriorityLevel.java
└── events/
    ├── BaseDomainEvent.java
    └── EventPublisher.java
```

## 5.1 Package Architectural Specifications
- **`response/`**: Standardizes all REST responses. Provides unified wrappers for success (`ApiResponse<T>`), paginated data (`PagedResponse<T>`), and standard error structures (`ErrorResponse`).
- **`exception/`**: Houses `BaseException` (a runtime exception carrying error codes and HTTP status hints) and `@ControllerAdvice` (`GlobalExceptionHandler`) to intercept all unhandled errors and format them into consistent JSON responses.
- **`validation/`**: Custom reusable Jakarta Bean Validation annotations and implementation classes.
- **`annotation/`**: Aspect-Oriented Programming (AOP) marker annotations for logging, auditing, performance tracing, and security.
- **`constants/`**: Immutable `public static final` system constants organized by concern.
- **`base/`**: Abstract parent JPA mapped superclasses (`BaseEntity` providing `id`, `createdAt`, `updatedAt`, `createdBy`, `updatedBy`, `version`) to ensure database column audit consistency across all tables.
- **`events/`**: The base `BaseDomainEvent` record carrying event ID, timestamp, and context metadata.

---

# SECTION 6: Configuration Architecture

Configurations are encapsulated inside `com.lifeos.config`.

```
com.lifeos.config/
├── DatabaseConfig.java
├── SecurityConfig.java
├── JwtConfig.java
├── OpenApiConfig.java
├── RedisConfig.java
├── CacheConfig.java
├── JacksonConfig.java
├── AsyncConfig.java
├── SchedulingConfig.java
├── FlywayConfig.java
└── CorsConfig.java
```

## 6.1 Configuration Package Responsibilities
- **`DatabaseConfig.java`**: Configures HikariCP connection pooling, Spring Data JPA settings, and enables JPA Auditing (`@EnableJpaAuditing`).
- **`SecurityConfig.java`**: Configures Spring Security Filter Chain, disables session storage (stateless), registers security filters, and assigns entry points.
- **`JwtConfig.java`**: Reads JWT secrets, expiration intervals, and RSA key pairs from Spring Environment properties.
- **`OpenApiConfig.java`**: Defines Swagger UI configuration, metadata, server environments, and JWT Bearer security scheme definitions for OpenAPI documentation.
- **`RedisConfig.java`**: Instantiates `RedisTemplate<String, Object>` with custom Jackson2JsonRedisSerializer for fast session and cache serialization.
- **`CacheConfig.java`**: Enables Spring Cache (`@EnableCaching`), configures TTL policies per cache region (`ai_context`, `user_permissions`, `exchange_rates`).
- **`JacksonConfig.java`**: Configures `ObjectMapper` to handle Java 8 time types (ISO-8601 formatting), ignore unknown properties, and exclude null values during API serialization.
- **`AsyncConfig.java`**: Configures `ThreadPoolTaskExecutor` for handling asynchronous events and background processing with thread-naming conventions (`lifeos-async-1`).
- **`SchedulingConfig.java`**: Enables Quartz / Spring Scheduled tasks (`@EnableScheduling`) with safe concurrency handling.
- **`FlywayConfig.java`**: Ensures database schema migrations execute sequentially before Hibernate initializes entity mappings.
- **`CorsConfig.java`**: Configures Cross-Origin Resource Sharing rules for web and mobile frontends.

---

# SECTION 7: Security Architecture

Stateless security infrastructure is defined under `com.lifeos.security`.

```
com.lifeos.security/
├── filter/
│   ├── JwtAuthenticationFilter.java
│   ├── RateLimitingFilter.java
│   └── LoggingMdcFilter.java
├── provider/
│   ├── JwtTokenProvider.java
│   └── JwtKeyResolver.java
├── user/
│   ├── UserPrincipal.java
│   └── CustomUserDetailsService.java
├── permission/
│   ├── CustomPermissionEvaluator.java
│   ├── Role.java
│   └── Permission.java
├── entrypoint/
│   └── CustomAuthenticationEntryPoint.java
├── handler/
│   └── CustomAccessDeniedHandler.java
└── config/
    └── MethodSecurityConfig.java
```

## 7.1 Security Enforcement Workflow

```
+-----------------------------------------------------------------------------------+
|                              INCOMING HTTP REQUEST                                |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                               LoggingMdcFilter                                    |
|                 Generates unique Trace-Id for logger context                      |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                              RateLimitingFilter                                   |
|                Validates IP / Token rate limits using Redis                       |
+-----------------------------------------------------------------------------------+
                                         |
                                         v
+-----------------------------------------------------------------------------------+
|                            JwtAuthenticationFilter                                |
|        Extracts Bearer Token -> Validates Signature -> Builds UserPrincipal        |
+-----------------------------------------------------------------------------------+
                    |                                       |
           (Token Valid)                             (Token Invalid / Missing)
                    v                                       v
+---------------------------------------+   +---------------------------------------+
|    SecurityContextHolder Populated    |   |    CustomAuthenticationEntryPoint     |
| (Authentication object set in context)|   |     Returns 401 Unauthorized JSON     |
+---------------------------------------+   +---------------------------------------+
                    |
                    v
+-----------------------------------------------------------------------------------+
|                    Method Security (@PreAuthorize Evaluation)                      |
|                Evaluates User Principal Roles & Specific Permissions              |
+-----------------------------------------------------------------------------------+
                    |                                       |
              (Access Granted)                       (Access Denied)
                    v                                       v
+---------------------------------------+   +---------------------------------------+
|       Execute Controller Method       |   |       CustomAccessDeniedHandler       |
|                                       |   |       Returns 403 Forbidden JSON      |
+---------------------------------------+   +---------------------------------------+
```

---

# SECTION 8: AI Architecture (AI Context Engine & Reasoning)

The AI subsystem (`com.lifeos.ai`) coordinates RAG (Retrieval-Augmented Generation), context window construction, memory management, and interaction with Google Gemini models.

```
com.lifeos.ai/
├── prompt/
│   ├── PromptBuilder.java
│   ├── PromptTemplateRegistry.java
│   └── SystemPromptProvider.java
├── context/
│   ├── ContextBuilder.java
│   ├── UserContextAggregator.java
│   └── ContextWindowTrimmer.java
├── memory/
│   ├── MemoryBuilder.java
│   ├── VectorStoreClient.java
│   ├── EmbeddingService.java
│   └── LongTermMemoryEngine.java
├── reasoning/
│   ├── ReasoningEngine.java
│   ├── DecisionSupportService.java
│   └── GoalAlignmentAnalyzer.java
├── suggestion/
│   ├── SuggestionGenerator.java
│   └── ProactiveAgentService.java
├── insight/
│   ├── InsightEngine.java
│   └── PatternRecognitionService.java
├── timeline/
│   └── AITimelineService.java
├── chat/
│   ├── AIChatService.java
│   ├── ChatHistoryManager.java
│   └── StreamHandler.java
└── provider/
    └── gemini/
        ├── GeminiClient.java
        ├── GeminiConfig.java
        └── GeminiRequestMapper.java
```

## 8.1 AI Pipeline Component Architecture
- **`prompt/`**: Manages prompt engineering templates, dynamically injecting operational constraints, output formats (JSON/Markdown), and systemic instructions.
- **`context/`**: Synthesizes real-time user context (active tasks, schedule for today, financial budgets, health alerts) into a structured text context payload for LLM reasoning. Includes `ContextWindowTrimmer` to manage token limits.
- **`memory/`**: Manages long-term vector memory embeddings of notes, documents, and historical user actions to provide semantic recall.
- **`reasoning/`**: Executes multi-step decision analysis (e.g., evaluating if a large purchase aligns with financial goals and task schedules).
- **`suggestion/`**: Generates proactive recommendations without user prompting (e.g., "You have 3 high-priority tasks and an upcoming flight tomorrow; consider rescheduling low-priority meetings").
- **`insight/`**: Analyzes multi-module data to uncover behavioral patterns (e.g., cross-referencing late-night work with decreased morning habit completion).
- **`chat/`**: Provides streaming chat interface handlers (Server-Sent Events / WebSockets) to interface with mobile/web AI chat views.
- **`provider/gemini/`**: Encapsulates external API interactions with Google Gemini Pro and Flash models, using retry strategies and response mapping.

---

# SECTION 9: Integration Architecture

External systems are integrated via `com.lifeos.integration`, adhering to Hexagonal Architecture adapters.

```
com.lifeos.integration/
├── firebase/
│   ├── FirebasePushAdapter.java
│   └── FirebaseConfig.java
├── email/
│   ├── EmailServiceAdapter.java
│   └── SmtpEmailClient.java
├── sms/
│   ├── SmsServiceAdapter.java
│   └── TwilioSmsClient.java
├── whatsapp/
│   ├── WhatsAppAdapter.java
│   └── MetaWhatsAppClient.java
├── gcalendar/
│   ├── GoogleCalendarAdapter.java
│   └── GoogleCalendarSyncClient.java
├── gdrive/
│   ├── GoogleDriveAdapter.java
│   └── GoogleDriveClient.java
└── storage/
    ├── StorageService.java
    ├── S3StorageProvider.java
    └── LocalStorageProvider.java
```

## 9.1 Adapter Isolation Principles
- **Third-Party Decoupling:** Domain modules NEVER reference Twilio, SendGrid, Firebase, or Google API classes directly. They call abstract interfaces (`NotificationService`, `CalendarSyncService`, `StorageService`).
- **Resilience:** Integrations are wrapped with Resilience4j Circuit Breakers and Retry mechanisms inside their respective package clients.

---

# SECTION 10: Resources Folder

The `src/main/resources` folder houses configurations, database migrations, and static assets.

```
src/main/resources/
├── application.yml
├── application-local.yml
├── application-dev.yml
├── application-prod.yml
├── logback-spring.xml
├── db/
│   └── migration/
│       ├── V1.0.0__init_schema.sql
│       ├── V1.0.1__create_auth_tables.sql
│       ├── V1.0.2__create_user_profile_tables.sql
│       ├── V1.0.3__create_task_project_tables.sql
│       ├── V1.0.4__create_finance_tables.sql
│       └── V1.0.5__create_ai_memory_tables.sql
├── messages/
│   ├── messages.properties
│   └── messages_es.properties
├── templates/
│   ├── email-welcome.html
│   ├── email-password-reset.html
│   └── email-weekly-report.html
└── static/
    └── swagger/
        └── favicon.ico
```

## 10.1 Resource File Responsibilities
- **`application.yml`**: Base Spring Boot properties, database driver defaults, common log levels, security defaults.
- **`application-*.yml`**: Environment-specific overrides for DB credentials, Redis endpoints, Gemini API keys, and log levels.
- **`db/migration/`**: Versioned Flyway SQL migration scripts enforcing additive, immutable schema updates (`V<Version>__<Description>.sql`).
- **`messages/`**: Internationalization (i18n) properties for localized validation and error messages.
- **`templates/`**: HTML email rendering templates processed via Thymeleaf.
- **`logback-spring.xml`**: Structured JSON log layout configuration for production APM ingestion (e.g., Datadog, ELK stack).

---

# SECTION 11: Testing Architecture

Tests are structured under `src/test/java/com/lifeos` to mirror main source packages.

```
src/test/java/com/lifeos/
├── common/
│   ├── response/
│   └── util/
├── config/
│   └── TestContainersConfig.java
├── security/
│   └── JwtTokenProviderTest.java
├── ai/
│   └── PromptBuilderTest.java
├── integration/
│   └── WhatsAppAdapterTest.java
└── module/
    ├── auth/
    │   ├── controller/
    │   └── service/
    ├── task/
    │   ├── controller/
    │   ├── service/
    │   └── repository/
    └── finance/
        ├── controller/
        ├── service/
        └── repository/
```

## 11.1 Testing Strategy Categorization
1. **Unit Tests (`service/`, `util/`, `prompt/`):** Tested in complete isolation using JUnit 5 and Mockito. Zero Spring Context launch time overhead.
2. **Controller Tests (`controller/`):** Tested using `@WebMvcTest`. Verifies JSON serialization, HTTP status code response mappings, and URL route parameters with mocked services.
3. **Repository Tests (`repository/`):** Tested using `@DataJpaTest` with real PostgreSQL containers via Testcontainers to verify custom JPQL queries and Flyway migrations.
4. **Integration Tests (`integration/`):** Full end-to-end flow validation using `@SpringBootTest` with mock external server endpoints (MockServer).

---

# SECTION 12: Dependency Rules & Matrix

## 12.1 Core Architectural Dependency Laws
1. **Layer Rule:** `Controller` -> `Service` -> `Repository` -> `Database`. Downward dependencies only.
2. **Anti-Leakage Rule:** DTOs are restricted to Controller/Service interaction. JPA Entities must NEVER cross the Service boundary into API outputs.
3. **Cross-Module Rule:** Feature Module A must NEVER depend directly on Feature Module B's internal entities or repositories. Modules communicate strictly via exported Service Interfaces or asynchronous `DomainEvent` listeners.
4. **AI & Integration Isolation:** External SDKs (Gemini, Meta, Google) must be isolated inside `ai/provider` or `integration/*` packages.

## 12.2 Package Dependency Matrix

| Source Package \ Target Package | `common` | `config` | `security` | `ai` | `integration` | Feature Module (`module.*`) |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **`common`** | - | NO | NO | NO | NO | NO |
| **`config`** | YES | - | YES | YES | YES | NO |
| **`security`** | YES | YES | - | NO | NO | YES (User/Auth interfaces only) |
| **`ai`** | YES | YES | NO | - | YES | YES (Read-Only Service Query Interfaces) |
| **`integration`** | YES | YES | NO | NO | - | NO |
| **Feature Module (`module.*`)** | YES | YES | YES (Annotations) | NO (Events only) | YES (Interfaces) | NO (Cross-module via Service Interface) |

---

# SECTION 13: Naming Standards

| Component Type | Naming Convention | Example |
| :--- | :--- | :--- |
| **Package** | Lowercase, singular domain names | `com.lifeos.module.finance` |
| **Class (General)** | PascalCase | `FinancialCalculator` |
| **Interface** | PascalCase, clean noun | `ExpenseService`, `StorageProvider` |
| **Interface Impl** | Name + `Impl` suffix | `ExpenseServiceImpl` |
| **Controller** | Feature + `Controller` | `ExpenseController` |
| **Service** | Feature + `Service` | `ExpenseService` |
| **Repository** | Entity + `Repository` | `ExpenseRepository` |
| **Entity** | Feature + `Entity` | `ExpenseEntity` |
| **DTO (Request)** | Action + Feature + `Request` | `CreateExpenseRequest` |
| **DTO (Response)** | Feature + `Response` | `ExpenseResponse` |
| **Enum** | UpperCamelCase, Suffix `Type`/`Status` | `ExpenseCategoryType`, `PaymentStatus` |
| **Exception** | Domain + Condition + `Exception` | `ExpenseNotFoundException` |
| **Constants** | UpperCamelCase + `Constants` | `FinanceConstants` |
| **Config Class** | Concern + `Config` | `RedisConfig` |
| **Flyway File** | `V<Major>.<Minor>.<Patch>__<description>.sql` | `V1.0.1__create_expense_tables.sql` |
| **API REST URL** | Lowercase, plural, hyphenated | `/api/v1/finance/expenses` |

---

# SECTION 14: Scalability Strategy

## 14.1 Microservices Extraction Blueprint
Should transaction throughput or team size necessitate microservice extraction:
1. **Bounded Context Boundary:** Each feature package inside `com.lifeos.module.<feature>` maps 1:1 to a candidate microservice (`lifeos-finance-service`, `lifeos-task-service`).
2. **Database Separation:** Extract feature tables into an independent database schema. Replace cross-module JPA join queries with asynchronous domain event consumers.
3. **API Gateway Routing:** Route `/api/v1/<feature>/**` via Spring Cloud Gateway to the new microservice endpoint.

## 14.2 Distributed Event-Driven & Search Scale
- **Event Bus Transition:** Replace Spring `ApplicationEventPublisher` with an Apache Kafka cluster or RabbitMQ by swapping the `EventPublisher` helper implementation in `com.lifeos.common.events`.
- **Search & Vector Memory Scale:** Migrate `LongTermMemoryEngine` and `Search` query specifications from PostgreSQL pgvector/FTS to an external Elasticsearch cluster.

---

# SECTION 15: Self-Critical Architecture Review

## 15.1 Architectural Strengths
- **Future-Proof Cohesion:** Modular monolith setup ensures rapid initial feature development while preserving a clean migration path to microservices.
- **Zero Entity Leakage:** Strict DTO/Entity separation guarantees database schema modifications won't break API client contracts.
- **AI Engine Native Integration:** RAG and Context Building are first-class architecture citizens rather than ad-hoc integrations.

## 15.2 Identified Risks & Mitigations
- **Context Synthesis Latency:** Aggregating data across 10+ modules to build AI prompt context can introduce query latency.  
  *Mitigation:* Pre-aggregate active user context asynchronously into Redis cache key-value windows (`user:context:{id}`).
- **Module Coupling Over Time:** Developers might bypass public service interfaces and inject repositories across modules.  
  *Mitigation:* Use ArchUnit static code analysis rules in automated CI test pipelines to fail builds if cross-module repository imports occur.
- **Boilerplate Overhead:** Strict separation of DTOs, Entities, Mappers, and Controllers increases file count.  
  *Mitigation:* Use Lombok records and MapStruct interface generators to eliminate runtime boilerplate.

---

## Conclusion

This Architecture Blueprint provides the technical baseline for the LifeOS backend application. By strictly enforcing module isolation, DTO boundaries, event-driven integration patterns, and clean configuration abstractions, LifeOS will scale efficiently across features, team sizes, and deployment models for years to come.
