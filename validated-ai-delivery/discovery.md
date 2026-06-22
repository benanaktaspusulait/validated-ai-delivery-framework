# Discovery

Stage 1: define the problem, assess feasibility and agree success criteria before any code or data work begins.

## Purpose

```text
Ensure the right problem is being solved, with clear success criteria and stakeholder alignment, before committing resources.
```

## Activities

```text
1. Problem definition
   - What business decision or user outcome does this model support?
   - Who is the decision-maker? What happens if the model is wrong?
   - What is the cost of being wrong vs. the cost of not having the model?

2. Feasibility assessment
   - Is the required data available, accessible and sufficient?
   - Is the problem solvable with ML (vs. rules, heuristics or process change)?
   - What is the minimum viable model complexity?

3. Success criteria
   - Define quantitative metrics (accuracy, latency, cost targets).
   - Define qualitative criteria (explainability requirements, fairness constraints).
   - Define non-goals (what this model will NOT do).

4. Stakeholder alignment
   - Who approves the model for production?
   - Who monitors it post-deployment?
   - What is the escalation path for model failures?

5. Risk assessment
   - What are the ethical risks (bias, discrimination)?
   - What are the operational risks (latency, availability)?
   - What are the regulatory risks (EU AI Act high-risk classification)?
```

## Gate criteria (before proceeding to Data)

```text
- [ ] Problem statement documented (use templates/discovery_template.md).
- [ ] Success criteria agreed with stakeholders.
- [ ] Feasibility checklist passed.
- [ ] Risk assessment completed.
- [ ] Data availability confirmed (at least one viable source identified).
- [ ] Budget and timeline agreed.
- [ ] Regulatory classification determined (high-risk / limited / minimal).
```

## Deliverables

```text
- Completed discovery_template.md
- Feasibility report
- Stakeholder sign-off
```

## References

```text
principles.md - validation over speed, transparency
compliance.md - EU AI Act classification
```
