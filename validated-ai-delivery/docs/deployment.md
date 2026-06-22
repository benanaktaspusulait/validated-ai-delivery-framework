# Deployment Guide

How to deploy the Validated AI Delivery platform in different environments.

## Quick start (Docker Compose)

```bash
cd implementation
docker-compose up -d

# Verify
curl http://localhost:8000/health
open http://localhost:5000   # MLflow
open http://localhost:8501   # Streamlit
open http://localhost:3000   # Grafana (admin/admin)
```

## Services and ports

| Service | Port | Purpose |
|---|---|---|
| MLflow | 5000 | Experiment tracking, model registry |
| API | 8000 | Model serving |
| Streamlit | 8501 | Demo UI |
| Grafana | 3000 | Monitoring dashboards |
| Prometheus | 9090 | Metrics collection |
| PostgreSQL | 5432 | MLflow backend |
| MinIO | 9000/9001 | Artefact storage |

## Environment variables

```text
MLFLOW_TRACKING_URI=http://mlflow:5000
MODEL_NAME=default-model
MODEL_STAGE=Production
API_KEY=changeme-in-production
CHECK_INTERVAL_HOURS=24
```

## Production deployment

```text
1. Replace default API_KEY with a strong secret.
2. Replace MinIO credentials with production secrets.
3. Set up TLS termination (nginx reverse proxy or cloud load balancer).
4. Configure persistent volumes for PostgreSQL and MinIO.
5. Set up backup schedule for PostgreSQL (daily).
6. Configure alerting (see implementation/monitoring/alerts.yml).
7. Set up log aggregation (ELK, Loki, or cloud logging).
```

## Kubernetes deployment (outline)

```text
1. Build Docker images and push to container registry.
2. Create Kubernetes manifests:
   - Deployment for each service
   - Service (ClusterIP) for internal communication
   - Ingress for external access
   - ConfigMap for environment variables
   - Secret for API keys and credentials
   - PersistentVolumeClaim for PostgreSQL and MinIO
3. Deploy with: kubectl apply -f k8s/
4. Verify: kubectl get pods, kubectl logs -f deployment/api
```

## Rollback

```bash
# Rollback to previous model version
./implementation/scripts/rollback.sh <previous_version>

# Rollback entire deployment
kubectl rollout undo deployment/api
```
