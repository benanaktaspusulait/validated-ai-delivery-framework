# Disaster Recovery

Recovering from model failures, data loss or infrastructure outages.

## Guiding principle

```text
The model must never be the reason users cannot access the service.
If the model fails, the system degrades gracefully (fallback to rules, default response or previous model).
```

## Recovery scenarios

| Scenario | RPO | RTO | Recovery action |
|---|---|---|---|
| Model prediction error | 0 | < 5 min | Rollback to previous model version |
| Data pipeline failure | 1 hour | < 1 hour | Restart pipeline; backfill from source |
| Model serving outage | 0 | < 5 min | Failover to backup instance; auto-restart |
| Database failure | < 1 hour | < 4 hours | Restore from backup; replay from event log |
| Full environment loss | < 24 hours | < 24 hours | Rebuild from IaC; restore from backups |

## Rollback procedure

```text
Automatic rollback:
  1. Monitoring detects SLO breach or quality drop.
  2. Alert fires to on-call.
  3. System automatically promotes previous model version.
  4. Notification sent to team.

Manual rollback:
  1. On-call identifies issue.
  2. Run: rollback-command --model <name> --version <previous>
  3. Verify: predictions are serving correctly.
  4. Post-incident review within 48 hours.
```

## Backup strategy

```text
Model artefacts:
  - Model weights: backed up daily to separate storage.
  - Model registry: backed up on every change.
  - Minimum 2 previous versions kept in registry.

Training data:
  - Versioned datasets: backed up via DVC/data versioning.
  - Raw data: backed up per source system's policy.

Configuration:
  - Infrastructure as Code (Terraform, Pulumi): stored in version control.
  - Pipeline definitions: stored in version control.
  - Secrets: backed up in secrets manager (not in code).

Monitoring baselines:
  - Performance baselines: stored alongside model in registry.
  - Drift thresholds: stored in configuration, version-controlled.
```
