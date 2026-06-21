# Validated AI Delivery Framework v1.8 - Phase Packages

This folder contains the physical stage-gate phase packages for implementing the Validated AI Delivery Framework v1.8.

Each phase is a mini-project with clear entry criteria, exit criteria, fail criteria and deliverables. A phase does not start until the previous phase has passed its gate.

## Work Tracks

```text
1. Engineering: integrations, collectors, storage, APIs and automation.
2. Metrics and risk: calculation logic, confidence scoring, risk scoring and policy signals.
3. Culture and people: psychological safety, onboarding, communication and team feedback.
4. Governance and reporting: legal/privacy readiness, leadership reporting and policy calibration.
```

## Master Stage-Gate Map

| Phase | Name | Duration | Primary deliverable | Gate | Package |
|---:|---|---:|---|---|---|
| 0 | Groundwork and Legal Assurance | 2 weeks | Approved legal/HR package and pilot agreement | Legal and HR sign-off | [phase-0-groundwork-and-legal-assurance.md](phase-0-groundwork-and-legal-assurance.md) |
| 1 | Data Architecture and Raw Collection | 3 weeks | Working GitHub/Jira event pipeline | Data Confidence Score >= 75 | [phase-1-data-architecture-and-raw-collection.md](phase-1-data-architecture-and-raw-collection.md) |
| 2 | Metrics and Risk Engine | 3 weeks | Read-only dashboard with five core metrics | Target metrics calculated and validated | [phase-2-metrics-and-risk-engine.md](phase-2-metrics-and-risk-engine.md) |
| 3 | Soft Landing and Experiment | 4 weeks | A/B test result and psychological safety report | Psychological safety score > 3.5 | [phase-3-soft-landing-and-experiment.md](phase-3-soft-landing-and-experiment.md) |
| 4 | Automated Guardrails | 4 weeks | Dynamic AI WIP plus metadata blocker | Positive Net AI Delivery Value in pilot | [phase-4-automated-guardrails.md](phase-4-automated-guardrails.md) |
| 5 | Enterprise Rollout | Continuous | Self-service onboarding package | Open to wider teams | [phase-5-enterprise-rollout.md](phase-5-enterprise-rollout.md) |

## Operating Modes

```text
Observation Mode = collect and calculate only; no developer-facing warnings or blocks.
Warning Mode = non-blocking PR comments and soft guidance.
Recommendation Mode = team-level recommendations and playbooks.
Enforcement Mode = calibrated blocking checks with Emergency Override.
```

## Product Surface (UI)

The platform is a control plane, not only a report screen. It collects data, computes metrics, runs risk and policy rules, and pushes warnings or blocks back into GitHub and CI.

Role-based views:

```text
Platform Team view  - operate the system: integrations, data confidence, policy violations, rollout pace.
Engineering Manager / Tech Lead view - team delivery health and risky PRs.
Executive view      - Net AI Delivery Value, adoption, risk and ROI summary.
Developer view      - lightweight, in-PR guidance only. No personal dashboard.
```

MVP screens are built progressively across the phases:

| Screen | Introduced |
|---|---|
| Admin login, integration connect, team mapping | Phase 1 |
| Read-only Overview | Phase 2 |
| Team Dashboard | Phase 2 |
| PR Risk View | Phase 2 |
| Metrics Detail | Phase 2 |
| Developer in-PR view and PR comment bot | Phase 3 |
| Policy Settings | Phase 4 |
| Recommendations / Playbooks | Phase 4 |
| Executive Summary, multi-team navigation, RBAC, self-service onboarding | Phase 5 |

Reference stack (see Phase 1 for detail):

```text
Java 21 + Quarkus backend, React / Next.js frontend, PostgreSQL, Keycloak SSO, GitHub App + Jira REST.
Built first as an internal platform tool, not a multi-tenant SaaS.
```

## Measurement Confidence Roadmap

The product produces decision-grade operational signals, not laboratory-grade causal proof. Directly measurable data is shown as fact, estimates carry a confidence label, and weak signals are trend-only.

| Phase | Measurement layer | Confidence |
|---:|---|---|
| 1 | Observable delivery metrics (GitHub/Jira events) | High |
| 2 | AI usage metadata plus computed metrics and risk | Medium-high (estimates labelled) |
| 3 | Quality linkage plus calibrated review cost | Medium |
| 4 | Enforcement on metrics with Data Confidence Score >= 70 | Decision-grade only |
| 5 | Advanced intelligence (telemetry, trust calibration) | Variable, labelled |

## Developer Experience Boundaries

```text
Developers do not get a personal productivity dashboard.
Developer interaction happens mainly inside PRs.
No individual leaderboard.
No "who uses AI most" view.
No performance-review export.
```

## Stage-Gate Rule
```text
At the end of every phase, the Platform Lead and pilot-team Engineering Manager must answer:
"Would moving to the next phase destabilise the current system?"

If the answer is yes, the current phase repeats or a bug-fix sprint is inserted.
Exit criteria must not be relaxed because the calendar is tight.
```

## Required Deliverables

Every phase must produce physical decision artefacts:

```text
[PhaseX]_exit_report.pdf
[PhaseX]_data_dictionary.json
[PhaseX]_config_changes.yaml
```

Deliverable rules:

```text
The exit report is a 2-page decision summary for platform, engineering and leadership stakeholders.
The data dictionary records schema, source, ownership, confidence and retention changes.
The config changes file records policy, threshold, connector and team-configuration updates.
```

## Implementation Readiness Checks

```text
Every phase with implementation work must include test evidence.
Every production-facing collector, metric job, policy rule and dashboard state must expose observability signals.
Phase gates cannot pass on narrative confidence alone; they require source-data checks, calculation checks or policy regression checks appropriate to the phase.
```

## Weekly RACI

| Role | Weekly responsibility |
|---|---|
| Platform Engineer | Build the week's collector, engine, API, dashboard or policy work |
| Platform Lead | Monitor data confidence, triage feedback and decide threshold changes |
| Engineering Manager | Interpret team-level metrics, protect psychological safety and run retros |
| Tech Lead | Review high-risk PRs, validate recommendations and coach explainable AI usage |
| Data Steward | Review data retention, access and override logs |
| Security Lead | Review security-sensitive paths, prompt leakage signals and incident linkage |

## Weekly Pause Criteria

Do not move to the next phase if any of these conditions are true:

```text
Data Confidence Score is below 70 for 2 consecutive weeks.
Psychological safety score is below 3.5.
Platform alert volume exceeds 10 actionable alerts per week.
Developers report that AI metadata feels punitive.
Emergency overrides exceed 3 per sprint without retro review.
Required phase deliverables are incomplete.
```

Pause action:

```text
Hold the current phase for 1 week.
Fix connector quality, communication, thresholds or policy design.
Run a blameless retro if the issue affects team trust.
Resume only after the Platform Lead can explain what changed.
```

## First Three Actions

```text
1. Block 2 calendar weeks for Phase 0: legal/privacy, HR/People and pilot-team readiness.
2. Create GitHub and Jira service accounts with the minimum permissions required.
3. Meet the pilot team lead and explain: "We are not measuring individual productivity. We are validating whether the system can use AI safely."
```
