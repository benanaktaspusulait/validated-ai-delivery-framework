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
