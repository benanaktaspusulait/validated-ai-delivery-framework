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
No blocking enforcement except from High-confidence metrics (Data Confidence Score >= 90); metrics below 70 are withheld from decisions.
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

## Legal basis and anonymisation

Legal basis (to be confirmed per jurisdiction with counsel; this is guidance, not legal advice):

| Regime | Likely basis | Implication |
|---|---|---|
| GDPR (EU/UK) | Legitimate interest in engineering governance, balanced against developer rights | Requires data minimisation, transparency, a documented balancing test and honoured access/erasure requests |
| CCPA/CPRA (California) | Processing of employee-related data with notice | Requires disclosure of categories collected and purpose, and honouring deletion requests where applicable |
| Local employment law | Varies; often requires works-council or representative consultation | May require consultation before processing developer activity data |

```text
Purpose limitation: data is processed for team-level delivery governance only. Individual performance use is prohibited and is a basis violation, not just a policy breach.
Transparency: developers are told what is collected (data scope above), why, and that it is never used to rank them.
Data subject rights: export and deletion requests are handled by the Data Steward within the legal window.
```

Anonymisation and pseudonymisation methods:

```text
Pseudonymisation: replace provider identities with a stable platform identifier; keep the mapping in an access-controlled store separate from analytics tables.
Aggregation: dashboards present team-level figures; suppress any view that would resolve to a single identifiable person (small-group suppression).
Minimisation: collect only the fields the metrics need (see data scope); never collect code or prompt content.
Irreversibility at rest: where individual linkage is no longer needed for operations, drop the mapping so remaining data is effectively anonymous.
Access control: raw author/reviewer-level data is restricted to platform administrators and data stewards; managers and executives see aggregates only.
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
| Leader | > 50% | Report the Validated Delivery Trend (VDT) signal |

```text
If the labels create status anxiety, use neutral bands in the UI: Low / Moderate / High / Very high AI adoption.
A low-adoption regulated team and a high-adoption internal-tooling team can both be making rational decisions.
```

## Known blind spots

```text
Shadow AI (undetected AI usage):
  The platform detects AI usage through two mechanisms:
    1. Explicit declaration: developer checks the AI usage box in the PR template.
    2. Inference: signals in commit messages, PR body, or file patterns.

  The platform does NOT detect:
    - Code pasted from ChatGPT/Claude without any metadata or signal.
    - Code generated by an AI tool outside the IDE (e.g. browser-based chat, then copy-paste).
    - Code generated by an AI tool whose telemetry is not connected (IDE telemetry is Stage 2+).

  Impact: the AI-assisted PR Rate and all dependent metrics measure only the DETECTED share of AI usage. If developers use AI but do not declare it and no inference signals exist, those PRs are classified as "unknown" and excluded from both cohorts.

  Mitigation:
    - Communicate clearly: "Declare AI usage honestly. Undeclared AI usage is not punished."
    - Inference signals (commit message patterns, PR body references) catch some undeclared usage.
    - The platform reports what it can measure; it does not claim to measure everything.
    - Periodically survey developers about actual AI usage patterns to estimate the detection gap.
```

```text
Other known limitations:
  - The platform observes delivery outcomes, not AI model quality or prompt engineering skill.
  - Metrics are correlational, not causal (see metrics-catalogue.md validity guardrails).
  - Data confidence reflects measurement reliability, not the quality of the AI-generated code itself.
```
