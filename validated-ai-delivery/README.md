# Validated AI Delivery Framework

A process-oriented framework for delivering AI/ML solutions with validated, production-grade quality. Covers the full lifecycle from discovery to integration, with emphasis on robustness, fairness, explainability and operational excellence.

## Lifecycle stages

| Stage | Focus | Canonical reference |
|---|---|---|
| 1. Discovery | Problem definition, feasibility, success criteria | [discovery.md](discovery.md) |
| 2. Data | Sources, quality, versioning, labelling | [data.md](data.md) |
| 3. Model | Training, evaluation, selection, experiment tracking | [model.md](model.md) |
| 4. Validation | Robustness, fairness, explainability, A/B testing | [validation.md](validation.md) |
| 5. Integration | API, CI/CD/CT, monitoring, handover | [integration.md](integration.md) |

## Cross-cutting concerns

| Topic | Reference |
|---|---|
| Founding principles | [principles.md](principles.md) |
| Reference architecture and tech stack | [architecture.md](architecture.md) |
| Model registry and lifecycle | [model-registry.md](model-registry.md) |
| Drift monitoring | [drift-monitoring.md](drift-monitoring.md) |
| Security and MLSecOps | [security.md](security.md) |
| Scalability and infrastructure | [scalability.md](scalability.md) |
| Testing strategy | [testing-strategy.md](testing-strategy.md) |
| Explainability and fairness tools | [explainability-fairness.md](explainability-fairness.md) |
| Cost optimisation and FinOps | [finops.md](finops.md) |
| Compliance (EU AI Act, GDPR) | [compliance.md](compliance.md) |
| Disaster recovery | [disaster-recovery.md](disaster-recovery.md) |
| Stakeholder communication | [stakeholder-communication.md](stakeholder-communication.md) |
| Experiment tracking | [experiment-tracking.md](experiment-tracking.md) |
| Human-in-the-loop | [human-in-the-loop.md](human-in-the-loop.md) |
| Multi-model and multi-environment | [multi-model.md](multi-model.md) |
| Root cause analysis | [root-cause-analysis.md](root-cause-analysis.md) |
| Sustainability and green AI | [sustainability.md](sustainability.md) |
| Community and contributing | [CONTRIBUTING.md](CONTRIBUTING.md) |
| UI/UX and visualisation | [ui-ux.md](ui-ux.md) |
| Feature store | [feature-store.md](feature-store.md) |
| Auditability and audit logs | [auditability.md](auditability.md) |

## Templates

Reusable artefacts for each lifecycle stage:

| Template | Stage |
|---|---|
| [discovery_template.md](templates/discovery_template.md) | Discovery |
| [data_report_template.md](templates/data_report_template.md) | Data |
| [model_card_template.md](templates/model_card_template.md) | Model |
| [validation_report_template.md](templates/validation_report_template.md) | Validation |
| [handover_template.md](templates/handover_template.md) | Integration |
| [architecture_decision_record.md](templates/architecture_decision_record.md) | Cross-cutting |
| [incident_postmortem_template.md](templates/incident_postmortem_template.md) | Operations |

## Quick start

```text
1. Read principles.md — understand the founding principles.
2. Start with discovery.md — define the problem and success criteria.
3. Follow the lifecycle stages in order (1→5).
4. Use cross-cutting references as needed.
5. Fill in templates at each stage gate.
```
