# Phase 0 — Groundwork and Legal Assurance

No code is written in this phase. Tasks cover setup, legal/HR sign-off and pilot team preparation.

## Duration

```text
2 weeks (calendar time; active work ~7h + 1 week legal + 3 days HR + 24h survey wait)
```

## Tasks

| ID | Task | Owner | Est. | Depends on |
|---|---|---|---|---|
| T0.1 | [GitHub App setup](tasks/T0.1-github-app-setup.md) | Platform Engineer | 2h | — |
| T0.2 | [Jira service account preparation](tasks/T0.2-jira-preparation.md) | Platform Engineer | 2h | — |
| T0.3 | [PR template deployment](tasks/T0.3-pr-template-deployment.md) | Platform Engineer | 1h | — |
| T0.4 | [Branch protection registration](tasks/T0.4-branch-protection-registration.md) | Platform Engineer | 1h | T0.1 |
| T0.5 | [Psychological safety baseline](tasks/T0.5-psychological-safety-baseline.md) | Pilot-team EM | 2h + 24h wait | T0.8 |
| T0.6 | [Legal/privacy review](tasks/T0.6-legal-privacy-review.md) | Legal counsel | 1w | — |
| T0.7 | [HR/People approval](tasks/T0.7-hr-approval.md) | HR/People Lead | 3d | — |
| T0.8 | [Pilot team briefing](tasks/T0.8-pilot-team-briefing.md) | Platform Lead | 1-1.5h | T0.6, T0.7 |

## Dependency flow

```text
T0.1, T0.2, T0.3 can run in parallel (no dependencies).
T0.4 depends on T0.1 (GitHub App must exist).
T0.6 and T0.7 can run in parallel (legal and HR review).
T0.8 depends on T0.6 and T0.7 (briefing requires approvals).
T0.5 depends on T0.8 (survey distributed during briefing).
```

## Deliverables

```text
- Signed legal/privacy approval
- Signed HR/People acknowledgement
- PR template in pilot repos
- Branch protection registered (not activated)
- Psychological safety baseline score >= 3.5
- [Phase0]_exit_report.pdf (assembled by T0.8)
- [Phase0]_config_changes.yaml (created by T0.4, updated by T0.6)
- [Phase0]_data_dictionary.json (created by T0.8)
```

## Gate

```text
All exit criteria in phase-packages/phase-0-groundwork-and-legal-assurance.md must pass.
```
