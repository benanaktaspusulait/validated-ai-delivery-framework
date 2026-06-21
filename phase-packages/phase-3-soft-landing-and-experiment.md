# Phase 3 - Soft Landing and Experiment

## Purpose

```text
Start touching developer workflow with comments and warnings, but do not block PRs.
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
Week 7: Enable PR Comment Bot for AI-assisted PRs over 300 changed lines and missing AI metadata.
Week 8: Start experiment mode: show risk warnings to 50% of eligible AI-assisted PRs and keep 50% as control.
Week 9: Run psychological safety pulse.
Week 10: Analyse A/B test results for review time, rework and defect signals.
Ship the Developer in-PR view and PR comment experience.
```

## Technical Specifications

### Soft-recommendation surface (warn only, never block)

```text
Add PR warnings (large PR, missing metadata, high-risk path).
Add recommendation engine output as PR comments.
Add review debt alerts to the platform channel.
Start Net AI Delivery Value reporting in the team view.
```

Recommendation mapping (surface as guidance, not enforcement):

| Signal | Recommendation |
|---|---|
| AI Review Debt high | Reduce AI WIP limit next sprint |
| PR > 300 LOC | Split PR or add second reviewer |
| High-risk domain change | Require senior + security review |
| Defect rate rising | Pause AI expansion for affected team |
| Low ownership confidence | Require explainable review |
| High prompt iteration count | Re-refine story before coding |
| High coverage but low mutation score | Improve test quality gate |
| Low data confidence | Use metric for trend only, not decision |

### Experiment mode design

```text
Randomly assign 50% of eligible AI-assisted PRs to receive soft warnings.
Keep the other 50% as a control group.
Compare warning and control outcomes after 2 sprints.
Measure PR split rate, review wait time, defect linkage and developer feedback.
Use results to tune policies before Phase 4 enforcement.
```

Constraints:

```text
Experiment mode stays team-level and non-punitive.
It validates whether a policy improves outcomes, not whether one developer outperforms another.
```

### Human Validation Cost calibration study

```text
Run a 2-week calibration study with the pilot reviewers.
Reviewers log actual review time for AI-assisted and comparable non-AI PRs.
Use the study to tune the timestamp-based estimation rules from Phase 2.
After calibration, record the new confidence label for Human Validation Cost.
```

### Psychological safety pulse (re-run, compare against the Phase 0 baseline)

```text
Re-run the same six-question psychological safety pulse defined in Phase 0 and compare the result against the recorded baseline.
```

Action rule:

```text
If the average score is below 3.5, pause metric expansion and run a team retro before adding any enforcement.
```

### Suggested warning copy

```text
Large AI-assisted PR: "This AI-assisted PR is large. Consider splitting it or adding a second reviewer."
Missing metadata: "Please record AI assistance metadata so the team can learn from delivery trends. This is not used for individual scoring."
High-risk path: "This change touches a high-risk area. A senior or security reviewer is recommended."
```

### Developer View (lightweight, in-PR)

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

Intended developer experience:

```text
"This tool is not tracking me; it is helping me merge my PR more safely."
No individual scoring is shown to anyone, including managers.
```

Alert fatigue threshold:

```text
If PR bot comments exceed 20% of PRs or more than 10 actionable alerts per week for one team, review thresholds.
If developers describe the bot as pressure or surveillance, disable or soften warnings before continuing.
```

### Measurement confidence at this phase

```text
Layer: quality linkage added (defects, rework, coverage signals) plus calibrated review cost.
Confidence: medium. Defect linkage and review effort are estimated, not exact.
Use: soft warnings and experiment learning only.
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

## Master Reference Map

```text
Section 7.5  - Recommendation engine
Section 9.6  - Lightweight experiment mode (MVP non-goals boundary)
Section 10.4 - Human Validation Cost calibration mechanism
Section 19.2 - Psychological safety pulse
Section 20   - Platform rollout model: Phase 2 soft recommendations and experiment mode
```
