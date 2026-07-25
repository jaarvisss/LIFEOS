# LifeOS - Flutter Frontend Operating System

LifeOS is an AI-first Personal Operating System built with **Flutter 3.x** and **Dart 3.x**. It provides a unified, glassmorphic, intelligence-driven user interface across iOS, Android, Desktop, and Web.

## Architectural Highlights
- **18 Bounded Context Domains:** Maps directly to backend Spring Boot modules (`identity`, `profile`, `people`, `calendar`, `event`, `task`, `goal`, `project`, `finance`, `knowledge`, `document`, `health`, `notification`, `ai`, `analytics`).
- **Clean Architecture:** Modular separation into `core/`, `infrastructure/`, `design_system/`, `intelligence/`, `domains/`, `experiences/`, and `shared/`.
- **State Management:** Flutter Riverpod for deterministic state trees.
- **Universal Capture:** Multi-modal text, voice, and image ingestion engine.

## Documentation
See [FRONTEND_ARCHITECTURE_BLUEPRINT.md](FRONTEND_ARCHITECTURE_BLUEPRINT.md) for the exhaustive 18-section architectural specification.
