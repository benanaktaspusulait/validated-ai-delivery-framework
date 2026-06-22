# UI / UX Specification

Canonical reference for the product surface: role-based views, screens, states and navigation. Read-only screens are built in Phase 2, the developer in-PR view in Phase 3, Policy Settings and Playbook in Phase 4, and the executive/multi-team/RBAC surface in Phase 5.

## Principle

The platform is a control plane, not only a report screen. It collects data, computes metrics, runs risk and policy rules, and pushes warnings or blocks back into GitHub and CI.

## Role-based views

```text
Platform Team view     - operate the system: integrations, data confidence, policy violations, rollout pace.
Engineering Manager / Tech Lead view - team delivery health and risky PRs.
Executive view         - Validated Delivery Trend (VDT), adoption and risk summary (no PR-level detail, no causal ROI).
Developer view         - lightweight, in-PR guidance only. No personal dashboard.
Security / Compliance view - risk and control oversight: security-sensitive changes, prompt leakage signals, policy overrides, incident linkage.
```

## Screens and when they ship

| Screen | Introduced | Purpose |
|---|---|---|
| Admin login + integration connect + team mapping | Phase 1 | Set up SSO, GitHub, Jira and team-to-repo mapping |
| Overview Dashboard | Phase 2 | Core metric cards with status and confidence |
| Team Dashboard | Phase 2 | Per-team comparison of adoption, debt, defects, value, risk |
| Pull Request Risk View | Phase 2 | Per-PR AI flag, size, risk, signal, recommendation |
| Metrics Detail View | Phase 2 | Drill-down: inputs, baseline, confidence label |
| Developer in-PR view | Phase 3 | In-PR guidance and comment bot |
| Policy Settings | Phase 4 | Rule editor mapping to CI/GitHub Checks |
| Recommendations / Playbook | Phase 4 | Problem -> action -> owner |
| Executive Summary | Phase 5 | Leadership ROI/risk/adoption summary |
| Multi-team navigation + RBAC | Phase 5 | Scale across teams with role-scoped access |

Full navigation (enterprise): Dashboard, Teams, Pull Requests, Metrics, Risks, Policies, Recommendations, Integrations, Reports, Settings. Settings includes RBAC via Keycloak so each role sees only the right view.

## Security / Compliance view

```text
Purpose: risk and control oversight. Available from Phase 4.
Widgets:
- AI-generated security-sensitive changes (flagged PRs touching auth/payment/infra paths)
- Prompt leakage alerts (pattern matches in PR text, comment-only mode)
- Policy override audit log (who overrode what, when, why)
- Incident-to-PR linkage candidates (post-incident review, not automatic blame)
- SAST/DAST findings on AI-assisted PRs (Stage 2+, when security integrations are wired)
- Audit trail completeness (data retention, access logs, override trends)
```

## What is visible by phase

| Phase | Visible to operators | Visible to developers |
|---|---|---|
| 0 | Nothing live; PR template registered in repos | PR template in new PRs (no enforcement) |
| 1 | Admin connect + team-mapping screen; connector health | Nothing |
| 2 | Overview, Team, PR Risk, Metrics Detail (read-only, with confidence labels) | Nothing |
| 3 | The above plus recommendations and experiment results | In-PR panel and comment bot (warnings, never blocks) |
| 4 | The above plus Policy Settings, Playbook, override audit, Dynamic AI WIP | In-PR guidance plus blocking checks with emergency override |
| 5 | The above plus Executive Summary, multi-team navigation, RBAC, integrations | Same in-PR experience; never a personal dashboard |

```text
Information accumulates: each phase adds surface area, never removing the confidence labelling or the team-level-only rule.
Developers never gain a personal dashboard at any phase.
```

## Example: Overview card row

| Metric | Current | Trend | Status |
|---|---|---|---|
| AI-assisted PR Rate | 34% | up | Healthy |
| AI Review Debt | 12 PRs | up | Warning |
| Validated Delivery Trend (VDT) | 90-day trend chart | up | Positive signal (shown as a trend, never a single value) |
| Post-merge Defect Rate | 1.2x baseline | up | Watch |
| Data Confidence | 82% | flat | Good |

## Example: Pull Request Risk row

| PR | AI | LOC | Risk | Signal | Recommendation |
|---|---|---:|---|---|---|
| Add payment retry logic | Yes | 420 | High | Large AI PR + payment domain | Split PR + senior reviewer |
| Update docs | Yes | 80 | Low | None | Normal review |
| Auth token refactor | Yes | 260 | Critical | Security-sensitive files | AppSec review required |

## Developer in-PR view

```text
Surface inside the GitHub PR and a small panel, never a large personal dashboard.
Show only: Is this PR AI-assisted? Is metadata missing? Is the PR large? Is the risk score high?
Is a second reviewer suggested? Is test coverage low? Does AI-generated output need explanation?
```

Example PR comment:

```text
This PR is AI-assisted and high risk.
Reason:
- 420 lines changed
- payment domain
- reviewer load is high
Recommended actions:
- split into smaller PRs
- request a senior reviewer
- add additional test coverage
```

```text
Intended experience: "This tool is not tracking me; it is helping me merge my PR more safely."
No individual scoring is shown to anyone, including managers.
```

## UI guardrails

```text
Team-level by default. No individual developer ranking, leaderboard or "who uses AI most" view.
Every metric shows its data confidence. Never present an estimate as a precise fact.
Wrong: "AI delivery value: £12,400."
Right: "Estimated AI delivery value: £12,400. Data confidence: Medium."
```

## Empty and low-confidence states

```text
Insufficient data:        "Not enough data yet. Continue collecting for N more sprints."
Data Confidence 70-89:    "Warn-grade only. Blocking is disabled at this confidence."
Data Confidence < 70:     metric hidden from dashboards; a Data Quality Alert is sent to the data steward only.
Low AI metadata coverage: "AI usage declaration coverage is below threshold. Improve metadata before interpreting AI impact."
```
