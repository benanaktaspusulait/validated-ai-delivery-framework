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

## Implementation guidance

```text
Self-service onboarding:

1. Build an onboarding flow in the admin UI:
   a. Team name and ID.
   b. Domain criticality selector (low/medium/high).
   c. Seniority mix input (senior/mid/junior counts) — used for WIP calculation.
   d. Repository mapping: select GitHub org, enter repo names (or auto-discover).
   e. Jira project key input.
   f. Sensitive paths configuration (for risk scoring).
   g. Submit -> creates team in teams table, repos in repositories table, starts Observation Mode.
2. Validate team-config.yaml on submit:
   - Required fields: team.id, team.name, team.domain_criticality, repositories (at least one).
   - Validate repo exists via GitHub API.
   - Validate Jira project exists via Jira API.
3. Every new team starts in Observation Mode. No exceptions.

Multi-team dashboards:

4. Overview Dashboard now shows all teams in a table.
5. Team selector: dropdown or sidebar to switch between teams.
6. Executive Summary screen:
   - VDT trend across all teams (aggregated chart).
   - Adoption rate by team (bar chart).
   - Risk posture: number of high-risk PRs, override trends.
   - Confidence distribution: how many metrics are High/Medium/Low.
   - No PR-level detail. No causal ROI claims.
7. Multi-team navigation: sidebar with team list, click to drill down.

RBAC (Keycloak integration):

8. Define roles in Keycloak:
   - Platform Admin: full access.
   - Data Steward: retention, access logs, override logs.
   - Engineering Manager: team-level metrics for their team only.
   - Tech Lead: team metrics + PR risk for their team.
   - Executive: aggregated summaries only.
   - Developer: own PR in-PR guidance only.
9. API middleware: check Keycloak token roles on every request.
10. Dashboard: filter visible data by role.
11. Never show individual developer rankings to any role.

Retention purge job:

12. Scheduled job (nightly or weekly):
    - DELETE FROM raw_events WHERE created_at < now() - interval '12 months'.
    - DELETE FROM pull_requests WHERE created_at < now() - interval '12 months' AND id NOT IN (SELECT pull_request_id FROM metric_snapshots).
    - Aggregate data is retained for 24 months.
    - Log: retention_purge_records_deleted_total (by table, team).
13. Dry-run mode: run with --dry-run flag to preview what would be deleted.
14. Data Steward must approve the purge schedule before activation.

Incident integration:

15. Workflow:
    a. When an incident occurs, manually link the PagerDuty/Opsgenie incident ID to a Jira issue.
    b. Platform automatically identifies AI-assisted PRs linked to that Jira issue (via branch name or commit message).
    c. Add those PRs to an incident review report.
    d. Update AI-assisted defect metrics ONLY after the incident review confirms contribution.
16. UI: incident review page showing linked PRs, AI metadata, and risk scores.
17. NEVER automatic blame. Incident linkage creates candidates for human review.

Quarterly governance forum:

18. Prepare a governance pack:
    - AI adoption health (PR rate by team).
    - Validation trends (review debt, validation cost).
    - Quality trends (defect rate, quality gap).
    - VDT signal (correlational, with disclaimer).
    - Risk posture (dependency risk, security signals, overrides).
    - Psychological safety pulse trend.
    - Policy adjustments proposed.
    - 90-day roadmap.
19. Present to CTO, Security Lead, Engineering Managers.
20. Record decisions in [Phase5]_config_changes.yaml.

Security pen test:

21. Before enterprise rollout: engage a security team for penetration testing.
22. Test areas: RBAC bypass, webhook signature forgery, SQL injection, XSS, API rate limiting, data leakage.
23. Fix all critical/high findings before expanding to more teams.

Observability:
    - retention_purge_records_deleted_total (by table, team)
    - rbac_access_denied_total (by role, resource)
    - onboarding_success_rate, onboarding_failure_reason_total
    - dashboard_load_latency_p95 (multi-team views)
    - support_ticket_volume_total
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
Security pen test report
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
docs/rollout-operating-model.md  - operating modes, weekly RACI, pause criteria, governance forum, onboarding rule, enterprise roadmap
docs/governance-and-privacy.md   - retention policy, incident integration, maturity bands, manager-misuse escalation, non-negotiable rules
docs/ui-ux-spec.md               - executive view, multi-team navigation, RBAC, Security view
docs/data-model.md               - schema for purge queries, audit_log for RBAC, config_versions for policy changes
docs/api-spec.md                 - API endpoints for self-service onboarding, RBAC middleware
docs/testing-and-observability.md - Phase 5 tests (retention purge, RBAC bypass, onboarding failure paths, load test), observability
examples/team-config.yaml        - self-service team configuration template
examples/policy-config.yaml      - default policy set for new teams
quick-start.md                   - new-team onboarding guide
faq.md                           - common questions for new teams
```
