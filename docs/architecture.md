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

## Connector reliability requirements

```text
Handle provider rate limits with backoff and retry.
Handle pagination explicitly for list APIs.
Support webhook replay without duplicating records.
Use provider + event_type + external_id + source_timestamp as the idempotency key where possible.
Write webhook events to raw_events before processing.
Move failed processing to retry, then dead-letter after configured attempts.
Run nightly reconciliation comparing provider source counts against local counts.
Support backfill for the pilot team's previous 30 days of PR and Jira data.
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
