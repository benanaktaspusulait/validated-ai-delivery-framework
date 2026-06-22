# Cost Optimisation and FinOps

Tracking, managing and optimising the cost of ML workloads.

## Implementation

```text
See implementation/mlflow/train.py for training cost tracking. Carbon tracking is integrated into the training pipeline.
CO2 tracking is automatic for every training run when using the CarbonTracker context manager.
Cost data is logged to MLflow alongside metrics.
```

| Category | What it includes | How to track |
|---|---|---|
| Training compute | GPU/TPU hours, instance costs | Cloud billing, experiment tracker |
| Inference compute | API endpoint costs, serverless | Cloud billing, monitoring |
| Storage | Data, models, logs, artefacts | Cloud billing |
| Data labelling | Human labour, annotation tools | Project management |
| Tooling | MLflow, W&B, monitoring SaaS | Subscription tracking |
| Operations | Team time, on-call, support | Time tracking |

## Cost tracking

```text
Per training run:
  - Log: start time, end time, instance type, GPU hours, total cost.
  - Use experiment tracker (MLflow, W&B) to associate cost with run.
  - Compare cost vs. accuracy improvement: is the improvement worth the cost?

Per model:
  - Training cost: total cost to develop and validate.
  - Inference cost: cost per prediction × expected volume.
  - Total cost of ownership: training + inference + operations over model lifetime.

Per endpoint:
  - Monthly cost = instance cost + storage + data transfer + monitoring.
  - Cost per 1000 predictions = monthly cost / (predictions per month / 1000).
```

## Optimisation strategies

```text
Training:
  - Use spot/preemptible instances (60-80% savings).
  - Early stopping: stop training when validation metric plateaus.
  - Hyperparameter search budget: set max runs and max time.
  - Use smaller datasets for initial experiments; scale up for final training.

Inference:
  - Right-size instances: do not use GPU for CPU-suitable models.
  - Batch predictions when real-time is not required.
  - Cache frequent predictions.
  - Use quantisation or distillation for smaller, cheaper models.
  - Auto-scale: scale down during low-traffic periods.

Storage:
  - Lifecycle policies: move old data to cheaper storage tiers.
  - Delete unneeded artefacts (old model versions, intermediate datasets).
  - Compress datasets where possible.
```

## FinOps dashboard

```text
Display:
  - Monthly ML spend by category (training, inference, storage).
  - Cost per prediction trend.
  - Cost vs. business value (revenue, cost savings).
  - Budget vs. actual (alert at 80% and 100%).
  - Cost anomalies (sudden spikes).
```
