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
| Legal/privacy review of PR and review analytics data | Legal/privacy counsel | Platform Lead | Data Steward, Security Lead | Pilot-team EM |
| HR/People approval of "no individual performance use" | HR/People Lead | Platform Lead | Engineering Manager | Pilot team |
| Pilot team selection and agreement | Platform Lead | Platform Lead | Pilot-team EM | HR/People Lead |
| PR template + branch-protection registration | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| Data retention defaults | Data Steward | Platform Lead | Legal/privacy counsel | Engineering Manager |
| Pilot-team psychological-safety briefing | Pilot-team EM | Platform Lead | HR/People Lead | Pilot team |

## Entry Criteria

```text
Platform sponsor identified.
Candidate pilot team identified.
Initial product owner or Platform Lead assigned.
```

## Actions

```text
Complete legal/privacy review for the data scope in docs/governance-and-privacy.md.
Obtain HR or People approval for the "no individual performance use" policy.
Select one pilot team of 4-6 engineers doing real but non-critical product work.
Register, but do not activate, AI-Metadata-Required branch protection for pilot repositories (enforcement begins in Phase 4).
Add the PR template (examples/pr-template.md) to pilot repositories.
Run a 30-minute pilot-team briefing covering purpose, data usage and the non-negotiable cultural rules.
Record the psychological safety baseline (docs/psychological-safety.md).
```

## Deliverables

```text
[Phase0]_exit_report.pdf
[Phase0]_data_dictionary.json
[Phase0]_config_changes.yaml
Signed legal/privacy approval
Signed HR or People acknowledgement
Pilot team agreement
PR template committed to pilot repositories
Documented data retention defaults
Psychological safety baseline score
```

## Exit Criteria

```text
Written legal/privacy approval received.
Written HR or People approval received.
Pilot team members attended the briefing.
PR template is available in pilot repositories.
Data retention defaults are documented.
Psychological safety baseline is recorded and is at least 3.5.
```

## Fail Criteria

```text
Legal or HR approval is denied.
Pilot team refuses or cannot participate.
Data collection scope cannot be made compliant.
Psychological safety baseline is below 3.5 and cannot be addressed in this phase.
```

## Fail Action

```text
Cancel the pilot or reduce data scope before Phase 1.
Do not start collection of developer activity data.
Run a culture-repair retro before re-attempting if the safety baseline is the blocker.
```

## Gate Decision

```text
Proceed to Phase 1 only after legal/privacy and HR or People approval are written down and stored with the Phase 0 exit report.
```

## Reference Docs

```text
docs/governance-and-privacy.md - data scope, identifier handling, retention, non-negotiable rules, manager-misuse escalation
docs/psychological-safety.md   - pulse questions, baseline rule, briefing message
examples/pr-template.md        - AI usage PR template
Master framework: sections 12.6, 13, 19, 20, 31
```
