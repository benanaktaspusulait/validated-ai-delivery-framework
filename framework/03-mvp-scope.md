# MVP Scope

## Problem Statement

Engineering leaders cannot tell whether AI-assisted PRs create real delivery value or merely shift cost into review, rework and defects.

## Integrations

```text
Required: GitHub + Jira.
Optional: SonarQube, Slack.
Avoid starting with IDE telemetry (privacy, consent, change-management complexity).
```

## Five Core Metrics

Each metric carries a Data Confidence Score:

| Metric | Definition |
|---|---|
| AI-assisted PR Rate | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |
| AI Review Debt Age Ratio | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |
| Post-Merge Defect Rate | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |
| Human Validation Cost | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |
| Validated Delivery Trend (VDT) | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |

## MVP Non-Goals

```text
IDE telemetry, EU AI Act automation, model drift monitoring,
prompt repository, multi-model evaluation, advanced security scanning,
custom report builder, full A/B testing engine.
```

## MVP Dashboard Screens

```text
Overview, Team, PR Risk, Metrics Detail     — Phase 2
Developer in-PR view                         — Phase 3
Policy Settings, Recommendations/Playbook    — Phase 4
Executive Summary, multi-team navigation     — Phase 5
```

Details: [docs/ui-ux-spec.md](../docs/ui-ux-spec.md)

Implementation sequence: [06-mvp-backlog.md](06-mvp-backlog.md)

Expanded metrics for later stages: [04-expanded-metrics.md](04-expanded-metrics.md)
