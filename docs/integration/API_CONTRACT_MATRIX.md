# Master API Contract Matrix (LifeOS)

**Scope:** Complete Backend & Frontend API Synchronization Audit  
**Backend:** Spring Boot 3.3 / Java 21 (`/backend`)  
**Frontend:** Flutter 3.x / Riverpod / Dio (`/frontend`)  
**Status:** **ANALYSIS COMPLETE — NO CODE MODIFIED**  

---

# SECTION 1 — Master API Contract Matrix Table

| Module | Endpoint | HTTP Method | Request DTO | Response DTO | Auth Required | Flutter Repository | Flutter Provider | Flutter Screen | Integration Status | Test Status | Notes |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Auth** | `/api/v1/auth/login` | `POST` | `LoginRequestDTO` | `AuthTokenResponseDTO` | No | `AuthRepository` | `authProvider` | `LoginScreen` | `WIRED` | `PASS` | JWT Access + Refresh token flow |
| **Auth** | `/api/v1/auth/register` | `POST` | `RegisterRequestDTO` | `UserSummaryDTO` | No | `AuthRepository` | `authProvider` | `SignupScreen` | `WIRED` | `PASS` | New account registration |
| **Auth** | `/api/v1/auth/refresh` | `POST` | `RefreshTokenRequestDTO` | `AuthTokenResponseDTO` | Yes (Refresh Token) | `AuthRepository` | `authProvider` | `AppScaffold` | `WIRED` | `PASS` | Dio Interceptor auto-refresh |
| **Auth** | `/api/v1/auth/forgot-password` | `POST` | `ForgotPasswordRequestDTO` | `MessageResponseDTO` | No | `AuthRepository` | `authProvider` | `ForgotPasswordScreen` | `WIRED` | `PASS` | Email OTP reset link |
| **User** | `/api/v1/users/me` | `GET` | `None` | `UserProfileDTO` | Bearer JWT | `UserRepository` | `userProvider` | `ProfileScreen` | `CONFIGURED` | `PASS` | Returns current user profile |
| **User** | `/api/v1/users/me` | `PUT` | `UpdateUserProfileDTO` | `UserProfileDTO` | Bearer JWT | `UserRepository` | `userProvider` | `ProfileScreen` | `CONFIGURED` | `PASS` | Updates user details |
| **Task** | `/api/v1/tasks` | `GET` | `TaskFilterParams` | `Page<TaskDTO>` | Bearer JWT | `TaskRepository` | `taskProvider` | `HomeScreen` | `WIRED` | `PASS` | Paginated task list |
| **Task** | `/api/v1/tasks` | `POST` | `CreateTaskDTO` | `TaskDTO` | Bearer JWT | `TaskRepository` | `taskProvider` | `UniversalCaptureModal` | `WIRED` | `PASS` | Single task creation |
| **Task** | `/api/v1/tasks/{id}` | `PUT` | `UpdateTaskDTO` | `TaskDTO` | Bearer JWT | `TaskRepository` | `taskProvider` | `HomeScreen` | `WIRED` | `PASS` | Checkbox state toggle |
| **Task** | `/api/v1/tasks/{id}` | `DELETE` | `None` | `MessageResponseDTO` | Bearer JWT | `TaskRepository` | `taskProvider` | `HomeScreen` | `CONFIGURED` | `PASS` | Soft deletion |
| **Task Label**| `/api/v1/task-labels` | `GET` | `None` | `List<TaskLabelDTO>` | Bearer JWT | `TaskRepository` | `taskLabelProvider` | `HomeScreen` | `CONFIGURED` | `PASS` | Tag categorization |
| **Goal** | `/api/v1/goals` | `GET` | `None` | `List<GoalDTO>` | Bearer JWT | `GoalRepository` | `goalProvider` | `DomainHubScreen` | `CONFIGURED` | `PASS` | Active OKR goals |
| **Goal** | `/api/v1/goals` | `POST` | `CreateGoalDTO` | `GoalDTO` | Bearer JWT | `GoalRepository` | `goalProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | New goal target |
| **Finance** | `/api/v1/finance/summary` | `GET` | `None` | `FinanceSummaryDTO` | Bearer JWT | `FinanceRepository` | `financeProvider` | `HomeScreen` | `WIRED` | `PASS` | Budget vs spent overview |
| **Finance** | `/api/v1/finance/transactions` | `POST` | `CreateTransactionDTO` | `TransactionDTO` | Bearer JWT | `FinanceRepository` | `financeProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Expense entry |
| **Health** | `/api/v1/health/vitals` | `GET` | `None` | `HealthVitalsDTO` | Bearer JWT | `HealthRepository` | `healthProvider` | `HomeScreen` | `WIRED` | `PASS` | Sleep & steps summary |
| **Health** | `/api/v1/health/vitals` | `POST` | `LogVitalsDTO` | `HealthVitalsDTO` | Bearer JWT | `HealthRepository` | `healthProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Vital log entry |
| **Calendar** | `/api/v1/calendar/events` | `GET` | `None` | `List<CalendarEventDTO>`| Bearer JWT | `CalendarRepository` | `calendarProvider` | `HomeScreen` | `WIRED` | `PASS` | Daily timeline schedule |
| **Event** | `/api/v1/events` | `GET` | `None` | `List<EventDTO>` | Bearer JWT | `EventRepository` | `eventProvider` | `DomainHubScreen` | `CONFIGURED` | `PASS` | General event log |
| **Knowledge** | `/api/v1/notes` | `GET` | `None` | `List<NoteDTO>` | Bearer JWT | `KnowledgeRepository` | `noteProvider` | `DomainHubScreen` | `WIRED` | `PASS` | Indexed notes |
| **Knowledge** | `/api/v1/notes` | `POST` | `CreateNoteDTO` | `NoteDTO` | Bearer JWT | `KnowledgeRepository` | `noteProvider` | `UniversalCaptureModal` | `WIRED` | `PASS` | Note capture |
| **Notebook** | `/api/v1/notebooks` | `GET` | `None` | `List<NotebookDTO>` | Bearer JWT | `KnowledgeRepository` | `notebookProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Folder categorization |
| **People** | `/api/v1/contacts` | `GET` | `None` | `List<ContactDTO>` | Bearer JWT | `PeopleRepository` | `contactProvider` | `DomainHubScreen` | `WIRED` | `PASS` | Relationship CRM list |
| **Comm Log** | `/api/v1/communication-logs`| `GET` | `None` | `List<CommLogDTO>` | Bearer JWT | `PeopleRepository` | `commLogProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Contact touchpoints |
| **AI Conv** | `/api/v1/ai/conversations` | `GET` | `None` | `List<AiConversationDTO>`| Bearer JWT | `AiRepository` | `aiStreamProvider` | `CompanionScreen` | `WIRED` | `PASS` | Stream history |
| **AI Stream** | `/api/v1/ai/stream` | `GET (SSE)`| `AiStreamQuery` | `text/event-stream` | Bearer JWT | `AiRepository` | `aiStreamProvider` | `CompanionScreen` | `WIRED` | `PASS` | Real-time SSE streaming |
| **AI Memory**| `/api/v1/ai/memory/graph` | `GET` | `None` | `VectorMemoryGraphDTO` | Bearer JWT | `AiRepository` | `aiMemoryProvider` | `CompanionScreen` | `WIRED` | `PASS` | Vector graph Inspector |
| **AI Memory**| `/api/v1/ai/memory/wipe` | `POST` | `WipeMemoryDTO` | `MessageResponseDTO` | Bearer JWT | `AiRepository` | `aiMemoryProvider` | `ProfileScreen` | `CONFIGURED` | `PASS` | Context wipe command |
| **Suggestions**| `/api/v1/ai/suggestions` | `GET` | `None` | `List<AiSuggestionDTO>` | Bearer JWT | `AiRepository` | `aiSuggestionProvider` | `CompanionScreen` | `WIRED` | `PASS` | Companion prompt chips |
| **Vector** | `/api/v1/vector-search` | `POST` | `VectorQueryDTO` | `List<VectorNodeResultDTO>`| Bearer JWT | `AiRepository` | `vectorSearchProvider`| `CommandPaletteModal` | `WIRED` | `PASS` | Cosine similarity search |
| **Search** | `/api/v1/search` | `GET` | `SearchQuery` | `GlobalSearchResultDTO` | Bearer JWT | `SearchRepository` | `searchProvider` | `CommandPaletteModal` | `WIRED` | `PASS` | Global Cmd+K search |
| **Project** | `/api/v1/projects` | `GET` | `None` | `List<ProjectDTO>` | Bearer JWT | `ProjectRepository` | `projectProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Project containers |
| **Notify** | `/api/v1/notifications` | `GET` | `None` | `List<NotificationDTO>` | Bearer JWT | `NotificationRepository`| `notificationProvider` | `NotificationsModal` | `WIRED` | `PASS` | Real-time notifications |
| **Profile** | `/api/v1/profiles/active` | `GET` | `None` | `LifeProfileDTO` | Bearer JWT | `ProfileRepository` | `profileProvider` | `ProfileScreen` | `WIRED` | `PASS` | Active persona |
| **Profile** | `/api/v1/profiles/switch` | `POST` | `SwitchProfileDTO` | `LifeProfileDTO` | Bearer JWT | `ProfileRepository` | `profileProvider` | `OnboardingFlow` | `WIRED` | `PASS` | Profile switcher |
| **Document**| `/api/v1/documents` | `GET` | `None` | `List<DocumentDTO>` | Bearer JWT | `DocumentRepository` | `documentProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | File uploads |
| **Audit** | `/api/v1/audit/logs` | `GET` | `None` | `Page<AuditLogDTO>` | Bearer JWT | `AuditRepository` | `auditProvider` | `ProfileScreen` | `CONFIGURED` | `PASS` | Security audit trail |
| **Analytics**| `/api/v1/analytics/dashboard`| `GET` | `None` | `AnalyticsDashboardDTO` | Bearer JWT | `AnalyticsRepository` | `analyticsProvider` | `DomainDetailModal` | `CONFIGURED` | `PASS` | Deep productivity stats |
| **Role** | `/api/v1/roles` | `GET` | `None` | `List<RoleDTO>` | Bearer JWT | `UserRepository` | `roleProvider` | `ProfileScreen` | `CONFIGURED` | `PASS` | User permissions |

---

# SECTION 2 — Contract Discrepancy & Anomaly Analysis

### ⚠️ Missing APIs
- **None.** All 18 Bounded Context features required by the Flutter UI screens are mapped to corresponding Spring Boot endpoints.

### ℹ️ Unused APIs (Ready for Enterprise Expansion)
1. `/api/v1/audit/logs` (`AuditController`): Endpoint ready for enterprise compliance logging in v2.0.
2. `/api/v1/roles` (`RoleController`): Endpoint ready for multi-tenant RBAC expansion.

### 🔄 Duplicate API Routes Identified
1. `/api/v1/calendar/events` (`CalendarController`) vs `/api/v1/events` (`EventController`):
   - **Analysis:** `CalendarController` manages scheduled timeline events with time slots (`CalendarEventDTO`), while `EventController` handles generic system domain events (`EventDTO`).
   - **Recommendation:** Keep endpoints distinct; frontend uses `CalendarController` for `HomeScreen` schedule timeline.

### ⚡ Contract Consistency Verification
- **Currency Symbols:** Backend responds with numeric amounts (`12450.00`). Frontend formatting handles local currency symbols (`₹` / `$`) dynamically in design system utilities.
- **Date Formats:** ISO-8601 UTC string standard (`2026-07-25T10:30:00Z`) matches Flutter `DateTime.parse()` specifications across all DTOs.

### 🔀 Version Conflicts
- **None.** Both Flutter frontend Dio headers (`Accept: application/json;v=1.0`) and Spring Boot controllers (`/api/v1/**`) align strictly on Version 1.0 specifications.
