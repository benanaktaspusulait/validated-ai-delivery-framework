# Testing and Observability

Canonical test-strategy and observability requirements per phase. Phase gates cannot pass on narrative confidence alone; they require the evidence below.

## Programme rule

```text
Every phase with implementation work must include test evidence.
Every production-facing collector, metric job, policy rule and dashboard state must expose observability signals.
Gates require source-data checks, calculation checks or policy regression checks appropriate to the phase.
```

## Phase 1 — collectors

Tests:

```text
Unit-test GitHub and Jira payload parsers with recorded fixtures.
Integration-test webhook receipt -> raw_events write -> normalized table update.
Regression-test webhook replay idempotency using duplicate provider events.
Backfill-test the previous 30 days of PR and Jira data for the pilot team.
Failure-test retry, dead-letter and processing_error paths.
```

Observability:

```text
collector_events_received_total by provider and event_type.
collector_processing_failures_total by provider and error class.
raw_events_dead_letter_total and retry_attempts_total.
source_reconciliation_gap_percent (nightly).
webhook lag from source_timestamp to received_at.
Alert when reconciliation gap exceeds 5% or webhook lag exceeds 1 hour.
```

## Phase 2 — metrics and risk

Tests:

```text
Unit-test the five core metric functions with fixed input fixtures.
Unit-test AI Contextual Risk Score factors and boundary values.
Regression-test Human Validation Cost fallback proxy and confidence downgrade behaviour.
Regression-test Validated Delivery Trend (VDT) presentation: trend-chart only (never a single KPI), mandatory disclaimer present, confidence label shown.
Integration-test metric_snapshots writes, calculation_version changes and API response shapes.
Sample-test 50 PRs manually against computed AI-assisted PR Rate.
```

Observability:

```text
metric_calculations_total by metric_name, calculation_version and result.
metric_calculation_failures_total by metric_name and error class.
low_confidence_metrics_total when Data Confidence Score < 70.
dashboard_empty_state_total by metric and reason.
metric freshness lag from source event to snapshot creation.
Alert when a core metric has no successful calculation for more than 24 hours.
```

## Phase 3 — soft landing and experiment

Tests:

```text
Unit-test PR Comment Bot logic: recommendation severity maps to comment priority.
Integration-test experiment-mode assignment: random assignment correctness and persistence per PR.
Regression-test A/B analysis reproducibility: identical inputs produce consistent A/B reports.
Unit-test alert-fatigue counters: increment on delivery, reset on new sprint.
End-to-end test: high-risk PR triggers Slack/Teams notification and PR comment.
```

Observability:

```text
pr_bot_comments_total by recommendation_type and team.
experiment_assignment_total by group (treatment/control).
alert_fatigue_hits_total when team thresholds are exceeded.
time_to_comment from webhook receipt to PR comment publication.
notification_delivery_success_rate for the platform channel.
```

## Phase 4 — guardrails

Tests:

```text
Unit-test policy rule evaluation, confidence gates and Emergency Override handling.
Integration-test GitHub Actions metadata enforcement against sample PR bodies.
Integration-test high-risk reviewer assignment with representative changed-file sets.
Regression-test that low-confidence defect data cannot reduce Dynamic AI WIP or block a PR.
Regression-test that Dynamic AI WIP blocks only on High-confidence (>= 90) metrics and warns on the 70-89 band.
Exercise metadata_missing behaviour separately from inferred metric confidence (completeness can block in Enforcement Mode).
```

Observability:

```text
policy_evaluations_total by rule, action and result.
policy_blocks_total, policy_warnings_total and emergency_overrides_total.
policy_false_positive_reports_total from pilot feedback.
dynamic_ai_wip_recommendation changes with before, after and reason.
Alert when actionable policy alerts exceed 10 per team per week or PR bot comments exceed 20% of PRs.
```

## Phase 5 — enterprise rollout

Tests:

```text
Unit-test retention purge: detailed data deleted, aggregates preserved at the 12/24 month boundaries.
Integration-test RBAC: CTO, EM and Developer roles see only the intended data levels.
Integration-test self-service onboarding: a new team-config.yaml initialises a team in Observation Mode.
Regression-test onboarding failure paths: invalid repo mappings or missing Jira keys block onboarding with a clear error.
Load-test multi-team dashboards with 50+ concurrent team views.
```

Observability:

```text
retention_purge_records_deleted_total by table and team.
rbac_access_denied_total by role and resource.
onboarding_success_rate and onboarding_failure_reason_total.
dashboard_load_latency_p95 for multi-team views.
support_ticket_volume_total (integrated with Jira/Zendesk if possible).
```
