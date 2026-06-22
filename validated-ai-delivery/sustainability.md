# Sustainability (Green AI)

Measuring and reducing the environmental cost of ML workloads.

## Carbon footprint tracking

```text
What to measure:
  - Training: GPU hours × carbon intensity of electricity.
  - Inference: compute hours × carbon intensity.
  - Data transfer: GB transferred × network carbon intensity.

Tools:
  - CodeCarbon: tracks CO2 emissions of Python code.
  - Cloud provider carbon dashboards (AWS, GCP, Azure).
  - Custom tracking: log energy consumption per run.
```

## Reduction strategies

```text
Training:
  - Use efficient architectures (smaller models, distillation).
  - Early stopping (avoid over-training).
  - Use renewable-energy regions for training.
  - Spot instances (utilise spare capacity, lower marginal carbon).
  - Share compute: reuse embeddings, feature stores.

Inference:
  - Right-size instances (do not over-provision).
  - Batch when real-time is not required.
  - Quantise models (smaller models = less compute).
  - Cache predictions (avoid redundant inference).
  - Scale down during low-traffic periods.

Data:
  - Compress datasets.
  - Use efficient storage formats (Parquet, not CSV).
  - Deduplicate data.
```

## Reporting

```text
Per model:
  - Total training CO2 (kg).
  - Monthly inference CO2 (kg).
  - Comparison: model CO2 vs. business value.
  - Trend: is CO2 decreasing as models improve?

Organisational:
  - Total ML carbon footprint per quarter.
  - Percentage from training vs. inference.
  - Targets: reduce by X% per year.
```
