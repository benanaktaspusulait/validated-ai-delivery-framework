# Expanded Metrics

Beyond the five core MVP metrics, later stages include:

| Metric | Definition |
|---|---|
| Cognitive Load Index | [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) |
| Dynamic AI WIP Limit | [docs/risk-policy-engine.md](../docs/risk-policy-engine.md) |

## Stage 4+ metrics (to be specified)

The following metrics are planned for Stage 4 but not yet fully specified:

| Metric | Status |
|---|---|
| Trust Calibration Gap | Definition pending |
| Prompt Efficiency Score | Definition pending |
| AI Test Quality Score | Definition pending |
| Maintainability Risk Score | Definition pending |

See [docs/metrics-catalogue.md](../docs/metrics-catalogue.md) for the expanded metrics table.

## AI Dependency Risk

```text
AI Dependency Risk = (AI-assisted PR Rate x AI Ownership Confidence Risk) / Team Size
Where: AI Ownership Confidence Risk = 1 - Ownership Confidence Score
       Ownership Confidence Score = developer understanding + edge-case test evidence
```

| Signal | Meaning |
|---|---|
| High AI usage + low ownership confidence | AI dependency risk is rising |
| Moderate AI usage + high ownership confidence | Healthy augmentation |
| Low AI usage + low ownership confidence | Training or workflow problem, not AI scale problem |

Teams with AI Dependency Risk > 0.5 should run a quarterly ownership review.

## AI-assisted PR Quality Gap

```text
AI Quality Gap = AI-assisted PR Defect Rate - Comparable Non-AI PR Defect Rate
Quality Gap Improvement = Previous 6-month Gap - Current 6-month Gap
```

| Trend | Meaning |
|---|---|
| Gap shrinking | AI-assisted delivery quality is improving |
| Gap flat | AI quality is not materially changing |
| Gap widening | AI-assisted work is diverging from expected quality |
