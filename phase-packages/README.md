# Phase Packages

Stage-gate implementation plan for the Validated AI Delivery Framework v1.8. Each phase is a mini-project with entry/exit/fail criteria and a gate. A phase does not start until the previous phase passes its gate.

Phase files are gate documents: what is done, by whom, with which criteria. Each phase file now includes an **Implementation guidance** section with concrete tech decisions, code structure suggestions, and step-by-step build instructions. Technical specs live in `../docs/` and copyable artefacts in `../examples/`; each phase links to the ones it needs under "Reference Docs".

## Master stage-gate map

| Phase | Name | Duration | Mode | Gate | Package |
|---:|---|---:|---|---|---|
| 0 | Groundwork and Legal Assurance | 2 weeks | Readiness | Legal/HR sign-off + safety baseline >= 3.5 | [phase-0-groundwork-and-legal-assurance.md](phase-0-groundwork-and-legal-assurance.md) |
| 1 | Data Architecture and Raw Collection | 3 weeks | Observation | Data Confidence Score >= 75 | [phase-1-data-architecture-and-raw-collection.md](phase-1-data-architecture-and-raw-collection.md) |
| 2 | Metrics and Risk Engine | 3 weeks | Observation | Core metrics validated | [phase-2-metrics-and-risk-engine.md](phase-2-metrics-and-risk-engine.md) |
| 3 | Soft Landing and Experiment | 4 weeks | Warning / Recommendation | Psychological safety > 3.5 | [phase-3-soft-landing-and-experiment.md](phase-3-soft-landing-and-experiment.md) |
| 4 | Automated Guardrails | 4 weeks | Enforcement | Positive VDT trend | [phase-4-automated-guardrails.md](phase-4-automated-guardrails.md) |
| 5 | Enterprise Rollout | Continuous | Staged | >= 30% teams active, support < 5/wk, all new teams in Observation | [phase-5-enterprise-rollout.md](phase-5-enterprise-rollout.md) |

## Exit criteria at a glance

The single most measurable gate per phase; each phase file holds the complete, measurable exit-criteria list.

| Phase | Headline measurable gate |
|---:|---|
| 0 | Written legal/privacy and HR approval stored; psychological safety baseline recorded and >= 3.5 (100% survey participation) |
| 1 | >= 90% of last-30-day PRs ingested with required fields; average Data Confidence Score >= 75; reconciliation gap < 5%; webhook p99 latency < 5 seconds |
| 2 | Manual 50-PR sample within 30% of computed AI-assisted PR Rate; pilot lead confirms the dashboard matches reality; all 5 core metrics computed for last 2 sprints |
| 3 | Psychological safety > 3.5 (same 6 questions); warned-PR review time not up more than 20% vs control without quality gain; A/B analysis statistically powered (n >= 20 per group) |
| 4 | VDT signal neutral-to-positive and trending up over 90 days (>= 3 sprint data points); emergency-fix usage < 5% of total PRs; zero emergency fixes delayed by the blocker |
| 5 | >= 30% of teams (or 10 teams) sending active data; support tickets < 5/week; every new team starts in Observation Mode; RBAC tested for all 6 roles |

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
../docs/rollout-operating-model.md  - operating modes, weekly RACI, pause criteria, governance forum, roadmap, confidence-by-phase
../docs/ui-ux-spec.md               - role-based views and the screen-to-phase map
../docs/data-confidence.md          - confidence scoring and presentation tiers
../docs/testing-and-observability.md - per-phase test and observability requirements
../docs/governance-and-privacy.md   - non-negotiable rules and privacy controls
```
