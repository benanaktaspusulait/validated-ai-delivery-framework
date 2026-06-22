# Implementation

Technical prototype for the Validated AI Delivery Framework. Provides a working Quarkus-based API, MLflow experiment tracking, model serving, monitoring, drift detection, CI/CD pipeline and testing framework.

## Tech Stack

```text
Language:    Java 21 (LTS)
Framework:   Quarkus 3.17.5
Build:       Maven
API:         Jakarta REST (JAX-RS) with Quarkus REST
DI:          CDI (ArC)
ML Tracking: MLflow 2.17.0 (Java client)
Monitoring:  Prometheus + Grafana
Testing:     JUnit 5 + REST Assured + Quarkus Test
Container:   Docker multi-stage build
CI/CD:       GitHub Actions
```

## Quick start

```bash
# Start all services
docker-compose up -d

# Access:
# API:        http://localhost:8080/q/health
# API Docs:   http://localhost:8080/q/openapi
# MLflow:     http://localhost:5000
# Grafana:    http://localhost:3000 (admin/admin)
# Prometheus: http://localhost:9090
# Streamlit:  http://localhost:8501

# Run tests
cd api && ./mvnw test
```

## Architecture

```text
┌─────────────────────────────────────────────────────┐
│                    Docker Compose                    │
├─────────┬──────────┬──────────┬──────────┬──────────┤
│ MLflow  │ MinIO    │ Postgres │ API      │ Grafana  │
│ Tracker │ (artifacts)│ (backend)│ (Quarkus)│+Prometheus│
└─────────┴──────────┴──────────┴──────────┴──────────┘
                            │
                    ┌───────┴───────┐
                    │  Streamlit    │
                    │  (demo UI)    │
                    └───────────────┘
```

## Directory structure

```text
implementation/
├── docker-compose.yml              # All services
├── README.md                       # This file
├── api/                            # Quarkus API (Java 21)
│   ├── pom.xml                     # Maven build
│   ├── Dockerfile                  # Multi-stage build
│   └── src/main/java/com/mlplatform/
│       ├── config/                 # Configuration interfaces
│       │   ├── MlflowConfig.java
│       │   └── ApiConfig.java
│       ├── model/                  # Request/Response records
│       │   ├── PredictionRequest.java
│       │   ├── PredictionResponse.java
│       │   └── HealthResponse.java
│       ├── service/                # Business logic
│       │   └── ModelService.java
│       ├── webhooks/               # REST endpoints
│       │   ├── HealthResource.java
│       │   ├── PredictResource.java
│       │   ├── ExperimentResource.java
│       │   ├── ExplainabilityResource.java
│       │   └── FairnessResource.java
│       └── health/                 # Health checks
│           └── ModelHealthCheck.java
│   └── src/test/java/com/mlplatform/
│       ├── HealthResourceTest.java
│       └── PredictResourceTest.java
├── mlflow/
│   ├── train.py                    # Python training script
│   ├── conda.yaml                  # Conda environment
│   └── Dockerfile                  # MLflow server
├── streamlit/
│   ├── app.py                      # Demo UI
│   ├── Dockerfile
│   └── requirements.txt
├── drift/
│   ├── monitor.py                  # Drift detection
│   ├── Dockerfile
│   └── requirements.txt
├── monitoring/
│   ├── prometheus.yml
│   ├── alerts.yml
│   └── grafana/
│       ├── provisioning/
│       │   ├── dashboards/default.yml
│       │   └── datasources/default.yml
│       └── dashboards/ml-overview.json
├── tests/
│   ├── test_data_validation.py
│   ├── test_model.py
│   ├── load_test.py
│   └── requirements.txt
├── scripts/
│   ├── register_model.py
│   ├── promote_model.py
│   └── rollback.sh
└── .github/workflows/
    ├── ci.yml
    └── cd.yml
```

## Services

| Service | Port | Technology | Purpose |
|---|---|---|---|
| API | 8080 | Quarkus (Java 21) | Model serving, experiments, explainability, fairness |
| MLflow | 5000 | Python | Experiment tracking, model registry |
| MinIO | 9000/9001 | Go | S3-compatible artefact storage |
| PostgreSQL | 5432 | PostgreSQL 16 | MLflow backend database |
| Streamlit | 8501 | Python | Demo UI |
| Prometheus | 9090 | Go | Metrics collection |
| Grafana | 3000 | Go | Monitoring dashboards |
| Drift Monitor | - | Python | Continuous drift detection |

## API Endpoints

```text
GET  /api/v1/health           - Health check
GET  /api/v1/metrics          - Model metadata
POST /api/v1/reload           - Reload model from registry
POST /api/v1/predict          - Model prediction
POST /api/v1/experiments/assign - A/B experiment assignment
GET  /api/v1/experiments/{id}/results - Experiment results
POST /api/v1/explain          - SHAP/LIME explanation
GET  /api/v1/explain/methods  - Available explanation methods
POST /api/v1/fairness/evaluate - Fairness evaluation
GET  /api/v1/fairness/methods  - Available fairness metrics

Quarkus built-in:
GET  /q/health/live           - Liveness check
GET  /q/health/ready          - Readiness check
GET  /q/metrics               - Prometheus metrics
GET  /q/openapi               - OpenAPI specification
```
