# Product Requirements Document (PRD): AI Delivery Control Plane

## 1. Vision
To provide engineering organizations with a platform-team-owned control plane for measuring, governing, and improving AI-assisted software delivery, ensuring that AI speed does not compromise quality, safety, or psychological safety.

## 2. Target Users
- **Platform Engineering Teams:** Operators and owners of the control plane.
- **Engineering Managers:** Consumers of team-level delivery health and value metrics.
- **Tech Leads:** Validators of AI output and managers of PR risk.
- **CTO / CTO Office:** Strategic decision-makers for AI investment and governance.
- **Security & Compliance:** Auditors of AI usage risk and prompt safety.

## 3. Use Cases
- **Value Validation:** Measure the real economic impact (Net AI Delivery Value) of AI tools.
- **Risk Governance:** Automatically identify and flag high-risk AI-assisted changes.
- **Validation Capacity Management:** Detect when AI output exceeds the team's capacity to review and test.
- **Policy Enforcement:** Gradually introduce guardrails (e.g., mandatory AI metadata) without blocking emergency work.
- **Psychological Safety Monitoring:** Ensure AI adoption doesn't create surveillance anxiety or punitive pressure.

## 4. MVP Scope
- **Integrations:** GitHub (PRs, webhooks) and Jira (Issues, defects).
- **Core Metrics:** AI-assisted PR rate, AI Review Debt, Estimated Net AI Delivery Value, Post-merge Defect Rate, Data Confidence Score.
- **Key Features:**
  - Automated PR risk scoring.
  - PR Comment Bot for soft recommendations.
  - AI Metadata Blocker with emergency override.
  - Team and Overview dashboards.
  - Policy Settings and Recommendations views.

## 5. Out of Scope (Phase 1-5)
- Multi-tenant SaaS capabilities (MVP is internal-platform only).
- Direct raw prompt inspection (privacy boundary).
- Individual developer productivity ranking or leaderboards.
- Automatic code fixing by the platform.

## 6. Data Model (High Level)
- `raw_events`: Source payload store.
- `pull_requests`: Normalized PR data with AI risk scores.
- `review_analytics`: Calibrated review effort and load.
- `metric_snapshots`: Time-series of team-level metrics with confidence scores.
- `recommendations`: Actionable guidance for teams.
- `policy_overrides`: Audit log of emergency-fix usage.

## 7. API Design
- `/api/v1/teams/{teamId}/metrics`: GET team health.
- `/api/v1/pull-requests/{prId}/risk`: GET PR-level risk analysis.
- `/api/v1/policies`: GET/POST policy rules.
- `/api/v1/webhooks/github|jira`: POST event ingestion.

## 8. Milestones
- **Phase 0-1:** Groundwork and Data Pipeline (5 weeks).
- **Phase 2:** Metrics Engine and Dashboard (3 weeks).
- **Phase 3:** Soft Landing and Experimentation (4 weeks).
- **Phase 4:** Automated Guardrails (4 weeks).
- **Phase 5:** Enterprise Rollout (Ongoing).

## 9. Acceptance Criteria
- Data Confidence Score >= 75 for core metrics.
- Psychological Safety Score > 3.5 in pilot teams.
- Net AI Delivery Value is positive or trending positive.
- 0% delay of production emergency fixes due to platform blockers.
