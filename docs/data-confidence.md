# Data Confidence

Canonical method for scoring how much each metric can be trusted. Implemented as a batch job in Phase 1 and applied by every later phase.

## Formula

```text
Data Confidence Score = 100 - (Derivation Penalty + Coverage Penalty + Timeliness Penalty)
```

Derivation penalty:

| Source method | Penalty |
|---|---:|
| Direct instrumented source | 0 |
| Derived from timestamps | 15 |
| Derived from linked data | 25 |
| Manual input | 40 |

Coverage penalty:

| Coverage state | Penalty |
|---|---:|
| All expected events captured | 0 |
| Minor gaps under 5% | 5 |
| Significant gaps from 5-20% | 15 |
| Major gaps over 20% | 30 |

Timeliness penalty:

| Freshness | Penalty |
|---|---:|
| Real-time | 0 |
| Under 1 hour delay | 5 |
| Under 1 day delay | 10 |
| Over 1 day delay | 20 |

Worked example:

```text
Review debt from GitHub timestamps with minor webhook gaps and under 1 hour delay:
100 - (15 + 5 + 5) = 75
```

## Decision rule

```text
>= 70  : may drive hard enforcement (Phase 4).
50-69  : may warn, prompt review or trend-monitor; no blocking.
< 50   : trend-only; label "not suitable for decision-making".
```

Every metric must carry both a 0-100 score and a label, stored in `metric_snapshots` (`data_confidence_score`, `data_confidence`, `confidence_issue`).

## Confidence tiers (how the value is presented)

| Tier | Examples | UI treatment |
|---|---|---|
| Directly measurable | PR cycle time, review wait time, changed files/LOC, reviewer count, CI pass/fail | Show as fact |
| Estimated | Human Validation Cost, Net AI Delivery Value, Cognitive Load Index | Show with confidence label and method note |
| Trend-only | Ownership confidence, trust calibration, exact productivity gain | Show as direction over several sprints; never for enforcement |

## Worked example (step by step)

Scenario: computing the confidence of AI Review Debt for one team over one sprint.

```text
Input situation:
- Source: GitHub review timestamps (not active-time telemetry).
- Coverage: 3 of 60 expected review events were missed due to a brief webhook outage (5%).
- Freshness: events arrive within about 40 minutes.
```

| Step | Decision | Penalty |
|---|---|---:|
| 1. Derivation | Derived from timestamps, not instrumented active time | 15 |
| 2. Coverage | 5% missed events sits at the boundary of the "minor gaps under 5%" band; treat as minor | 5 |
| 3. Timeliness | Under 1 hour delay | 5 |
| 4. Sum penalties | 15 + 5 + 5 | 25 |
| 5. Score | 100 - 25 | 75 |

```text
Result: Data Confidence Score = 75, label medium-high, decision-grade (>= 70).
Recorded in metric_snapshots with a confidence_issue note: "review time derived from timestamps; 5% event gap this sprint".
```

If the same metric had used a manual estimate (penalty 40) with a 25% coverage gap (penalty 30) and a one-day delay (penalty 10), the score would be 100 - 80 = 20: trend-only, not suitable for decisions.

## When confidence drops, and what to do

| Trigger | Effect on score | Action |
|---|---|---|
| Too little data (new team, few PRs) | High coverage uncertainty | Show an empty/low-data state; wait for more sprints before interpreting |
| Stale data (collection lag) | Timeliness penalty rises | Investigate collector lag; flag the metric as delayed until caught up |
| Webhook outage / missed events | Coverage penalty rises | Backfill from provider history; recompute; annotate the affected window |
| Manual or self-reported input | Derivation penalty rises | Keep the metric trend-only; never use it for enforcement |
| Reconciliation gap above 5% | Coverage penalty rises | Treat dependent metrics as directional until the gap closes |

```text
A metric that drops below 70 automatically loses enforcement rights until confidence recovers.
A metric below 50 is labelled "not suitable for decision-making" in the UI.
```
