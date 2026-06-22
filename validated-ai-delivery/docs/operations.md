# Operations Guide

Day-to-day operational procedures for the AI Delivery platform.

## Monitoring

```text
Dashboards:
  - Grafana: http://localhost:3000 (ML Model Monitoring dashboard)
  - MLflow: http://localhost:5000 (experiment tracking)

Key metrics to watch:
  - API request rate and latency (p95 < 100ms target)
  - API error rate (< 1% target)
  - Model accuracy (>= 0.90 target)
  - Drift detection alerts
  - Data quality alerts
```

## Alerting

```text
Critical alerts (immediate response):
  - API error rate > 5% for 5 minutes
  - Model accuracy < 0.80 for 15 minutes
  - MLflow server down for 2 minutes
  - Data drift detected

Warning alerts (investigate within 4 hours):
  - API p95 latency > 1 second
  - Container memory usage > 90%
  - Drift detected (non-critical)
```

## Troubleshooting

| Symptom | Likely cause | Action |
|---|---|---|
| API returns 503 | Model not loaded | Check MLflow connectivity; run `/reload` |
| API high latency | Model too large or overloaded | Check Grafana; scale up or optimise model |
| Drift alert | Data distribution changed | Run drift report; consider retraining |
| MLflow unreachable | Service down | `docker-compose restart mlflow` |
| PostgreSQL full | Old data not purged | Run retention purge; check disk space |

## Backup and restore

```text
PostgreSQL:
  Backup: docker exec postgres pg_dump -U mlflow mlflow > backup.sql
  Restore: docker exec -i postgres psql -U mlflow mlflow < backup.sql

MinIO:
  Backup: mc mirror minio/mlflow-artifacts backup/
  Restore: mc mirror backup/ minio/mlflow-artifacts/

Configuration:
  All config is in version control; restore by checking out the correct commit.
```

## Scaling

```text
Vertical scaling: increase CPU/memory for API and MLflow containers.
Horizontal scaling: run multiple API instances behind a load balancer.
Database scaling: add PostgreSQL read replica for dashboard queries.
Storage scaling: lifecycle policies on MinIO/S3 to move old artefacts to cold storage.
```
