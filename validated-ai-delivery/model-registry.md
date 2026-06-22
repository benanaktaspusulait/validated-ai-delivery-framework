# Model Registry

How models are versioned, staged, approved and promoted through their lifecycle.

## Lifecycle stages

```text
Development → Staging → Production → Archived
     |            |          |           |
   (training)  (validation) (serving)  (retired)
```

## Metadata schema

Every registered model must carry:

```yaml
model_name: "credit-scoring-v2"
version: "2.3.1"
stage: "production"  # development | staging | production | archived

# Lineage
training_dataset: "datasets/credit-v2.1.parquet"
training_code_commit: "abc1234"
experiment_run_id: "mlflow-run-456"

# Metrics
primary_metric: { name: "auc", value: 0.891 }
secondary_metrics:
  - { name: "precision", value: 0.84 }
  - { name: "recall", value: 0.79 }
  - { name: "fairness_parity", value: 0.92 }

# Approval
approved_by: "ml-lead@company.com"
approval_date: "2025-02-15"
approval_notes: "Passed all validation gates. Fairness within bounds."

# Deployment
deployed_at: "2025-02-16T10:00:00Z"
deployment_target: "production-api-v2"
rollback_version: "2.2.0"

# Cost
training_cost_usd: 45.20
inference_cost_per_1k_predictions: 0.12
```

## Promotion rules

| From | To | Required |
|---|---|---|
| Development | Staging | Validation report complete; all gate criteria pass |
| Staging | Production | Shadow deployment passed; approval from ML lead + ops |
| Production | Archived | New version deployed; monitoring stable for 7 days |

## Approval gates

```text
Development → Staging:
  - Validation report completed
  - Fairness metrics within bounds
  - No critical security findings
  - Model card complete

Staging → Production:
  - Shadow deployment: disagreement rate < 5%
  - API SLAs met in staging
  - Rollback mechanism tested
  - ML lead + operations approval
  - Runbook documented
```

## Rollback

```text
Automatic rollback triggers:
  - SLO breach (latency > p95 threshold for > 5 minutes)
  - Prediction quality drop > 10% from baseline
  - Data drift alert (critical severity)
  - Security incident

Manual rollback:
  - One command: promote previous version to production
  - Previous version is always kept in registry (minimum 2 versions)
```
