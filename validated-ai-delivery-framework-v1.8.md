# Validated AI Delivery Framework v1.8

## AI Delivery Control Plane for Platform Engineering Teams

> Validated AI Delivery Platform is an AI Delivery Control Plane for platform teams: it measures whether AI-assisted engineering is producing real delivery value, or merely increasing validation cost, risk and review debt.

**Version:** 1.8 | **Status:** Platform-ready | **Audience:** Platform Engineering, DevEx, CTO Office, Security, Engineering Managers

## Read this first

| Section | File | What you'll learn |
|---|---|---|
| Overview and thesis | [framework/01-overview-and-thesis.md](framework/01-overview-and-thesis.md) | Why this exists, what it is, what it is not |
| Personas | [framework/02-personas.md](framework/02-personas.md) | Who uses it and what they see |
| MVP scope | [framework/03-mvp-scope.md](framework/03-mvp-scope.md) | Problem, integrations, five core metrics, non-goals |
| Expanded metrics | [framework/04-expanded-metrics.md](framework/04-expanded-metrics.md) | AI Dependency Risk, Quality Gap, Cognitive Load |
| Technical references | [framework/05-technical-references.md](framework/05-technical-references.md) | Links to all docs/ specs |
| MVP backlog | [framework/06-mvp-backlog.md](framework/06-mvp-backlog.md) | 7 epics in implementation order |
| Pilot criteria | [framework/07-pilot-criteria.md](framework/07-pilot-criteria.md) | Success/failure triggers, rollback |
| Enterprise roadmap | [framework/08-enterprise-roadmap.md](framework/08-enterprise-roadmap.md) | 5-stage evolution plan |
| Investment logic | [framework/09-investment-logic.md](framework/09-investment-logic.md) | Cost of not acting, ROI |
| Governance and adoption | [framework/10-governance-and-adoption.md](framework/10-governance-and-adoption.md) | Non-negotiable rules, phases, safe usage, onboarding |

## Navigate by purpose

| If you want to... | Go to |
|---|---|
| Understand the thesis | [framework/01-overview-and-thesis.md](framework/01-overview-and-thesis.md) |
| Know what to build | [framework/03-mvp-scope.md](framework/03-mvp-scope.md) + [framework/06-mvp-backlog.md](framework/06-mvp-backlog.md) |
| Implement in order | [phase-packages/README.md](phase-packages/README.md) |
| Start coding | [implementation/README.md](implementation/README.md) |
| Look up a spec | [docs/](docs/) |
| Copy a template | [examples/](examples/) |
| See what's still open | [TODO.md](TODO.md) |

## Non-negotiable rules

```text
No individual AI productivity ranking, personal dashboard, "who uses AI most" view or performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement except from High-confidence metrics (Data Confidence Score >= 90).
No causal claims stronger than the data supports.
```

Full governance: [docs/governance-and-privacy.md](docs/governance-and-privacy.md)
