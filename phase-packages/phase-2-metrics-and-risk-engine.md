# Phase 2 - Metrics and Risk Engine

Operating mode: Observation Mode. Turn clean data into read-only insight. No developer-facing warnings or blockers.

## Purpose

```text
Compute the five core metrics and contextual risk, and present them read-only with confidence labels.
```

## Duration

```text
3 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Pilot-team Engineering Manager
Optional data scientist
```

## Work Tracks (this phase)

```text
Metrics and risk: five core metric functions, risk scoring, VDT, Cognitive Load Index (dominant track).
Engineering: read-only dashboards, metric_snapshots, cost-input configuration.
Governance and reporting: confidence labels surfaced next to every metric.
Culture and people: pilot-team lead reality-check of the read-only dashboard.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Core metric functions | Platform Engineer | Platform Lead | Data scientist | Engineering Manager |
| Risk scoring function | Platform Engineer | Platform Lead | Tech Lead, Security Lead | Engineering Manager |
| VDT calculation | Platform Engineer | Platform Lead | Engineering Manager | Executive sponsor |
| Read-only dashboards | Platform Engineer | Platform Lead | Pilot-team EM | Pilot team |
| Dashboard reality validation | Pilot-team EM | Platform Lead | Tech Lead | Platform team |

## Entry Criteria

```text
Phase 1 exit criteria passed.
Core PR and review analytics tables populated.
Data Confidence Score >= 75.
```

## Actions (high-level)

```text
Week 4: Implement 5 core metric functions (ai_pr_rate, review_debt, defect_rate, human_validation_cost, vdt_signal_score).
Week 5: Implement contextual risk score (10 factors with weights).
Week 6: Build read-only dashboards (Overview, Team, PR Risk, Metrics Detail).
         Enable VDT signal. Calculate Cognitive Load Index.
         Surface Data Confidence label next to every metric.
```

For detailed implementation steps, see [implementation/phase-2/](../implementation/phase-2/).

## Deliverables

```text
Five core metric functions
Risk scoring function
Read-only pilot dashboard (Overview, Team, PR Risk, Metrics Detail)
VDT calculation
Cognitive Load Index calculation
Confidence labelling on every metric
Metric tests (unit, data quality, manual 50-PR validation)
Metric-engine observability counters
[Phase2]_exit_report.pdf
[Phase2]_data_dictionary.json
[Phase2]_config_changes.yaml
```

## Exit Criteria

```text
Last 2 sprints of AI Review Debt display correctly.
Pilot lead confirms dashboard matches reality.
Cognitive Load Index calculated with complexity filter.
Manual 50-PR sample within 30% of computed AI-assisted PR Rate.
```

## Fail Criteria

```text
AI-assisted PR Rate differs from manual sample by > 30%.
AI Review Debt cannot be reproduced from source events.
Pilot team does not trust the read-only dashboard.
```

## Fail Action

```text
Fix AI metadata parsing, baseline selection or metric definitions.
Repeat Phase 2 validation before developer-facing rollout.
```

## Gate Decision

```text
Proceed to Phase 3 only when the pilot team agrees the dashboard reflects reality.
```

## Reference Docs

```text
docs/metrics-catalogue.md        - five core metrics, VDT, Cognitive Load Index, validity guardrails
docs/risk-policy-engine.md       - contextual risk score, weights, proxies (read-only this phase)
docs/data-confidence.md          - confidence scoring, decision rule, presentation tiers
docs/ui-ux-spec.md               - four read-only screens, empty/low-confidence states
docs/api-spec.md                 - Phase 2 endpoints, pagination, error codes
docs/data-model.md               - cost_config, metric_lineage
docs/testing-and-observability.md - Phase 2 tests and observability signals
implementation/phase-2/          - detailed task breakdown (T2.1-T2.17)
```
