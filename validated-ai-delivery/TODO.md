# TODO

Tracks remaining work for the Validated AI Delivery Framework.

## Open

### High priority

- [ ] Add OpenAPI/Swagger specification for the model serving API (`docs/api-reference.md`).
- [ ] Create deployment guide (`docs/deployment.md`) with Docker Compose and Kubernetes instructions.
- [ ] Create operations guide (`docs/operations.md`) with monitoring, alerting and troubleshooting.
- [ ] Complete the testing strategy document with concrete pytest examples for each test type.
- [ ] Add Feast feature store integration example to `implementation/`.

### Medium priority

- [ ] Add multi-environment configuration (dev, staging, prod) to docker-compose.
- [ ] Add JWT/OAuth authentication to the API (replace simple API key).
- [ ] Add adversarial robustness testing with ART (Adversarial Robustness Toolbox).
- [ ] Add model watermarking for IP protection.
- [ ] Create DPIA (Data Protection Impact Assessment) template for GDPR compliance.
- [ ] Add Locust load test with latency threshold assertions.
- [ ] Add MkDocs or Sphinx for documentation publishing.
- [ ] Add DVC pipeline configuration for end-to-end reproducibility.

### Low priority (future roadmap)

- [ ] Add federated learning example.
- [ ] Add differential privacy integration.
- [ ] Add Kubernetes HPA manifests for auto-scaling.
- [ ] Add ONNX model conversion example.
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
- [x] CONTRIBUTING.md, CODE_OF_CONDUCT.md, SECURITY.md, LICENSE, CHANGELOG.md.
