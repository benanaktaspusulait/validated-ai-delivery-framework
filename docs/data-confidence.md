# Data Confidence

Canonical method for scoring how much each metric can be trusted, and how the platform behaves as that trust changes. Implemented as a batch job in Phase 1 and applied by every later phase.

The score is a transparent, multi-factor weighted value: every point traces back to a measurable input, so a metric can defend itself in an audit. The platform withdraws its own authority when its data is weak.

## Confidence Score engine

The score is the weighted average of four components, each scored 0-100. Confidence is computed **per team, per metric** (not aggregated across teams or metrics), because different metrics have different data dependencies.

### Component definitions

| Component | Weight | Measures | Scoring rule | Min | Max |
|---|---:|---|---|---:|---:|
| Data Volume | 30% | Relevant events (PRs, reviews, deploys) in the last 30 days | `min(100, (events_last_30d / 100) x 100)` — 100+ events score full | 0 | 100 |
| Data Freshness | 25% | Minutes since the most recent event | Piecewise decay (see table below) | 0 | 100 |
| Completeness | 25% | Share of required fields populated on incoming events | `(populated_required_fields / expected_required_fields) x 100` | 0 | 100 |
| Statistical Stability | 20% | Variability of the metric over the last 7 days (CV) | Band-based (see table below) | 0 | 100 |

### Volume scoring

```text
Volume_Score = min(100, (events_last_30d / 100) x 100)

Examples:
  10 events  -> min(100, 10)  = 10
  50 events  -> min(100, 50)  = 50
  100 events -> min(100, 100) = 100
  200 events -> min(100, 200) = 100 (capped)
```

### Freshness scoring (piecewise decay)

| Time since last event | Score | Band |
|---|---:|---|
| 0 - 6 hours | 100 | Fresh |
| 6 - 12 hours | 75 | Acceptable |
| 12 - 24 hours | 50 | Stale |
| 24 - 48 hours | 25 | Outdated |
| 48+ hours | 0 | Dead |

This provides early warnings instead of a sudden cliff at 24 hours. Low-volume teams (e.g. weekly deployers) should configure their expected event frequency in `team-config.yaml` to avoid being penalised for predictable gaps.

### Completeness scoring

```text
Required fields for completeness (checked on pull_requests table):
  - event_type (from raw_events)
  - author_id
  - repository_id
  - source_timestamp

Completeness_Score = (populated_required_fields / expected_required_fields) x 100

Examples:
  4/4 fields populated -> 100
  3/4 fields populated -> 75
  2/4 fields populated -> 50
  1/4 fields populated -> 25
```

### Statistical stability scoring

```text
Minimum sample size: at least 5 data points in the 7-day window.
Below 5: Stability_Score = 30, confidence_issue = "insufficient_sample_size".

Coefficient of Variation (CV) = standard_deviation / mean x 100

| CV range | Stability_Score | Band |
|---|---:|---|
| < 10% | 100 | Very stable |
| 10-30% | 75 | Stable |
| 30-50% | 50 | Volatile |
| > 50% | 20 | Highly volatile |

Example:
  Metric values over 7 days: [12, 13, 11, 14, 12, 13, 12]
  Mean = 12.43, StdDev = 0.98
  CV = 0.98 / 12.43 x 100 = 7.9%
  CV < 10% -> Stability_Score = 100
```

### Final score formula

```text
Confidence_Score = (Volume_Score x 0.30)
                + (Freshness_Score x 0.25)
                + (Completeness_Score x 0.25)
                + (Stability_Score x 0.20)

Round to nearest integer. Range: 0-100.
```

### Label assignment

| Score range | Label | Behaviour |
|---|---|---|
| 90-100 | high | Blocking enforcement permitted |
| 70-89 | medium | Warnings only; blocking forbidden |
| 0-69 | low | Metric hidden from dashboards |

## Worked example (step by step)

Scenario: confidence of AI Review Debt for one team this sprint.

```text
Inputs:
- 80 relevant events in the last 30 days.
- Most recent event 60 minutes ago.
- 3 of 4 required fields populated (author_id missing).
- Week-over-week metric variability CV = 12%.
```

| Component | Raw input | Component score | Weighted |
|---|---|---|---:|
| Volume | 80 events | min(100, 80) = 80 | 24.0 |
| Freshness | 60 min | 100 (0-6h band) | 25.0 |
| Completeness | 3/4 | 75 | 18.75 |
| Stability | CV 12% (10-30% band) | 75 | 15.0 |
| Total | | | 82.75 |

```text
Confidence Score ≈ 83 → Medium. The metric may inform warnings and recommendations, but may not block.
Stored in metric_snapshots: data_confidence_score = 83, data_confidence = "medium", confidence_issue = "completeness: author_id missing, stability CV 12%".
```

## Score bands, badges and behaviour

| Score | Badge | System behaviour |
|---:|---|---|
| 90-100 | High | Metric shown prominently (bold). Blocking and warning actions are permitted. |
| 70-89 | Medium | Metric shown muted (greyed). Warnings allowed; blocking is forbidden. |
| 0-69 | Low | Metric is not shown on team or role dashboards. A "Data Quality Alert" is sent to the Data Steward only. |

```text
Blocking enforcement requires High confidence (>= 90).
Warnings and recommendations require at least Medium (>= 70).
Below 70, the metric is withheld from decision-makers and surfaced only as a data-quality problem to fix.
```

This is deliberately stricter than a single "70" gate: 70-89 can inform but never block, and below 70 the metric disappears from operational views rather than appearing as a weak trend.

## Degradation: what lowers the score, and the response

| Trigger | Components hit | Response |
|---|---|---|
| Webhook outage / missed events | Freshness, Completeness | Score falls; backfill from provider history; recompute; annotate the affected window |
| Provider/Jira integration broken | Completeness, Volume | Linked metrics (defects, validation cost) drop; mark them stale until restored |
| New repository or new team | Volume, Stability | Low volume and high variability keep the score Low until enough history accrues |
| Manual or self-reported input | Completeness, Stability | Stays Low/Medium by design; never blocks |
| Metric swings week to week | Stability | High CV caps the score; investigate before acting |

Automatic degradation and recovery rule:

```text
When a metric's Confidence Score drops below 70, any automated policy that depends on it is disabled automatically.

Recovery options (configurable per policy):
  Option A (recommended): Policy auto-re-enables when confidence returns to >= 90 for 2 consecutive evaluation cycles.
    - Data Steward receives a notification: "Policy <name> re-enabled for team <team>; confidence recovered."
  Option B: Policy stays disabled until manually re-enabled by a platform administrator.
    - Data Steward receives a daily reminder until the policy is re-enabled or the data issue is resolved.

Default: Option A for warnings and recommendations; Option B for blocking enforcement (extra caution).
```

This autonomic behaviour — the system removes its own authority when its data is weak — is cross-referenced in `docs/governance-and-privacy.md` and enforced by the policy engine (`docs/risk-policy-engine.md`). It is a strong audit argument: the platform cannot act on data it does not trust.

## Presentation tiers (measurability axis)

A complementary axis to the score: how directly a metric is measured at all.

| Tier | Examples | UI treatment |
|---|---|---|
| Directly measurable | PR cycle time, review wait time, changed files/LOC, reviewer count, CI pass/fail | Show as fact |
| Estimated | Human Validation Cost, AI Efficiency Signal, Cognitive Load Index | Show with confidence label and method note |
| Trend-only | Ownership confidence, trust calibration, exact productivity gain | Show as direction over several sprints; never for enforcement |
