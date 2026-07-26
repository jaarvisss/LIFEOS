# Cross-Module System Data Flows (LifeOS)

## 1. Document Definition

### Purpose
The **Cross-Module System Data Flows** document details multi-step orchestration scenarios where data originates in one domain (e.g., Universal Capture) and propagates asynchronously across multiple bounded context services and database stores (e.g., Tasks, AI Vector Store, Calendar).

### Structure
1. **Universal Capture to Task Creation Flow**
2. **AI Stream Conversation & Vector Context Retrieval Flow**
3. **Financial Transaction & Budget Alert Flow**
4. **Life Profile Switching & Context Refresh Flow**

### How it Will be Updated
- Updated when cross-service workflows or domain event triggers are modified.

### Owner
- **Primary Owner:** Solution Architect
- **Co-Owner:** Backend Integration Lead

### Completion Criteria
- Sequence diagrams and state transition specifications documented for all cross-module journeys.

---

## 2. Universal Capture Sequence Flow

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Flutter as Flutter App
    participant Dio as Dio HTTP / Network
    participant Gateway as Spring Gateway / Auth Filter
    participant AI as AI Intent Classifier
    participant Task as Task Bounded Context
    participant DB as PostgreSQL 16 DB

    User->>Flutter: Submit Thought in Universal Capture Sheet
    Flutter->>Dio: POST /api/v1/capture (Bearer JWT)
    Dio->>Gateway: Forward Request with Security Context
    Gateway->>AI: Send Text for Intent Classification
    AI-->>Gateway: Return Intent ("TASK", Confidence: 0.94)
    Gateway->>Task: Create Task Command
    Task->>DB: INSERT INTO tasks (...)
    DB-->>Task: Task Entity Saved (ID: 1042)
    Task-->>Flutter: Return 201 Created (TaskDTO)
    Flutter-->>User: Display Toast "Thought synthesized & task created!"
```
