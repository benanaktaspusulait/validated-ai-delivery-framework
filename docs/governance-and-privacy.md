# Governance and Privacy

Canonical reference for data governance, privacy, the non-negotiable cultural rules and misuse handling. Established in Phase 0 and applied throughout.

## Non-negotiable rules

```text
No individual AI productivity ranking.
No personal productivity dashboard.
No "who uses AI most" view.
No performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement from metrics with Data Confidence Score < 70.
No causal claims stronger than the data supports.
```

## Data scope (Phase 0 legal/privacy review)

```text
Repository and team metadata.
Pull request metadata: title, author handle, timestamps, changed files, changed lines, labels.
Review analytics: first-review time, approval time, comment and thread counts, reviewer count.
AI usage metadata declared in the PR template.
No prompt content is collected.
```

## Identifier handling

```text
Data Steward must approve identifier handling.
Pseudonymise developer identifiers where possible.
Restrict raw author/reviewer-level data to platform administrators and data stewards.
Managers receive team-level views only.
```

## Data retention defaults

```text
Retain detailed PR and review analytics for 12 months.
Retain aggregated team-level trend metrics for 24 months.
Do not retain raw prompt content unless the organisation has explicit consent, legal basis and security controls.
Prefer derived prompt safety signals over raw prompt storage.
```

## Data Steward accountabilities

```text
Define retention periods and enforce data minimisation.
Ensure GDPR, CCPA and local privacy compliance.
Manage data export and deletion requests.
Sign off data sources (GitHub, Jira) before Phase 1.
Approve prompt metadata retention policies and identifier handling.
```

## Pilot Team Agreement contents

```text
Scope, repositories, data collected, data not collected.
No individual performance use.
Escalation path and opt-out route.
Phase timeline and named owners.
```

## Manager misuse escalation

If a manager attempts to use individual AI metrics for performance scoring:

```text
1. Pause expansion for that team.
2. Notify Platform Lead and HR/People Lead.
3. Re-brief the manager and team.
4. Remove individual-level exports from that manager's access.
5. Resume only after misuse risk is addressed.
```

The quarterly governance forum is the standing checkpoint for detecting and acting on misuse across teams.

## Incident integration

```text
1. Manually link a PagerDuty or Opsgenie incident ID to a Jira issue.
2. Automatically identify AI-assisted PRs linked to that Jira issue.
3. Add those PRs to the incident review report.
4. Update AI-assisted defect metrics only after the incident review confirms contribution.
```

```text
Incident linkage creates candidates for human review, not automatic blame or automatic defect attribution.
Do not assume a nearby AI-assisted PR caused the incident.
```

## Maturity bands (descriptive, never a ranking)

| Maturity | AI-assisted PR rate | Platform focus |
|---|---:|---|
| Laggard | < 10% | Remove barriers and improve enablement |
| Explorer | 10-30% | Measure review debt and defects |
| Adopter | 30-50% | Tune Dynamic AI WIP and policy thresholds |
| Leader | > 50% | Report Net AI Delivery Value and AI ROI |

```text
If the labels create status anxiety, use neutral bands in the UI: Low / Moderate / High / Very high AI adoption.
A low-adoption regulated team and a high-adoption internal-tooling team can both be making rational decisions.
```
