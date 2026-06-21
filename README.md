# Validated AI Delivery Framework v1.8

This repository contains the Validated AI Delivery Framework v1.8 documentation set.

Use this file as the entry point. The phase documents are the operational source of truth for implementation. The master framework remains the product thesis, high-level architecture, metric catalogue, roadmap and reference map.

## Document Map

| Document | Purpose |
|---|---|
| [validated-ai-delivery-framework-v1.8.md](validated-ai-delivery-framework-v1.8.md) | Master reference, product thesis, metric catalogue and architecture overview |
| [phase-packages/README.md](phase-packages/README.md) | Stage-gate overview and phase-package navigation |
| [phase-packages/phase-0-groundwork-and-legal-assurance.md](phase-packages/phase-0-groundwork-and-legal-assurance.md) | Legal, privacy, HR, cultural readiness and PR template registration |
| [phase-packages/phase-1-data-architecture-and-raw-collection.md](phase-packages/phase-1-data-architecture-and-raw-collection.md) | Raw data collection, connectors, event store, schema and Data Confidence Score |
| [phase-packages/phase-2-metrics-and-risk-engine.md](phase-packages/phase-2-metrics-and-risk-engine.md) | Read-only metrics, risk scoring, dashboard states and metric confidence |
| [phase-packages/phase-3-soft-landing-and-experiment.md](phase-packages/phase-3-soft-landing-and-experiment.md) | Warning Mode, PR comment bot, experiment mode and calibration |
| [phase-packages/phase-4-automated-guardrails.md](phase-packages/phase-4-automated-guardrails.md) | Enforcement Mode, GitHub checks, metadata blocker, policy engine and Emergency Override |
| [phase-packages/phase-5-enterprise-rollout.md](phase-packages/phase-5-enterprise-rollout.md) | Enterprise rollout, self-service onboarding, governance forum, RBAC and retention purge |
| [TODO.md](TODO.md) | Outstanding implementation gaps, tracked per phase |

## Implementation Phases

| Phase | Name | Operating mode |
|---:|---|---|
| 0 | Groundwork and Legal Assurance | Readiness only |
| 1 | Data Architecture and Raw Collection | Observation Mode |
| 2 | Metrics and Risk Engine | Observation Mode |
| 3 | Soft Landing and Experiment | Warning Mode and Recommendation Mode |
| 4 | Automated Guardrails | Enforcement Mode |
| 5 | Enterprise Rollout | Staged rollout across modes |

## Operating Modes

```text
Observation Mode = collect and calculate only; no developer-facing warnings or blocks.
Warning Mode = non-blocking PR comments and soft guidance.
Recommendation Mode = team-level recommendations and playbooks.
Enforcement Mode = calibrated blocking checks with Emergency Override.
```

## Non-Negotiable Rules

```text
No individual AI productivity ranking.
No personal productivity dashboard.
No "who uses AI most" view.
No performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement from metrics with Data Confidence Score < 70.
No causal claims stronger than the data supports.
```

## Start Here

```text
1. Read phase-packages/README.md.
2. Start Phase 0 from phase-packages/phase-0-groundwork-and-legal-assurance.md.
3. Do not begin Phase 1 until Phase 0 exit criteria have passed.
```
