# Phase 0 - Groundwork and Legal Assurance

Operating mode: Readiness only. No production code is built in this phase.

## Purpose

```text
Remove legal, privacy and cultural risks before any developer activity data is collected.
```

## Duration

```text
2 weeks
```

## Owners

```text
Platform Lead
Legal or privacy counsel
HR or People Lead
Pilot-team Engineering Manager
Data Steward
```

## Work Tracks (this phase)

```text
Governance and reporting: legal/privacy review, retention defaults, sign-off artefacts (dominant track).
Culture and people: psychological safety statement, pilot-team briefing, non-punitive commitments.
Engineering: register (do not activate) branch protection and add the PR template to pilot repositories only.
Metrics and risk: none. No data is interpreted in this phase.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Legal/privacy review | Legal/privacy counsel | Platform Lead | Data Steward, Security Lead | Pilot-team EM |
| HR/People approval | HR/People Lead | Platform Lead | Engineering Manager | Pilot team |
| Pilot team selection | Platform Lead | Platform Lead | Pilot-team EM | HR/People Lead |
| PR template + branch protection | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| Data retention defaults | Data Steward | Platform Lead | Legal/privacy counsel | Engineering Manager |
| Psychological-safety briefing | Pilot-team EM | Platform Lead | HR/People Lead | Pilot team |

## Entry Criteria

```text
Platform sponsor identified.
Candidate pilot team identified.
Initial product owner or Platform Lead assigned.
```

## Actions (high-level)

```text
1. Complete legal/privacy review.
2. Obtain HR/People approval for "no individual performance use".
3. Select pilot team (4-6 engineers).
4. Register branch protection (not activated).
5. Deploy PR template to pilot repos.
6. Run pilot-team briefing.
7. Record psychological safety baseline.
```

For detailed implementation steps, see [implementation/phase-0/](../implementation/phase-0/).

## Deliverables

```text
Signed legal/privacy approval
Signed HR or People acknowledgement
Pilot team agreement
PR template in pilot repositories
Data retention defaults documented
Psychological safety baseline score
[Phase0]_exit_report.pdf
[Phase0]_data_dictionary.json
[Phase0]_config_changes.yaml
```

## Exit Criteria

```text
Written legal/privacy approval received.
Written HR/People approval received.
Pilot team attended briefing.
PR template available in pilot repos.
Data retention defaults documented.
Psychological safety baseline >= 3.5 (100% survey participation).
```

## Fail Criteria

```text
Legal or HR approval denied.
Pilot team refuses or cannot participate.
Data collection scope cannot be made compliant.
Safety baseline < 3.5 and cannot be addressed.
```

## Fail Action

```text
Cancel pilot or reduce data scope.
Do not start collection of developer activity data.
Run culture-repair retro before re-attempting.
```

## Gate Decision

```text
Proceed to Phase 1 only after legal/privacy and HR/People approval are written and stored.
```

## Reference Docs

```text
docs/governance-and-privacy.md - data scope, retention, non-negotiable rules
docs/psychological-safety.md   - pulse questions, briefing message
examples/pr-template.md        - AI usage PR template
examples/team-config.yaml      - team configuration
implementation/phase-0/        - detailed task breakdown (T0.1-T0.8)
```
