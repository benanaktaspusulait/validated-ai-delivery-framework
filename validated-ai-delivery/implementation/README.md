# Implementation

Technical prototype for the Validated AI Delivery Framework. Provides a working MLflow-based experiment tracking setup, model serving API, monitoring dashboards, drift detection, CI/CD pipeline and testing framework.

## Quick start

```bash
# Start all services
docker-compose up -d

# Access:
# MLflow UI:       http://localhost:5000
# Model API:       http://localhost:8000
# Monitoring:      http://localhost:3000 (Grafana)
# Streamlit demo:  http://localhost:8501
```

## Architecture

```text
┌─────────────────────────────────────────────────────┐
│                    Docker Compose                    │
├─────────┬──────────┬──────────┬──────────┬──────────┤
│ MLflow  │ MinIO    │ Postgres │ API      │ Grafana  │
│ Tracker │ (artefacts)│ (backend)│ (FastAPI)│+Prometheus│
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
├── docker-compose.yml          # All services
├── README.md                   # This file
├── mlflow/
│   ├── train.py                # Example training script
│   ├── conda.yaml              # Conda environment
│   └── Dockerfile              # MLflow server image
├── api/
│   ├── main.py                 # FastAPI model serving
│   ├── Dockerfile              # API image
│   └── requirements.txt        # API dependencies
├── streamlit/
│   ├── app.py                  # Demo UI
│   └── requirements.txt        # Streamlit dependencies
├── drift/
│   ├── monitor.py              # Drift detection with Evidently
│   └── requirements.txt        # Drift dependencies
├── monitoring/
│   ├── prometheus.yml          # Prometheus config
│   └── grafana/
│       └── dashboards/
│           └── ml-overview.json # Grafana dashboard
├── tests/
│   ├── test_data_validation.py # Data quality tests
│   ├── test_model.py           # Model behaviour tests
│   ├── test_api.py             # API integration tests
│   └── conftest.py             # Shared fixtures
├── .github/
│   └── workflows/
│       ├── ci.yml              # CI pipeline
│       └── cd.yml              # CD pipeline
└── scripts/
    ├── register_model.py       # Register model in registry
    ├── promote_model.py        # Promote staging -> production
    └── rollback.sh             # Rollback to previous version
```

## Services

| Service | Port | Purpose |
|---|---|---|
| MLflow | 5000 | Experiment tracking, model registry |
| MinIO | 9000 | S3-compatible artefact storage |
| PostgreSQL | 5432 | MLflow backend database |
| FastAPI | 8000 | Model serving REST API |
| Streamlit | 8501 | Demo UI |
| Prometheus | 9090 | Metrics collection |
| Grafana | 3000 | Monitoring dashboards |
