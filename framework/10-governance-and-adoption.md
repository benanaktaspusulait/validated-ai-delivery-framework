# Governance and Adoption

## Non-Negotiable Rules

These rules hold across every version. No release relaxes them.

```text
No individual AI productivity ranking, personal dashboard, "who uses AI most" view or performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement except from High-confidence metrics (Data Confidence Score >= 90).
No causal claims stronger than the data supports.
```

Full detail: [docs/governance-and-privacy.md](../docs/governance-and-privacy.md)

## Implementation Phases

| Phase | Name | Duration | Mode | Gate |
|---:|---|---:|---|---|
| 0 | Groundwork and Legal | 2 weeks | Readiness | Legal/HR + safety >= 3.5 (see docs/psychological-safety.md) |
| 1 | Data Architecture | 3 weeks | Observation | Confidence >= 75 |
| 2 | Metrics and Risk | 3 weeks | Observation | Metrics validated |
| 3 | Soft Landing | 4 weeks | Warning | Safety > 3.5 (see docs/psychological-safety.md) |
| 4 | Guardrails | 4 weeks | Enforcement | VDT positive |
| 5 | Enterprise Rollout | Continuous | Staged | >= 30% teams |

Gate details: [phase-packages/README.md](../phase-packages/README.md)
Task breakdown: [implementation/](../implementation/)
Pilot success and failure criteria: [07-pilot-criteria.md](07-pilot-criteria.md)

## Safe AI Usage Examples

| Good | Caution | Problematic |
|---|---|---|
| Generate test cases for well-specified API | Generate business logic before specification | Submit code author doesn't understand |
| Refactor known pattern with existing tests | Security change without verification | Bypass design/review processes |
| Generate documentation from code | Translate without deep understanding | Paste regulated data into public AI |
| Find edge cases developer missed | Change high-churn files blindly | Treat AI tests as proof of correctness |
| Explain unfamiliar code before changing | Create tests without meaningful assertions | |

## Adoption Value Staircase

| Level | Capability | Platform team question |
|---:|---|---|
| 1 | Measure | Where and how is AI being used? |
| 2 | Triage | Which teams or workflows show risk hotspots? |
| 3 | Govern | Which guardrails improve outcomes without damaging trust? |
| 4 | Optimise | How do we balance speed, validation and risk at scale? |
| 5 | Transform | How should AI change our delivery strategy? |

The MVP starts at Level 1 (Measure). Hard enforcement does not begin until Level 3 (Govern) in Phase 4.

## Onboarding Checklist

For developers new to a team using AI:

```text
1. [ ] Understand how AI is used on this team: code, tests, docs, refactoring or debugging.
2. [ ] Review the team's AI governance policies and thresholds.
3. [ ] Understand the AI metadata declaration process.
4. [ ] Complete a sample AI-assisted PR walkthrough.
5. [ ] Understand risk-sensitive paths and high-risk workflows.
6. [ ] Complete the team-specific AI usage guide.
7. [ ] Have a buddy assigned for the first AI-assisted PR.
8. [ ] Review the last sprint's AI delivery health metrics.
```
