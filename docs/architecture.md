# Architecture

Canonical architecture and technology reference for the AI Delivery Control Plane. Phase packages reference this file instead of repeating it.

## System flow

```text
Source Systems
  GitHub / GitLab
  Jira / Azure DevOps
  SonarQube / CI-CD            (Stage 2+)
        ↓
Connectors / Collectors
        ↓
Event Store / Operational Database   (Phase 1 ends here)
        ↓
Metrics Engine                       (Phase 2)
        ↓
Risk & Policy Engine                 (Phase 2 read-only, Phase 4 enforcing)
        ↓
Recommendation Engine                (Phase 3)
        ↓
UI Dashboards / GitHub Checks / Slack-Teams alerts
```

## Reference implementation stack

The stack below is illustrative. The framework is technology-agnostic; adopt whatever fits your organisation.

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
Build the first version as an internal platform tool, not a multi-tenant SaaS.
Defer tenant isolation, billing and SOC2-grade controls until after the pilot proves value.
```

## Collector interface boundary

To support multi-provider environments (GitLab, Azure DevOps) in later stages, collectors follow an interface-driven design.

```text
Interface: SourceConnector
- authenticate()
- subscribeWebhooks()
- fetchBackfill(days: int)
- parsePayload(rawBody: json) -> List<NormalizedEvent>

Normalization Layer:
- Maps provider-specific fields (e.g. GitHub 'pull_request.user' vs GitLab 'object_attributes.author_id') to the platform schema.
- Decouples the database schema from vendor-specific payload structures.
```

## AI usage inference and fallback

When developers do not explicitly mark a PR as AI-assisted or non-AI, the platform must not silently default to `ai_assisted = false`, which would bias all AI metrics downward.

```text
Classification precedence:
1. Explicit declaration: PR template checkbox or label says AI was used -> ai_assisted = true.
2. Explicit denial: PR template says "No AI assistance used" -> ai_assisted = false.
3. Inference: no explicit declaration, but signals exist -> ai_assisted = inferred, confidence = medium.
4. Unknown: no declaration and no signals -> ai_assisted = NULL; excluded from both AI and non-AI cohorts.

Inference signals (MVP):
- Commit message contains "copilot", "gpt", "ai-generated" or similar keywords.
- PR body or footer contains AI tool references.
- PR title contains AI-related patterns.
- File paths match known AI-generated output patterns (e.g. auto-generated test files).

Inference confidence:
- Single signal matched: inferred with low confidence.
- Two or more signals matched: inferred with medium confidence.
- Inferred PRs are included in the AI-assisted cohort for metric calculation but annotated with confidence_issue = "inferred_not_declared".
```

## Configuration service

The platform needs a central configuration service for cost parameters, policy thresholds, team configs and feature flags.

```text
Storage: database-backed (config_versions table) with file-based override (team-config.yaml for self-service).
Versioning: every config change creates a new version row with created_by and created_at.
Audit: config changes are logged in the audit_log table.
Mid-sprint changes: config changes apply to future metric calculations only; historical snapshots are not retroactively altered.
Access control: platform-admin role required for cost_config, policy thresholds and team config.
Hot-reload: policy engine reloads config on each evaluation cycle; no restart required.
```

## Connector reliability requirements

```text
Handle provider rate limits with exponential backoff (1s, 2s, 4s, 8s, max 30s) and jitter.
Handle pagination explicitly for list APIs.
Support webhook replay without duplicating records (idempotency key: provider + event_type + external_id + source_timestamp).
Write webhook events to raw_events before processing (write-ahead).
Move failed processing to retry (max 3 attempts with backoff), then dead-letter after configured attempts.
Run nightly reconciliation comparing provider source counts against local counts.
Support backfill for the pilot team's previous 30 days of PR and Jira data.
```

## Backpressure and overflow

```text
Queue backpressure:
- Kafka: consumer lag threshold alert at > 10,000 messages; pause webhook ingestion at > 50,000.
- RabbitMQ: queue depth alert at > 5,000 messages; circuit breaker at > 20,000.
- When backpressure triggers: new webhooks receive 503 with Retry-After header; existing events continue processing.

Circuit breaker for provider APIs:
- Open after 5 consecutive failures; half-open after 30 seconds.
- When open: webhook receipt returns 202 Accepted but defers processing to a batch retry queue.

Graceful degradation:
- If event store is temporarily unavailable: buffer raw events to disk (max 1 hour / 10,000 events) then reject with 503.
- If metrics engine is degraded: serve stale metric snapshots with data_confidence annotated as "stale".
```

## Webhook security

```text
GitHub webhooks:
- Validate HMAC-SHA256 signature using the webhook secret (X-Hub-Signature-256 header).
- Reject with 401 Unauthorized if signature is missing or invalid; log the attempt.
- Rotate webhook secret quarterly or on suspected compromise.

Jira webhooks:
- Validate Atlassian Connect webhook token.
- Reject with 401 if token is invalid; log the attempt.

Secret management:
- Store webhook secrets in a secrets manager (HashiCorp Vault, AWS Secrets Manager, or environment variables for pilot).
- Never log webhook secrets; redact in all error messages.
- Document rotation procedure in operations runbook.
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

## MVP connector scope

```text
Required: GitHub + Jira.
Do not start with IDE telemetry; it increases privacy, consent and change-management complexity.
SonarQube, GitLab, CI/CD and notification connectors arrive in Stage 2+ (see docs/rollout-operating-model.md).
```

## Related references

```text
docs/data-model.md          - schema the collectors write to
docs/data-confidence.md     - scoring of collected data
docs/api-spec.md            - webhook and read endpoints
docs/testing-and-observability.md - collector tests and signals
```
