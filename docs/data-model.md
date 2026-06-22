# Data Model

Canonical schema for the AI Delivery Control Plane MVP. Created in Phase 1 (`policy_overrides` is added in Phase 4). Phase packages reference this file.

## Processing status values

```text
Valid values for raw_events.processing_status:
  pending      - received, not yet processed
  processing   - actively being handled
  completed    - successfully normalized and stored
  retry        - failed, will be retried (max 3 attempts)
  dead_letter  - exceeded retry limit, requires manual intervention
  failed       - permanently failed (e.g. malformed payload)
```

## Schema

```sql
CREATE TYPE processing_status AS ENUM ('pending', 'processing', 'completed', 'retry', 'dead_letter', 'failed');

CREATE TABLE raw_events (
  id UUID PRIMARY KEY,
  provider TEXT NOT NULL,
  event_type TEXT NOT NULL,
  external_id TEXT,
  idempotency_key TEXT,
  source_timestamp TIMESTAMPTZ,
  payload JSONB NOT NULL,
  received_at TIMESTAMPTZ DEFAULT now(),
  processing_status processing_status DEFAULT 'pending',
  processing_error TEXT
);

CREATE TABLE teams (
  id UUID PRIMARY KEY,
  name TEXT NOT NULL,
  domain_criticality TEXT NOT NULL DEFAULT 'medium',
  created_at TIMESTAMPTZ DEFAULT now(),
  updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE repositories (
  id UUID PRIMARY KEY,
  team_id UUID REFERENCES teams(id) ON DELETE RESTRICT,
  provider TEXT NOT NULL,
  external_id TEXT NOT NULL,
  name TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now(),
  updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE pull_requests (
  id UUID PRIMARY KEY,
  repository_id UUID REFERENCES repositories(id) ON DELETE CASCADE,
  external_id TEXT NOT NULL,
  title TEXT,
  author_id TEXT,
  created_at_source TIMESTAMPTZ,
  merged_at_source TIMESTAMPTZ,
  closed_at_source TIMESTAMPTZ,
  changed_files INT,
  changed_lines INT,
  ai_assisted BOOLEAN DEFAULT false,
  ai_usage_inferred BOOLEAN DEFAULT false,
  ai_usage_inference_confidence TEXT,
  ai_usage_types TEXT[],
  ai_assistance_confidence TEXT,
  ownership_confidence_score NUMERIC,
  codebase_familiarity TEXT,
  change_freshness_score NUMERIC,
  ownership_boundary_risk TEXT,
  risk_score NUMERIC,
  created_at TIMESTAMPTZ DEFAULT now(),
  updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE review_analytics (
  id UUID PRIMARY KEY,
  pull_request_id UUID REFERENCES pull_requests(id) ON DELETE CASCADE,
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
  linked_pull_request_id UUID REFERENCES pull_requests(id) ON DELETE SET NULL,
  created_at_source TIMESTAMPTZ,
  resolved_at_source TIMESTAMPTZ,
  created_at TIMESTAMPTZ DEFAULT now(),
  updated_at TIMESTAMPTZ DEFAULT now()
);

CREATE TABLE metric_snapshots (
  id UUID PRIMARY KEY,
  team_id UUID REFERENCES teams(id) ON DELETE CASCADE,
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
  team_id UUID REFERENCES teams(id) ON DELETE CASCADE,
  pull_request_id UUID REFERENCES pull_requests(id) ON DELETE SET NULL,
  recommendation_type TEXT NOT NULL,
  severity TEXT NOT NULL,
  message TEXT NOT NULL,
  status TEXT DEFAULT 'open',
  created_at TIMESTAMPTZ DEFAULT now(),
  updated_at TIMESTAMPTZ DEFAULT now(),
  resolved_at TIMESTAMPTZ
);

-- Added in Phase 4
CREATE TABLE policy_overrides (
  id UUID PRIMARY KEY,
  pull_request_id UUID REFERENCES pull_requests(id) ON DELETE SET NULL,
  policy_name TEXT NOT NULL,
  override_label TEXT,
  overridden_by TEXT NOT NULL,
  reason TEXT,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Audit trail for metadata and config changes
CREATE TABLE audit_log (
  id UUID PRIMARY KEY,
  entity_type TEXT NOT NULL,
  entity_id UUID NOT NULL,
  action TEXT NOT NULL,
  old_value JSONB,
  new_value JSONB,
  performed_by TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Configuration versioning
CREATE TABLE config_versions (
  id UUID PRIMARY KEY,
  config_type TEXT NOT NULL,
  config_key TEXT NOT NULL,
  config_value JSONB NOT NULL,
  created_by TEXT NOT NULL,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Metric lineage for provenance tracking
CREATE TABLE metric_lineage (
  id UUID PRIMARY KEY,
  metric_snapshot_id UUID REFERENCES metric_snapshots(id) ON DELETE CASCADE,
  source_event_ids UUID[],
  calculation_inputs JSONB,
  created_at TIMESTAMPTZ DEFAULT now()
);

-- Indexes
CREATE INDEX idx_pull_requests_repository_created
ON pull_requests(repository_id, created_at_source);

CREATE INDEX idx_pull_requests_ai_assisted
ON pull_requests(ai_assisted);

CREATE INDEX idx_pull_requests_author
ON pull_requests(author_id, created_at_source);

CREATE INDEX idx_pull_requests_external
ON pull_requests(external_id);

CREATE INDEX idx_repositories_provider_external
ON repositories(provider, external_id);

CREATE INDEX idx_jira_issues_external_key
ON jira_issues(external_key);

CREATE INDEX idx_jira_issues_linked_pr
ON jira_issues(linked_pull_request_id);

CREATE INDEX idx_metric_snapshots_team_metric_created
ON metric_snapshots(team_id, metric_name, created_at);

CREATE INDEX idx_recommendations_team_status
ON recommendations(team_id, status);

CREATE INDEX idx_raw_events_idempotency
ON raw_events(idempotency_key)
WHERE idempotency_key IS NOT NULL;

CREATE INDEX idx_audit_log_entity
ON audit_log(entity_type, entity_id);

CREATE INDEX idx_config_versions_key
ON config_versions(config_type, config_key, created_at DESC);
```

## Unique constraints

```text
repositories(provider, external_id): prevents duplicate repo records from webhook replay or backfill.
jira_issues(external_key): prevents duplicate Jira issue records from webhook replay.
pull_requests(external_id): prevents duplicate PR records from webhook replay.
raw_events(idempotency_key) WHERE NOT NULL: prevents duplicate event processing (already present).
```

## Foreign key behaviour

```text
repositories.team_id        -> ON DELETE RESTRICT (teams cannot be deleted while repos exist)
pull_requests.repository_id -> ON DELETE CASCADE  (delete PRs when repo is removed)
review_analytics.pull_request_id -> ON DELETE CASCADE
jira_issues.linked_pull_request_id -> ON DELETE SET NULL (issues exist independently)
metric_snapshots.team_id    -> ON DELETE CASCADE
recommendations.team_id     -> ON DELETE CASCADE
recommendations.pull_request_id -> ON DELETE SET NULL
policy_overrides.pull_request_id -> ON DELETE SET NULL
metric_lineage.metric_snapshot_id -> ON DELETE CASCADE
```

## Metric name catalogue

Canonical metric names (used in `metric_snapshots.metric_name`). Full definitions in `docs/metrics-catalogue.md`.

```text
Canonical metric names (used in metric_snapshots.metric_name):
  ai_assisted_pr_rate
  ai_review_debt_age_ratio
  post_merge_defect_rate
  weighted_defect_rate
  human_validation_cost
  vdt_signal_score
  cognitive_load_index
```

## Updated-at triggers

```text
Tables with updated_at should use a trigger to auto-set the value on UPDATE:
  CREATE OR REPLACE FUNCTION update_timestamp() RETURNS TRIGGER AS $$
  BEGIN NEW.updated_at = now(); RETURN NEW; END;
  $$ LANGUAGE plpgsql;

Apply to: teams, repositories, pull_requests, jira_issues, recommendations.
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
audit_log is populated from Phase 1 for legal/privacy tracking, expanded in Phase 4 for policy overrides.
config_versions is populated from Phase 1 for cost_config, expanded in Phase 4 for policy thresholds.
metric_lineage is populated from Phase 2 when metric calculations run.
```

## Cost-input configuration

Parameters consumed by the Validated Delivery Trend's directional cost context (defined in Phase 1, used in Phase 2). Read from the central configuration service (`config_versions` table) or environment.

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
