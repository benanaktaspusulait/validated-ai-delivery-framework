# Pilot Success and Failure Criteria

## Success Criteria

After 4-6 sprints (measurement window after the pilot goes live), the pilot is successful if:

```text
1. AI-assisted PRs reliably identified (declared + inferred).
2. AI Review Debt measured with Data Confidence >= 70 (see docs/data-confidence.md).
3. Post-merge defects linked to PRs with acceptable accuracy.
4. Engineering managers find recommendations useful.
5. No psychological safety concerns from developers (see docs/psychological-safety.md).
6. At least one actionable improvement made from the dashboard.
7. VDT computed with Medium data confidence (>= 70) (see docs/metrics-catalogue.md).
8. Human Validation Cost calibrated or accepted as directional.
```

**Business success:** VDT non-negative over 90 days. AI defect rate not materially worse than non-AI. Review debt < 2x baseline.

**Failure triggers:** AI defect rate > 2x baseline for 2 sprints. Safety score < 3.5. Developers hide AI usage. Confidence < 70 after 3 sprints. Overrides frequent without retro.

**Rollback procedure:** see [docs/rollout-operating-model.md](../docs/rollout-operating-model.md)
