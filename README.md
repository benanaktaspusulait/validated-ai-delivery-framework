# Validated AI Delivery Framework v1.8

A platform-team-owned control plane for measuring, governing and improving AI-assisted software delivery. This repository is the documentation set for building it.

Read this file first, then start Phase 0. The phase packages are the implementation source of truth; `docs/` holds the canonical technical references; `examples/` holds copyable artefacts; the master framework holds the thesis.

## Where to go

| If you want to... | Read |
|---|---|
| Understand why this exists (thesis, positioning, roadmap) | [validated-ai-delivery-framework-v1.8.md](validated-ai-delivery-framework-v1.8.md) |
| Know what we are building (product definition) | [PRD.md](PRD.md) |
| Stand a team up quickly | [quick-start.md](quick-start.md) |
| Answer common questions | [faq.md](faq.md) |
| Implement, in order, with gates | [phase-packages/README.md](phase-packages/README.md) |
| Look up a technical spec | [docs/](docs/) |
| Copy a template or config | [examples/](examples/) |
| See what is still open | [TODO.md](TODO.md) |

## Reference docs (`docs/`)

| Doc | Scope |
|---|---|
| [architecture.md](docs/architecture.md) | System flow, reference stack, collector interface, reliability |
| [data-model.md](docs/data-model.md) | Schema, indexes, cost config, retention, migration ownership |
| [data-confidence.md](docs/data-confidence.md) | Confidence scoring, decision rule, presentation tiers |
| [metrics-catalogue.md](docs/metrics-catalogue.md) | Core metrics, Cognitive Load Index, validity guardrails |
| [risk-policy-engine.md](docs/risk-policy-engine.md) | Risk scoring, policy engine, Dynamic AI WIP, playbook |
| [ui-ux-spec.md](docs/ui-ux-spec.md) | Role-based views, screens, states, navigation |
| [governance-and-privacy.md](docs/governance-and-privacy.md) | Non-negotiable rules, privacy, retention, escalation |
| [psychological-safety.md](docs/psychological-safety.md) | Pulse questions and gating rules |
| [rollout-operating-model.md](docs/rollout-operating-model.md) | Operating modes, RACI, pause criteria, roadmap |
| [testing-and-observability.md](docs/testing-and-observability.md) | Per-phase test and observability requirements |

## Implementation phases

| Phase | Name | Operating mode |
|---:|---|---|
| 0 | Groundwork and Legal Assurance | Readiness only |
| 1 | Data Architecture and Raw Collection | Observation |
| 2 | Metrics and Risk Engine | Observation |
| 3 | Soft Landing and Experiment | Warning / Recommendation |
| 4 | Automated Guardrails | Enforcement |
| 5 | Enterprise Rollout | Staged |

Operating-mode definitions and the per-phase confidence roadmap are in [docs/rollout-operating-model.md](docs/rollout-operating-model.md).

## Non-negotiable rules

```text
No individual AI productivity ranking, personal dashboard, "who uses AI most" view or performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement except from High-confidence metrics (Data Confidence Score >= 90); metrics below 70 are withheld from decisions.
No causal claims stronger than the data supports.
```

Full governance detail: [docs/governance-and-privacy.md](docs/governance-and-privacy.md).

## Start here

```text
1. Read phase-packages/README.md.
2. Start Phase 0 (phase-packages/phase-0-groundwork-and-legal-assurance.md).
3. Do not begin Phase 1 until Phase 0 exit criteria have passed.
```
