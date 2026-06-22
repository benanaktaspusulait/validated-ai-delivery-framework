# Principles

Founding principles of the Validated AI Delivery Framework. Every decision, tool choice and process design should be traceable back to these.

## Core principles

```text
1. Validation over speed: shipping faster is never more important than shipping correctly.
2. Reproducibility: every experiment, training run and deployment must be reproducible from recorded inputs.
3. Transparency: decisions are documented, not assumed. Models carry explanations, not just predictions.
4. Fairness by design: bias is checked before deployment, not after a incident.
5. Human oversight: no model makes high-impact decisions without a human-in-the-loop path.
6. Incremental trust: models earn deployment stages through evidence, not time served.
7. Operational excellence: a model that cannot be monitored, rolled back or explained is not production-ready.
8. Cost awareness: every training run and inference endpoint has a cost; optimise deliberately.
9. Security first: models are attack surfaces; protect data, weights and endpoints.
10. Sustainability: measure and reduce the environmental cost of training and inference.
```

## How principles map to lifecycle stages

| Principle | How it is enforced | Evidence |
|---|---|---|
| Validation over speed | Gate criteria in every lifecycle stage; no promotion without passing tests | `validation.md` gate criteria, `tests/test_*.py` |
| Reproducibility | DVC for data versioning, MLflow for experiment tracking, code + data + model hash logged | `implementation/dvc-config.md`, `implementation/mlflow/train.py` |
| Transparency | Model cards auto-generated from MLflow metadata, decision logs in audit trail | `implementation/api/src/main/java/com/mlplatform/webhooks/ExplainabilityResource.java`, `auditability.md` |
| Fairness by design | Fairlearn metrics computed before every staging promotion | `implementation/api/src/main/java/com/mlplatform/webhooks/FairnessResource.java`, `validation.md` fairness section |
| Human oversight | HITL review queue for low-confidence predictions, override mechanism | `human-in-the-loop.md`, `implementation/api/src/main/java/com/mlplatform/webhooks/HealthResource.java` |
| Incremental trust | Promotion gates: dev→staging→production with evidence at each step | `model-registry.md` promotion rules |
| Operational excellence | Grafana dashboards, Prometheus alerts, drift monitoring running continuously | `implementation/monitoring/`, `implementation/drift/monitor.py` |
| Cost awareness | CodeCarbon tracks CO2 per training run, cost logged in MLflow | `implementation/mlflow/train.py`, `finops.md` |
| Security | Threat model, API key auth, container scanning (Trivy), input validation | `security.md`, `implementation/.github/workflows/ci.yml` |
| Sustainability | Efficient model techniques documented, cost tracking | `implementation/mlflow/train.py`, `scalability.md` |
