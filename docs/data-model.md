# Data Model

Canonical schema for the AI Delivery Control Plane MVP. Created in Phase 1 (`policy_overrides` is added in Phase 4). Phase packages reference this file.

## Schema

```sql
CREATE TABLE raw_events (
  id UUID PRIMARY KEY,
  provider TEXT NOT NULL,
  event_type TEXT NOT NULL,
  external_id TEXT,
  idempotency_key TEXT,
  source_timestamp TIMESTAMPTZ,
  payload JSONB NOT NULL,
  received_at TIMESTAMPTZ DEFAULT now(),
  processing_status TEXT DEFAULT 'pending',
  processing_error TEXT
);

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

CREATE TABLE recommendations (
  id UUID PRIMARY KEY,
  team_id UUID REFERENCES teams(id),
  pull_request_id UUID REFERENCES pull_requests(id),
  recommendation_type TEXT NOT NULL,
  severity TEXT NOT NULL,
  message TEXT NOT NULL,
  status TEXT DEFAULT 'open',
  created_at TIMESTAMPTZ DEFAULT now(),
  resolved_at TIMESTAMPTZ
);

-- Added in Phase 4
CREATE TABLE policy_overrides (
  id UUID PRIMARY KEY,
  pull_request_id UUID REFERENCES pull_requests(id),
  policy_name TEXT NOT NULL,
  override_label TEXT,
  overridden_by TEXT NOT NULL,
  reason TEXT,
  created_at TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_pull_requests_repository_created
ON pull_requests(repository_id, created_at_source);

CREATE INDEX idx_pull_requests_ai_assisted
ON pull_requests(ai_assisted);

CREATE INDEX idx_metric_snapshots_team_metric_created
ON metric_snapshots(team_id, metric_name, created_at);

CREATE INDEX idx_recommendations_team_status
ON recommendations(team_id, status);

CREATE INDEX idx_jira_issues_linked_pr
ON jira_issues(linked_pull_request_id);

CREATE UNIQUE INDEX idx_raw_events_idempotency
ON raw_events(idempotency_key)
WHERE idempotency_key IS NOT NULL;
```

## Migration ownership

```text
All schema changes must update:
- migration SQL
- data dictionary
- affected API schemas
- phase exit report
```

Population timing:

```text
metric_snapshots is created in Phase 1 but only carries the Data Confidence Score until Phase 2 adds metric values.
recommendations is populated from Phase 3 (read-only feed) and drives the Phase 4 Playbook view.
policy_overrides is created and written in Phase 4.
```

## Cost-input configuration

Parameters consumed by Net AI Delivery Value (defined in Phase 1, used in Phase 2). Read from the central configuration service or environment.

```yaml
cost_config:
  blended_hourly_rate:
    description: "Average hourly cost of an engineer (salary + benefits + overhead)"
    type: currency
    example: 70.0
  reviewer_hourly_cost:
    description: "Hourly cost specifically for peer reviews (often uses blended rate)"
    type: currency
    example: 70.0
  senior_opportunity_cost_rate:
    description: "Hourly rate used to value saved senior capacity"
    type: currency
    example: 110.0
  tooling_cost_allocation_per_pr:
    description: "Fixed allocation of AI tool licensing and infrastructure per PR"
    type: currency
    example: 20.0
```

Mapping to the metric:

```text
Estimated Gross AI Time Saving Value -> blended_hourly_rate
Human Validation Cost                -> reviewer_hourly_cost
Tooling Operational Cost             -> tooling_cost_allocation_per_pr
Senior Opportunity Cost              -> senior_opportunity_cost_rate
```

## Retention and identifier handling

Schema-level rules; full policy lives in `docs/governance-and-privacy.md`.

```text
Raw events follow the detailed-data retention policy (12 months) unless legal/privacy requires shorter retention.
Redact or exclude sensitive raw payload fields before storage where possible.
Pseudonymise developer identifiers where possible.
Restrict raw author/reviewer-level access to platform administrators and data stewards; managers get team-level views only.
Never store raw prompt content; keep only derived prompt safety signals.
```
