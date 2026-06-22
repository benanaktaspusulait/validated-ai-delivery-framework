# Validation Report Template

## Model under validation

| Property | Value |
|---|---|
| Model name | |
| Version | |
| Validation date | |
| Validated by | |

## Robustness tests

| Test | Input type | Result | Details |
|---|---|---|---|
| Adversarial perturbation | | Pass/Fail | |
| Missing features | | Pass/Fail | |
| Out-of-distribution | | Pass/Fail | |
| Corrupted input | | Pass/Fail | |
| Load test (latency) | | Pass/Fail | p95 = ms |
| Load test (throughput) | | Pass/Fail | req/s |

## Fairness validation

| Metric | Group A | Group B | Disparity | Threshold | Pass/Fail |
|---|---|---|---|---|---|
| Demographic parity | | | | > 0.8 | |
| Equalised odds | | | | > 0.8 | |
| Predictive parity | | | | > 0.8 | |

## Explainability validation

- [ ] Explanations generated for sample predictions.
- [ ] Explanations are consistent and plausible.
- [ ] Key features align with domain expectations.
- [ ] Explanations available in production API.

## A/B test results (if applicable)

| Property | Value |
|---|---|
| Sample size | |
| Duration | |
| Primary metric (control) | |
| Primary metric (treatment) | |
| Statistical significance | p = |
| Business impact | |

## Shadow deployment results

| Property | Value |
|---|---|
| Duration | |
| Predictions compared | |
| Agreement rate | |
| Disagreement analysis | |

## Security testing

| Test | Result | Details |
|---|---|---|
| Adversarial robustness | Pass/Fail | |
| Data leakage | Pass/Fail | |
| API security | Pass/Fail | |
| Input validation | Pass/Fail | |

## Overall decision

```text
Decision: APPROVE / REJECT / CONDITIONAL
Conditions (if conditional):
Justification:
```

## Approvals

| Role | Name | Date | Decision |
|---|---|---|---|
| ML lead | | | |
| Security | | | |
| Compliance | | | |
