# Stakeholder Communication

How to communicate model status, results and decisions to different audiences.

## Communication matrix

| Audience | What they need | Format | Frequency |
|---|---|---|---|
| ML team | Technical details, experiments, metrics | Dashboard, experiment logs | Daily |
| Engineering team | API status, SLOs, incidents | Runbook, alerts | On events |
| Product team | Business impact, A/B results, user feedback | Report, presentation | Weekly |
| Leadership | ROI, risk posture, rollout progress | Executive summary | Monthly |
| Compliance | Audit trail, fairness reports, regulatory status | Compliance report | Quarterly |
| End users | "AI is used to X. You can request a human review." | In-product notice | Always |

## Report templates

```text
Weekly ML status report:
  - Models in production: count, health status.
  - Recent deployments: what changed, outcome.
  - Drift alerts: any active alerts, response status.
  - Cost: training and inference spend vs. budget.
  - Risks: any open issues, blockers.

Monthly executive summary:
  - Business value delivered (cost savings, revenue impact).
  - Model performance trends.
  - Compliance status.
  - Upcoming changes and roadmap.
  - Budget status.

Quarterly compliance report:
  - EU AI Act compliance status.
  - Fairness audit results.
  - Incident summary.
  - Audit log summary.
  - Recommendations.
```

## Escalation paths

```text
Model prediction error affecting users:
  1. Alert to on-call ML engineer.
  2. If unresolved in 15 min: escalate to ML lead.
  3. If user-facing impact: notify product team.
  4. If compliance impact: notify compliance officer.

Fairness concern raised:
  1. Log the concern.
  2. ML lead investigates within 48 hours.
  3. If confirmed: model paused; remediation plan created.
  4. If disputed: escalation to ethics committee (if exists).
```
