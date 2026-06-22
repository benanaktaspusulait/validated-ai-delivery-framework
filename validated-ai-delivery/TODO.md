# TODO

Tracks remaining work for the Validated AI Delivery Framework.

## Open

### High priority

- [ ] Expand `security.md` to cover MLSecOps topics (threat model, API security, container scanning, adversarial testing) — currently only covers vulnerability disclosure.
- [ ] Add Grafana provisioning YAML files (`monitoring/grafana/provisioning/dashboards/default.yml` and datasource config) — dashboards won't auto-load without them.
- [ ] Add Prometheus exporter to drift monitor (`drift/monitor.py`) — current alerts reference metrics not exposed by any service.
- [ ] Wire API modules (ab_testing, explainability, fairness, model_card_generator, carbon_tracking) into FastAPI main.py as routers — currently dead code.
- [ ] Fix deprecated MLflow API in `scripts/rollback.sh` (`transition_model_version_stage` → new Model Registry API).
- [ ] Add unit tests for untested modules: ab_testing.py, explainability.py, fairness.py, model_card_generator.py, carbon_tracking.py.
- [ ] Add OpenAPI YAML spec file (docs/api-reference.md exists as markdown but no machine-readable spec).

### Medium priority

- [ ] Add missing fields to templates: regulatory classification in discovery_template.md, re-validation conditions in validation_report_template.md, detection method in incident_postmortem_template.md.
- [ ] Add cross-references from templates back to their parent lifecycle stage files.
- [ ] Add cross-references from docs/ files (deployment, operations, api-reference) to lifecycle and cross-cutting files.
- [ ] Add Kubernetes HPA manifests for auto-scaling (docs/deployment.md mentions K8s outline but no manifests).
- [ ] Add ONNX model conversion example to implementation/.
- [ ] Add feature store (Feast) integration example.

### Low priority (future roadmap)

- [ ] Add federated learning example.
- [ ] Add differential privacy integration.
- [ ] Add Streamlit drift visualization tab.
- [ ] Add fairness metrics display to Streamlit app.
- [ ] Add model card auto-population from fairness and explainability outputs.
- [ ] Add Prometheus cost tracking metrics.

## Completed

- [x] Core lifecycle documentation (discovery, data, model, validation, integration).
- [x] Cross-cutting reference documents (22 documents).
- [x] Implementation prototype with Docker Compose.
- [x] MLflow experiment tracking with hyperparameter search.
- [x] FastAPI model serving with model registry integration.
- [x] Streamlit demo UI.
- [x] Drift monitoring with Evidently AI.
- [x] A/B testing and shadow deployment.
- [x] Explainability (SHAP, LIME) and fairness (Fairlearn).
- [x] Carbon footprint tracking (CodeCarbon).
- [x] CI/CD pipeline with Trivy security scanning.
- [x] Model rollback script.
- [x] Grafana dashboard and Prometheus alerts.
- [x] Data, model and API tests.
- [x] Load testing with Locust.
- [x] 7 lifecycle templates.
- [x] CONTRIBUTING.md, CODE_OF_CONDUCT.md, security.md, LICENSE, CHANGELOG.md.
- [x] Fixed broken SECURITY.md casing in README, CHANGELOG, TODO.
- [x] Fixed broken paths in integration.md, docs/deployment.md.
- [x] Fixed principles.md inconsistent implementation/ paths.
- [x] Fixed compliance.md "Operations" stage contradiction.
- [x] Fixed docker-compose.yml healthchecks, env vars, alerts mount.
- [x] Fixed GitHub Actions broken working-directory paths.
- [x] Added locust dependency to tests/requirements.txt.
- [x] Fixed api/requirements.txt (added scikit-learn, removed unused requests).
- [x] Fixed mlflow/Dockerfile (added scikit-learn).
- [x] Fixed prometheus.yml rule_files configuration.
- [x] Added CODE_OF_CONDUCT link to CONTRIBUTING.md.
