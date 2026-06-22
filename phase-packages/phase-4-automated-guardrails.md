# Phase 4 - Automated Guardrails

Operating mode: Enforcement Mode. Selective blocking after warnings are calibrated. Emergency Override always available.

## Purpose

```text
Enable calibrated, confidence-gated enforcement without blocking emergency delivery.
```

## Duration

```text
4 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Security Lead
Tech Lead
Pilot-team Engineering Manager
```

## Work Tracks (this phase)

```text
Engineering: AI Metadata Blocker, risk-based reviewer assignment, prompt leakage scanner (dominant track).
Metrics and risk: Dynamic AI WIP recommendation including high-risk multiplicative penalty.
Governance and reporting: emergency override audit log and override retro.
Culture and people: keep enforcement explainable; watch for hidden AI usage.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| AI Metadata Blocker | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| Risk-based reviewer assignment | Platform Engineer | Platform Lead | Security Lead, Tech Lead | Engineering Manager |
| Dynamic AI WIP recommendation | Platform Engineer | Platform Lead | Engineering Manager | Pilot team |
| Emergency override audit + retro | Pilot-team EM | Platform Lead | Tech Lead | Data Steward |
| Prompt leakage scanner | Platform Engineer | Security Lead | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 3 exit criteria passed.
Warning thresholds calibrated.
Emergency override logging implemented.
```

## Actions (high-level)

```text
Week 11: Enable AI Metadata Blocker (usage type + confidence required before merge).
Week 12: Require code owner + senior reviewer for risk score >= 10.
Week 13: Publish Dynamic AI WIP recommendation.
Week 14: Run override retro if emergency-fix used > 3 times.
         Enable prompt leakage scanning (comment-only).
         Ship Policy Settings and Recommendations/Playbook screens.
         Create policy_overrides table.
```

For detailed implementation steps, see [implementation/phase-4/](../implementation/phase-4/).

## Deliverables

```text
AI Metadata Blocker
Emergency override audit log (policy_overrides)
High-risk reviewer assignment
Dynamic AI WIP recommendation
Override retro notes (where applicable)
Prompt leakage scanner (comment-only)
Policy Settings screen
Recommendations / Playbook screen
Policy tests (confidence gate, precedence, override rate limiting)
Policy observability counters
[Phase4]_exit_report.pdf
[Phase4]_data_dictionary.json
[Phase4]_config_changes.yaml
```

## Exit Criteria

```text
VDT signal neutral-to-positive and trending up over 90 days (>= 3 sprint data points).
Emergency-fix usage < 5% of total PRs.
No production emergency fix delayed by metadata blocker.
Dynamic AI WIP recommendation visible and explainable.
```

## Fail Criteria

```text
Dynamic AI WIP repeatedly falls below minimum of 2.
Emergency fixes delayed.
Emergency-fix usage > 5% without clear operational reason.
Developers start hiding AI usage.
```

## Fail Action

```text
Pause enforcement.
Return affected policy to warning mode.
Revisit WIP formula, override process or communication.
```

## Gate Decision

```text
Proceed to Phase 5 only if guardrails improve safety without blocking emergency delivery or causing hidden AI usage.
```

## Reference Docs

```text
docs/risk-policy-engine.md       - policy rules, Dynamic AI WIP, prompt leakage, override retro
docs/data-confidence.md          - confidence gate, auto-disable/recovery
examples/policy-config.yaml      - policy set, security controls
examples/github-actions/         - ai-metadata-enforcement.yml, risk-reviewer-assignment.yml
docs/api-spec.md                 - Phase 4 endpoints (policies, overrides, recommendations PATCH)
docs/data-model.md               - policy_overrides, config_versions tables
docs/ui-ux-spec.md               - Policy Settings, Recommendations/Playbook screens
docs/testing-and-observability.md - Phase 4 tests and observability signals
implementation/phase-4/          - detailed task breakdown (T4.1-T4.13)
```
