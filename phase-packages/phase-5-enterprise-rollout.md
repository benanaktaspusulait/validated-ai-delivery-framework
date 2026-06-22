# Phase 5 - Enterprise Rollout

Operating mode: Staged rollout across modes. Every new team starts in Observation Mode.

## Purpose

```text
Turn the validated pilot into an internal platform product.
```

## Duration

```text
Continuous after initial 2-week rollout setup.
```

## Owners

```text
Platform Team
Developer Experience Team
Security Lead
Data Steward
Engineering Managers
```

## Work Tracks (this phase)

```text
Engineering: self-service onboarding, retention purge job, multi-team dashboards.
Governance and reporting: quarterly governance forum, incident integration, executive reporting (dominant track).
Culture and people: every new team starts in observation mode; maturity labels stay non-ranking.
Metrics and risk: expand metrics per enterprise roadmap as confidence allows.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Self-service onboarding | DevEx Team | Platform Lead | Engineering Managers | New teams |
| Quarterly governance forum | Platform Lead | Platform team owner | Security Lead, Data Steward | CTO/VP Eng |
| Incident integration | Platform Engineer | Platform Lead | Security Lead | Engineering Managers |
| Retention purge job | Platform Engineer | Data Steward | Legal/privacy counsel | Platform Lead |
| Executive reporting | Platform Lead | Platform team owner | Engineering Managers | CTO/VP Eng |

## Entry Criteria

```text
Phase 4 exit criteria passed.
Pilot team recommends continuation.
Platform support load manageable.
```

## Actions (high-level)

```text
Publish self-service onboarding (team-config.yaml).
Confirm quick-start.md and faq.md are current.
Run first quarterly governance forum.
Enable retention purge job (12 months detailed, 24 months aggregates).
Ship Executive Summary, multi-team navigation, RBAC, self-service onboarding.
Wire incident-to-Jira-to-PR linkage.
```

For detailed implementation steps, see [implementation/phase-5/](../implementation/phase-5/).

## Deliverables

```text
Self-service onboarding package
Quarterly governance pack
Retention purge job
Executive summary screen
Multi-team navigation and RBAC (Keycloak)
Self-service integration onboarding journey
Rollout test suite and observability (purge, RBAC, onboarding)
Security pen test report
[Phase5]_exit_report.pdf
[Phase5]_data_dictionary.json
[Phase5]_config_changes.yaml
```

## Exit Criteria

```text
>= 30% of teams (or 10 teams) sending active data.
Support tickets < 5/week.
Every new team starts in Observation Mode.
No team moves directly into guardrail enforcement.
RBAC tested for all 6 roles.
```

## Fail Criteria

```text
New teams skip observation mode.
Support tickets exceed platform capacity.
Governance forum cannot agree on policy ownership.
Retention purge not operational before scale-out.
```

## Fail Action

```text
Pause expansion to new teams.
Fix onboarding, support, ownership or data-retention gaps.
Resume rollout in batches.
```

## Gate Decision

```text
Continue expansion only while support load, data confidence and psychological safety remain healthy.
```

## Reference Docs

```text
docs/rollout-operating-model.md  - operating modes, weekly cadence, governance forum, onboarding rule
docs/governance-and-privacy.md   - retention, incident integration, maturity bands, manager-misuse escalation
docs/ui-ux-spec.md               - executive view, multi-team navigation, RBAC
docs/data-model.md               - schema for purge queries, audit_log, config_versions
docs/api-spec.md                 - API endpoints for self-service onboarding, RBAC middleware
docs/testing-and-observability.md - Phase 5 tests and observability signals
examples/team-config.yaml        - self-service team configuration
examples/policy-config.yaml      - default policy set for new teams
quick-start.md                   - new-team onboarding guide
faq.md                           - common questions for new teams
implementation/phase-5/          - detailed task breakdown (T5.1-T5.13)
```
