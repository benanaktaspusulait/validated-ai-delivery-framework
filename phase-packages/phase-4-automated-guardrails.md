# Phase 4 - Automated Guardrails

Operating mode: Enforcement Mode. Selective blocking after warnings are calibrated. Emergency Override is always available.

## Purpose

```text
Enable calibrated, confidence-gated enforcement without blocking emergency delivery.
```

## Duration

```text
4 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Security Lead
Tech Lead
Pilot-team Engineering Manager
```

## Work Tracks (this phase)

```text
Engineering: AI Metadata Blocker, risk-based reviewer assignment, prompt leakage comment-mode scanner (dominant track).
Metrics and risk: Dynamic AI WIP recommendation including the high-risk multiplicative penalty.
Governance and reporting: emergency override audit log and override retro.
Culture and people: keep enforcement explainable; watch for hidden AI usage.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| AI Metadata Blocker | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| High-risk reviewer assignment | Platform Engineer | Platform Lead | Security Lead, Tech Lead | Engineering Manager |
| Dynamic AI WIP recommendation | Platform Engineer | Platform Lead | Engineering Manager | Pilot team |
| Emergency override audit + retro | Pilot-team EM | Platform Lead | Tech Lead | Data Steward |
| Prompt leakage comment-mode scanner | Platform Engineer | Security Lead | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 3 exit criteria passed.
Warning thresholds are calibrated.
Emergency override logging is implemented.
```

## Actions

```text
Week 11: Enable the AI Metadata Blocker (examples/github-actions/ai-metadata-enforcement.yml). Usage type and Low/Medium/High confidence are required before merge.
Week 12: Require code owner and senior reviewer for risk score >= 10 (examples/github-actions/risk-reviewer-assignment.yml).
Week 13: Publish the Dynamic AI WIP recommendation to Jira or GitHub Projects.
Week 14: Run an override retro if emergency-fix was used more than 3 times in the sprint.
Enable prompt leakage scanning in comment-only mode.
Ship the Policy Settings rule-editor screen and the Recommendations / Playbook screen.
Create the policy_overrides table.
```

## Implementation guidance

```text
AI Metadata Blocker (GitHub Actions required status check):

1. Copy examples/github-actions/ai-metadata-enforcement.yml to pilot repos.
2. Activate the branch protection rule registered in Phase 0:
   - GitHub repo Settings > Branches > Branch protection rules > edit main rule.
   - Enable "Require status checks to pass".
   - Add "AI Metadata Enforcement" as a required check.
3. The workflow:
   a. Checks for "emergency-fix" label -> if present, skip all checks, log override.
   b. Parses PR body for AI metadata checkboxes.
   c. If no "No AI assistance used" AND no AI usage category selected -> FAIL.
   d. If AI assistance selected but no confidence level -> FAIL.
   e. If all metadata present -> PASS.
4. Override flow:
   - Developer adds "emergency-fix" label -> workflow passes immediately.
   - Workflow posts a comment: "Emergency override recorded. A retrospective will be scheduled if this exceeds 3 per sprint."
   - Override logged to policy_overrides table via POST /api/v1/policy-overrides.
5. Policy engine integration:
   - Before blocking, check Data Confidence Score >= 90 for the backing metric.
   - If confidence < 90: downgrade block to warning.
   - If confidence < 70: skip the rule entirely.

Risk-based reviewer assignment:

6. Copy examples/github-actions/risk-reviewer-assignment.yml to pilot repos.
7. The workflow:
   a. Computes a simple risk score from changed lines and file paths.
   b. If risk_score >= 10: posts a comment recommending senior + code owner review.
   c. Optionally auto-requests reviewers via GitHub API (POST /repos/{owner}/{repo}/pulls/{pr}/requested_reviewers).
8. The platform risk engine (from Phase 2) is the authoritative source. This GitHub Action is a lightweight approximation.

Dynamic AI WIP recommendation:

9. Compute Dynamic AI WIP per team using the formula in docs/risk-policy-engine.md:
   base = (senior * 2) + (mid * 1) + (junior * 0.5)
   adjusted = base - (defect_deviation * 0.5) - (review_debt_deviation * 0.3) + (seniority_ratio * 0.2)
   If high-risk penalty triggers: adjusted = base * 0.7 * (1 - min(defect_deviation, 3) / 10)
   Floor: configured minimum (default 2).
10. Publish recommendation as a dashboard card and optionally as a Jira/GitHub Projects update.
11. Show: current WIP, recommended WIP, reason (which signals drove the change).

Emergency override audit:

12. Every override logged to policy_overrides table:
    { pull_request_id, policy_name, override_label, overridden_by, reason, created_at }
13. Dashboard: override count by team per sprint, trend over time.
14. Alert when overrides exceed 3 per team per sprint.

Override retro:

15. If team exceeds 3 overrides per sprint:
    - Platform Lead schedules a 15-minute retro.
    - Questions: (1) Is emergency-fix used correctly? (2) Workflow improvement? (3) Threshold adjustment?
    - Record outcomes in the Phase 4 exit report.

Prompt leakage scanner:

16. Implement as a GitHub Action or platform service.
17. Scan PR title, body, and comments for sensitive patterns (from examples/policy-config.yaml):
    password, secret, api_key, token, auth, internal, pii
    Email regex: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}
    Phone regex: \+?[\d\s\-\(\)]{7,15}
18. If pattern found: post a comment. Do NOT block. Do NOT store raw matches.
19. Comment: "This PR contains text that may contain sensitive data. Please verify before merging."
20. Prefer existing provider scanning (GitHub secret scanning, Gitleaks) over this custom scanner.

Policy Settings screen:

21. UI for Platform Lead to:
    - Enable/disable individual policies.
    - Adjust thresholds (e.g. large PR threshold from 300 to 500).
    - View policy evaluation history.
    - View confidence gate status (which policies are active/blocked/warned).
22. Store changes in config_versions table with audit trail.

Recommendations / Playbook screen:

23. UI showing:
    - Active recommendations by team (from recommendations table).
    - Playbook: problem -> recommended action -> owner -> status.
    - Override trend chart.
    - Dynamic WIP recommendation with reason.

Observability:
    - policy_evaluations_total (by rule, action, result)
    - policy_blocks_total, policy_warnings_total, emergency_overrides_total
    - override_rate_per_sprint (by team)
    - dynamic_ai_wip_recommendation (before, after, reason)
    - Alert when policy alerts > 10 per team per week.
    - Alert when overrides > 3 per team per sprint.
```

## Deliverables

```text
[Phase4]_exit_report.pdf
[Phase4]_data_dictionary.json
[Phase4]_config_changes.yaml
AI Metadata Blocker
Emergency override audit log (policy_overrides)
High-risk reviewer assignment
Dynamic AI WIP recommendation
Override retro notes where applicable
Prompt leakage comment-mode scanner
Policy Settings rule-editor screen
Recommendations / Playbook screen
Policy test suite and low-confidence enforcement regression tests
Policy observability counters and alert thresholds
```

## Exit Criteria

```text
Pilot team's Validated Delivery Trend (VDT) signal is neutral-to-positive and trending up over 90 days.
Emergency-fix usage is below 5% of total PRs.
No production emergency fix has been delayed by the metadata blocker.
Dynamic AI WIP recommendation is visible and explainable.
```

## Fail Criteria

```text
Dynamic AI WIP repeatedly falls below the configured minimum of 2.
Emergency fixes are delayed.
Emergency-fix usage exceeds 5% of total PRs without a clear operational reason.
Developers start hiding AI usage.
```

## Fail Action

```text
Pause enforcement.
Return affected policy to warning mode.
Revisit WIP formula, override process or communication.
```

## Gate Decision

```text
Proceed to Phase 5 only if guardrails improve safety without blocking emergency delivery or causing hidden AI usage.
```

## Reference Docs

```text
docs/risk-policy-engine.md       - policy engine rules, precedence, Dynamic AI WIP, prompt leakage, override rate limiting, playbook
docs/data-confidence.md          - confidence gate (>= 90 block, 70-89 warn, < 70 withhold), auto-disable/recovery
examples/policy-config.yaml      - policy set, security controls, declarative rules
examples/github-actions/         - ai-metadata-enforcement.yml, risk-reviewer-assignment.yml
docs/api-spec.md                 - Phase 4 endpoints (GET/POST /policies, GET/POST /policy-overrides, PATCH /recommendations/{id})
docs/data-model.md               - policy_overrides table, config_versions table
docs/ui-ux-spec.md               - Policy Settings screen, Recommendations/Playbook screen, Security view
docs/testing-and-observability.md - Phase 4 tests (policy evaluation, confidence gate, override rate limiting, precedence), observability
```
