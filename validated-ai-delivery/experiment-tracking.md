# Experiment Tracking

Systematic tracking of ML experiments for reproducibility and comparison.

## What to log

```text
Every experiment run must log:
  - Experiment name and description.
  - Code version (git commit SHA).
  - Dataset version (DVC hash or equivalent).
  - Hyperparameters (full configuration).
  - Training metrics (loss, accuracy per epoch).
  - Validation metrics (primary + secondary).
  - Fairness metrics (if applicable).
  - Training duration and compute cost.
  - Model artefact (weights, architecture).
  - Notes: what was tried, what was learned.
```

## Tool comparison

| Tool | Type | Best for | Cost |
|---|---|---|---|
| MLflow | Open-source | Self-hosted, flexible | Free |
| Weights & Biases | SaaS | Collaboration, visualisation | Free tier + paid |
| Neptune.ai | SaaS | Metadata management | Paid |
| Comet ML | SaaS | Experiment comparison | Free tier + paid |

## Experiment comparison workflow

```text
1. Log all experiments (including failed ones).
2. After each batch of experiments:
   a. Open comparison view.
   b. Sort by primary metric.
   c. Check secondary metrics and fairness.
   d. Check cost and training time.
   e. Select candidate for validation.
3. Document selection rationale in model card.
4. Archive comparison results for audit trail.
```

## Naming conventions

```text
Experiment name: {stage}-{description}-{date}
  Example: "baseline-xgboost-2025-02-15"
  Example: "fairness-remedy-resampling-2025-02-16"

Run name: {experiment}-{variant}
  Example: "baseline-xgboost-lr0.01"
  Example: "baseline-xgboost-lr0.001"
```
