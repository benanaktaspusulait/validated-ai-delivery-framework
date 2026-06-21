# Phase 1 - Data Architecture and Raw Collection

## Purpose

```text
Collect source data without interpreting it, warning developers or blocking PRs.
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
Week 1: Install GitHub App and store pull_request, pull_request_review and pull_request_review_comment events as raw JSON in the event store.
Week 2: Connect Jira and collect issue, issue link and sprint/team metadata.
Week 3: Build ETL to populate pull_requests and review_analytics from raw events.
Build the Data Confidence Score batch job for each metric.
Expose /api/v1/teams even if the response is initially sparse.
Stand up the admin shell: SSO login plus the integration connect screen (GitHub org, Jira project, team-to-repo mapping).
```

## Technical Specifications

### Target architecture (scope for this phase: source systems through event store and operational database)

```text
Source Systems
  GitHub / GitLab
  Jira / Azure DevOps
        ↓
Connectors / Collectors
        ↓
Event Store / Operational Database   <- Phase 1 ends here
        ↓
Metrics Engine                       <- Phase 2
```

### MVP connector scope

```text
Required: GitHub + Jira.
Do not start with IDE telemetry. It increases privacy, consent and change-management complexity.
```

GitHub collection (Epic 1):

```text
Collect PR metadata.
Collect PR review timestamps.
Collect changed files and changed lines.
Collect labels.
Parse AI metadata from the PR template.
Parse AI assistance confidence.
Store webhook events.
Capture emergency override labels.
```

Jira collection (Epic 2):

```text
Collect issues.
Link Jira issues to PRs.
Identify post-merge defects.
Map severity.
Map sprint/team.
```

### Database schema to create in this phase

```sql
CREATE TABLE teams (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  domain_criticality TEXT NOT NULL DEFAULT 'medium',
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE repositories (
  id UUID PRIMARY KEY,
  team_id UUID REFERENCES teams(id),
  provider TEXT NOT NULL,
  external_id TEXT NOT NULL,
  name TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE pull_requests (
  id UUID PRIMARY KEY,
  repository_id UUID REFERENCES repositories(id),
  external_id TEXT NOT NULL,
  title TEXT,
  author_id TEXT,
  created_at_source TIMESTAMPTZ,
  merged_at_source TIMESTAMPTZ,
  closed_at_source TIMESTAMPTZ,
  changed_files INT,
  changed_lines INT,
  ai_assisted BOOLEAN DEFAULT false,
  ai_usage_types TEXT[],
  ai_assistance_confidence TEXT,
  ownership_confidence_score NUMERIC,
  codebase_familiarity TEXT,
  change_freshness_score NUMERIC,
  ownership_boundary_risk TEXT,
  risk_score NUMERIC,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE review_analytics (
  id UUID PRIMARY KEY,
  pull_request_id UUID REFERENCES pull_requests(id),
  first_review_at TIMESTAMPTZ,
  approved_at TIMESTAMPTZ,
  comments_count INT DEFAULT 0,
  threads_count INT DEFAULT 0,
  reviewer_count INT DEFAULT 0,
  reviewer_load INT,
  estimated_review_hours NUMERIC,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE jira_issues (
  id UUID PRIMARY KEY,
  external_key TEXT NOT NULL,
  issue_type TEXT,
  severity TEXT,
  status TEXT,
  linked_pull_request_id UUID REFERENCES pull_requests(id),
  created_at_source TIMESTAMPTZ,
  resolved_at_source TIMESTAMPTZ,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE metric_snapshots (
  id UUID PRIMARY KEY,
  team_id UUID REFERENCES teams(id),
  sprint_id TEXT,
  metric_name TEXT NOT NULL,
  metric_value NUMERIC,
  data_confidence TEXT,
  data_confidence_score INT,
  confidence_issue TEXT,
  calculation_version TEXT,
  created_at TIMESTAMPTZ DEFAULT now()
);
```

Note: `metric_snapshots` is created now but is only populated with the Data Confidence Score in this phase. Metric values arrive in Phase 2.

### API surface for this phase

```yaml
/api/v1/teams:
  get:
    description: List teams and AI delivery health summaries (may be sparse in Phase 1)

/api/v1/webhooks/github:
  post:
    description: Receive GitHub webhook events

/api/v1/webhooks/jira:
  post:
    description: Receive Jira webhook events
```

### Data Confidence Score methodology (implement the batch job here)

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

Each metric must carry both a 0-100 score and a label so downstream phases can apply the decision rule (below 70 = no hard enforcement).

### Recommended implementation stack

```text
Backend: Java 21 + Quarkus
Frontend: React / Next.js (introduced from Phase 2)
Database: PostgreSQL (TimescaleDB optional for metric time series)
Queue: Kafka or RabbitMQ for collector event processing
Auth: Keycloak (SSO) with role-based access
Integrations: GitHub App, Jira REST API
Deployment: Docker Compose for the pilot, Kubernetes for enterprise
```

```text
Treat the first version as an internal platform tool, not a multi-tenant SaaS.
Defer tenant isolation, billing and SOC2-grade controls until after the pilot proves value.
```

### Platform setup flow (admin, MVP)

```text
1. Platform admin signs in via Keycloak / SSO.
2. Admin installs the GitHub App on the pilot organisation.
3. Admin connects Jira via API token or OAuth.
4. Admin maps teams: Team -> GitHub repositories -> Jira project.
5. Data collection begins. No developer-facing UI is exposed yet.
```

The connect-and-map screen is the only UI in this phase. Dashboards arrive in Phase 2.

### Measurement confidence at this phase

```text
Layer: observable delivery metrics from GitHub and Jira.
Confidence: high. These are direct source events (timestamps, counts, sizes, labels).
Use: safe to display as factual once the Data Confidence Score is at least 75.
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
```

## Exit Criteria

```text
At least 90% of PRs opened in the last 30 days exist in the database with title, author, files and timestamps.
Average Data Confidence Score is at least 75.
/api/v1/teams responds successfully.
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

## Master Reference Map

```text
Section 6   - Platform architecture overview
Section 7.1 - Connectors and MVP connector recommendation
Section 16  - Recommended MVP data model
Section 17  - API design for the platform tool
Section 18  - Data confidence rules and calculation methodology
Section 21  - MVP backlog: Epic 1 (GitHub) and Epic 2 (Jira)
```
