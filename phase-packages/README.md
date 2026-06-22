# Phase Packages

Stage-gate implementation plan for the AI Delivery Control Plane. Each phase is a mini-project with entry/exit/fail criteria and a gate.

## Relationship between phase-packages and implementation

```text
phase-packages/  = WHAT to build and WHEN to proceed (gate documents)
implementation/  = HOW to build it (task-level breakdown with code, SQL, tests)

Read phase-packages first to understand the plan.
Read implementation/ when you're ready to start coding.
```

## Master stage-gate map

| Phase | Name | Duration | Mode | Gate | Tasks |
|---:|---|---:|---|---|---|
| 0 | Groundwork and Legal Assurance | 2 weeks | Readiness | Legal/HR sign-off + safety baseline >= 3.5 | 8 tasks |
| 1 | Data Architecture and Raw Collection | 3 weeks | Observation | Data Confidence Score >= 75 | 11 tasks |
| 2 | Metrics and Risk Engine | 3 weeks | Observation | Core metrics validated | 17 tasks |
| 3 | Soft Landing and Experiment | 4 weeks | Warning / Recommendation | Psychological safety >= 3.5 | 10 tasks |
| 4 | Automated Guardrails | 4 weeks | Enforcement | Positive VDT trend | 14 tasks |
| 5 | Enterprise Rollout | Continuous | Staged | >= 30% teams active | 13 tasks |

## Exit criteria at a glance

| Phase | Headline measurable gate |
|---:|---|
| 0 | Legal/HR approval stored; safety baseline >= 3.5 |
| 1 | >= 90% PRs ingested; Data Confidence >= 75; reconciliation < 5% |
| 2 | Manual 50-PR sample within 30% of computed rate; dashboard matches reality |
| 3 | Safety >= 3.5; review time not up > 20% without quality gain |
| 4 | VDT neutral-to-positive over 90 days; emergency-fix < 5% |
| 5 | >= 30% teams active; support < 5/wk; all new teams in Observation |

## Work tracks

```text
Engineering: integrations, collectors, storage, APIs, dashboards and automation.
Metrics and risk: calculation logic, confidence scoring, risk scoring and policy signals.
Culture and people: psychological safety, onboarding, communication and team feedback.
Governance and reporting: legal/privacy readiness, leadership reporting and policy calibration.
```

## Stage-gate rule

```text
At the end of every phase, the Platform Lead and pilot-team Engineering Manager must answer:
"Would moving to the next phase destabilise the current system?"
If yes, the current phase repeats or a bug-fix sprint is inserted.
Exit criteria must not be relaxed because the calendar is tight.
```

## Required deliverables (every phase)

```text
[PhaseX]_exit_report.pdf      - 2-page decision summary for platform, engineering and leadership.
[PhaseX]_data_dictionary.json - schema, source, ownership, confidence and retention changes.
[PhaseX]_config_changes.yaml  - policy, threshold, connector and team-configuration updates.
```

## First three actions

```text
1. Block 2 calendar weeks for Phase 0: legal/privacy, HR/People and pilot-team readiness.
2. Create GitHub and Jira service accounts with the minimum permissions required.
3. Meet the pilot team lead: "We are not measuring individual productivity. We are validating whether the system can use AI safely."
```

## Cross-phase references

```text
docs/rollout-operating-model.md  - operating modes, weekly RACI, pause criteria, governance forum
docs/ui-ux-spec.md               - role-based views and the screen-to-phase map
docs/data-confidence.md          - confidence scoring and presentation tiers
docs/testing-and-observability.md - per-phase test and observability requirements
docs/governance-and-privacy.md   - non-negotiable rules and privacy controls
implementation/                  - task-level breakdown with technical details
```
