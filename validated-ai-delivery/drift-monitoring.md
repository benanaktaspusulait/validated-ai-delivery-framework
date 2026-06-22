# Drift Monitoring

Detecting data drift, concept drift and model degradation in production.

## Types of drift

| Type | Definition | Detection method | Example |
|---|---|---|---|
| Data drift | Input distribution changes | Statistical tests (KS, PSI, Jensen-Shannon) | User demographics shift |
| Concept drift | Relationship between inputs and target changes | Performance monitoring, error rate tracking | Fraud patterns evolve |
| Model decay | Model performance degrades over time | Metric tracking against baseline | Seasonal patterns not captured |
| Feature drift | Individual feature distribution changes | Per-feature statistical tests | One feature becomes stale |

## Monitoring setup

```text
Baseline: model performance and input distribution at deployment time.
Monitoring window: last 7 days (configurable).
Alert thresholds:
  - Data drift: PSI > 0.2 (significant), PSI > 0.4 (critical)
  - Concept drift: primary metric drops > 5% from baseline
  - Model decay: primary metric drops > 10% from baseline
  - Feature drift: any feature PSI > 0.3
```

## Tools

| Tool | Type | Strengths |
|---|---|---|
| Evidently AI | Open-source | Reports, dashboards, integrations |
| WhyLabs | SaaS | Centralised monitoring, alerts |
| NannyML | Open-source | Performance estimation without ground truth |
| Grafana + Prometheus | Self-hosted | Custom dashboards, alerting |

## Response playbooks

```text
Data drift detected:
  1. Identify which features drifted.
  2. Check if the drift is expected (seasonality, new market).
  3. If unexpected: investigate data pipeline for errors.
  4. If real drift: schedule retraining with new data.

Concept drift detected:
  1. Check if the target definition has changed.
  2. Analyse recent errors for patterns.
  3. If confirmed: trigger retraining pipeline.
  4. If model cannot adapt: escalate to ML lead.

Model decay detected:
  1. Compare current vs. baseline metrics.
  2. Check for infrastructure issues (latency, errors).
  3. If metrics genuinely degraded: rollback to previous version.
  4. Investigate root cause before retraining.
```
