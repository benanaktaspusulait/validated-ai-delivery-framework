# Phase 3 — Soft Landing and Experiment

PR Comment Bot, experiment mode, Human Validation Cost calibration, psychological safety pulse.

## Duration

```text
4 weeks (20 working days). Single Platform Engineer + part-time EM/Tech Lead.
```

## Tasks

| ID | Task | Owner | Est. | Depends on |
|---|---|---|---|---|
| T3.1 | [PR Comment Bot](tasks/T3.1-pr-comment-bot.md) | Platform Engineer | 3d | T2.6, T2.14 |
| T3.2 | [Experiment mode assignment](tasks/T3.2-experiment-assignment.md) | Platform Engineer | 1d | T3.1 |
| T3.3 | [A/B analysis engine](tasks/T3.3-ab-analysis.md) | Platform Engineer | 2d | T3.2 |
| T3.4 | [HVC calibration study](tasks/T3.4-hvc-calibration.md) | Platform Engineer + reviewers | 3d | T2.4, T3.2 |
| T3.5 | [Psychological safety pulse](tasks/T3.5-safety-pulse.md) | Pilot-team EM | 1d + 3d wall-clock | T0.5 |
| T3.6 | [Warning copy review](tasks/T3.6-warning-copy-review.md) | Tech Lead + pilot team | 1d | T3.1 |
| T3.7 | [Developer in-PR view](tasks/T3.7-developer-in-pr-view.md) | Platform Engineer | 2d | T3.1, T3.2 |
| T3.8 | [Slack/Teams alerts](tasks/T3.8-slack-alerts.md) | Platform Engineer | 1d | T2.8 |
| T3.9 | [Tests (bot, experiment, A/B)](tasks/T3.9-tests.md) | Platform Engineer | 2d | T3.1, T3.2, T3.3, T3.8, T3.10 |
| T3.10 | [Observability counters](tasks/T3.10-observability-counters.md) | Platform Engineer | 0.5d | T3.1, T3.2, T3.8 |

## Execution order

```text
Week 7: T3.1(3d) + T3.5(1d+3d wait) + T3.6(1d) in parallel
Week 8: T3.2(1d) + T3.7(2d) + T3.8(1d) in parallel
Week 9: T3.3(2d) + T3.4(3d) in parallel
Week 10: T3.9(2d) + T3.10(0.5d) — final validation and tests
```

## Deliverables

```text
- PR Comment Bot with experiment-group gating (T3.1, T3.2)
- A/B analysis engine (T3.3)
- Human Validation Cost calibration result (T3.4)
- Psychological safety pulse report (T3.5)
- Updated warning copy and thresholds (T3.6)
- Developer in-PR view with experiment gating (T3.7)
- Slack/Teams alert integration (T3.8)
- Tests (T3.9)
- Observability counters (T3.10)
- [Phase3]_exit_report.pdf (assembled by Platform Lead)
- [Phase3]_data_dictionary.json (compiled by T3.10)
- [Phase3]_config_changes.yaml (compiled by T3.6)
```

## Gate

```text
All exit criteria in phase-packages/phase-3-soft-landing-and-experiment.md must pass.
```
