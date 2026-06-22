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
Optional data scientist or analytics engineer
```

## Work Tracks (this phase)

```text
Metrics and risk: five core metric functions, risk scoring, Validated Delivery Trend (VDT), Cognitive Load Index (dominant track).
Engineering: read-only dashboards, metric_snapshots population, cost-input configuration.
Governance and reporting: confidence labels surfaced next to every metric.
Culture and people: pilot-team lead reality-check of the read-only dashboard. No developer-facing UI yet.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Core metric functions | Platform Engineer | Platform Lead | Data scientist | Engineering Manager |
| Risk scoring function | Platform Engineer | Platform Lead | Tech Lead, Security Lead | Engineering Manager |
| Validated Delivery Trend (VDT) | Platform Engineer | Platform Lead | Engineering Manager | Executive sponsor |
| Read-only dashboards | Platform Engineer | Platform Lead | Pilot-team EM | Pilot team |
| Dashboard reality validation | Pilot-team EM | Platform Lead | Tech Lead | Platform team |

## Entry Criteria

```text
Phase 1 exit criteria passed.
Core PR and review analytics tables are populated.
Data Confidence Score is at least 75.
```

## Actions

```text
Week 4: Implement fn_ai_pr_rate(), fn_review_debt_ratio(), fn_defect_rate_weighted(), fn_human_validation_cost_estimate() and fn_net_ai_delivery_value_estimate().
Week 5: Implement the contextual risk score (PR size, security path regex, 6-month codebase familiarity).
Week 6: Build the read-only role-based screens: Overview, Team, Pull Request Risk and Metrics Detail.
Enable the Validated Delivery Trend (VDT) signal; the optional directional cost context reads cost_config.
Calculate Cognitive Load Index using the required complexity filter.
Surface a Data Confidence label next to every metric.
```

## Deliverables

```text
[Phase2]_exit_report.pdf
[Phase2]_data_dictionary.json
[Phase2]_config_changes.yaml
Five core metric functions
Risk scoring function
Read-only pilot dashboard (Overview, Team, PR Risk, Metrics Detail)
Validated Delivery Trend (VDT) calculation
Cognitive Load Index calculation
Measurement confidence labelling applied to every metric
Metric function unit tests, API integration tests and manual 50-PR sample validation
Metric-engine observability counters and freshness alert
```

## Exit Criteria

```text
Pilot team's last 2 sprints of AI Review Debt display correctly.
Pilot-team lead confirms the dashboard broadly matches team reality.
Cognitive Load Index is calculated with the configured complexity filter.
Manual review of 50 PRs differs from calculated AI-assisted PR Rate by no more than 30%.
```

## Fail Criteria

```text
AI-assisted PR Rate differs from the manual 50-PR sample by more than 30%.
AI Review Debt cannot be reproduced from source events.
The pilot team does not trust the read-only dashboard.
```

## Fail Action

```text
Fix AI metadata parsing, baseline selection or metric definitions.
Repeat Phase 2 validation before developer-facing rollout.
```

## Gate Decision

```text
Proceed to Phase 3 only when the pilot team agrees that the read-only dashboard reflects reality closely enough for soft recommendations.
```

## Reference Docs

```text
docs/metrics-catalogue.md        - five core metrics, Cognitive Load Index, supporting signals, validity guardrails
docs/risk-policy-engine.md       - contextual risk score, weights and proxies (read-only in this phase)
docs/data-confidence.md          - confidence scoring, decision rule and presentation tiers
docs/ui-ux-spec.md               - the four read-only screens, examples, empty/low-confidence states
docs/api-spec.md                 - Phase 2 metrics, risk and recommendations read endpoints
docs/data-model.md               - cost_config inputs for the VDT directional cost context
docs/testing-and-observability.md - Phase 2 tests and signals
Master framework: sections 7.2, 7.3, 10, 11, 18, 21
```
