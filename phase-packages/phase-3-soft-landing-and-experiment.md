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
docs/risk-policy-engine.md       - recommendation mapping surfaced as PR comments
docs/ui-ux-spec.md               - developer in-PR view and comment experience
docs/psychological-safety.md     - pulse questions and gating rule
docs/metrics-catalogue.md        - Human Validation Cost (calibrated this phase); MVP quality linkage is Jira-defect-based
docs/testing-and-observability.md - Phase 3 tests and signals
Master framework: sections 7.5, 9.6, 10.4, 19.2, 20
```
