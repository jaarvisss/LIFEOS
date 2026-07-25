# LifeOS Flutter Frontend Architecture Blueprint
**Document Version:** 1.0.0  
**Target Platform:** Flutter 3.x / Dart 3.x (Cross-platform: iOS, Android, Desktop, Web)  
**Backend Alignment:** Java 21 / Spring Boot 3.3 / PostgreSQL 16 (18 Bounded Context Schemas)  
**Status:** Approved for Core Development & Implementation  

---

# SECTION 1 — Product Vision

## 1.1 What is LifeOS?
LifeOS is an AI-first Personal Operating System that unifies every dimension of human daily life—calendar management, tasks, goals, habits, personal finance, health metrics, relationship nurturing, document storage, and contextual knowledge—into a single cognitive command center.

## 1.2 Why LifeOS Exists & What Problem It Solves
Modern digital productivity is fragmented. Users balance 8–15 disconnected applications (Notion for notes, Todoist for tasks, Google Calendar for schedules, YNAB for finance, Apple Health for metrics). Each switch incurs heavy context-switching cognitive tax. LifeOS resolves this fragmentation by establishing an AI intelligence layer over all human life domains.

## 1.3 Why Current Software is Insufficient
Current apps are passive static databases. They wait for manual user input, manual tagging, and manual maintenance. LifeOS is proactive: it ingests unstructured thoughts, text, audio, images, and telemetry; infers context; routes data into structured bounded contexts; and nudges the user with context-aware, low-friction micro-actions.

## 1.4 Emotional Experience & One-Month Testimonial
- **Target Emotion:** Calm clarity, effortless control, and profound trust.
- **One-Month Benchmark User Quote:** *"LifeOS removed my daily mental clutter. I speak or type a random thought, and it instantly organizes my day, protects my budget, remembers my relationships, and keeps me focused without nagging."*

## 1.5 Competitive Differentiation
| Dimension | Traditional Apps (Todoist, Notion, GCal) | LifeOS |
| :--- | :--- | :--- |
| **Input Paradigm** | Manual forms, buttons, dropdowns | Natural language, multi-modal universal capture |
| **Data Structure** | Isolated silos (tasks vs events vs notes) | Interconnected Knowledge Graph & Vector Memory |
| **AI Integration** | Chatbot sidebar wrapper | Native AI Operating Engine driving proactive flows |
| **Cognitive Effort** | High (User organizes everything) | Near Zero (AI infers intent & maps destination) |

---

# SECTION 2 — Target Users & Life Profiles

LifeOS customizes experience according to distinct **Life Profiles**:

1. **Student:** Academic deadlines, exam schedules, habit tracking, budget boundaries.
2. **Professional:** Project tracking, high-velocity calendar orchestration, meeting context recall.
3. **Entrepreneur:** Strategic goal alignment, multi-project execution, high cognitive load mitigation.
4. **Freelancer:** Client relationship management, invoice tracking, flexible time-blocking.
5. **Family Manager:** Shared logistics, domestic finance tracking, family event reminders.
6. **Senior Citizen:** Simplified high-contrast UI, voice-first interaction, health metric alerts.
7. **Custom Profile:** User-configurable module weights and AI interaction cadences.

---

# SECTION 3 — Human Psychology & Behavioral Science

LifeOS is explicitly designed to address human cognitive limitations:

- **Procrastination Mitigation:** Breaks massive tasks into micro-steps automatically; prompts with low-friction entry points.
- **Reminder Fatigue Prevention:** Eliminates static noisy alarms; uses context-aware notification windows based on active focus states.
- **Habit Formation:** Utilizes habit loops (Cue -> Routine -> Reward) with visual streaks and positive reinforcement.
- **Decision Fatigue & Memory Overload:** Offloads working memory to persistent vector memory; asks one clarifying question at a time.
- **Context Switching Reduction:** In-context Universal Capture overlay eliminates app-switching overhead.

---

# SECTION 4 — Product Philosophy & Constitution

1. **Thought-First, Not Feature-First:** The user expresses intent; the AI determines data destination.
2. **Never Ask What AI Can Infer:** Deduce dates, tags, priorities, and domains from natural language context.
3. **One Question at a Time:** If clarification is needed, never bombard the user with multi-field forms.
4. **Trust-Gated Automation:** Critical actions (sending emails, editing budgets, deleting records) require confirmation until user-granted auto-approval thresholds are reached.
5. **Progressive Profiling:** Build user understanding gradually over days and weeks through passive observation and subtle interactions.

---

# SECTION 5 — Information Architecture

LifeOS Information Architecture maps cleanly to 18 Bounded Contexts:

```
[LifeOS Navigation Root]
  ├── Home (Daily Overview, AI Nudges, Quick Actions)
  ├── Companion (AI Stream, Memory Graph, Reasoning Engine)
  ├── Universal Capture (Multi-modal Voice/Text/Image/File Overlay)
  ├── Life Domains
  │   ├── Task & Goal Engine (Tasks, Projects, OKRs, Habits)
  │   ├── Calendar & Events (Schedules, Time Blocks, RSVPs)
  │   ├── Finance & Budgets (Accounts, Expenses, Cash Flow, Subscriptions)
  │   ├── Knowledge & Documents (Notes, Artifacts, Vector Search, Files)
  │   ├── Health & Vitality (Metrics, Sleep, Habits, Streaks)
  │   └── People & Relationships (CRM, Touchpoints, Important Dates)
  ├── Search & Command Palette (Global Vector & Keyword Hybrid Search)
  └── Settings & Privacy (Biometrics, PII Redaction, Memory Export/Wipe)
```

---

# SECTION 6 — Complete User Journey

1. **Onboarding & Setup:** Life Profile selection -> Initial voice/text seed -> AI baseline personalization.
2. **Daily Cycle:**
   - *Morning:* Proactive Daily Briefing & Recommended Intent Blocks.
   - *Mid-day:* Universal Capture & Ambient Task Execution.
   - *Evening:* 2-minute Guided Micro-Reflection & Streak Updates.
3. **Long-Term Memory:** Continuous vector indexing updates AI contextual intelligence over 6–12 months.

---

# SECTION 7 — Screen Inventory

1. `HomeScreen`: Primary cockpit, daily schedule, active nudges.
2. `CompanionScreen`: Multi-modal AI conversation, memory visualizer.
3. `UniversalCaptureModal`: Quick floating modal for text, speech, image OCR.
4. `TaskGoalScreen`: Master task lists, project trees, goal progress bars.
5. `CalendarScreen`: Dynamic agenda, time-block canvas, event details.
6. `FinanceScreen`: Net worth overview, budget cards, transaction feed.
7. `HealthScreen`: Vital stats dashboard, habit tracker grid.
8. `KnowledgeScreen`: Markdown editor, bidirectional node graph view.
9. `PeopleScreen`: Contact cards, relationship warmth indicators, history log.
10. `CommandPaletteModal`: Global search, quick jump navigation.
11. `SettingsPrivacyScreen`: Security controls, encryption keys, PII rules, data export.

---

# SECTION 8 — User Flow & Screen Specifications

Every Flutter screen follows a standardized contract:
- **State Management:** Riverpod `StateNotifierProvider` or `AsyncNotifierProvider`.
- **Loading State:** Shimmer skeletons and glassmorphic micro-spinners.
- **Error State:** Non-blocking inline banner with retry callbacks.
- **Offline Flow:** Hive local cache persistence with background sync queue.

---

# SECTION 9 — AI Intelligence Engine Architecture

- **Capture Engine:** Ingests raw inputs; performs preliminary tokenization.
- **Intent Engine:** Classifies intent into Domain Actions (Create Task, Book Event, Log Expense, Index Note).
- **Memory Engine:** Communicates with backend `pgvector` for semantic context retrieval.
- **Suggestion Engine:** Scores proactive nudges using user priority weightings.
- **Conversation Engine:** Orchestrates SSE (Server-Sent Events) streaming responses.

---

# SECTION 10 — Universal Capture System

Multi-modal ingestion pipeline:
- **Text:** Raw string parsing & regex entity extraction.
- **Voice:** Live speech-to-text audio stream streaming to AI backend.
- **Image/Camera:** Optical Character Recognition (OCR) for receipts, whiteboards, business cards.
- **Shared Content:** OS Share Extension receiver.

---

# SECTION 11 — AI Learning Model & Confidence Scoring

- **Confidence >= 0.85:** Automatic ingestion with silent toast confirmation.
- **0.60 <= Confidence < 0.85:** Soft preview banner asking "Mapped to [Task: Pay Bill]. Confirm?"
- **Confidence < 0.60:** Prompt user with single multiple-choice clarification question.

---

# SECTION 12 — Navigation Philosophy

- **Bottom Navigation Bar:** Quick access to 4 core anchors (Home, Companion, Domains, Settings).
- **Floating Action Button:** Instant trigger for Universal Capture Modal.
- **Keyboard Shortcuts (Desktop/Web):** `Cmd/Ctrl + K` for Command Palette, `Cmd/Ctrl + N` for Quick Capture.

---

# SECTION 13 — Complete Frontend Architecture (Flutter Structure)

```
frontend/lib/
├── main.dart
├── core/
│   ├── config/ (env_config.dart, api_config.dart)
│   ├── security/ (auth_store.dart, token_manager.dart, pii_sanitizer.dart, biometric_guard.dart)
│   ├── router/ (app_router.dart, route_guards.dart, navigation_service.dart)
│   ├── state/ (global_state.dart, app_provider_observer.dart)
│   ├── events/ (event_bus.dart, domain_events.dart)
│   └── error/ (error_boundary.dart, exception_handler.dart)
├── infrastructure/
│   ├── api/ (api_client.dart, dio_interceptors.dart, api_endpoints.dart)
│   ├── websocket/ (socket_client.dart, ai_stream_listener.dart)
│   ├── storage/ (secure_storage.dart, hive_cache.dart)
│   └── telemetry/ (metrics_collector.dart, app_logger.dart)
├── design_system/
│   ├── tokens/ (app_colors.dart, app_typography.dart, app_spacing.dart, app_animations.dart, app_shadows.dart)
│   ├── primitives/ (app_button.dart, app_card.dart, app_text_field.dart, app_modal.dart, glass_container.dart)
│   ├── theme/ (app_theme.dart, theme_mode_notifier.dart)
│   └── widgets/ (loading_indicator.dart, empty_state_view.dart, error_card.dart)
├── intelligence/
│   ├── capture/ (capture_engine.dart, capture_parser.dart, voice_listener.dart, ocr_processor.dart)
│   ├── intent/ (intent_classifier.dart, action_mapper.dart)
│   ├── memory/ (vector_memory_client.dart, short_term_context.dart, memory_graph.dart)
│   ├── suggestion/ (proactive_nudges.dart, suggestion_ranker.dart)
│   └── conversation/ (conversation_stream.dart, prompt_formatter.dart)
├── domains/
│   ├── identity/ (identity_model.dart, identity_service.dart, identity_state.dart)
│   ├── profile/ (profile_model.dart, profile_service.dart, profile_state.dart)
│   ├── people/ (people_model.dart, relationship_tracker.dart)
│   ├── calendar/ (calendar_model.dart, calendar_service.dart, schedule_planner.dart)
│   ├── event/ (event_model.dart, event_service.dart)
│   ├── task/ (task_model.dart, task_service.dart, task_state.dart)
│   ├── goal/ (goal_model.dart, goal_service.dart, goal_tracker.dart)
│   ├── project/ (project_model.dart, project_service.dart)
│   ├── finance/ (finance_model.dart, finance_service.dart, budget_analyzer.dart)
│   ├── knowledge/ (knowledge_model.dart, knowledge_service.dart, note_graph.dart)
│   ├── document/ (document_model.dart, document_service.dart)
│   ├── health/ (health_model.dart, health_service.dart, metric_aggregator.dart)
│   ├── notification/ (notification_model.dart, notification_service.dart)
│   ├── ai/ (ai_context_model.dart, context_provider.dart)
│   └── analytics/ (analytics_model.dart, analytics_service.dart)
├── experiences/
│   ├── home/ (home_screen.dart, daily_overview_widget.dart, quick_action_grid.dart)
│   ├── companion/ (companion_screen.dart, ai_stream_view.dart, memory_inspector.dart)
│   ├── capture/ (universal_capture_modal.dart, voice_capture_sheet.dart, quick_text_capture.dart)
│   ├── search/ (command_palette.dart, universal_search_screen.dart)
│   ├── domains/ (domain_hub_screen.dart, finance_view.dart, health_view.dart, tasks_goals_view.dart)
│   ├── timeline/ (life_timeline_screen.dart, reflection_card.dart)
│   ├── settings/ (settings_screen.dart, privacy_control_panel.dart, memory_management_view.dart)
│   └── onboarding/ (onboarding_flow.dart, life_profile_selector.dart)
└── shared/
    ├── mixins/ (ai_awareness_mixin.dart, auto_dispose_mixin.dart)
    ├── utils/ (formatters.dart, validators.dart, date_utils.dart, string_utils.dart)
    ├── types/ (result.dart, failure.dart, ui_state.dart)
    └── layouts/ (app_scaffold.dart, main_header.dart, bottom_nav_bar.dart, sidebar_nav.dart)
```

---

# SECTION 14 — Design Language & Aesthetics

- **Color Palette:** Deep Space Obsidian (`#0B0F17`), Electric Cyan (`#00F2FE`), Radiant Violet (`#4FACFE`), Emerald Pulse (`#00F5A0`), Soft Slate Text (`#94A3B8`).
- **Glassmorphism:** Frosted blur (`BackdropFilter` with `sigmaX: 12`, `sigmaY: 12`), subtle glowing borders.
- **Typography:** Google Fonts Inter / Outfit for modern, highly readable headers and body text.
- **Micro-Animations:** Fluid transitions powered by `flutter_animate` (fade, slide, spring curves).

---

# SECTION 15 — Backend Mapping

Frontend domain services map directly to Spring Boot 3.3 REST endpoints:
- `AuthService` -> `POST /api/v1/auth/login`, `/refresh`
- `TaskService` -> `GET/POST/PUT /api/v1/tasks` (`task` schema)
- `FinanceService` -> `GET/POST /api/v1/finance` (`finance` schema)
- `AICaptureService` -> `POST /api/v1/ai/capture` (`ai` schema, Gemini API)

---

# SECTION 16 — Security & Privacy Philosophy

- **Zero-Knowledge Token Storage:** `FlutterSecureStorage` (Keychain / KeyStore).
- **PII Scrubbing:** Local Dart regex sanitizer strips sensitive SSN/Card numbers before sending to AI endpoints.
- **Biometric Guard:** Local Authentication (FaceID/Fingerprint) required to view Finance or Health domain screens.

---

# SECTION 17 — Scalability Roadmap

- **V1 (Current Baseline):** Core 18 Bounded Contexts, Universal Capture, AI Companion, Cross-Platform Desktop/Mobile Flutter app.
- **V2:** Wearables integration (Apple Watch / Wear OS sync), Offline LLM support via On-Device AI models.
- **V3:** Ambient Voice Glass / Smart Home Context Sensors.

---

# SECTION 18 — Product Rules & Non-Negotiables

1. **Never Lose User Data:** Local persistent queue guarantees zero data loss during network outages.
2. **Always Provide Undo:** Every automated AI action offers a 10-second snackbar undo window.
3. **Transparent AI Reasoning:** Always display the source reference or context node that triggered an AI recommendation.
4. **Preserve Context:** Switching screens must preserve active draft states and capture buffers.
