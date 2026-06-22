# Phase 1 - Data Architecture and Raw Collection

Operating mode: Observation Mode. Collect source data without interpreting it, warning developers or blocking PRs.

## Purpose

```text
Collect source data reliably. No metrics, warnings or blocks.
```

## Duration

```text
3 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Data Steward
Pilot-team Engineering Manager
```

## Work Tracks (this phase)

```text
Engineering: GitHub and Jira collectors, raw event store, ETL into normalized tables, /api/v1/teams stub (dominant track).
Metrics and risk: Data Confidence Score batch job only. No metric interpretation yet.
Governance and reporting: Data Steward signs off the live data sources and confirms retention is wired in.
Culture and people: none. The platform stays invisible to developers in this phase.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| GitHub App install and event ingestion | Platform Engineer | Platform Lead | Security Lead | Pilot-team EM |
| Jira connection and issue/sprint mapping | Platform Engineer | Platform Lead | Pilot-team EM | Data Steward |
| ETL into pull_requests and review_analytics | Platform Engineer | Platform Lead | Data Steward | Engineering Manager |
| Data Confidence Score job | Platform Engineer | Platform Lead | Data Steward | Platform Lead |
| Data source sign-off | Data Steward | Platform Lead | Legal/privacy counsel | Engineering Manager |

## Entry Criteria

```text
Phase 0 exit criteria passed.
GitHub and Jira service accounts are available.
Pilot repositories and Jira projects are identified.
```

## Actions

```text
Week 1: Install the GitHub App and store pull_request, pull_request_review and pull_request_review_comment events as raw JSON in raw_events.
Week 2: Connect Jira and collect issue, issue link and sprint/team metadata.
Week 3: Build ETL to populate pull_requests and review_analytics from raw events.
Build the Data Confidence Score batch job for each metric.
Expose /api/v1/teams even if the response is initially sparse.
Stand up the admin shell: SSO login plus the integration connect screen (GitHub org, Jira project, team-to-repo mapping).
```

## Implementation guidance

```text
Tech stack (reference — adapt to your organisation):
  Backend: Java 21 + Quarkus (or your preferred framework)
  Database: PostgreSQL
  Queue: Kafka or RabbitMQ (or in-memory queue for pilot)
  Auth: Keycloak SSO (or your existing SSO)
  Deployment: Docker Compose for pilot

Project structure suggestion:
  src/
    main/
      collectors/          - GitHub and Jira webhook handlers
      storage/             - raw_events, normalized table writers
      confidence/          - Data Confidence Score batch job
      api/                 - REST endpoints
      config/              - Configuration service (cost_config, team-config)
    test/
      collectors/          - Unit tests with recorded fixtures
      storage/             - Integration tests
      api/                 - API tests

Week 1 — GitHub Collector:
  1. Create the GitHub App (from Phase 0 setup). Install it on the pilot org.
  2. Implement POST /api/v1/webhooks/github:
     - Validate HMAC-SHA256 signature (X-Hub-Signature-256 header).
     - Parse event type from X-GitHub-Event header.
     - Write raw payload to raw_events (idempotency key: sha256 of body + event_type + timestamp).
     - Return 202 Accepted.
  3. Subscribe to events: pull_request, pull_request_review, pull_request_review_comment.
  4. Handle rate limits: GitHub returns 403 with X-RateLimit-Remaining. Implement exponential backoff.
  5. Build backfill script: use GitHub REST API to fetch last 30 days of PRs for pilot repos.
     Endpoint: GET /repos/{owner}/{repo}/pulls?state=all&since={30_days_ago}
     Store each as a raw_event with event_type = "backfill_pull_request".
  6. Write unit tests with recorded GitHub webhook payloads (store in test/fixtures/).

Week 2 — Jira Collector:
  1. Create Jira API token for the service account.
  2. Implement POST /api/v1/webhooks/jira:
     - Validate Atlassian-Connect-Token header.
     - Parse webhook payload (issue_created, issue_updated, sprint_started, sprint_completed).
     - Write raw payload to raw_events.
     - Return 202 Accepted.
  3. Use Jira REST API for backfill:
     GET /rest/api/3/search?jql=project={key}&maxResults=100
     Fetch issues, links, sprints and team assignments.
  4. Store Jira issue metadata in jira_issues table.
  5. Link Jira issues to PRs via commit messages or branch names (heuristic: branch name contains Jira key).
  6. Unit tests with recorded Jira webhook payloads.

Week 3 — ETL and Normalized Tables:
  1. Build ETL pipeline (batch job or event-driven):
     raw_events -> pull_requests: extract PR metadata (title, author, files, lines, timestamps, AI metadata from PR template)
     raw_events -> review_analytics: extract review timestamps, comments, threads, reviewer count
     raw_events -> jira_issues: extract issue metadata, link to PRs
  2. AI metadata extraction from PR body:
     - Parse checkboxes: "[x] AI used for code suggestions" -> ai_usage_types = ["code_suggestions"]
     - Parse "[x] No AI assistance used" -> ai_assisted = false
     - Parse confidence: "[x] High" -> ai_assistance_confidence = "high"
     - If neither declared, set ai_assisted = NULL (unknown — excluded from both cohorts)
  3. Build Data Confidence Score batch job:
     - For each team and metric: compute volume, freshness, completeness, stability.
     - Use piecewise freshness decay (0-6h: 100, 6-12h: 75, 12-24h: 50, 24-48h: 25, 48h+: 0).
     - Store result in metric_snapshots with data_confidence_score and data_confidence label.
     - Run nightly via cron or scheduled job.
  4. Expose GET /api/v1/teams:
     - Query teams table, return list with basic info.
     - Include data_confidence_score for each team.
  5. Build admin shell:
     - SSO login page (Keycloak integration).
     - Integration connect screen: GitHub org selector, Jira project key input, team-to-repo mapping form.
     - Store mapping in teams + repositories tables.
  6. Observability:
     - collector_events_received_total (by provider, event_type)
     - collector_processing_failures_total (by provider, error_class)
     - raw_events_dead_letter_total
     - source_reconciliation_gap_percent (nightly comparison)
     - Alert when gap > 5% or webhook lag > 1 hour.
  7. Nightly reconciliation job:
     - Compare GitHub API PR count vs local pull_requests count for last 30 days.
     - Compare Jira issue count vs local jira_issues count.
     - Log discrepancy as source_reconciliation_gap_percent.
```

## Deliverables

```text
[Phase1]_exit_report.pdf
[Phase1]_data_dictionary.json
[Phase1]_config_changes.yaml
Working GitHub event ingestion
Working Jira event ingestion
Raw event store
Normalized pull_requests and review_analytics tables
Initial Data Confidence Score job
Admin SSO login and integration connect/team-mapping screen
Collector integration tests, webhook replay test and 30-day backfill evidence
Collector observability counters and reconciliation dashboard
```

## Exit Criteria

```text
At least 90% of PRs opened in the last 30 days exist in the database with title, author, files and timestamps.
Average Data Confidence Score is at least 75.
/api/v1/teams responds successfully.
Webhook replay does not duplicate raw_events or normalized records.
Nightly source reconciliation gap is below 5%.
No PR warnings or blockers have been enabled.
```

## Fail Criteria

```text
GitHub or Jira API limits prevent reliable collection.
More than 20% of required fields are empty.
Data Confidence Score remains below 75 after remediation.
```

## Fail Action

```text
Extend by 1 week.
Fix connector pagination, retry logic, rate limiting or field mapping.
Do not proceed to metrics interpretation until source data is reliable.
```

## Gate Decision

```text
Proceed to Phase 2 only when the platform team trusts the raw data layer and can explain the Data Confidence Score.
```

## Reference Docs

```text
docs/architecture.md             - system flow, reference stack, collector interface, connector reliability, backpressure, webhook security
docs/data-model.md               - full schema (raw_events, pull_requests, review_analytics, jira_issues, repositories, teams, metric_snapshots), indexes, ON DELETE behaviour, audit_log
docs/data-confidence.md          - Data Confidence Score methodology, 4-component formula, freshness decay, score bands
docs/api-spec.md                 - Phase 1 endpoints (GET /teams, POST /webhooks/github, POST /webhooks/jira), error codes, pagination
docs/testing-and-observability.md - Phase 1 collector tests (unit, integration, contract, load), observability signals
examples/team-config.yaml        - team configuration template
```
