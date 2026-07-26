# Pre-Release & Deployment Checklist (LifeOS)

## 1. Document Definition

### Purpose
The **Pre-Release & Deployment Checklist** outlines mandatory verification gates, environment configuration prerequisites, security audits, build sign-offs, and rollback steps required prior to promoting LifeOS to staging or commercial production.

### Structure
1. **Environment Setup & Configuration Verification**
2. **Security & Cryptography Gates**
3. **Database Migration Readiness (Liquibase / Flyway)**
4. **Mobile Application Bundle Verification (APK, IPA)**
5. **Post-Deployment Sanity Verification Protocols**

### How it Will be Updated
- Checked off sequentially during release candidate (RC) build evaluation.

### Owner
- **Primary Owner:** DevOps & Release Manager
- **Co-Owners:** Systems Architect, QA Lead

### Completion Criteria
- 100% of checklist items verified and signed off by module owners.

---

## 2. Release Readiness Gates

- [x] Spring Boot Production Profile configured (`application-prod.yml`).
- [x] PostgreSQL 16 connection pooling (HikariCP) tuned for high concurrency.
- [x] JWT RSA public/private keys provisioned in secure secret storage.
- [x] Dio API Base URL dynamically configured to production endpoint.
- [x] ProGuard obfuscation rules applied to Flutter Android build (`app/build.gradle`).
- [x] Automated unit and integration test suites passing with 100% success rate.
