# Model

Stage 3: develop, train, evaluate and select models with rigorous experiment tracking.

## Purpose

```text
Build models systematically with tracked experiments, clear evaluation criteria and documented selection rationale.
```

## Activities

```text
1. Experiment tracking
   - Log every training run: hyperparameters, dataset version, code version, metrics.
   - Use MLflow, Weights & Biases or equivalent.
   - Compare experiments side-by-side; never select a model without comparison.

2. Training pipeline
   - Define the training pipeline as code (not notebooks).
   - Pipeline stages: data load → preprocessing → feature engineering → training → evaluation → registration.
   - Pipeline must be idempotent: same inputs → same outputs.

3. Evaluation metrics
   - Define primary metric (the one you optimise for).
   - Define secondary metrics (precision, recall, F1, AUC, latency, cost).
   - Define fairness metrics (demographic parity, equalised odds).
   - Define business metrics (revenue impact, user satisfaction).

4. Model selection
   - Select based on primary metric + constraints (latency, cost, fairness).
   - Document why the selected model was chosen over alternatives.
   - Document known limitations and failure modes.

5. Model card
   - Complete a model card for every model that goes to validation.
   - Use templates/model_card_template.md.
   - Model card travels with the model through staging → production.

6. Hyperparameter optimisation
   - Use structured search (Bayesian, grid, random).
   - Budget the optimisation: max runs, max time, max compute cost.
   - Record all tried configurations, not just the winner.
```

## Gate criteria (before proceeding to Validation)

```text
- [ ] Experiment tracking active; all runs logged.
- [ ] Primary metric meets minimum threshold.
- [ ] Model card completed.
- [ ] Training pipeline is reproducible from recorded inputs.
- [ ] Known limitations documented.
- [ ] Training cost logged.
```

## Deliverables

```text
- Completed model_card_template.md
- Experiment comparison report
- Selected model with metadata in model registry
```

## References

```text
experiment-tracking.md - tool comparison, metric logging
model-registry.md - model versioning and staging
explainability-fairness.md - fairness metrics
finops.md - training cost tracking
```
