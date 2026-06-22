# Compliance

Regulatory and ethical compliance checklist — EU AI Act, GDPR and related frameworks.

## EU AI Act classification

| Risk level | Description | Requirements |
|---|---|---|
| Unacceptable | Social scoring, subliminal manipulation | Prohibited |
| High-risk | Credit scoring, hiring, law enforcement, education | Full compliance required |
| Limited risk | Chatbots, emotion recognition | Transparency obligations |
| Minimal risk | Spam filters, games | No specific requirements |

## High-risk AI requirements (EU AI Act)

```text
Before deployment:
  [ ] Risk management system established.
  [ ] Data governance: training data meets quality criteria.
  [ ] Technical documentation complete.
  [ ] Record-keeping: automatic logging of operations.
  [ ] Transparency: users are informed they are interacting with AI.
  [ ] Human oversight: mechanisms for human intervention.
  [ ] Accuracy, robustness and cybersecurity: appropriate levels ensured.

Post-deployment:
  [ ] Post-market monitoring system active.
  [ ] Incident reporting to authorities.
  [ ] Regular updates and re-validation.
```

## GDPR requirements for AI

```text
Data processing:
  [ ] Lawful basis established (consent, legitimate interest, contract).
  [ ] Data minimisation: only collect what is needed.
  [ ] Purpose limitation: data used only for stated purpose.
  [ ] Storage limitation: data deleted when no longer needed.

Individual rights:
  [ ] Right to access: individuals can request their data.
  [ ] Right to erasure: data can be deleted ("right to be forgotten").
  [ ] Right to explanation: individuals can request meaningful information about automated decisions.
  [ ] Right to contest: individuals can challenge automated decisions.

Technical measures:
  [ ] Pseudonymisation of personal identifiers.
  [ ] Encryption at rest and in transit.
  [ ] Data protection impact assessment (DPIA) for high-risk processing.
```

## Compliance checklist by lifecycle stage

| Stage | Compliance action |
|---|---|
| Discovery | EU AI Act classification; DPIA if high-risk |
| Data | GDPR lawful basis; data minimisation; retention policy |
| Model | Bias audit; documentation; audit trail |
| Validation | Fairness validation; explainability; human oversight |
| Integration | Transparency notices; monitoring; incident reporting |
| Operations | Post-market monitoring; regular re-validation; audit logs |

## Audit trail

```text
Every significant action must be logged:
  - Who performed the action.
  - When it was performed.
  - What changed (before/after).
  - Why it was performed (justification).

Logs must be:
  - Immutable (append-only).
  - Retained for the required period (typically 3-5 years).
  - Accessible to auditors.
```
