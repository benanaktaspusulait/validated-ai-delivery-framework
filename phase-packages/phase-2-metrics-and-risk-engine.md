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
Week 4: Implement fn_ai_pr_rate(), fn_review_debt_ratio(), fn_defect_rate_weighted(), fn_human_validation_cost_estimate() and fn_vdt_signal_score().
Week 5: Implement the contextual risk score (PR size, security path regex, 6-month codebase familiarity).
Week 6: Build the read-only role-based screens: Overview, Team, Pull Request Risk and Metrics Detail.
Enable the Validated Delivery Trend (VDT) signal; the optional directional cost context reads cost_config.
Calculate Cognitive Load Index using the required complexity filter.
Surface a Data Confidence label next to every metric.
```

## Implementation guidance

```text
Metric functions (implement as pure functions with unit tests):

1. fn_ai_pr_rate(team_id, sprint_window):
   Input: pull_requests where repository_id IN (team's repos)
   Logic:
     ai_count = COUNT WHERE ai_assisted = true OR ai_usage_inferred = true
     total_count = COUNT ALL (including unknown, but unknown excluded from denominator for rate)
     rate = ai_count / total_count
   Output: { metric_name: "ai_assisted_pr_rate", value: 0.34, confidence_score: 82 }
   Tests: fixture with 100 PRs, 34 AI-assisted -> rate = 0.34. Edge cases: 0 PRs, all unknown, all AI.

2. fn_review_debt_ratio(team_id, sprint_window):
   Input: pull_requests + review_analytics for team
   Logic:
     baseline = MEDIAN(first_review_at - created_at_source) WHERE ai_assisted = false
       Requires minimum 10 non-AI PRs. If below, extend window or use platform-wide baseline.
     ai_waits = AVG(first_review_at - created_at_source) WHERE ai_assisted = true
     ratio = ai_waits / baseline
   Output: { metric_name: "ai_review_debt_age_ratio", value: 1.4, confidence_score: 78 }
   Tests: fixture with known wait times. Edge: no non-AI PRs, single PR, identical waits.

3. fn_defect_rate_weighted(team_id, sprint_window):
   Input: pull_requests + jira_issues linked to merged PRs
   Logic:
     defects = COUNT jira_issues WHERE severity IN ('cosmetic','minor','medium','major','critical')
       AND linked to a merged PR in window
     weight_map = {cosmetic: 0.25, minor: 0.5, medium: 1.0, major: 2.0, critical: 5.0}
     weighted_sum = SUM weight_map[severity] for each defect
     rate = weighted_sum / merged_pr_count
   Output: { metric_name: "weighted_defect_rate", value: 0.85, confidence_score: 71 }
   Tests: fixture with known defects. Edge: 0 defects, 0 merged PRs.

4. fn_human_validation_cost(team_id, sprint_window):
   Input: review_analytics + cost_config
   Logic:
     For each AI PR: estimated_hours = base_estimate + comment_factor + change_request_factor + re_review_factor + size_factor + risk_factor
     total_cost = SUM estimated_hours * reviewer_hourly_cost
   Output: { metric_name: "human_validation_cost", value: 4200.0, confidence_score: 65, confidence_issue: "directional_proxy" }
   Note: Directional for MVP. Display with "Directional" label.

5. fn_vdt_signal_score(team_id, sprint_window_90d):
   Input: pull_requests (AI vs non-AI cohorts, matched by team/size/type)
   Logic:
     lt_diff = MEAN(non_ai_lead_time) - MEAN(ai_lead_time)
     defect_diff = MEAN(non_ai_defect_rate) - MEAN(ai_defect_rate)
     signal = (lt_diff / STDEV(non_ai_lead_time)) + (defect_diff / STDEV(non_ai_defect_rate))
   Output: { metric_name: "vdt_signal_score", value: 0.8, confidence_score: 74, ci_lower: 0.2, ci_upper: 1.4 }
   Display: trend chart only (per-sprint, 90-day rolling), never a single KPI number.
   Disclaimer: "This chart shows a correlational trend. It cannot be used as a causal ROI calculation."

Risk scoring function:

6. fn_contextual_risk(pr_id):
   Input: pull_requests row + repository metadata
   Logic:
     Read 10 factors from the PR: task_type, developer_experience, domain_criticality,
     changed_lines, security_sensitivity, cross_team_impact, reviewer_load,
     codebase_familiarity, change_freshness, ownership_boundary.
     Each factor scored per docs/risk-policy-engine.md tables.
     Risk = SUM (factor_score * weight) where weights sum to 1.0.
   Output: { risk_score: 7, signals: ["large_pr", "security_path"], required_reviewers: [...] }
   Tests: fixture with known factor values. Boundary: score 0, score 15+.

Cognitive Load Index:

7. fn_cognitive_load(team_id, sprint_window):
   Input: pull_requests + review_analytics
   Logic:
     Filter: changed_lines > 20 AND changed_files > 1
     ai_ratio = AVG(review_time / changed_lines) WHERE ai_assisted = true
     non_ai_ratio = AVG(review_time / changed_lines) WHERE ai_assisted = false
     index = ai_ratio / non_ai_ratio
   Output: { metric_name: "cognitive_load_index", value: 1.2 }

Dashboard screens (read-only, Phase 2):

8. Overview Dashboard:
   - Cards for each of the 5 core metrics + Cognitive Load Index.
   - Each card shows: current value, trend arrow (up/down/flat), data confidence badge.
   - VDT shown as a 90-day trend chart (per-sprint granularity), not a single number.

9. Team Dashboard:
   - Table of all teams with: AI PR rate, review debt, defect rate, VDT signal, confidence.
   - Sortable and filterable.

10. PR Risk View:
    - Table of recent PRs with: title, author, AI-assisted (yes/no/inferred), LOC, risk score, signals.
    - Click to expand: full risk breakdown, required reviewers.

11. Metrics Detail View:
    - Drill-down for any metric: raw inputs, baseline method, calculation version, confidence breakdown.
    - Shows the 4 confidence components (volume, freshness, completeness, stability) as a bar.

Cost configuration:

12. Load cost_config from config_versions table (or environment for pilot):
    - blended_hourly_rate: 70.0
    - reviewer_hourly_cost: 70.0
    - senior_opportunity_cost_rate: 110.0
    - tooling_cost_allocation_per_pr: 20.0
    Store in config_versions table. UI settings page for Platform Lead to update.

Observability:
    - metric_calculations_total (by metric_name, calculation_version, result)
    - metric_calculation_failures_total (by metric_name, error_class)
    - confidence_band_distribution (gauge: High/Medium/Low counts)
    - metric freshness lag (source event -> snapshot creation)
    - Alert when a core metric has no successful calculation for > 24 hours.
    - Alert when > 30% of metrics are in Low confidence.
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
docs/metrics-catalogue.md        - five core metrics formulas, AI usage classification, cohort rules, minimum sample sizes, VDT bucketing and confidence intervals
docs/risk-policy-engine.md       - contextual risk score weights, factor tables, worked examples (read-only in this phase)
docs/data-confidence.md          - 4-component scoring, freshness decay, score bands, auto-disable/recovery
docs/ui-ux-spec.md               - the four read-only screens, examples, empty/low-confidence states, Security view
docs/api-spec.md                 - Phase 2 endpoints (GET /teams/{id}/metrics, GET /pull-requests/{id}, GET /pull-requests/{id}/risk, GET /teams/{id}/recommendations), pagination, error codes
docs/data-model.md               - metric_snapshots table, cost_config, config_versions, metric_lineage
docs/testing-and-observability.md - Phase 2 tests (unit, data quality, load), observability signals, canary rollout strategy
examples/team-config.yaml        - team configuration with risk and WIP settings
```
