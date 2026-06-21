# Phase 2 - Metrics and Risk Engine

## Purpose

```text
Turn clean data into read-only insight.
No developer-facing warnings or blockers are enabled yet.
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
Metrics and risk: five core metric functions, risk scoring, Net AI Delivery Value, Cognitive Load Index (dominant track).
Engineering: read-only dashboards, metric_snapshots population, cost-input configuration.
Governance and reporting: confidence labels surfaced next to every metric.
Culture and people: pilot-team lead reality-check of the read-only dashboard. No developer-facing UI yet.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Core metric functions | Platform Engineer | Platform Lead | Data scientist | Engineering Manager |
| Risk scoring function | Platform Engineer | Platform Lead | Tech Lead, Security Lead | Engineering Manager |
| Net AI Delivery Value calculator | Platform Engineer | Platform Lead | Engineering Manager | Executive sponsor |
| Read-only dashboards | Platform Engineer | Platform Lead | Pilot-team EM | Pilot team |
| Dashboard reality validation | Pilot-team EM | Platform Lead | Tech Lead | Platform team |

## Entry Criteria

```text
Phase 1 exit criteria passed.
Core PR and review analytics tables are populated.
Data confidence is at least 75.
```

## Actions

```text
Week 4: Implement fn_ai_pr_rate(), fn_review_debt_ratio() and fn_defect_rate_weighted().
Week 5: Implement risk score using PR size, security path regex and codebase familiarity based on 6-month commit counts.
Week 6: Prepare read-only executive and team dashboards.
Enable Net AI Delivery Value with cost inputs read from configuration.
Calculate Cognitive Load Index using changed_lines > 20 and changed_files > 1.
Build the read-only role-based screens: Overview, Team, Pull Request Risk and Metrics Detail.
```

## Technical Specifications

### Five core metrics

AI-assisted PR Rate:

```text
AI-assisted PR Rate = AI-assisted PR count / Total PR count
```

For MVP an AI-assisted PR is identified by explicit PR metadata and labels.

AI Review Debt:

```text
AI Review Debt Age Ratio = Average AI PR Review Wait Time / Normal PR Review Wait Time Baseline
Baseline = median first-review wait time for non-AI PRs over the last 3-6 sprints (use median, not average).
```

| Ratio | Meaning | Action |
|---:|---|---|
| < 1.0 | Healthy | Continue |
| 1.0 - 1.5 | Watch | Monitor |
| 1.5 - 2.0 | Warning | Add reviewer capacity |
| > 2.0 | Critical | Reduce AI WIP / split PRs |

Post-merge defect rate:

```text
Post-Merge Defect Rate = Defects linked to merged PRs / Merged PR count
Weighted Defect Rate = Σ Defect Severity Weight / Merged PR count
```

| Severity | Weight |
|---|---:|
| Cosmetic | 0.25 |
| Minor | 0.5 |
| Medium | 1.0 |
| Major | 2.0 |
| Critical | 5.0 |

Always compare AI-assisted PRs against comparable non-AI PRs, not against all PRs.

Human Validation Cost:

```text
Human Validation Cost = Review Hours x Reviewer Hourly Cost
Review Hours (MVP proxy) = approval timestamp - first review timestamp
```

Engagement signals raise the estimate: comments left, changes requested, review threads opened, multiple review rounds. Approval without comments lowers confidence. Treat this metric as directional for MVP and calibrate it in Phase 3.

Net AI Delivery Value:

```text
Net AI Delivery Value =
  Gross AI Time Saving Value
- Human Validation Cost
- Rework Cost
- Defect Cost
- Tooling Operational Cost
- Senior Opportunity Cost
- Adoption Friction Cost
+ Counterfactual Value of Redirected Cognitive Capacity

Gross AI Time Saving Value = (Estimated Manual Baseline Effort - AI-assisted Effort) x Blended Hourly Rate
```

Show AI ROI alongside it:

```text
AI ROI = (Gross AI Time Saving Value + Counterfactual Value) / Total AI Delivery Cost
```

Counterfactual value is directional only in this phase. Cap it at the estimated gross AI time saving until stronger evidence exists, and never use self-reported counterfactual value for hard enforcement.

### Risk scoring

```text
AI Contextual Risk Score =
  Task Type Weight
+ Developer Experience Weight
+ Domain Criticality Weight
+ Change Size Weight
+ Security Sensitivity Weight
+ Cross-team Impact Weight
+ Reviewer Load Weight
+ Codebase Familiarity Weight
+ Change Freshness Weight
+ Ownership Boundary Weight
```

| Factor | Low | Medium | High |
|---|---:|---:|---:|
| Documentation | 1 | - | - |
| Test generation | 2 | 3 | - |
| Internal tooling code | 2 | 3 | 4 |
| Business logic | 3 | 4 | 5 |
| Payment/auth/security | 5 | 7 | 10 |
| Cross-service contract | 5 | 7 | 10 |
| Codebase familiarity | Many local examples | Some comparable patterns | Few or no comparable examples |
| Change freshness | Stable area | Modified within 30 days | High churn or recently refactored |
| Ownership boundary | Same team | Shared module | Multiple teams or service owners |

Codebase familiarity proxy (implement here):

```text
Compare PR file paths to commits from the previous 6 months.
Low familiarity:    fewer than 5 commits in a similar path area.
Medium familiarity: 5-20 commits in a similar path area.
High familiarity:   more than 20 commits in a similar path area.
```

Change freshness defaults:

```text
Change Freshness Score =
  (code_modified_within_30_days ? 1 : 0)
+ (file_with_high_churn_history ? 2 : 0)
+ (file_modified_by_multiple_teams ? 2 : 0)

High churn history = file modified more than 10 times in the last 3 months.
Multiple teams = file modified by developers from more than 2 teams in the last 6 months.
```

| Score | Meaning | Action |
|---:|---|---|
| 0 | Stable area | Normal risk weighting |
| 1-2 | Some recent activity | Add reviewer context or local pattern check |
| 3+ | High churn or ownership conflict | Require explicit owner review and consider splitting PR |

### Cognitive Load Index

```text
Cognitive Load Index =
(AI PR Review Time / AI PR Size) / (Baseline Review Time for Non-AI PRs / Baseline Non-AI PR Size)
```

| Index | Meaning | Action |
|---:|---|---|
| < 0.8 | AI PRs appear easier to review | Validate sample quality; do not assume this is healthy |
| 0.8 - 1.5 | Comparable to non-AI PRs | Continue monitoring |
| > 1.5 | AI PRs are harder to review | Split PRs, improve prompts, require clearer explanations |

Complexity filter (required) and stratification:

```text
Only include PRs where changed_lines > 20 AND changed_files > 1.
Compare AI and non-AI PRs within similar buckets: small feature, medium feature, test-only, refactoring, security-sensitive.
```

### Confidence handling for this phase

```text
Surface each metric's confidence label next to the value.
Metrics below 70 confidence are trend-only and must not drive any future enforcement.
Store metric value, data_confidence_score and confidence_issue in metric_snapshots.
```

### Role-based read-only UI (MVP screens 1-4)

```text
Overview Dashboard: AI-assisted PR rate, AI Review Debt, Net AI Delivery Value, post-merge defect rate, Human Validation Cost, Data Confidence Score.
Team Dashboard: per-team comparison of adoption, review debt, defect trend, net value and risk band.
Pull Request Risk View: per-PR AI flag, LOC, files, critical-path flag, reviewer load, Cognitive Load Index, risk score, triggered signal, recommended action.
Metrics Detail View: drill-down for any metric showing its inputs, baseline and confidence label.
```

Example Overview card row:

| Metric | Current | Trend | Status |
|---|---|---|---|
| AI-assisted PR Rate | 34% | up | Healthy |
| AI Review Debt | 12 PRs | up | Warning |
| Net AI Delivery Value | £4,800 | up | Good |
| Post-merge Defect Rate | 1.2x baseline | up | Watch |
| Data Confidence | 82% | flat | Good |

Example Pull Request Risk row:

| PR | AI | LOC | Risk | Signal | Recommendation |
|---|---|---:|---|---|---|
| Add payment retry logic | Yes | 420 | High | Large AI PR + payment domain | Split PR + senior reviewer |
| Update docs | Yes | 80 | Low | None | Normal review |
| Auth token refactor | Yes | 260 | Critical | Security-sensitive files | AppSec review required |

UI guardrails:

```text
Team-level by default. No individual developer ranking, leaderboard or "who uses AI most" view.
Every metric shows its data confidence. Never present an estimate as a precise fact.
Wrong: "AI saved £12,400 this sprint."
Right: "Estimated AI delivery value: £12,400. Data confidence: Medium."
```

### Seven robust MVP metrics (prioritise for display)

```text
1. AI-assisted PR Rate        (metadata-driven)
2. AI Review Debt             (open AI PRs awaiting review)
3. AI Review Debt Age Ratio   (AI wait time / non-AI wait time)
4. PR Size Risk               (changed lines + files + critical paths)
5. Review Load                (open PRs per reviewer)
6. Post-Merge Defect Linkage  (bugs linked to a merged PR's component/story/release)
7. Policy Violations          (missing metadata, large PR, missing reviewer, coverage drop)
```

### Measurement confidence tiers

| Tier | Examples | UI treatment |
|---|---|---|
| Directly measurable | PR cycle time, review wait time, changed files/LOC, reviewer count, CI pass/fail | Show as fact |
| Estimated | Human Validation Cost, Net AI Delivery Value, Cognitive Load Index | Show with confidence label and method note |
| Trend-only | Ownership confidence, trust calibration, exact productivity gain | Show as direction over several sprints; never for enforcement |

### Causation vs correlation guardrails

```text
AI-assisted work may cluster in easy tasks, which can make AI look faster than it is.
To reduce this distortion, compare AI and non-AI PRs within matched cohorts:
- similar story points
- similar domain / criticality
- similar PR size
- similar developer seniority
- same team
- across several sprints, not a single sprint
```

Product promise language:

```text
The system does not claim exact causation. It produces decision-grade operational signals about
the speed, review load, quality risk and economic impact of AI-assisted delivery.
```

### Measurement confidence at this phase

```text
Layer: AI usage metadata plus computed metrics and risk.
Confidence: medium-high for observable metrics; estimated for value and cost; trend-only for ownership and trust.
Use: read-only insight and reality validation. No enforcement.
```

## Deliverables

```text
[Phase2]_exit_report.pdf
[Phase2]_data_dictionary.json
[Phase2]_config_changes.yaml
Five core metric functions
Risk scoring function
Read-only pilot dashboard
Net AI Delivery Value calculator
Cognitive Load Index calculation
Overview, Team, Pull Request Risk and Metrics Detail read-only screens
Measurement confidence tiering applied to every metric
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

## Master Reference Map

```text
Section 7.2  - Metrics engine (MVP metrics)
Section 7.3  - Risk engine, weights and proxies
Section 10   - Core metric definitions (10.1-10.8)
Section 11   - Dynamic AI WIP limit base formula (consumed in Phase 4)
Section 18   - Data confidence rules
Section 21   - MVP backlog: Epic 3 (Metrics) and Epic 4 (Risk)
```
