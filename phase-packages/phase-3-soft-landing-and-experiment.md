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
Metrics and risk: A/B analysis of review time, rework and defect signals; HVC calibration.
Culture and people: psychological safety pulse, warning copy review, alert-fatigue monitoring (dominant track).
Governance and reporting: threshold and copy recommendations recorded for Phase 4.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| PR Comment Bot | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| Experiment mode + A/B analysis | Platform Engineer | Platform Lead | Data scientist | Engineering Manager |
| Human Validation Cost calibration | Platform Engineer | Platform Lead | Pilot-team reviewers | Data Steward |
| Psychological safety pulse | Pilot-team EM | Platform Lead | HR/People Lead | Pilot team |
| Warning copy review | Tech Lead | Pilot-team EM | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 2 exit criteria passed.
Pilot team has seen and accepted the read-only dashboard.
Psychological safety baseline >= 3.5.
```

## Actions (high-level)

```text
Week 7: Enable PR Comment Bot for AI-assisted PRs > 300 LOC and missing metadata.
Week 8: Start experiment mode: 50% treatment (warnings), 50% control (no warnings).
Week 9: Re-run psychological safety pulse.
Week 10: Analyse A/B results. Run HVC calibration study.
         Ship developer in-PR view. Route review-debt alerts to Slack.
```

For detailed implementation steps, see [implementation/phase-3/](../implementation/phase-3/).

## Deliverables

```text
PR Comment Bot
A/B test result
Psychological safety pulse report
Human Validation Cost calibration result
Updated warning copy and threshold recommendations
Developer in-PR view
PR-bot and experiment-mode tests
[Phase3]_exit_report.pdf
[Phase3]_data_dictionary.json
[Phase3]_config_changes.yaml
```

## Exit Criteria

```text
Psychological safety score > 3.5.
Review time for warned PRs not up > 20% without quality gain.
Developers do not report bot feels punitive.
Warning thresholds and copy reviewed with pilot team.
```

## Fail Criteria

```text
Developers report bot creates pressure or surveillance anxiety.
Review time increases > 20% without quality improvement.
Warning volume creates alert fatigue.
```

## Fail Action

```text
Disable or soften warnings.
Re-run communication with pilot team.
Tune thresholds before Phase 4.
```

## Gate Decision

```text
Proceed to Phase 4 only if soft recommendations improve or preserve quality without damaging safety.
```

## Reference Docs

```text
docs/risk-policy-engine.md       - recommendation mapping, playbook view
docs/ui-ux-spec.md               - developer in-PR view, comment examples
docs/psychological-safety.md     - pulse questions, gating rule
docs/metrics-catalogue.md        - HVC formula, MVP quality linkage
docs/testing-and-observability.md - Phase 3 tests and observability signals
examples/pr-template.md          - AI usage PR template
implementation/phase-3/          - detailed task breakdown (T3.1-T3.10)
```
