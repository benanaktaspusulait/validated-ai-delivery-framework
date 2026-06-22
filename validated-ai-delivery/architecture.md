# Reference Architecture and Tech Stack

Recommended tools and infrastructure patterns for each lifecycle stage.

## Conceptual architecture

```text
Discovery → Data → Model → Validation → Integration
    |          |       |        |           |
    v          v       v        v           v
  Docs      DVC/    MLflow/  Fairness   API Gateway
  Templates Delta   W&B     + SHAP     + Monitoring
            Lake              + Tests    + Registry
```

## Tool recommendations by stage

| Stage | Category | Recommended tools | Alternatives |
|---|---|---|---|
| Discovery | Documentation | Confluence, Notion, Markdown | Google Docs |
| Data | Versioning | DVC, Delta Lake, LakeFS | Pachyderm, Git LFS |
| Data | Quality | Great Expectations, Pandera | Deequ, TFX Data Validation |
| Data | Feature store | Feast, Tecton | Hopsworks, FeatureStore |
| Model | Experiment tracking | MLflow, Weights & Biases | Neptune, Comet ML |
| Model | Training | PyTorch, TensorFlow, scikit-learn | JAX, XGBoost |
| Model | Pipeline orchestration | Airflow, Kubeflow Pipelines | Prefect, Dagster, Argo Workflows |
| Validation | Fairness | Fairlearn, AIF360 | What-If Tool |
| Validation | Explainability | SHAP, LIME, Captum | Alibi, InterpretML |
| Validation | Adversarial | ART (Adversarial Robustness Toolbox) | Foolbox |
| Integration | Model serving | Seldon Core, BentoML, Triton | TensorFlow Serving, TorchServe |
| Integration | API gateway | Kong, Envoy, AWS API Gateway | NGINX |
| Integration | Monitoring | Evidently AI, WhyLabs, Grafana | NannyML, Prometheus |
| Integration | CI/CD | GitHub Actions, GitLab CI | Jenkins, CircleCI |
| Integration | Registry | MLflow Model Registry | Vertex AI Model Registry, SageMaker |
| Integration | Feature store | Feast | Tecton, Hopsworks |

## Infrastructure patterns

### Pilot (1-2 models, small team)

```text
Compute: single VM or managed service (SageMaker, Vertex AI)
Database: managed PostgreSQL
Storage: S3 or equivalent
Orchestration: Airflow (managed) or cron
Monitoring: Grafana + Prometheus
Cost: low (pay-per-use)
```

### Department (5-10 models, 1-2 teams)

```text
Compute: Kubernetes (EKS/GKE/AKS) or managed ML platform
Database: PostgreSQL with read replica
Storage: S3 with lifecycle policies
Orchestration: Airflow or Kubeflow Pipelines
Feature store: Feast
Monitoring: Evidently AI + Grafana
Cost: moderate (reserved instances)
```

### Enterprise (10+ models, multiple teams)

```text
Compute: Kubernetes with GPU node pools
Database: PostgreSQL cluster + time-series DB
Storage: S3 with intelligent tiering
Orchestration: Kubeflow or Argo Workflows
Feature store: Tecton or Feast (centralised)
Monitoring: WhyLabs or Evidently AI (centralised)
Model registry: MLflow or Vertex AI
Cost: significant (requires FinOps — see finops.md)
```
