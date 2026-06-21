# Phase 0 - Groundwork and Legal Assurance

## Purpose

```text
Remove legal, privacy and cultural risks before any developer activity data is collected.
No production code is built in this phase.
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
Complete legal/privacy review for pull_request and review analytics data.
Obtain HR or People approval for the "no individual performance use" policy.
Select one pilot team of 4-6 engineers doing real but non-critical product work.
Register, but do not activate, AI-Metadata-Required branch protection for pilot repositories.
Add the AI usage PR template to pilot repositories.
Run a 30-minute pilot-team briefing explaining purpose, data usage and psychological safety guardrails.
```

## Technical Specifications

### Data scope submitted to legal/privacy review

Only these data categories are in scope for Phase 0 approval. No prompt content is collected.

```text
Repository and team metadata.
Pull request metadata: title, author handle, timestamps, changed files, changed lines, labels.
Review analytics: first-review time, approval time, comment and thread counts, reviewer count.
AI usage metadata declared in the PR template.
```

### Data retention defaults (must be documented and approved)

```text
Retain detailed PR and review analytics for 12 months.
Retain aggregated team-level trend metrics for 24 months.
Do not retain raw prompt content unless the organisation has explicit consent, legal basis and security controls.
Prefer derived prompt safety signals over raw prompt storage.
```

### Data Steward accountabilities established here

```text
Define data retention periods and enforce data minimisation.
Ensure GDPR, CCPA and local privacy compliance.
Manage data export and deletion requests.
Sign off the first data sources (GitHub, Jira) before Phase 1.
Approve prompt metadata retention policies.
```

### AI usage PR template (added to pilot repositories)

```markdown
## AI Assistance

- [ ] No AI assistance used
- [ ] AI used for code suggestions
- [ ] AI used for test generation
- [ ] AI used for documentation
- [ ] AI used for refactoring
- [ ] AI used for debugging or explanation

## AI Assistance Confidence

- [ ] Low
- [ ] Medium
- [ ] High

## Ownership Confirmation

- [ ] I understand the submitted changes
- [ ] I can explain and support this change in production
- [ ] I have reviewed AI-assisted output manually
- [ ] Tests have been added or updated where appropriate
- [ ] I have added or checked edge-case coverage where risk is material
```

Psychological safety note to include in the template:

```text
AI usage metadata is used for team-level delivery learning and governance. It is not used for individual performance scoring.
```

Branch protection is registered but not enforced in Phase 0:

```text
Create the AI-Metadata-Required check definition but leave failure_mode disabled.
Enforcement begins in Phase 4, not before.
```

### Non-negotiable cultural principles (must be stated to the pilot team)

```text
No individual AI productivity ranking.
No use in performance reviews.
Team-level reporting by default.
Blameless retrospectives.
Transparent data usage policy.
Developers can challenge metric interpretation.
AI usage disclosure must not be punished.
```

### Psychological safety baseline pulse (run once for a baseline)

Questions, scored 1 (strongly disagree) to 5 (strongly agree):

```text
I feel safe declaring AI usage in my work.
I feel safe challenging AI-generated output.
I feel safe saying I do not understand AI-generated code.
Our team reviews AI-assisted code carefully.
AI metrics are used to improve the system, not blame individuals.
I do not feel pressured to use AI when it is not helpful.
```

Baseline rule:

```text
Record the baseline average. If the baseline is already below 3.5, address culture before Phase 1.
```

### Pilot-team briefing message

```text
We are not measuring individual productivity. We are validating whether the system can use AI safely.
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

## Master Reference Map

```text
Section 12.6  - Data Steward responsibilities and retention defaults
Section 13    - AI usage tagging strategy and PR template
Section 19    - Cultural adoption guardrails and psychological safety pulse
Section 20    - Platform rollout model: Phase 0 readiness check
Section 31    - AI-assisted delivery onboarding checklist (for the pilot briefing)
```
