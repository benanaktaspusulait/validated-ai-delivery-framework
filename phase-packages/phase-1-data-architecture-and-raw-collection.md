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
docs/architecture.md             - system flow, reference stack, collector interface, connector reliability
docs/data-model.md               - full schema, cost_config, retention and identifier handling
docs/data-confidence.md          - Data Confidence Score methodology and decision rule
docs/api-spec.md                 - Phase 1 webhook and teams endpoints
docs/testing-and-observability.md - Phase 1 collector tests and signals
Master framework: sections 6, 7.1, 16, 17, 18, 21
```
