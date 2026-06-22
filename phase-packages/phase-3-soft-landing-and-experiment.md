# Phase 3 - Soft Landing and Experiment

Operating mode: Warning Mode and Recommendation Mode. Touch developer workflow with comments and warnings, but do not block PRs.

## Purpose

```text
Introduce soft, non-blocking guidance and validate that it helps without harming psychological safety.
```

## Duration

```text
4 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Pilot-team Engineering Manager
Tech Lead
```

## Work Tracks (this phase)

```text
Engineering: PR Comment Bot, experiment-mode assignment, recommendation surfacing.
Metrics and risk: A/B analysis of review time, rework and defect signals; Human Validation Cost calibration.
Culture and people: psychological safety pulse, warning copy review, alert-fatigue monitoring (dominant track).
Governance and reporting: threshold and copy recommendations recorded for Phase 4.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| PR Comment Bot | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| Experiment-mode assignment and analysis | Platform Engineer | Platform Lead | Data scientist | Engineering Manager |
| Human Validation Cost calibration study | Platform Engineer | Platform Lead | Pilot-team reviewers | Data Steward |
| Psychological safety pulse | Pilot-team EM | Platform Lead | HR/People Lead | Pilot team |
| Warning copy and threshold review | Tech Lead | Pilot-team EM | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 2 exit criteria passed.
Pilot team has seen and accepted the read-only dashboard.
Psychological safety baseline is at least 3.5.
```

## Actions

```text
Week 7: Enable the PR Comment Bot for AI-assisted PRs over 300 changed lines and missing AI metadata (warn only).
Week 8: Start experiment mode: show risk warnings to 50% of eligible AI-assisted PRs, keep 50% as control.
Week 9: Re-run the psychological safety pulse and compare against the Phase 0 baseline.
Week 10: Analyse A/B results for review time, rework and defect signals.
Run the Human Validation Cost calibration study and record the new confidence label.
Ship the developer in-PR view; route review-debt alerts to the platform Slack/Teams channel.
```

## Implementation guidance

```text
PR Comment Bot (GitHub Action or platform service):

1. Trigger: on pull_request events (opened, edited, synchronize, reopened).
2. Logic (non-blocking — never sets check to fail):
   a. Read PR metadata: changed_lines, ai_assisted, ai_assistance_confidence, risk_score.
   b. If ai_assisted = false AND ai_assisted IS NOT NULL (explicitly non-AI):
      -> No comment. Skip.
   c. If ai_assisted IS NULL (unknown):
      -> Comment: "This PR has no AI usage metadata. If AI was used, please fill in the PR template."
      -> Severity: info (lowest).
   d. If ai_assisted = true AND changed_lines > 300:
      -> Comment: "This AI-assisted PR has {N} lines changed. Consider splitting into smaller PRs."
      -> Severity: warning.
   e. If ai_assisted = true AND risk_score >= 10:
      -> Comment: "This AI-assisted PR is high risk (score {N}). Recommended reviewers: {list}."
      -> Severity: warning.
   f. If ai_assisted = true AND ai_assistance_confidence = "low":
      -> Comment: "Low AI confidence declared. Please ensure you understand the AI-generated code and have added tests."
      -> Severity: info.
3. Comment formatting: use GitHub Markdown, include a link to the dashboard for details.
4. Never block. Never set a required check. All comments are advisory.
5. Track: pr_bot_comments_total (by recommendation_type, team).

Experiment mode:

6. Assignment: when a PR is opened, randomly assign to treatment (50%) or control (50%).
   - Use a deterministic hash (PR number mod 100) for reproducibility.
   - Store assignment in pull_requests table: experiment_group = 'treatment' | 'control' | NULL.
7. Treatment group: receives PR comments (from the bot above).
8. Control group: receives no PR comments.
9. After 2 sprints (4 weeks): analyse:
   - Review time: treatment vs control (Welch's t-test or Mann-Whitney U).
   - Rework rate: changes_requested count.
   - Defect linkage: post-merge defects in each group.
   - Developer sentiment: optional anonymous survey.
10. If treatment shows no quality improvement AND review time increases > 20%: soften or disable warnings.
11. A/B results go into the Phase 3 exit report.

Human Validation Cost calibration:

12. Select 20 AI-assisted PRs and 20 non-AI PRs from the pilot team.
13. Ask reviewers to log actual review time (start/stop timer) for each PR.
14. Compare logged time vs estimated time (from fn_human_validation_cost).
15. Compute calibration factor: actual / estimated.
16. Apply factor to future estimates. Update confidence label if calibration improves accuracy.

Psychological safety pulse:

17. Re-administer the 6 questions from docs/psychological-safety.md.
18. Compare average score against Phase 0 baseline.
19. If score drops below 3.5: pause experiment, run retro, do not proceed to Phase 4.
20. If score is stable or improved: proceed.

Warning copy review:

21. Show all PR comment templates to the pilot team.
22. Ask: "Does this feel helpful or punitive?" Adjust wording based on feedback.
23. Record recommended copy changes in [Phase3]_config_changes.yaml.

Slack/Teams integration (optional for Phase 3):

24. Route review-debt alerts to a platform channel: "#ai-delivery-alerts".
25. Format: "Team {name}: AI Review Debt is at {ratio}x baseline. {N} PRs waiting."
26. This is a notification, not an enforcement action.

Observability:
    - pr_bot_comments_total (by recommendation_type, team)
    - experiment_assignment_total (by group: treatment/control)
    - alert_fatigue_hits_total (when team thresholds exceeded)
    - time_to_comment (webhook receipt -> comment publication)
    - notification_delivery_success_rate
```

## Deliverables

```text
[Phase3]_exit_report.pdf
[Phase3]_data_dictionary.json
[Phase3]_config_changes.yaml
PR Comment Bot
A/B test result
Psychological safety pulse report
Human Validation Cost calibration result
Updated warning copy and threshold recommendations
Developer in-PR view and PR comment experience
PR-bot and experiment-mode tests and observability counters
```

## Exit Criteria

```text
Psychological safety score is above 3.5.
Review time for warned PRs has not increased by more than 20% compared with control without quality benefit.
Developers do not report that the bot feels punitive.
Warning thresholds and copy have been reviewed with the pilot team.
```

## Fail Criteria

```text
Developers report that the bot creates pressure or surveillance anxiety.
Review time increases by more than 20% without quality improvement.
Warning volume creates alert fatigue.
```

## Fail Action

```text
Disable or soften warnings.
Re-run communication with the pilot team.
Tune thresholds before Phase 4.
```

## Gate Decision

```text
Proceed to Phase 4 only if soft recommendations improve or preserve quality without damaging psychological safety.
```

## Reference Docs

```text
docs/risk-policy-engine.md       - recommendation mapping, playbook view, policy precedence
docs/ui-ux-spec.md               - developer in-PR view, PR comment examples, UI guardrails
docs/psychological-safety.md     - pulse questions, scoring, gating rule
docs/metrics-catalogue.md        - Human Validation Cost formula, MVP quality linkage (Jira-defect-based)
docs/testing-and-observability.md - Phase 3 tests (PR bot, experiment mode, A/B reproducibility), observability signals
examples/pr-template.md          - AI usage PR template
examples/github-actions/         - ai-metadata-enforcement.yml (reference for comment format)
```
