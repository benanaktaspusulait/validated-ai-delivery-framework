# Human-in-the-Loop

Incorporating human feedback into the model lifecycle.

## When human oversight is required

```text
Mandatory (regulatory or policy):
  - High-stakes decisions (credit, hiring, healthcare).
  - EU AI Act high-risk classification.
  - When model confidence is low.
  - When fairness concerns are flagged.

Recommended:
  - Model first deployed (shadow/soft launch phase).
  - Prediction errors with high business impact.
  - Edge cases not seen in training data.
```

## HITL patterns

| Pattern | Description | Use case |
|---|---|---|
| Human review queue | Predictions flagged for human review | Low-confidence predictions |
| Active learning | Human labels uncertain examples; model retrains | Labelling efficiency |
| Approval gate | Human approves model output before action | High-stakes decisions |
| Feedback loop | User feedback collected and fed back to training | Continuous improvement |
| Override | Human overrides model prediction; logged | Edge cases, errors |

## Feedback collection

```text
In-product feedback:
  - "Was this prediction helpful?" (thumbs up/down).
  - "Suggest a correction" (text input).
  - Feedback tied to prediction ID for traceability.

Expert review:
  - Regular review of model errors by domain experts.
  - Label corrections fed back into training data.
  - Expert feedback tracked as a dataset version.

Escalation:
  - When model is uncertain: route to human.
  - When model is wrong: log correction, flag for retraining.
  - When user disputes: human reviews, decision logged.
```

## Active learning workflow

```text
1. Model predicts on unlabeled data.
2. Select most uncertain predictions (entropy, margin, or confidence threshold).
3. Route to human labeller.
4. Human labels; label stored with provenance.
5. Retrain model with new labels.
6. Evaluate improvement; if significant, deploy.
7. Repeat.
```
