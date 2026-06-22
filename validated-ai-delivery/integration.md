# Integration

Stage 5: deploy, monitor, maintain and hand over the model to operations.

## Purpose

```text
Ship the model to production with monitoring, rollback capability and clear ownership. The model is not "done" when it deploys — it is done when it is reliably serving users and being monitored.
```

## Activities

```text
1. API deployment
   - Expose model via REST or gRPC API.
   - Define SLAs: latency (p95 < Xms), availability (99.X%), throughput.
   - Implement request/response logging (without storing sensitive data).
   - Version the API (v1, v2) for backward compatibility.

2. CI/CD/CT pipeline
   - CI: code tests, linting, security scanning.
   - CD: deploy to staging → canary → production.
   - CT (Continuous Training): triggered by drift detection or scheduled retraining.
   - Pipeline as code (GitHub Actions, Jenkins, Airflow).
   - Automated rollback on SLO breach.

3. Monitoring
   - Model performance: prediction quality, latency, error rate.
   - Data quality: input distribution, missing values, anomalies.
   - Drift detection: data drift, concept drift (see drift-monitoring.md).
   - Business metrics: conversion, revenue, user satisfaction.
   - Alerting: thresholds for each metric; escalation paths.

4. Model registry
   - Register every production model with version, lineage and approval status.
   - Model lifecycle: development → staging → production → archived.
   - Approval gate: who can promote to production.
   - See model-registry.md for full specification.

5. Rollback mechanism
   - Keep previous model version ready for instant rollback.
   - Automated rollback on: SLO breach, drift alert, security incident.
   - Manual rollback: one-command revert to previous version.
   - Document rollback procedure in runbook.

6. Handover
   - Complete handover_template.md.
   - Transfer ownership to operations team.
   - Provide runbook, monitoring dashboards and escalation contacts.
   - Schedule knowledge transfer sessions.
```

## Gate criteria (deployment complete)

```text
- [ ] API deployed with SLAs defined (p95 < 100ms, 99.5% availability).
- [ ] CI/CD/CT pipeline operational.
- [ ] Monitoring dashboards live; alerts configured.
- [ ] Model registered in model registry.
- [ ] Rollback mechanism tested (implementation/scripts/rollback.sh).
- [ ] Handover template completed.
- [ ] Runbook documented.
- [ ] On-call rotation established.
```

## Implementation reference

```text
Concrete implementations for this stage:
  - API serving: Quarkus (Java 21) with health/predict/reload endpoints
  - Docker Compose: implementation/docker-compose.yml (all services)
  - CI/CD: implementation/.github/workflows/ci.yml, cd.yml
  - Rollback: implementation/scripts/rollback.sh
  - Model promotion: implementation/scripts/promote_model.py
  - Monitoring: implementation/monitoring/ (Prometheus + Grafana)
  - Drift detection: implementation/drift/monitor.py
  - Streamlit demo: implementation/streamlit/app.py
```

## Deliverables

```text
- Production API with SLAs
- CI/CD/CT pipeline configuration
- Monitoring dashboards
- Runbook
- Completed handover_template.md
```

## References

```text
architecture.md - reference tech stack
model-registry.md - model versioning
drift-monitoring.md - post-deployment monitoring
disaster-recovery.md - rollback and recovery
stakeholder-communication.md - handover communication
```
