# validated-ai-delivery Audit TODO

Audit date: 2025-06-22. Total issues found: 65 (30 root .md, 20 implementation, 15 templates/docs).

## Completed (fixed in this session)

- [x] Fix broken SECURITY.md casing in README.md, CHANGELOG.md, TODO.md
- [x] Fix broken paths in integration.md, docs/deployment.md, validation.md
- [x] Fix principles.md inconsistent implementation/ paths (8 instances)
- [x] Fix compliance.md "Operations" stage contradiction
- [x] Fix integration.md internal path inconsistency
- [x] Update stale TODO.md items (docs/deployment, docs/operations, docs/api-reference)
- [x] Fix docker-compose.yml: healthchecks (3 services), env vars (MODEL_A/B, AB_SPLIT_RATIO), alerts.yml mount
- [x] Fix monitoring: prometheus.yml rule_files configuration
- [x] Fix GitHub Actions cd.yml broken working-directory paths
- [x] Fix missing locust dependency (tests/requirements.txt created)
- [x] Fix missing scikit-learn in api/requirements.txt
- [x] Fix unused requests dependency removed from api/requirements.txt
- [x] Fix mlflow/Dockerfile missing scikit-learn
- [x] Fix Grafana provisioning (documented as open in TODO.md — needs YAML files)
- [x] Fix docs/deployment.md broken monitoring path
- [x] Fix CONTRIBUTING.md missing CODE_OF_CONDUCT link
- [x] Update TODO.md with current status

## Open (not yet fixed)

### High priority
- [ ] Expand security.md to cover MLSecOps topics (threat model, API security, container scanning, adversarial testing)
- [ ] Add Grafana provisioning YAML files (monitoring/grafana/provisioning/dashboards/default.yml + datasource config)
- [ ] Add Prometheus exporter to drift monitor (drift/monitor.py) — alerts reference metrics not exposed
- [ ] Wire API modules (ab_testing, explainability, fairness, model_card_generator, carbon_tracking) into FastAPI main.py
- [ ] Fix deprecated MLflow API in scripts/rollback.sh
- [ ] Add unit tests for untested modules (5 modules)
- [ ] Add OpenAPI YAML spec file

### Medium priority
- [ ] Add missing fields to templates (regulatory classification, re-validation conditions, detection method)
- [ ] Add cross-references from templates back to lifecycle stage files
- [ ] Add cross-references from docs/ files to lifecycle and cross-cutting files
- [ ] Add Kubernetes HPA manifests
- [ ] Add ONNX model conversion example
- [ ] Add feature store (Feast) integration example

### Low priority
- [ ] Add federated learning example
- [ ] Add differential privacy integration
- [ ] Add Streamlit drift visualization tab
- [ ] Add fairness metrics display to Streamlit app
- [ ] Add model card auto-population from fairness/explainability
- [ ] Add Prometheus cost tracking metrics
