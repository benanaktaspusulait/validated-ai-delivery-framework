# FAQ

Grouped by audience. See [docs/](docs/) for full detail and [README.md](README.md) for navigation.

## For developers

**Is this tool monitoring my individual productivity?**
No. The platform reports at team and system level only. Individual rankings, personal dashboards and "who uses AI most" views are prohibited and disabled in the UI. AI usage metadata is used for team-level learning, never performance scoring.

**Why is my PR being blocked?**
Only in Enforcement Mode (Phase 4) and usually only when required AI metadata is missing. Add the metadata, or use the `emergency-fix` label for genuine production urgency. Every override is audited but is not held against you.

**Do you store my code or prompts?**
No. The platform stores metadata and derived metrics only. Raw code and raw prompt content are never stored.

**The PR bot gave a recommendation I disagree with.**
Recommendations are guidance, not orders. Post in `#ai-delivery-feedback` so thresholds can be tuned.

## For engineering managers and tech leads

**What does "AI-assisted PR Rate" mean?**
The share of PRs declared (via metadata) or inferred as AI-assisted. For MVP it is metadata-driven.

**How should I use these metrics in 1:1s?**
You should not. They are team/process-health signals. Using them for individual performance triggers the misuse escalation in [docs/governance-and-privacy.md](docs/governance-and-privacy.md).

**How is delivery value reported (the old "Net AI Delivery Value")?**
It has been replaced by the Validated Delivery Trend (VDT): a correlational signal comparing AI-assisted and matched non-AI PRs over time, shown as a 90-day trend, never a single value. It does not claim AI caused the difference. Method: [docs/metrics-catalogue.md](docs/metrics-catalogue.md).

**Can the platform prove AI made us faster?**
No, and it will not claim to. It produces decision-grade signals under matched comparison, not causal proof. See the validity guardrails in [docs/metrics-catalogue.md](docs/metrics-catalogue.md).

## For executives (CFO and board)

**How should this be presented to a CFO or the board?**
Present the AI Signal Trend (VDT), not a "Net AI Value" figure. Show the 90-day trend with its mandatory disclaimer. If the signal has risen for three consecutive months and psychological safety stays high, that is a strong qualitative case to continue investing. The number is never declared as an accounting line item.

**Why not just give us a single ROI number?**
A single ROI number would imply causation the data cannot support; differences can come from team maturity, task mix or other factors. A defensible signal that you can stand behind in an audit is worth more than a precise-looking number you cannot. Combine VDT with developer surveys for decisions.

**What would justify pausing or stopping AI investment?**
A sustained negative VDT signal (below -0.5 over the window) together with rising review debt or falling psychological safety. See [docs/rollout-operating-model.md](docs/rollout-operating-model.md).

## For platform admins

**How do I onboard a team?**
Follow [quick-start.md](quick-start.md). New teams start in Observation Mode.

**What is the "Data Confidence Score"?**
A 0-100 reliability score per metric, computed from four factors: data volume, freshness, completeness and statistical stability. Bands: 90+ High (may block), 70-89 Medium (warn only), below 70 Low (hidden, data-steward alert only). Method: [docs/data-confidence.md](docs/data-confidence.md).

**When can a policy block a merge?**
Only when its backing metric is in the High band (Data Confidence Score >= 90), and only from Phase 4. Metrics at 70-89 may warn but not block; below 70 they are withheld and any dependent policy is auto-disabled until confidence recovers.

## For security and compliance

**How is sensitive data in PRs handled?**
A comment-only scanner flags possible sensitive patterns in PR text without storing raw matches; prefer existing provider secret scanning. Detail: [docs/risk-policy-engine.md](docs/risk-policy-engine.md).

**What is the data retention policy?**
Detailed analytics for 12 months, aggregated trends for 24 months, no raw prompt content. Detail: [docs/governance-and-privacy.md](docs/governance-and-privacy.md).

**How are incidents linked to AI-assisted PRs?**
Incident-to-Jira-to-PR linkage creates candidates for human review, never automatic blame. Detail: [docs/governance-and-privacy.md](docs/governance-and-privacy.md).
