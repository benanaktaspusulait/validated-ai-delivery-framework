# Phase 1 — Data Architecture and Raw Collection

GitHub and Jira collectors, raw event store, ETL into normalized tables, Data Confidence Score job.

## Tasks

| ID | Task | Owner | Est. |
|---|---|---|---|
| T1.1 | [GitHub webhook handler](tasks/T1.1-github-webhook-handler.md) | Platform Engineer | 3d |
| T1.2 | [GitHub backfill script](tasks/T1.2-github-backfill.md) | Platform Engineer | 2d |
| T1.3 | [Jira webhook handler](tasks/T1.3-jira-webhook-handler.md) | Platform Engineer | 2d |
| T1.4 | [Jira backfill script](tasks/T1.4-jira-backfill.md) | Platform Engineer | 1d |
| T1.5 | [ETL pipeline](tasks/T1.5-etl-pipeline.md) | Platform Engineer | 3d |
| T1.6 | [Data Confidence Score job](tasks/T1.6-data-confidence-job.md) | Platform Engineer | 2d |
| T1.7 | [GET /api/v1/teams endpoint](tasks/T1.7-teams-endpoint.md) | Platform Engineer | 1d |
| T1.8 | [Admin shell (SSO + integration connect)](tasks/T1.8-admin-shell.md) | Platform Engineer | 3d |
| T1.9 | [Nightly reconciliation job](tasks/T1.9-reconciliation-job.md) | Platform Engineer | 1d |
| T1.10 | [Collector tests](tasks/T1.10-collector-tests.md) | Platform Engineer | 2d |
| T1.11 | [Observability counters](tasks/T1.11-observability-counters.md) | Platform Engineer | 1d |

## Gate

```text
All exit criteria in phase-packages/phase-1-data-architecture-and-raw-collection.md must pass.
```
