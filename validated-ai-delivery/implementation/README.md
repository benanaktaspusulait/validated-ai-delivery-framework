# Implementation

Technical prototype for the Validated AI Delivery Framework. Uses a **hybrid architecture**: Java/Quarkus for the API serving layer, Python for ML-specific components.

## Architecture: Why Two Languages

```text
┌─────────────────────────────────────────────────────────┐
│                   JAVA / QUARKUS LAYER                  │
│  API serving, authentication, model loading, health,    │
│  A/B routing, explainability/fairness endpoints,        │
│  Prometheus metrics, OpenAPI docs                       │
│  → Production-grade: type-safe, async, high-performance │
└─────────────────────────────────────────────────────────┘
                            │
                    REST API calls
                            │
┌─────────────────────────────────────────────────────────┐
│                    PYTHON ML LAYER                       │
│  MLflow training, Evidently drift, SHAP/LIME explain-   │
│  ability, Fairlearn fairness, Streamlit demo UI         │
│  → ML ecosystem: these libraries are Python-only        │
└─────────────────────────────────────────────────────────┘
```

**Why hybrid?**
- MLflow training is Python-native (no Java equivalent with same ecosystem support).
- Evidently AI (drift detection) is Python-only.
- Streamlit (demo UI) is Python-only.
- SHAP/LIME/Fairlearn are Python-first libraries.
- Converting these to Java (DJL/Tribuo) would sacrifice ecosystem maturity for language consistency.
- The API layer (Quarkus) handles all client-facing concerns; Python components are internal ML tools.

## Tech Stack

```text
API Layer (Java):
  Language:    Java 21 (LTS)
  Framework:   Quarkus 3.17.5
  Build:       Maven
  API:         Jakarta REST (JAX-RS)
  DI:          CDI (ArC)
  ML Client:   MLflow 2.17.0 (Java client)
  Testing:     JUnit 5 + REST Assured + Quarkus Test
  Container:   Multi-stage Docker build (eclipse-temurin:21)

ML Layer (Python):
  Training:    Python 3.11 + MLflow 2.14.0 + scikit-learn 1.4.2
  Drift:       Python 3.11 + Evidently 0.4.13
  UI:          Python 3.11 + Streamlit 1.36.0
  Tests:       Python 3.11 + pytest 8.2.0 + locust 2.20.0
  Container:   python:3.11-slim

Infrastructure:
  Monitoring:  Prometheus + Grafana
  Storage:     PostgreSQL 16 + MinIO (S3-compatible)
  CI/CD:       GitHub Actions + Trivy
```

## Quick start

```bash
# Start all services
docker-compose up -d

# Access:
# API (Java):    http://localhost:8080/q/health
# API Docs:      http://localhost:8080/q/openapi
# MLflow:        http://localhost:5000
# Grafana:       http://localhost:3000 (admin/admin)
# Prometheus:    http://localhost:9090
# Streamlit:     http://localhost:8501

# Run Java API tests
cd api && ./mvnw test

# Run Python ML tests
cd tests && pip install -r requirements.txt && pytest -v
```

## Directory structure

```text
implementation/
├── docker-compose.yml              # All 8 services
├── README.md                       # This file
│
├── api/                            # JAVA: Quarkus API (production serving)
│   ├── pom.xml                     # Maven build (JDK 21, Quarkus 3.17.5)
│   ├── Dockerfile                  # Multi-stage build (eclipse-temurin:21)
│   └── src/main/java/com/mlplatform/
│       ├── config/                 # Quarkus config interfaces
│       ├── model/                  # Request/Response records
│       ├── service/                # ModelService (MLflow client)
│       ├── webhooks/               # REST endpoints
│       └── health/                 # MicroProfile Health checks
│
├── mlflow/                         # PYTHON: Training pipeline
│   ├── train.py                    # MLflow experiment + hyperparameter search
│   ├── conda.yaml                  # Conda environment for training
│   └── Dockerfile                  # MLflow server (python:3.11-slim)
│
├── drift/                          # PYTHON: Drift monitoring
│   ├── monitor.py                  # Evidently AI drift detection
│   ├── requirements.txt            # evidently, mlflow, pandas
│   └── Dockerfile                  # Drift monitor service
│
├── streamlit/                      # PYTHON: Demo UI
│   ├── app.py                      # Experiment browser + inference demo
│   ├── Dockerfile                  # Streamlit server
│   └── requirements.txt            # streamlit, mlflow, pandas
│
├── scripts/                        # PYTHON + BASH: Operations scripts
│   ├── register_model.py           # Register model in MLflow registry
│   ├── promote_model.py            # Promote model between stages
│   └── rollback.sh                 # Rollback to previous model version
│
├── monitoring/                     # Infrastructure: Observability
│   ├── prometheus.yml              # Scrape config
│   ├── alerts.yml                  # Alert rules
│   └── grafana/                    # Dashboard + provisioning
│
├── tests/                          # PYTHON: ML and data tests
│   ├── test_data_validation.py     # Data quality tests
│   ├── test_model.py               # Model behaviour tests
│   ├── load_test.py                # Locust load tests
│   └── requirements.txt            # Test dependencies
│
└── .github/workflows/              # CI/CD
    ├── ci.yml                      # Java build + Python tests + Trivy
    └── cd.yml                      # Docker build + deploy + integration test
```

## Services

| Service | Port | Language | Purpose |
|---|---|---|---|
| API | 8080 | Java (Quarkus) | Model serving, experiments, explainability, fairness |
| MLflow | 5000 | Python | Experiment tracking, model registry |
| Drift Monitor | - | Python | Continuous drift detection with Evidently |
| Streamlit | 8501 | Python | Demo UI for experiment exploration |
| MinIO | 9000 | Go | S3-compatible artefact storage |
| PostgreSQL | 5432 | PostgreSQL 16 | MLflow backend database |
| Prometheus | 9090 | Go | Metrics collection |
| Grafana | 3000 | Go | Monitoring dashboards |

## API Endpoints (Java/Quarkus)

```text
GET  /api/v1/health              - Health check
GET  /api/v1/metrics             - Model metadata
POST /api/v1/reload              - Reload model from registry
POST /api/v1/predict             - Model prediction
POST /api/v1/experiments/assign  - A/B experiment assignment
GET  /api/v1/experiments/{id}/results - Experiment results
POST /api/v1/explain             - SHAP/LIME explanation
GET  /api/v1/explain/methods     - Available explanation methods
POST /api/v1/fairness/evaluate   - Fairness evaluation
GET  /api/v1/fairness/methods    - Available fairness metrics

Quarkus built-in:
GET  /q/health/live              - Liveness check
GET  /q/health/ready             - Readiness check
GET  /q/metrics                  - Prometheus metrics
GET  /q/openapi                  - OpenAPI specification
```
