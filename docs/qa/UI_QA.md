# UI & Design Quality Assurance Specification (LifeOS)

## 1. Document Definition

### Purpose
The **UI & Design Quality Assurance Specification** defines the visual, layout, interaction, state management, and design system verification criteria for every screen, card, modal, and navigation element in the LifeOS Flutter application.

### Structure
1. **Design System Token Compliance (Pearl White #F8FAFC, Glassmorphic Cards)**
2. **Screen-by-Screen Visual Inspection Matrix**
3. **State Coverage (Empty, Loading, Error, Populated States)**
4. **Responsive Layout Boundaries (Mobile, Tablet, Desktop)**

### How it Will be Updated
- Updated following UI QA test execution for each release candidate.

### Owner
- **Primary Owner:** Lead UI/UX QA Specialist
- **Co-Owner:** Flutter Frontend Engineer

### Completion Criteria
- 100% compliance with visual design token standards and zero layout overflow bugs across target screen sizes.

---

## 2. Screen QA Verification Matrix

| Screen Name | Route Path | Design Tokens | States Verified | Interactive Controls | Result |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Splash Intro** | `/splash` | Pearl White / Animated Logo | Initial, Boot Complete | Timed auto-transition | `PASS` |
| **Onboarding Flow** | `/onboarding` | Pearl White / Glass Cards | Selection Active, Unselected | Profile Selection Cards | `PASS` |
| **Login View** | `/login` | Pearl White / Input Glass | Normal, Loading, Error | Password Toggle, Form Submit | `PASS` |
| **Signup View** | `/signup` | Pearl White / Input Glass | Normal, Loading, Error | Account Creation Control | `PASS` |
| **Forgot Password** | `/forgot-password`| Pearl White / Glass Cards | Normal, Link Sent | Reset Request Submit | `PASS` |
| **Home Cockpit** | `/` | Pearl White / 3D Orb | Populated, Filtered | FAB, Bell, Task Checkboxes | `PASS` |
| **AI Companion** | `/companion` | Pearl White / Glass Cards | Stream Active, Graph Open | Memory Inspector, Mic Input | `PASS` |
| **Life Domains** | `/domains` | Pearl White / Metric Pills | Active, Modals Open | Domain Card Click, Modals | `PASS` |
| **Profile Screen** | `/profile` | Pearl White / Status Badges | Normal, Switch Profile | Settings List, Sign Out | `PASS` |
