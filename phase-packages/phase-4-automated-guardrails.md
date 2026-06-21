# Phase 4 - Automated Guardrails

Operating mode: Enforcement Mode. Selective blocking after warnings are calibrated. Emergency Override is always available.

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
Engineering: AI Metadata Blocker, risk-based reviewer assignment, prompt leakage comment-mode scanner (dominant track).
Metrics and risk: Dynamic AI WIP recommendation including the high-risk multiplicative penalty.
Governance and reporting: emergency override audit log and override retro.
Culture and people: keep enforcement explainable; watch for hidden AI usage.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| AI Metadata Blocker | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| High-risk reviewer assignment | Platform Engineer | Platform Lead | Security Lead, Tech Lead | Engineering Manager |
| Dynamic AI WIP recommendation | Platform Engineer | Platform Lead | Engineering Manager | Pilot team |
| Emergency override audit + retro | Pilot-team EM | Platform Lead | Tech Lead | Data Steward |
| Prompt leakage comment-mode scanner | Platform Engineer | Security Lead | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 3 exit criteria passed.
Warning thresholds are calibrated.
Emergency override logging is implemented.
```

## Actions

```text
Week 11: Enable the AI Metadata Blocker (examples/github-actions/ai-metadata-enforcement.yml). Usage type and Low/Medium/High confidence are required before merge.
Week 12: Require code owner and senior reviewer for risk score >= 10 (examples/github-actions/risk-reviewer-assignment.yml).
Week 13: Publish the Dynamic AI WIP recommendation to Jira or GitHub Projects.
Week 14: Run an override retro if emergency-fix was used more than 3 times in the sprint.
Enable prompt leakage scanning in comment-only mode.
Ship the Policy Settings rule-editor screen and the Recommendations / Playbook screen.
Create the policy_overrides table.
```

## Deliverables

```text
[Phase4]_exit_report.pdf
[Phase4]_data_dictionary.json
[Phase4]_config_changes.yaml
AI Metadata Blocker
Emergency override audit log (policy_overrides)
High-risk reviewer assignment
Dynamic AI WIP recommendation
Override retro notes where applicable
Prompt leakage comment-mode scanner
Policy Settings rule-editor screen
Recommendations / Playbook screen
Policy test suite and low-confidence enforcement regression tests
Policy observability counters and alert thresholds
```

## Exit Criteria

```text
Pilot team's Net AI Delivery Value is positive or trending positive.
Emergency-fix usage is below 5% of total PRs.
No production emergency fix has been delayed by the metadata blocker.
Dynamic AI WIP recommendation is visible and explainable.
```

## Fail Criteria

```text
Dynamic AI WIP repeatedly falls below the configured minimum of 2.
Emergency fixes are delayed.
Emergency-fix usage exceeds 5% of total PRs without a clear operational reason.
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
docs/risk-policy-engine.md       - policy engine rules, Dynamic AI WIP, prompt leakage, override retro, playbook
examples/policy-config.yaml      - policy set, security controls and declarative rules
examples/github-actions/         - ai-metadata-enforcement.yml and risk-reviewer-assignment.yml
docs/api-spec.md                 - Phase 4 policies and policy-overrides endpoints
docs/data-model.md               - policy_overrides table
docs/testing-and-observability.md - Phase 4 tests and signals
Master framework: sections 7.4, 8, 11, 14, 15, 21
```
