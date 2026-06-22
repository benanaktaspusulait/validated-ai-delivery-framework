# MVP Backlog

Epics ordered by implementation sequence:

```text
Epic 1: GitHub Integration
  Collect PR metadata, review timestamps, changed files, labels.
  Parse AI metadata from PR template and AI assistance confidence.
  Store webhook events. Capture emergency override labels.

Epic 2: Jira Integration
  Collect issues, link to PRs, identify post-merge defects, map severity and sprint/team.

Epic 3: Metrics Engine
  AI-assisted PR rate, AI Review Debt, Post-merge defect rate.
  Human Validation Cost (directional for MVP).
  Validated Delivery Trend (VDT) with confidence intervals.
  Data confidence scoring. Metric lineage provenance.

Epic 4: Risk Engine
  PR size, security path, domain criticality, reviewer load.
  Codebase familiarity, change freshness, ownership boundary.
  Contextual AI risk score with normalised weights.

Epic 5: Policy Engine
  AI metadata required, large PR warning, high-risk reviewer recommendation.
  Review debt threshold alert, Dynamic AI WIP recommendation.
  Emergency override audit logging, prompt-sensitive data policy warning.
  Policy precedence: emergency > confidence gate > blocking AND > warnings.

Epic 6: Dashboard
  Platform view, team lead view, executive summary, security/compliance.
  Risky PR list, recommendations list, trend charts, data confidence warnings.

Epic 7: Alerts
  Slack alert for review debt, Jira ticket for critical breach.
  PR comment for large AI PR, weekly email summary.
  Alert for low-confidence metrics used in policy decisions.
```
