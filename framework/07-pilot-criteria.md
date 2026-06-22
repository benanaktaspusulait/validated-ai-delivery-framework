# Pilot Success and Failure Criteria

## Success Criteria

After 4-6 sprints, the pilot is successful if:

```text
1. AI-assisted PRs reliably identified (declared + inferred).
2. AI Review Debt measured with Data Confidence >= 70.
3. Post-merge defects linked to PRs with acceptable accuracy.
4. Engineering managers find recommendations useful.
5. No psychological safety concerns from developers.
6. At least one actionable improvement made from the dashboard.
7. VDT computed with Medium data confidence (>= 70).
8. Human Validation Cost calibrated or accepted as directional.
```

**Business success:** VDT non-negative over 90 days. AI defect rate not materially worse than non-AI. Review debt < 2x baseline.

## Failure / Pause Triggers

```text
AI defect rate > 2x baseline for 2 consecutive sprints.
AI Review Debt > 2x baseline for 2 consecutive sprints.
Psychological safety score drops below 3.5.
Developers begin hiding AI usage.
Core metric confidence < 70 after 3 sprints.
Managers attempt individual performance scoring.
Emergency overrides frequent without retro review.
```

**Rollback:** Disable hard enforcement. Return to observation mode. Run blameless retro. Adjust policy. Restart with smaller scope.
