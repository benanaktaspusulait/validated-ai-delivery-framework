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
Governance and reporting: Data Steward signs off live data sources and confirms retention.
Culture and people: none. Platform stays invisible to developers in this phase.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| GitHub App install and ingestion | Platform Engineer | Platform Lead | Security Lead | Pilot-team EM |
| Jira connection and mapping | Platform Engineer | Platform Lead | Pilot-team EM | Data Steward |
| ETL into normalized tables | Platform Engineer | Platform Lead | Data Steward | Engineering Manager |
| Data Confidence Score job | Platform Engineer | Platform Lead | Data Steward | Platform Lead |
| Data source sign-off | Data Steward | Platform Lead | Legal/privacy counsel | Engineering Manager |

## Entry Criteria

```text
Phase 0 exit criteria passed.
GitHub and Jira service accounts available.
Pilot repositories and Jira projects identified.
```

## Actions (high-level)

```text
Week 1: Install GitHub App, store pull_request and review events as raw JSON.
Week 2: Connect Jira, collect issue and sprint metadata.
Week 3: Build ETL for pull_requests, review_analytics, jira_issues.
         Build Data Confidence Score batch job.
         Expose /api/v1/teams (initially sparse).
         Stand up admin shell (SSO + integration connect).
```

For detailed implementation steps, see [implementation/phase-1/](../implementation/phase-1/).

## Deliverables

```text
Working GitHub event ingestion
Working Jira event ingestion
Raw event store
Normalized pull_requests and review_analytics tables
Initial Data Confidence Score job
Admin SSO login and integration connect screen
Collector tests (unit, integration, contract, load)
Collector observability counters
[Phase1]_exit_report.pdf
[Phase1]_data_dictionary.json
[Phase1]_config_changes.yaml
```

## Exit Criteria

```text
>= 90% of last-30-day PRs ingested with required fields.
Average Data Confidence Score >= 75.
/api/v1/teams responds successfully.
Webhook replay does not duplicate records.
Reconciliation gap < 5%.
Webhook p99 latency < 5 seconds.
No PR warnings or blockers enabled.
```

## Fail Criteria

```text
GitHub or Jira API limits prevent reliable collection.
> 20% of required fields are empty.
Data Confidence Score remains below 75 after remediation.
```

## Fail Action

```text
Extend by 1 week.
Fix connector pagination, retry logic, rate limiting or field mapping.
Do not proceed to metrics until source data is reliable.
```

## Gate Decision

```text
Proceed to Phase 2 only when the platform team trusts the raw data layer.
```

## Reference Docs

```text
docs/architecture.md             - system flow, collector interface, backpressure, webhook security
docs/data-model.md               - full schema, indexes, ON DELETE behaviour, audit_log
docs/data-confidence.md          - 4-component formula, freshness decay, score bands
docs/api-spec.md                 - Phase 1 endpoints, error codes, pagination
docs/testing-and-observability.md - Phase 1 tests and observability signals
examples/team-config.yaml        - team configuration template
implementation/phase-1/          - detailed task breakdown (T1.1-T1.11)
```
