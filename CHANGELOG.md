# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added
- Java/Quarkus API implementation (JDK 21, Quarkus 3.17.5)
- 73 task-level implementation breakdown across 6 phases
- Phase-packages with gate criteria, RACI, and execution order
- Framework split into 10 focused documents
- 22 cross-cutting reference documents
- 7 lifecycle templates
- Docker Compose with 8 services (API, MLflow, MinIO, PostgreSQL, Streamlit, Prometheus, Grafana, Drift Monitor)
- CI/CD pipeline (GitHub Actions) with Trivy security scanning
- Model registry with promotion rules and rollback
- A/B testing and shadow deployment patterns
- Explainability (SHAP, LIME) and fairness (Fairlearn)
- Carbon footprint tracking integration
- Load testing with Locust
- Data, model and API tests (JUnit 5 + pytest)
- Grafana dashboards and Prometheus alerts
- Security policy and MLSecOps documentation
- Compliance documentation (EU AI Act, GDPR)
- Technology analysis document

### Fixed
- All Python-to-Java reference migrations
- Port consistency (8080 for API)
- Broken cross-references across 50+ files
- Relative path errors in docs/
- Circular dependency in T4.12

## [1.8] - 2025-01-01

### Added
- Initial framework documentation
- Five lifecycle stages (Discovery, Data, Model, Validation, Integration)
- 22 cross-cutting reference documents
- 7 lifecycle templates
- MVP scope definition
- Phase-gate implementation plan
