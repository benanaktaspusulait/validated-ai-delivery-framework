# Phase 5 - Enterprise Rollout

Operating mode: Staged rollout across modes. Every new team starts in Observation Mode.

## Purpose

```text
Turn the validated pilot into an internal platform product.
```

## Duration

```text
Continuous after an initial 2-week rollout setup.
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
Metrics and risk: expand metrics per the enterprise roadmap as confidence allows.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Self-service onboarding package | DevEx Team | Platform Lead | Engineering Managers | New teams |
| Quarterly governance forum | Platform Lead | Platform team owner | Security Lead, Data Steward | CTO/VP Eng |
| Incident integration | Platform Engineer | Platform Lead | Security Lead | Engineering Managers |
| Retention purge job | Platform Engineer | Data Steward | Legal/privacy counsel | Platform Lead |
| Executive reporting | Platform Lead | Platform team owner | Engineering Managers | CTO/VP Eng |

## Entry Criteria

```text
Phase 4 exit criteria passed.
Pilot team recommends continuation.
Platform support load is manageable.
```

## Actions

```text
Publish self-service onboarding using examples/team-config.yaml.
Confirm quick-start.md and faq.md are current for new teams.
Run the first quarterly governance forum with CTO, Security Lead and Engineering Managers; present Phase 0-4 results, policy recommendations and budget needs.
Enable the retention purge job for detailed data after 12 months.
Ship the Executive summary screen, multi-team navigation and RBAC settings, and the self-service onboarding journey.
Wire incident-to-Jira-to-PR linkage for post-incident review.
```

## Deliverables

```text
[Phase5]_exit_report.pdf
[Phase5]_data_dictionary.json
[Phase5]_config_changes.yaml
Self-service onboarding package
Quarterly governance pack
Retention purge job
Executive summary screen
Multi-team navigation and RBAC (Keycloak) settings
Self-service integration onboarding journey
Rollout test suite and observability (purge, RBAC, onboarding)
```

## Exit Criteria

```text
At least 30% of engineering teams or 10 teams send active data into the platform.
Platform support tickets are below 5 per week.
Every new team starts in observation mode.
No team moves directly into guardrail enforcement.
```

## Fail Criteria

```text
New teams skip observation mode.
Support tickets exceed platform capacity.
Governance forum cannot agree on policy ownership.
Retention purge is not operational before scale-out.
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
docs/rollout-operating-model.md  - operating modes, weekly cadence, governance forum, onboarding rule, enterprise roadmap
docs/governance-and-privacy.md   - retention, incident integration, maturity bands, manager-misuse escalation
docs/ui-ux-spec.md               - executive view, multi-team navigation, RBAC
examples/team-config.yaml        - self-service team configuration
docs/testing-and-observability.md - Phase 5 tests and signals
quick-start.md, faq.md           - new-team onboarding
Master framework: sections 12, 22, 23, 26, 30, 32, 33
```
