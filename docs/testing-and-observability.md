# Testing and Observability

Canonical test-strategy and observability requirements per phase. Phase gates cannot pass on narrative confidence alone; they require the evidence below.

## Programme rule

```text
Every phase with implementation work must include test evidence.
Every production-facing collector, metric job, policy rule and dashboard state must expose observability signals.
Gates require source-data checks, calculation checks or policy regression checks appropriate to the phase.
```

## Platform SLOs

```text
Webhook ingestion: p99 latency < 5 seconds from received_at to raw_events write.
Metric calculation: p95 latency < 30 seconds per team per sprint window.
API response time: p95 < 200ms for read endpoints, < 500ms for write endpoints.
Data freshness: metric snapshots updated within 1 hour of source event.
Availability: 99.5% uptime during business hours (defined per team timezone).
Error budget: 0.5% = ~2.2 hours downtime per month; breach triggers reliability review.
```

## Security testing

```text
Dependency scanning: run Snyk or Dependabot on every build; fail on critical/high vulnerabilities.
RBAC bypass tests: for each role (Platform Admin, EM, Tech Lead, Developer), verify that unauthorized endpoints return 403.
Webhook signature forgery: test GitHub HMAC-SHA256 validation with invalid/missing/tampered signatures.
SQL injection: parameterised query tests on all API endpoints that accept user input.
XSS testing: verify all API responses escape HTML/script content from PR titles and bodies.
Penetration test: required before Phase 5 enterprise rollout; repeat annually.
```

## Data quality testing

```text
Metric calculation correctness: unit-test each of the five core metrics with fixed input fixtures and known-answer outputs.
Confidence score boundary tests: verify behaviour at exact boundaries (69, 70, 89, 90).
Cost calculation consistency: same inputs must produce identical outputs across runs (deterministic).
Regression snapshot tests: store expected metric values for fixture datasets; fail on unexplained changes.
Non-AI PR classification: verify that unknown/inferred PRs are excluded from non-AI cohorts.
VDT confidence interval: verify CI calculation with known mean and stddev inputs.
```

## Contract testing for collectors

```text
Consumer-driven contract tests (e.g. Pact) against recorded GitHub and Jira webhook payloads.
Schema registry: maintain expected payload schemas for each provider event type.
Alert when a received payload deviates from the expected contract (possible provider API change).
Store raw webhook payloads as test fixtures; update fixtures when providers change their API.
```

## Phase 1 — collectors

Tests:

```text
Unit-test GitHub and Jira payload parsers with recorded fixtures.
Integration-test webhook receipt -> raw_events write -> normalized table update.
Regression-test webhook replay idempotency using duplicate provider events.
Backfill-test the previous 30 days of PR and Jira data for the pilot team.
Failure-test retry, dead-letter and processing_error paths.
Contract-test collector against recorded provider payloads.
Load-test ingestion: sustained 100 webhooks/second for 10 minutes.
```

Observability:

```text
collector_events_received_total by provider and event_type.
collector_processing_failures_total by provider and error class.
raw_events_dead_letter_total and retry_attempts_total.
source_reconciliation_gap_percent (nightly).
webhook lag from source_timestamp to received_at.
ingestion_throughput_per_second (gauge).
ingestion_latency_p99 (histogram).
Alert when reconciliation gap exceeds 5% or webhook lag exceeds 1 hour.
Alert when ingestion throughput drops below 50 webhooks/second.
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
Data quality: verify metric calculation correctness with known-answer fixtures.
Data quality: verify confidence score boundary behaviour (69, 70, 89, 90).
Load-test metric calculation: 50 teams x 1000 PRs per sprint must complete within 5 minutes.
```

Observability:

```text
metric_calculations_total by metric_name, calculation_version and result.
metric_calculation_failures_total by metric_name and error class.
low_confidence_metrics_total when Data Confidence Score < 70.
dashboard_empty_state_total by metric and reason.
metric freshness lag from source event to snapshot creation.
confidence_score_recalculations_total by metric.
confidence_band_distribution gauge (High/Medium/Low counts per team).
Alert when a core metric has no successful calculation for more than 24 hours.
Alert when more than 30% of metrics are in Low confidence band.
```

## Metric calculation canary rollout

```text
When calculation_version changes:
1. Run old and new calculation in parallel (shadow mode) for 1 sprint.
2. Compare results: if divergence > 5% on any core metric, block the rollout.
3. If divergence is acceptable, switch to the new version.
4. Keep the old calculation logic for 2 sprints as rollback option.
5. Rollback: revert to previous calculation_version, recalculate affected metric_snapshots, notify consumers.
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
Policy precedence: verify emergency override bypasses all blocking policies.
Policy precedence: verify all blocking policies must pass (AND logic) for merge.
Override rate limiting: verify soft limit (3) and hard limit (5) per sprint.
```

Observability:

```text
policy_evaluations_total by rule, action and result.
policy_blocks_total, policy_warnings_total and emergency_overrides_total.
policy_false_positive_reports_total from pilot feedback.
dynamic_ai_wip_recommendation changes with before, after and reason.
override_rate_per_sprint by team.
Alert when actionable policy alerts exceed 10 per team per week or PR bot comments exceed 20% of PRs.
Alert when emergency overrides exceed 3 per team per sprint.
```

## Phase 5 — enterprise rollout

Tests:

```text
Unit-test retention purge: detailed data deleted, aggregates preserved at the 12/24 month boundaries.
Integration-test RBAC: CTO, EM and Developer roles see only the intended data levels.
Integration-test self-service onboarding: a new team-config.yaml initialises a team in Observation Mode.
Regression-test onboarding failure paths: invalid repo mappings or missing Jira keys block onboarding with a clear error.
Load-test multi-team dashboards with 50+ concurrent team views.
Load-test end-to-end: realistic data volumes across ingestion, calculation and dashboard.
```

Observability:

```text
retention_purge_records_deleted_total by table and team.
rbac_access_denied_total by role and resource.
onboarding_success_rate and onboarding_failure_reason_total.
dashboard_load_latency_p95 for multi-team views.
support_ticket_volume_total (integrated with Jira/Zendesk if possible).
```

## Rollback and metric correction

```text
When a metric calculation bug is detected:
1. Revert to previous calculation_version.
2. Recalculate affected metric_snapshots using the corrected version.
3. Notify all consumers (dashboard banners, API response headers) that metrics have been corrected.
4. Log the correction in audit_log with old_value and new_value.
5. Post-incident review: root cause, timeline, corrective action.
```
