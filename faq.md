# Frequently Asked Questions (FAQ)

### Q: Is this tool being used to monitor my individual productivity?
**A:** No. The platform is designed for team-level insights and system-level governance. Individual developer rankings or leaderboards are strictly prohibited and technically disabled in the UI.

### Q: What is "AI-assisted PR Rate"?
**A:** It is the percentage of Pull Requests that have been declared as using AI assistance (via metadata) or inferred as using AI based on configured patterns.

### Q: Why is my PR being blocked?
**A:** PRs are typically only blocked in later phases if required AI metadata is missing. You can always use the `emergency-fix` label to override this if there is a production urgency. All overrides are audited.

### Q: What is the "Data Confidence Score"?
**A:** It represents how much we trust the data for a specific metric. A score below 70 means the metric should be used for trend analysis only and not for hard decision-making or enforcement.

### Q: How is "Net AI Delivery Value" calculated?
**A:** It is the estimated time saved by AI (Gross Value) minus the cost of human validation (review time), rework, defects, and tooling. It provides an economic view of AI adoption.

### Q: What should I do if the PR Bot gives a wrong recommendation?
**A:** Recommendations are guidance. If you feel a recommendation is incorrect, please provide feedback in the `#ai-delivery-feedback` channel so we can tune the thresholds.

### Q: Does the platform store my raw code or prompts?
**A:** No. The platform only stores metadata and derived metrics. Raw code and raw prompt content are not stored to ensure privacy and security.
