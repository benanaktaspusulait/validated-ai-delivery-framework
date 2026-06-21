# Glossary

Short definitions of the terms used across the framework. Full method and formula detail lives in the linked `docs/` files.

| Term | Definition |
|---|---|
| AI-assisted PR | A pull request declared (via metadata/label) or inferred to have used AI assistance. |
| AI-assisted PR Rate | Share of pull requests that are AI-assisted. See `docs/metrics-catalogue.md`. |
| AI Review Debt | Backlog of AI-assisted PRs waiting longer than the normal review baseline. |
| AI Review Debt Age Ratio | Average AI PR review wait divided by the non-AI baseline wait; the primary review-debt signal. |
| Post-Merge Defect Rate | Defects linked to merged PRs per merged PR; severity-weighted in the weighted form. |
| Human Validation Cost | Estimated cost of reviewing AI-assisted work; directional until calibrated. |
| Net AI Delivery Value | Estimated value of AI assistance after validation, rework, defect, tooling and opportunity costs, plus redirected senior capacity. |
| AI ROI | Gross value plus counterfactual value divided by total AI delivery cost. |
| Counterfactual Value | Value of higher-leverage work that became possible because AI freed senior capacity. |
| Cognitive Load Index | Review effort per unit of AI PR size relative to comparable non-AI PRs. |
| Contextual Risk Score | Weighted score estimating how much control an AI-assisted change needs. See `docs/risk-policy-engine.md`. |
| Codebase Familiarity | How many comparable examples exist near a change; few examples raise risk. |
| Change Freshness | How recently and how often the affected files changed; high churn raises risk. |
| Ownership Boundary | Whether a change crosses team or service ownership; crossing it raises risk. |
| Dynamic AI WIP Limit | Recommended cap on concurrent AI-assisted work, adjusted by defects, review debt and seniority. |
| Data Confidence Score | 0-100 reliability score per metric, driving how it may be used. See `docs/data-confidence.md`. |
| Decision-grade | A metric trustworthy enough (confidence >= 70) to drive enforcement. |
| Trend-only | A signal (confidence < 50, or inherently weak) usable only as direction, never for decisions. |
| Operating Mode | Observation, Warning, Recommendation or Enforcement; defines how much the platform intervenes. |
| Observation Mode | Collect and compute only; no developer-facing warnings or blocks. |
| Warning Mode | Non-blocking PR comments and soft guidance. |
| Recommendation Mode | Team-level recommendations and playbooks. |
| Enforcement Mode | Calibrated blocking checks with an always-available emergency override. |
| Emergency Override | The `emergency-fix` label that bypasses enforcement and is recorded in the audit log. |
| Policy Engine | The component that applies guardrails to PRs. See `docs/risk-policy-engine.md`. |
| Playbook | Problem-to-action-to-owner mapping surfaced to teams. |
| Psychological Safety Pulse | A six-question survey gating rollout; below 3.5 pauses expansion. See `docs/psychological-safety.md`. |
| Prompt Leakage Scanning | Comment-only detection of possible sensitive data in PR text; never blocks, never stores raw matches. |
| Maturity Band | Descriptive AI-adoption band (never a ranking). |
| SourceConnector | The capability contract every provider adapter must satisfy. See `docs/multi-provider-strategy.md`. |
| Data Confidence (label) | The human-readable band (high, medium-high, etc.) paired with the numeric score. |
| Net Reconciliation Gap | Nightly difference between provider source counts and local counts; must stay under 5%. |
