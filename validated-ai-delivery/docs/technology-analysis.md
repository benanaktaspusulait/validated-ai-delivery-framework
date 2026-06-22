# Technology Stack Analysis

Detailed analysis of all technologies chosen in the Validated AI Delivery Framework.

## Summary

| Category | Technology | Version | Currency | Key Risk |
|---|---|---|---|---|
| Core | Python | 3.11 | Current | Minor -- 3.12+ available |
| Core | Docker Compose | 3.9 | Current | No auto-scaling; pilot-only |
| Core | PostgreSQL | 16 | Current | Default credentials; single-node |
| Core | MinIO | latest (unpinned) | Unknown | No version pin; default credentials |
| ML/Data | MLflow | 2.14.0 | Current | Not latest (~2.17+); UI basic |
| ML/Data | scikit-learn | 1.4.2 | Current | No GPU; classical ML only |
| ML/Data | Pandas | 2.2.1 | Current | Memory limits for large data |
| ML/Data | NumPy | 1.26.4 | Current (1.x) | NumPy 2.0 breaking changes exist |
| ML/Data | Evidently | 0.4.13 | **Outdated** | ~18 months behind (0.6+ current) |
| ML/Data | SHAP | 0.45.0 | Current | Slow for non-tree models |
| ML/Data | LIME | 0.2.0.0 | **Stale** | Low maintenance; unstable explanations |
| ML/Data | Fairlearn | 0.10.0 | Current | Evaluation only; no mitigation used |
| ML/Data | CodeCarbon | 2.3.4 | Current | Estimates only; accuracy varies |
| API/Serving | FastAPI | 0.111.0 | Current | No built-in batching |
| API/Serving | Uvicorn | 0.30.0 | Current | Single-process default |
| API/Serving | Pydantic | 2.7.1 | Current | v2 learning curve |
| Monitoring | Prometheus | v2.51.0 | Current | Pull-based; single-node |
| Monitoring | Grafana | 10.4.0 | Current | Default admin password |
| Monitoring | prom-instrumentator | 6.1.0 | Current | HTTP metrics only |
| UI | Streamlit | 1.36.0 | Current | Not production-grade |
| CI/CD | GitHub Actions | v4/v5 | Current | GitHub-ecosystem locked |
| CI/CD | Ruff | latest (unpinned) | Current | No version pin |
| CI/CD | Pytest | 8.2.0 | Current | No coverage tool configured |
| CI/CD | Locust | 2.20.0 | Current | GIL limits load generation |
| CI/CD | Trivy | 0.24.0 | **Slightly outdated** | 0.28+ available |
| Infra | boto3 | 1.34.69 | Current | Large package |
| Infra | psycopg2-binary | 2.9.9 | Current | Binary variant |

## Strengths of the Stack

1. **Cohesive integration**: MLflow serves as the central nervous system connecting training, serving, drift, explainability, and UI.
2. **Open-source throughout**: No vendor lock-in; all components are OSS with active communities.
3. **Pilot-appropriate scale**: Docker Compose with 8 services is right for a 1-2 model team.
4. **Comprehensive coverage**: Fairness, explainability, drift, carbon tracking, and A/B testing all included.
5. **Production-aware**: CI/CD pipeline with security scanning, health checks, and rollback scripts.

## Weaknesses and Risks

1. **Security debt**: Default credentials on PostgreSQL, MinIO, Grafana. No TLS. No secrets management.
2. **Outdated libraries**: Evidently 0.4.13 is ~18 months behind; LIME appears unmaintained.
3. **Single-node infrastructure**: PostgreSQL, MinIO, MLflow all single-instance with no failover.
4. **No Kubernetes deployment**: CD pipeline deploys via docker-compose locally, not to a real cluster.
5. **Unpinned versions**: MinIO (`latest`) and Ruff (no version) risk breaking CI/CD.

## Priority Actions

1. Pin MinIO to a specific version tag.
2. Upgrade Evidently to 0.6.x (breaking API changes — plan migration).
3. Add secrets management (Docker secrets, Kubernetes Secrets, or Vault).
4. Configure TLS (nginx reverse proxy or cloud load balancer).
5. Add pytest-cov for test coverage reporting in CI.
6. Add pytest-asyncio for async FastAPI endpoint testing.
7. Consider BentoML or Triton if model serving performance becomes a bottleneck at scale.
