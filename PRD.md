# Product Requirements Document: AI Delivery Control Plane

Status: MVP definition. Companion to the thesis ([validated-ai-delivery-framework-v1.8.md](validated-ai-delivery-framework-v1.8.md)) and the implementation plan ([phase-packages/README.md](phase-packages/README.md)). Technical specs are not duplicated here; they live in [docs/](docs/).

## 1. Vision

Give engineering organisations a platform-team-owned control plane that measures, governs and improves AI-assisted software delivery, so AI speed does not quietly convert into review debt, quality risk or eroded psychological safety. The product produces decision-grade operational signals, not laboratory-grade causal proof.

## 2. Problem

Engineering leaders cannot tell whether AI-assisted PRs create real delivery value or merely shift cost into review, rework and defects. They lack a trustworthy, team-level, non-punitive view of AI's delivery impact.

## 3. Personas

| Persona | Goal | Primary surface |
|---|---|---|
| Platform / DevEx Engineer | Operate the system; keep data trustworthy | Platform view, Integrations, Policy Settings |
| AI Platform Engineer | Operate approved AI tooling, context/eval pipeline and Token FinOps | Platform view, Integrations, Operating Model |
| Engineering Manager | Manage team delivery health without surveilling individuals | Team Dashboard, Recommendations |
| Tech Lead | Catch risky AI-assisted PRs early | PR Risk View, in-PR guidance |
| Context Engineer / Agent Reviewer | Maintain domain context and calibrate review depth for AI-assisted work | Operating Model, PR Risk View |
| CTO / VP Engineering | Decide AI investment and rollout pace | Executive Summary |
| Security / Compliance | Oversee AI usage risk and prompt safety | Security signals, policy overrides |
| Developer | Merge safely with light, in-PR guidance | GitHub PR (no personal dashboard) |

## 4. Use cases

```text
Value validation: track the Validated Delivery Trend (VDT) signal with confidence labels, not a causal ROI figure.
Risk governance: flag high-risk AI-assisted changes and route the right reviewers.
Validation-capacity management: detect when AI output outpaces review/test capacity.
Operating-model scaling: make ownership explicit so pilots do not depend on one champion engineer.
Harness maturity: assess whether agent instructions, skills, scripts and quality gates are structured enough to support safe scaling.
Graduated policy: move from observation to warnings to confidence-gated enforcement without blocking emergencies.
Psychological-safety monitoring: ensure adoption does not create surveillance anxiety.
```

## 5. User journeys

```text
Platform admin onboarding:
SSO sign-in -> install GitHub App -> connect Jira -> map teams to repos/projects -> select starting policies -> Observation Mode -> recommendations appear after 1-2 sprints.

Engineering Manager weekly:
Open Team Dashboard -> review AI Review Debt and defect trend -> open Recommendations -> action one item -> discuss in retro (team-level only).

Developer in a PR:
Open PR -> see in-PR panel (AI-assisted? metadata complete? size/risk? reviewer suggestion?) -> address guidance -> merge. No personal scoring is shown to anyone.

Executive quarterly:
Open Executive Summary -> read the Validated Delivery Trend (VDT), adoption and risk (with confidence) -> attend governance forum -> adjust rollout pace and budget.
```

## 6. MVP scope

```text
Integrations: GitHub (PRs, reviews, webhooks) + Jira (issues, defects, sprints).
Core metrics: AI-assisted PR Rate, AI Review Debt, Post-Merge Defect Rate, Human Validation Cost, Validated Delivery Trend (VDT) (+ Data Confidence on each).
Screens: Overview, Team, PR Risk, Metrics Detail (Phase 2); Developer in-PR view (Phase 3); Policy Settings, Recommendations/Playbook (Phase 4); Executive Summary (Phase 5).
Guardrails: PR comment bot (warn), AI Metadata Blocker with emergency override, risk-based reviewer assignment, Dynamic AI WIP recommendation.
```

Screen-to-phase detail: [docs/ui-ux-spec.md](docs/ui-ux-spec.md).

## 7. Out of scope (MVP)

```text
Multi-tenant SaaS (tenant isolation, billing, SOC2-grade controls).
Raw prompt inspection or storage.
Individual developer productivity ranking or leaderboards.
Automatic code fixing by the platform.
IDE telemetry, model drift, EU AI Act automation, advanced security dashboards (Stage 2+).
```

## 8. Functional requirements

```text
FR1  Ingest GitHub and Jira events idempotently into a raw event store and normalized tables.
FR2  Compute a 0-100 Data Confidence Score per metric and display it with every value.
FR3  Compute the five core metrics and contextual risk per PR.
FR4  Present role-based, team-level dashboards; never an individual ranking.
FR5  Post non-blocking PR guidance in Warning/Recommendation mode.
FR6  Enforce confidence-gated policies in Phase 4 with an always-available emergency override.
FR7  Record every override in an auditable log and surface override trends.
FR8  Generate team-level recommendations and a playbook with owners.
FR9  Provide self-service onboarding that starts every new team in Observation Mode.
FR10 Purge detailed data at 12 months; retain aggregates for 24 months.
FR11 Maintain operating-model artefacts: approved tool catalogue, ownership map, context registry metadata, review-depth responsibilities and team-level Token FinOps.
FR12 Assess Agentic Harness Maturity at team/repository level; use low maturity for enablement recommendations, not individual scoring or automatic enforcement.
```

Specifications: data ([docs/data-model.md](docs/data-model.md)), metrics ([docs/metrics-catalogue.md](docs/metrics-catalogue.md)), risk/policy ([docs/risk-policy-engine.md](docs/risk-policy-engine.md)), API ([docs/api-spec.md](docs/api-spec.md)).
Operating-model detail: [docs/ai-operating-model.md](docs/ai-operating-model.md).

## 9. Non-functional requirements

```text
NFR1 Privacy: pseudonymise identifiers; team-level views by default; no raw prompt storage. (docs/governance-and-privacy.md)
NFR2 Trust: blocking enforcement requires High confidence (Data Confidence Score >= 90); 70-89 may warn only; below 70 the metric is withheld.
NFR3 Reliability: webhook idempotency; nightly source reconciliation gap < 5%; webhook lag < 1 hour.
NFR4 Observability: every collector, metric job and policy rule emits the counters in docs/testing-and-observability.md.
NFR5 Security: Keycloak SSO + RBAC; signed webhooks; least-privilege service accounts.
NFR6 Performance: multi-team dashboard p95 load within target under 50+ concurrent team views.
NFR7 Auditability: policy overrides and schema migrations are fully traceable.
```

## 10. Permission model (RBAC)

| Role | Sees | Can change |
|---|---|---|
| Platform Admin | Everything, incl. integrations and raw audit | Integrations, policies, team config |
| Data Steward | Retention, access and override logs; raw author-level audit | Retention, identifier handling |
| Engineering Manager | Team-level metrics and recommendations | Team thresholds (with approval) |
| Tech Lead | Team metrics, PR risk | Reviewer routing input |
| Executive | Aggregated summaries only | Nothing |
| Developer | Own PR in-PR guidance | Nothing platform-side |

## 11. Analytics events (product telemetry)

```text
team_onboarded(team_id, mode)
metric_viewed(role, metric_name, confidence_score)
recommendation_actioned(team_id, recommendation_type, outcome)
pr_guidance_shown(pr_id, signals)
policy_override_used(pr_id, reason)
psychological_safety_pulse_recorded(team_id, score)
```

These are product-usage events; they are team/role-scoped and never used for individual performance scoring.

## 12. Acceptance criteria

```text
Data Confidence Score >= 75 for core metrics in the pilot.
Manual 50-PR sample is within 30% of computed AI-assisted PR Rate.
Psychological safety score > 3.5 in pilot teams.
Validated Delivery Trend (VDT) is non-negative or improving over the trailing 90 days.
0% of production emergency fixes delayed by platform blockers.
Every new team starts in Observation Mode.
```

## 13. Release milestones

```text
Phase 0-1: Groundwork and data pipeline (5 weeks).
Phase 2:   Metrics engine and read-only dashboards (3 weeks).
Phase 3:   Soft landing and experiment (4 weeks).
Phase 4:   Automated guardrails (4 weeks).
Phase 5:   Enterprise rollout (continuous).
```

Gate criteria for each milestone are defined in the corresponding [phase package](phase-packages/README.md).
