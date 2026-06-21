# Quick Start

Stand a team up on the AI Delivery Control Plane. New teams always begin in Observation Mode (no warnings, no blocks) and graduate through the same phase gates as the pilot.

## Prerequisites

```text
SSO access to the platform.
Admin rights on your team's GitHub repositories and Jira projects (for initial setup only).
A named Engineering Manager who acknowledges the "no individual performance use" guarantee.
```

## Onboarding in five steps

```text
1. Sign in at https://ai-delivery.internal.example.com with corporate SSO.
2. Settings > Integrations: confirm the GitHub App is installed for your org.
3. Connect your Jira project (API token or OAuth).
4. Submit a team config based on examples/team-config.yaml (maps team -> repos -> Jira project).
5. Confirm Observation Mode is active. Data collection starts immediately; metrics stabilise after 1-2 sprints.
```

## What each role does first

```text
Platform / DevEx Engineer: verify connector health and Data Confidence Score >= 75 before trusting metrics.
Engineering Manager: review the Team Dashboard weekly; action one recommendation; keep it team-level.
Tech Lead: watch the PR Risk View for high-risk AI-assisted PRs.
Developer: nothing to install. Fill in the PR template (examples/pr-template.md); read the in-PR guidance.
Executive: check the Executive Summary at the quarterly governance forum.
```

## Reading the dashboard

```text
AI-assisted PR Rate     - share of PRs using AI.
AI Review Debt          - extra review load from AI output (ratio vs non-AI baseline).
Net AI Delivery Value   - estimated economic impact after validation/rework/defect/tooling costs.
Data Confidence Score   - reliability of each metric. Below 70 = trend only, not for decisions.
```

Estimates always show a confidence label. The platform never presents an estimate as exact fact and never ranks individuals.

## Graduating beyond Observation Mode

```text
Move to Warning Mode (soft PR comments) only after the team trusts the read-only dashboard.
Move to Enforcement Mode only after warnings are calibrated and psychological safety is above 3.5.
The phase gates in phase-packages/ define the criteria.
```

## Support

```text
Slack: #ai-delivery-support (help) and #ai-delivery-feedback (false positives, threshold tuning).
Questions: faq.md
Concepts and specs: docs/
```
