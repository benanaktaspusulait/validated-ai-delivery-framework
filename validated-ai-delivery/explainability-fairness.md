# Explainability and Fairness

Making models understandable and ensuring they do not discriminate.

## Explainability methods

| Method | Type | Use case | Tool |
|---|---|---|---|
| SHAP | Global + local | Feature importance, prediction explanations | shap |
| LIME | Local | Individual prediction explanations | lime |
| Captum | Global + local | PyTorch model interpretability | captum |
| Feature importance | Global | Simple model understanding | scikit-learn |
| Partial dependence | Global | Feature effect on prediction | scikit-learn |
| Attention weights | Local | Transformer model explanations | model-specific |

## When to explain

```text
Always:
  - High-stakes decisions (credit, hiring, healthcare).
  - Regulatory requirements (EU AI Act high-risk).
  - When stakeholders ask "why?".

Sometimes:
  - Debugging model errors.
  - Model selection (understanding what the model learned).

Rarely:
  - Low-stakes recommendations (content ranking).
  - When the model is a simple baseline.
```

## Fairness metrics

| Metric | Definition | When to use |
|---|---|---|
| Demographic parity | Equal prediction rates across groups | When outcomes should be equal |
| Equalised odds | Equal TPR and FPR across groups | When errors should be equal |
| Predictive parity | Equal PPV across groups | When precision should be equal |
| Calibration | Predicted probabilities match actual rates | When probability estimates matter |

## Fairness tools

| Tool | Type | Strengths |
|---|---|---|
| Fairlearn | Open-source | Python, scikit-learn compatible, mitigation algorithms |
| AIF360 | Open-source | Comprehensive metrics, bias mitigation |
| What-If Tool | Open-source | Visual exploration of model behaviour |

## Fairness report structure

```text
1. Protected groups identified (age, gender, race, etc.).
2. Metrics computed for each group.
3. Disparity ratios calculated.
4. Thresholds: disparity ratio > 0.8 is acceptable (configurable).
5. Trade-off analysis: fairness vs accuracy.
6. Decision: accept, mitigate or reject.
7. Mitigation actions (if needed): resampling, reweighting, adversarial debiasing.
```
