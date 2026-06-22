# Validated AI Delivery Framework v1.8

## AI Delivery Control Plane for Platform Engineering Teams

**Version:** 1.8  
**Status:** Platform-ready / MVP-aligned / Enterprise adoption oriented / Evidence-calibrated / Operationally implementable  
**Primary audience:** Platform Engineering, Engineering Productivity, DevEx, CTO Office, Security, Engineering Managers  
**Core product direction:** A platform-team-owned control plane for measuring, governing and improving AI-assisted software delivery.

---

## 1. Executive Summary

AI-assisted software delivery is no longer only a developer productivity topic. In enterprise environments, AI changes the whole software delivery system: code generation, test creation, documentation, review load, security posture, delivery economics and team behaviour.

The main risk is not that AI cannot generate output. The risk is that AI can generate more output than the organisation can safely validate, absorb and maintain.

The Validated AI Delivery Framework v1.8 positions this capability as a **Platform Engineering tool**:

> Platform teams operate the tool.  
> Engineering managers interpret the results.  
> Tech leads act on the recommendations.  
> Developers interact with it with minimal friction.  
> CTO, security and compliance teams use it for governance and investment decisions.

The product should not be positioned as an individual developer monitoring system. It should be positioned as:

> **An AI Delivery Control Plane that helps engineering organisations measure the real value, risk and validation cost of AI-assisted software delivery.**

v1.8 strengthens the framework in six areas:

```text
1. Operationally clear economic formulas, including explicit AI ROI denominator rules.
2. Practical counterfactual value estimation methods and confidence guardrails.
3. Repeatable data confidence scoring and risk-dimension measurement methods.
4. Actionable mitigation playbooks for dependency, bias, prompt leakage and overrides.
5. Stronger governance cadence through onboarding, quarterly review and incident integration.
6. Expanded cultural safety guidance with safe AI usage examples and non-ranking maturity language.
```

---

## 2. Core Thesis

The core thesis remains:

> AI output speed is not the same as validated delivery capacity.

In enterprise software delivery, value is created only when AI-assisted output is:

- understood by humans,
- reviewed properly,
- tested adequately,
- secure,
- maintainable,
- economically beneficial,
- aligned with team and organisational standards,
- safe to deploy and support in production.

Therefore, the primary metric is not AI output volume.

The primary metric is:

```text
Validated AI Delivery = AI-assisted output that has been reviewed, tested, understood, governed and successfully delivered without unacceptable downstream cost or risk.
```

---

## 3. Product Positioning

### 3.1 Recommended Product Category

The product should be positioned as:

```text
AI Delivery Intelligence & Governance Platform
```

or, more technically:

```text
AI Delivery Control Plane for Platform Engineering Teams
```

It sits at the intersection of:

```text
Engineering Intelligence
+ Developer Productivity Analytics
+ AI Governance
+ DevEx Platform
+ Software Delivery Risk Management
```

### 3.2 Simple Market Message

Recommended external positioning:

> Measure the real value and risk of AI-assisted software delivery.

Alternative:

> Govern AI-assisted engineering without slowing teams down.

Alternative for technical buyers:

> The control plane for AI-assisted software delivery.

---

## 4. Who Uses This Platform?

### 4.1 Primary User: Platform Engineering / Engineering Productivity Team

The platform team owns and operates the system.

Their responsibilities:

- connect GitHub, GitLab, Jira, SonarQube and CI/CD systems,
- define organisation-wide AI delivery policies,
- monitor AI review debt,
- tune dynamic AI WIP limits,
- manage team-level dashboards,
- support engineering managers and tech leads,
- provide governance reports to leadership,
- ensure the tool improves DevEx rather than creating bureaucracy.

Primary needs:

```text
Connectors
Metrics engine
Risk engine
Policy engine
Alerting
Team dashboards
Executive reports
Governance controls
Data confidence tracking
```

### 4.2 Secondary Users: Engineering Managers and Tech Leads

Engineering managers and tech leads use the platform to answer:

- Is AI helping this team or increasing hidden cost?
- Is review debt growing?
- Are senior engineers overloaded?
- Are AI-assisted PRs creating more post-merge defects?
- Which workflows need stronger review gates?
- Which teams need training or coaching?
- Should AI rollout be expanded, slowed or paused?

They should see team-level insights, not individual surveillance.

### 4.3 Tertiary Users: CTO, VP Engineering, Security and Compliance

Leadership users need aggregated insight:

- Net AI Delivery Value,
- AI adoption health,
- risk posture,
- AI governance maturity,
- cost trend,
- validated productivity trend,
- compliance exposure,
- security risk trend.

### 4.4 Developer Interaction Model

Developers should not feel that the tool is watching them individually.

Developer interaction should be light:

- PR template AI usage metadata,
- gentle CI warnings,
- PR size warnings,
- risk-based reviewer suggestions,
- explainability prompts,
- optional confidence check,
- team-level feedback in retrospectives.

Developer-facing message:

> This tool is not here to measure you. It is here to help the team use AI safely, sustainably and effectively.

---

## 5. What This Product Is Not

The product must avoid these positions:

### 5.1 Not a Developer Surveillance Tool

Wrong positioning:

```text
We track how much each developer uses AI.
```

Correct positioning:

```text
We help teams understand whether AI-assisted delivery is creating validated value or hidden risk.
```

### 5.2 Not an AI Coding Assistant

The product does not compete directly with Copilot, Cursor, CodeWhisperer, Tabnine or similar tools.

It measures and governs the delivery impact of those tools.

### 5.3 Not Just a DORA Dashboard

DORA metrics measure software delivery performance.

This framework adds AI-specific dimensions:

- AI validation cost,
- AI review debt,
- trust calibration,
- AI-assisted defect rate,
- AI usage risk,
- AI output acceptance,
- AI maintainability burden,
- AI governance compliance.

### 5.4 Not a Static Compliance Checklist

The platform should be adaptive:

- thresholds change by team maturity,
- WIP limits change based on defects and review capacity,
- risk scores change based on domain criticality,
- recommendations change based on trends.

---

## 6. Platform Architecture Overview

The recommended architecture is modular.

```text
Source Systems
  GitHub / GitLab
  Jira / Azure DevOps
  SonarQube / Snyk / Black Duck
  CI/CD
  Incident tools
  Optional IDE telemetry
        ↓
Connectors / Collectors
        ↓
Event Store / Operational Database
        ↓
Metrics Engine
        ↓
Risk Engine
        ↓
Policy Engine
        ↓
Recommendation Engine
        ↓
Dashboards / Alerts / Reports
```

---

## 7. Core Product Modules

## 7.1 Connectors

Initial connectors:

```text
GitHub or GitLab
Jira
SonarQube or equivalent code quality tool
CI/CD metadata
Slack or Teams notifications
```

Future connectors:

```text
PagerDuty / Opsgenie
Datadog / New Relic
Black Duck / FossID
Snyk
IDE telemetry
GitHub Copilot metrics
Azure DevOps
Jenkins
CircleCI
```

### MVP Connector Recommendation

Start with:

```text
GitHub + Jira
```

Then add:

```text
SonarQube + CI/CD
```

Avoid starting with IDE telemetry because it increases privacy, consent and change-management complexity.

---

## 7.2 Metrics Engine

The metrics engine calculates delivery, validation, quality and economic metrics.

### MVP Metrics

Only five metrics are required for the first MVP:

```text
1. AI-assisted PR rate
2. AI Review Debt
3. Post-merge defect rate
4. Human Validation Cost
5. Net AI Delivery Value
```

### Expanded Metrics

Later stages may include:

```text
Trust Calibration Gap
Cognitive Load Index
Prompt Efficiency Score
AI Test Quality Score
Maintainability Risk Score
Dynamic AI WIP Limit
AI Output Acceptance Rate
AI Governance Compliance Score
AI Dependency Risk
AI-assisted PR Quality Gap
Metric Confidence Score
Counterfactual Value of Redirected Cognitive Capacity
```

---

## 7.3 Risk Engine

The risk engine determines how much control an AI-assisted item requires.

Risk dimensions:

```text
Task type
Developer experience
PR size
Domain criticality
Security-sensitive files
Cross-team impact
Production blast radius
Historical defect trend
Reviewer load
Test quality
Codebase familiarity
Change freshness
Ownership boundary complexity
```

Example risk formula:

```text
AI Contextual Risk Score =
  Task Type Weight
+ Developer Experience Weight
+ Domain Criticality Weight
+ Change Size Weight
+ Security Sensitivity Weight
+ Cross-team Impact Weight
+ Reviewer Load Weight
+ Codebase Familiarity Weight
+ Change Freshness Weight
+ Ownership Boundary Weight
```

Example weights:

| Factor | Low | Medium | High |
|---|---:|---:|---:|
| Documentation | 1 | - | - |
| Test generation | 2 | 3 | - |
| Internal tooling code | 2 | 3 | 4 |
| Business logic | 3 | 4 | 5 |
| Payment/auth/security | 5 | 7 | 10 |
| Cross-service contract | 5 | 7 | 10 |

Additional v1.8 risk dimensions:

| Factor | Low | Medium | High |
|---|---:|---:|---:|
| Codebase familiarity | Many local examples | Some comparable patterns | Few or no comparable examples |
| Change freshness | Stable area | Modified within 30 days | High churn or recently refactored |
| Ownership boundary | Same team | Shared module | Multiple teams or service owners |

Example risk metadata:

```yaml
codebase_familiarity:
  low_risk: many_similar_patterns_exist
  medium_risk: some_similar_patterns_exist
  high_risk: few_or_no_similar_patterns_exist

change_freshness:
  code_modified_within_30_days: 1
  file_with_high_churn_history: 2
  file_modified_by_multiple_teams: 2

ownership_boundary:
  same_team_owned: 0
  shared_library_or_platform_module: 1
  cross_service_contract_or_multi_team_owner: 3
```

Rationale:

```text
AI risk rises when the model is asked to generate code in areas where the repository provides few examples, where recent churn makes the local pattern unstable, or where ownership boundaries make validation socially and technically harder.
```

Practical measurement for codebase familiarity:

```text
Codebase Familiarity Score = number of similar code patterns or commits in the affected repository area

MVP proxy:
- Compare PR file paths to commits from the previous 6 months.
- Low familiarity: fewer than 5 commits in a similar path area.
- Medium familiarity: 5-20 commits in a similar path area.
- High familiarity: more than 20 commits in a similar path area.
```

Alternative proxy:

```text
Use SonarQube duplication or structural similarity metrics on affected files to estimate whether the repository contains many examples of the pattern being generated.
```

Change freshness defaults:

```text
Change Freshness Score =
  (code_modified_within_30_days ? 1 : 0)
+ (file_with_high_churn_history ? 2 : 0)
+ (file_modified_by_multiple_teams ? 2 : 0)

High churn history = file modified more than 10 times in the last 3 months.
Multiple teams = file modified by developers from more than 2 teams in the last 6 months.
```

Risk interpretation:

| Score | Meaning | Action |
|---:|---|---|
| 0 | Stable area | Normal risk weighting |
| 1-2 | Some recent activity | Add reviewer context or local pattern check |
| 3+ | High churn or ownership conflict | Require explicit owner review and consider splitting PR |

These thresholds are configurable per team, but the defaults should be present so platform teams can start without inventing their own model.

---

## 7.4 Policy Engine

The policy engine applies automated controls.

Example policies:

```yaml
policies:
  ai_metadata_required:
    enabled: true
    applies_to: pull_request
    failure_mode: block

  large_ai_pr_warning:
    enabled: true
    threshold_changed_lines: 300
    action: warn_and_recommend_split

  high_risk_ai_pr_review:
    enabled: true
    risk_threshold: 10
    required_reviewers:
      - senior_engineer
      - code_owner

  security_sensitive_change:
    enabled: true
    paths:
      - "**/auth/**"
      - "**/security/**"
      - "**/payment/**"
      - "**/terraform/**"
    required_reviewers:
      - appsec
      - senior_engineer

  ai_review_debt_control:
    enabled: true
    if_review_debt_age_exceeds: "2x_normal_pr_wait_time"
    action: reduce_ai_wip_limit

  emergency_override:
    enabled: true
    label: emergency-fix
    requires_reason: true
    action: allow_with_audit_log
```

Policy enforcement must include an exception path. Emergency production fixes should not be blocked by missing AI metadata, but every override must be recorded, reviewed and trend-monitored.

Confidence gate:

```text
Only metrics with Data Confidence Score >= 70 may trigger blocking enforcement.
Metrics below 70 may warn only.
Metrics below 50 are trend-only and not decision-grade.
```

This applies to policy engine decisions, Dynamic AI WIP Limit changes, defect-based risk penalties and executive reporting. Metadata completeness checks may still block in Enforcement Mode because they validate required process input rather than inferred delivery quality.

Emergency override retrospective rule:

```text
Any team exceeding 3 emergency overrides per sprint must schedule a 15-minute retro.
```

Retro questions:

```text
1. Are we using emergency-fix correctly?
2. Is there a workflow improvement that would prevent future emergencies?
3. Should our policy thresholds be adjusted?
```

Frequent overrides are not inherently bad. They may indicate that policies are too strict, the team operates in a high-interruption domain, or emergency work is a normal part of the operating model. The retro exists to detect system problems, not to blame teams.

---

## 7.5 Recommendation Engine

The recommendation engine converts metrics into actions.

Examples:

| Signal | Recommendation |
|---|---|
| AI Review Debt high | Reduce AI WIP limit next sprint |
| PR > 300 LOC | Split PR or add second reviewer |
| High-risk domain change | Require senior + security review |
| Defect rate rising | Pause AI expansion for affected team |
| Low ownership confidence | Require explainable review |
| High prompt iteration count | Re-refine story before coding |
| High coverage but low mutation score | Improve test quality gate |
| Low data confidence | Use metric for trend only, not decision |

---

## 8. Role-Based Dashboard Model

Phase-based UI progression:

| Phase | Product surface introduced |
|---:|---|
| 1 | Admin login, integration connect, team mapping |
| 2 | Read-only Overview, Team Dashboard, PR Risk View, Metrics Detail |
| 3 | Developer in-PR view and PR comment bot |
| 4 | Policy Settings, Recommendations / Playbooks |
| 5 | Executive Summary, multi-team navigation, RBAC, self-service onboarding |

## 8.1 Platform Team View

Purpose: operate the system.

Widgets:

```text
Connector health
Data freshness
Data confidence by source
Policy violations
Team AI adoption trend
AI Review Debt by team
Dynamic WIP limit by team
Alert queue
Integration failures
Governance compliance status
```

## 8.2 Engineering Manager / Tech Lead View

Purpose: manage team delivery health.

Widgets:

```text
AI-assisted PR rate
AI Review Debt
Post-merge defect rate
Review load
PR size distribution
Net AI Delivery Value
Risky PRs requiring action
Team-level cognitive load
Recommended actions
Trend over last 3 sprints
```

## 8.3 Developer View

Purpose: lightweight guidance.

Widgets or PR comments:

```text
AI metadata missing
PR size warning
Suggested reviewer
Risk explanation
Checklist for AI-assisted work
Recommended tests
Explainability reminder
```

## 8.4 Executive View

Purpose: strategic decision-making.

Widgets:

```text
Net AI Delivery Value
AI adoption health
Validated productivity trend
Risk posture
Tooling cost vs value
Governance maturity
Top 5 teams needing support
Top 5 improvement opportunities
```

## 8.5 Security / Compliance View

Purpose: risk and control oversight.

Widgets:

```text
AI-generated security-sensitive changes
SAST/DAST findings on AI-assisted PRs
IP/licence similarity risk
Prompt/data policy violations
Sensitive data in prompts
AI output bias indicators
High-risk AI product features
Audit trail completeness
Regulated-domain exception list
```

Sensitive prompt data indicators:

```text
PII detected in prompt logs
API keys or credentials in prompt logs
Internal IP or customer data in prompt logs
Security policy violations
Repeated policy overrides
```

Example control:

```yaml
security_controls:
  prompt_sensitive_data_scanning:
    enabled: true
    sensitive_patterns:
      - "password"
      - "secret"
      - "api_key"
      - "token"
      - "auth"
      - "internal"
      - "pii"
```

MVP prompt leakage scanner scope:

```text
Check PR title, PR body and PR comments only.
Do not inspect or store raw AI tool prompts in MVP.
Add a PR comment when a pattern may indicate sensitive data.
Do not block on scanner output until the rule has been validated.
Do not store raw matches.
```

Use existing provider or specialist secret scanning where available:

```text
GitHub secret scanning
Gitleaks
TruffleHog
Snyk
```

The platform references these controls rather than building a full secret scanner in the MVP.

AI output bias indicators:

```text
Pattern repetition in generated code
Over-reliance on one architectural style
Under-generation of edge-case tests
Recency bias toward the newest local pattern
Security checks skipped in generated test cases
```

These indicators should be treated as review prompts, not as accusations against individual developers.

Prompt leakage scanning implementation:

```text
MVP:
- Implement as a pre-commit hook or GitHub Action comment.
- Scan PR description and comments for sensitive patterns.
- Add a PR comment when a pattern may indicate sensitive data.
- Do not block the PR automatically.
- Do not store raw matches.

Enterprise rollout:
- Use regex plus contextual/NLP scanning.
- Generate monthly reports on pattern frequency by team.
- Provide training for teams with high pattern frequency.
```

MVP sensitive patterns:

```text
password
secret
api_key
token
auth
internal
pii
email-like strings
phone-like strings
```

Suggested PR comment:

```text
This PR contains text that may contain sensitive data. Please verify before merging. The platform has not stored the raw match.
```

Bias indicator action rule:

```text
Bias indicators should be used as review prompts, not hard enforcement triggers.
```

If a PR triggers multiple bias indicators:

```text
1. Add a PR comment that the AI-assisted code may need deeper review.
2. Ask the reviewer to check edge-case handling.
3. Ask the reviewer to check error-path coverage.
4. Ask the reviewer to check architectural consistency.
5. Track whether indicator frequency changes after model or prompt changes.
```

---

## 9. MVP Scope

The MVP must be narrow.

### 9.1 MVP Problem Statement

> Engineering leaders do not know whether AI-assisted PRs are creating real delivery value or merely shifting cost into review, rework and defects.

### 9.2 MVP Target User

Primary:

```text
Platform Engineering / Engineering Productivity Lead
```

Secondary:

```text
Engineering Manager / Tech Lead
```

### 9.3 MVP Integrations

Required:

```text
GitHub
Jira
```

Optional but valuable:

```text
SonarQube
Slack
```

### 9.4 MVP Metrics

```text
AI-assisted PR rate
AI Review Debt
Post-merge defect rate
Human Validation Cost
Net AI Delivery Value
```

### 9.5 MVP Dashboard Screens

```text
1. Team AI Delivery Health
2. AI-assisted PR Comparison
3. Review Debt and Reviewer Load
4. Defect and Rework Trend
5. Net AI Delivery Value
6. Recommendations
```

### 9.6 MVP Non-Goals

Do not include in first MVP:

```text
IDE telemetry
Full EU AI Act automation
Model drift monitoring
Prompt repository
Multi-model evaluation
Advanced security scanning
Custom report builder
Full A/B testing engine
```

The MVP may still support a lightweight experiment mode during rollout by randomly showing warnings to a subset of PRs and comparing team-level outcomes. It should not ship a general-purpose experimentation platform.

---

## 10. Core Metric Definitions

## 10.1 AI-assisted PR Rate

```text
AI-assisted PR Rate = AI-assisted PR count / Total PR count
```

An AI-assisted PR is a PR where one or more of these are true:

```text
PR metadata says AI was used
AI label exists
AI usage is detected by configured automation
Developer confirms AI assistance
IDE/tool telemetry indicates AI assistance
```

For MVP, use explicit PR metadata and labels.

---

## 10.2 AI Review Debt

```text
AI Review Debt = AI-assisted PRs waiting for review beyond expected review time
```

Recommended formula:

```text
AI Review Debt Age Ratio =
Average AI PR Review Wait Time / Normal PR Review Wait Time Baseline
```

Interpretation:

| Ratio | Meaning | Action |
|---:|---|---|
| < 1.0 | Healthy | Continue |
| 1.0 - 1.5 | Watch | Monitor |
| 1.5 - 2.0 | Warning | Add reviewer capacity |
| > 2.0 | Critical | Reduce AI WIP / split PRs |

Normal PR review wait baseline:

```text
Median first-review wait time for non-AI PRs over last 3-6 sprints
```

Use median rather than average to reduce distortion from outliers.

---

## 10.3 Post-Merge Defect Rate

```text
Post-Merge Defect Rate =
Defects linked to merged PRs / Merged PR count
```

Weighted version:

```text
Weighted Defect Rate =
Σ Defect Severity Weight / Merged PR count
```

Example severity weights:

| Severity | Weight |
|---|---:|
| Cosmetic | 0.25 |
| Minor | 0.5 |
| Medium | 1.0 |
| Major | 2.0 |
| Critical | 5.0 |

AI-assisted PRs should be compared against similar non-AI PRs, not against all PRs.

---

## 10.4 Human Validation Cost

```text
Human Validation Cost = Review Hours x Reviewer Hourly Cost
```

Human Validation Cost is estimated, not directly measured, unless the organisation has explicit active review-time telemetry with consent and legal approval.

MVP proxy:

```text
Estimated Review Hours =
  Base Review Estimate
+ Comment Thread Factor
+ Change Request Factor
+ Re-review Round Factor
+ PR Size Factor
+ Risk Factor
```

Fallback proxy:

```text
First review timestamp to approval timestamp
```

The fallback proxy can overestimate or underestimate effort because elapsed review time is not the same as active review time.

Recommended data confidence:

| Method | Confidence |
|---|---|
| IDE/review active time telemetry | High |
| GitHub/GitLab timestamps | Medium |
| Manual estimate | Low |

MVP calibration mechanism:

```text
For MVP, treat Human Validation Cost as an approximation.
Before enterprise rollout, run a 2-week calibration study with 3 pilot teams.
Reviewers log actual review time for AI-assisted and comparable non-AI PRs.
Use the study to tune timestamp-based estimation rules.
```

GitHub engagement proxy:

```yaml
review_engagement_signals:
  comments_left: increases_estimated_review_effort
  changes_requested: increases_estimated_review_effort
  review_threads_opened: increases_estimated_review_effort
  multiple_review_rounds: increases_estimated_review_effort
  approval_without_comments: lower_confidence_estimate
```

This proxy should improve directional accuracy without requiring IDE telemetry in the MVP.

Display rule:

```text
Human Validation Cost is directional until calibrated in Phase 3.
It must be displayed with Data Confidence Score.
It must not be used for individual performance measurement.
```

---

## 10.5 Net AI Delivery Value

> Superseded: the absolute "Net AI Delivery Value" framing in this section is replaced by the correlational **Validated Delivery Trend (VDT)** in [docs/metrics-catalogue.md](docs/metrics-catalogue.md). Treat any figure below as directional only, never causal.

```text
Estimated Net AI Delivery Value =
Estimated Gross AI Time Saving Value
- Human Validation Cost
- Rework Cost
- Defect Cost
- Tooling Operational Cost
- Senior Opportunity Cost
- Adoption Friction Cost
+ Counterfactual Value of Redirected Cognitive Capacity
```

Where:

```text
Estimated Gross AI Time Saving Value =
Estimated Manual Baseline Effort - AI-assisted Effort
x Blended Hourly Rate
```

Counterfactual value captures what saved senior engineering capacity was redirected toward:

```text
Counterfactual Value =
Value of architecture, reliability, mentoring, security or product work
that became possible because AI reduced routine implementation effort.
```

This term prevents Net AI Delivery Value from becoming a simplistic pass/fail signal. AI may still be strategically useful when near-term delivery economics are neutral but senior capacity is redirected toward higher-leverage work.

Separate ROI view:

```text
AI ROI =
(Estimated Gross AI Time Saving Value + Counterfactual Value)
/ Total AI Delivery Cost

Total AI Delivery Cost =
Human Validation Cost
+ Rework Cost
+ Defect Cost
+ Tooling Operational Cost
+ Senior Opportunity Cost
+ Adoption Friction Cost
```

Equivalent implementable form:

```text
Total AI Delivery Cost =
Human Validation Cost
+ Rework Cost
+ Defect Cost
+ Tooling Operational Cost
+ Senior Opportunity Cost
+ Adoption Friction Cost
```

Recommendation:

```text
Show both Net AI Delivery Value and AI ROI.
Net value answers: "Did this create economic value after costs?"
ROI answers: "How efficiently did AI convert cost into value?"
```

Display rule:

```text
Every Net AI Delivery Value view must show:
- Estimated value
- Data confidence label
- Input assumptions
- Baseline method
```

Counterfactual value estimation methods:

| Method | How it works | Confidence | MVP recommendation |
|---|---|---|---|
| Survey-based | Ask senior engineers quarterly what percentage of AI-saved time was redirected to higher-value work, then multiply by senior blended rate | Low to medium | Use for MVP as directional input |
| Activity-based | Track architecture reviews, mentoring, security reviews and reliability work against a pre-AI baseline | Medium | Add after teams have consistent activity tagging |

Survey-based formula:

```text
Counterfactual Value =
Estimated AI-saved senior hours
x Percentage Redirected to Higher-Value Work
x Senior Blended Hourly Rate
```

Activity-based formula:

```text
Counterfactual Value =
(Current Higher-Value Engineering Hours - Pre-AI Baseline Higher-Value Engineering Hours)
x Senior Blended Hourly Rate
```

Guardrails:

```text
Treat counterfactual value as directional until calibrated.
Do not use self-reported counterfactual value for hard enforcement.
Compare quarterly survey results with activity evidence when available.
Ask for examples of redirected work to reduce optimism bias.
Cap counterfactual value at the estimated gross AI time saving until stronger evidence exists.
```

### Example

```text
Manual baseline effort: 20 hours
AI-assisted effort: 12 hours
Blended hourly rate: £70
Estimated gross AI time saving value: (20 - 12) x £70 = £560

Senior review: 3 hours x £110 = £330
Rework: 1.5 hours x £70 = £105
Tooling cost allocation: £20
Defect cost: £0
Opportunity cost: £55
Adoption friction: £15
Counterfactual value: £120

Estimated Net AI Delivery Value = £560 - £330 - £105 - £20 - £0 - £55 - £15 + £120 = £155
Confidence: Medium
Method: historical matched PR baseline + review cost proxy
```

Interpretation:

```text
AI created positive value, but the result depends heavily on whether saved senior capacity was redirected toward valuable work.
```

---

## 10.6 Cognitive Load Index

```text
Cognitive Load Index =
(AI PR Review Time / AI PR Size)
/
(Baseline Review Time for Non-AI PRs / Baseline Non-AI PR Size)
```

Interpretation:

| Index | Meaning | Action |
|---:|---|---|
| < 0.8 | AI PRs appear easier to review | Validate sample quality; do not assume this is always healthy |
| 0.8 - 1.5 | AI PRs are comparable to non-AI PRs | Continue monitoring |
| > 1.5 | AI PRs are harder to review | Split PRs, improve prompts, require clearer explanations |

This metric turns "AI code feels hard to review" into a measurable review-friction signal.

Baseline caveat:

```text
Cognitive Load Index should only be compared between teams and PR cohorts with similar baseline PR complexity.
If non-AI PRs are mostly documentation, configuration or small maintenance changes, the index should not drive AI governance decisions.
```

MVP complexity filter:

```text
Only include PRs where:
changed_lines > 20
AND changed_files > 1
```

Recommended stratification:

```text
Compare AI and non-AI PRs within similar buckets:
- small feature changes
- medium feature changes
- test-only changes
- refactoring changes
- security-sensitive changes
```

---

## 10.7 AI Dependency Risk

```text
AI Dependency Risk =
(AI-assisted PR Rate x AI Ownership Confidence Risk)
/ Team Size
```

Where:

```text
AI Ownership Confidence Risk = 1 - Ownership Confidence Score
Ownership Confidence Score = developer understanding + edge-case test evidence
```

Practical interpretation:

| Signal | Meaning |
|---|---|
| High AI usage + low ownership confidence | AI dependency risk is rising |
| Moderate AI usage + high ownership confidence | Healthy augmentation |
| Low AI usage + low ownership confidence | Training or workflow problem, not AI scale problem |

The aim is to identify whether teams are using AI as augmentation or unknowingly creating maintainability dependency.

Mitigation recommendations:

| Signal | Action |
|---|---|
| High AI usage + low ownership confidence | Rotate ownership of AI-generated code; require the author to demo AI-generated changes in a team meeting |
| Small team + high AI usage | Increase pairing on AI-assisted work; use AI code walkthrough checklists |
| High confidence + high AI usage | Continue, but monitor for over-reliance and require periodic manual revalidation |
| Low AI usage + low ownership confidence | Address general code ownership issues; do not treat this as an AI-specific failure |

Cadence:

```text
Teams with AI Dependency Risk > 0.5 should run a quarterly ownership review.
The review should sample AI-assisted PRs, check whether maintainers can explain them, and update team guidance.
```

---

## 10.8 AI-assisted PR Quality Gap

```text
AI-assisted PR Quality Gap =
AI-assisted PR Defect Rate - Comparable Non-AI PR Defect Rate
```

Trend view:

```text
AI Quality Gap Improvement =
Previous 6-month Quality Gap - Current 6-month Quality Gap
```

Interpretation:

| Trend | Meaning |
|---|---|
| Gap shrinking | AI-assisted delivery quality is improving |
| Gap flat | AI quality is not materially changing |
| Gap widening | AI-assisted work is diverging from expected quality |

Do not divide the gap by the number of AI examples. A larger sample should increase confidence, not make the quality gap appear smaller.

---

## 11. Dynamic AI WIP Limit

Static AI WIP limits are too crude. The limit should adapt to team capacity and quality outcomes.

### 11.1 Base Formula

```text
Base AI WIP Limit =
(Senior Engineers x 2)
+ (Mid-level Engineers x 1)
+ (Junior Engineers x 0.5)
```

### 11.2 Dynamic Adjustment

```text
Dynamic AI WIP Limit =
Base AI WIP Limit
- (Defect Rate Baseline Deviation x 0.5)
- (Review Debt Age Deviation x 0.3)
+ (Team Seniority Ratio x 0.2)
```

Where:

```text
Current AI Defect Rate Ratio =
Current AI-assisted weighted defect rate / comparable non-AI weighted defect baseline

Defect Rate Baseline Deviation = Current AI Defect Rate Ratio - 1

Current AI Review Debt Ratio = AI Review Debt Age Ratio

Review Debt Age Deviation = Current AI Review Debt Ratio - 1

Team Seniority Ratio = Senior engineers / Total engineers
```

### 11.3 Example

```text
Base WIP Limit: 8
Current AI defect rate: 1.3x baseline
Review debt age: 2.1x normal
Senior ratio: 0.6

Dynamic AI WIP =
8 - (0.3 x 0.5) - (1.1 x 0.3) + (0.6 x 0.2)
= 8 - 0.15 - 0.33 + 0.12
= 7.64

Rounded recommended limit: 7
```

Rule:

```text
Always round down when quality risk is increasing.
```

### 11.4 High-Risk Multiplicative Penalty

The standard formula is intentionally simple, but it can be too forgiving when multiple negative signals occur together. Severe quality and review deterioration should reduce AI WIP more sharply.

Trigger:

```text
If Current AI Defect Rate Ratio > 1.5
AND Current AI Review Debt Ratio > 1.2
THEN apply high-risk penalty
```

Penalty:

```text
High-Risk Dynamic AI WIP =
Base AI WIP Limit
x 0.7
x (1 - min(Defect Rate Baseline Deviation, 3) / 10)
```

Example:

```text
Base WIP Limit: 10
Defect rate deviation: 2.0
Review debt deviation: 1.5

High-Risk Dynamic AI WIP =
10 x 0.7 x (1 - 2.0 / 10)
= 10 x 0.7 x 0.8
= 5.6

Rounded recommended limit: 5
```

Guardrails:

```text
Never reduce below the configured team minimum automatically.
Never use low-confidence defect data for automatic high-risk penalties.
Show the reason for the WIP recommendation to the team.
Review penalty thresholds monthly during pilot rollout.
```

---

## 12. Platform-Team Operating Model

## 12.1 Platform Team Responsibilities

```text
Own integrations
Maintain data quality
Configure policies
Support teams during rollout
Monitor alerts
Tune thresholds
Maintain dashboards
Produce monthly AI delivery governance reports
Run adoption feedback sessions
```

## 12.2 Engineering Manager Responsibilities

```text
Review team-level metrics
Discuss findings in retrospectives
Protect psychological safety
Avoid individual performance use
Approve team-specific threshold adjustments
Act on recommendations
```

## 12.3 Tech Lead Responsibilities

```text
Review high-risk AI-assisted PRs
Coach developers on explainable AI usage
Ensure AI-generated code follows architecture standards
Support risk-based reviewer assignment
Provide feedback on false positives
```

## 12.4 Developer Responsibilities

```text
Declare AI assistance honestly
Own and understand submitted output
Add tests for AI-assisted work
Challenge AI output when needed
Avoid submitting unexplained generated code
Escalate unclear or risky AI output
```

## 12.5 Security Responsibilities

```text
Define sensitive paths
Own security-sensitive path reviewer requirements
Define data classification policy
Review high-risk AI-generated changes
Monitor SAST/DAST and IP/licence findings
Approve regulated-domain policies
```

## 12.6 Data Steward Responsibilities

Enterprise adoption requires explicit accountability for developer activity data, prompt metadata and retention rules.

```text
Define data retention periods
Ensure GDPR, CCPA and local privacy compliance
Manage data export and deletion requests
Audit access logs
Sign off new data sources
Approve prompt metadata retention policies
Enforce data minimisation principles
Review override and exception logs
```

Recommended default:

```text
Retain detailed PR and review analytics for 12 months.
Retain aggregated team-level trend metrics for 24 months.
Do not retain raw prompt content unless the organisation has explicit consent, legal basis and security controls.
Prefer derived prompt safety signals over raw prompt storage.
```

---

## 13. AI Usage Tagging Strategy

AI usage tagging should be transparent, low-friction and non-punitive.

### 13.1 PR Template

```markdown
## AI Assistance

- [ ] No AI assistance used
- [ ] AI used for code suggestions
- [ ] AI used for test generation
- [ ] AI used for documentation
- [ ] AI used for refactoring
- [ ] AI used for debugging or explanation

## AI Assistance Confidence

- [ ] Low
- [ ] Medium
- [ ] High

## Ownership Confirmation

- [ ] I understand the submitted changes
- [ ] I can explain and support this change in production
- [ ] I have reviewed AI-assisted output manually
- [ ] Tests have been added or updated where appropriate
- [ ] I have added or checked edge-case coverage where risk is material
```

### 13.2 Enforcement Rule

```text
A PR must explicitly select either "No AI assistance" or one or more AI usage categories.
```

This avoids ambiguous missing data.

If AI assistance is selected, the PR should also include an assistance confidence level. Low confidence should not block the PR automatically. It should trigger review guidance:

```text
Low confidence = ask for clearer explanation, stronger tests or senior review.
Medium confidence = normal review with AI-aware checklist.
High confidence = normal review; still validate critical paths.
```

### 13.3 Psychological Safety Note

The PR template should include:

```text
AI usage metadata is used for team-level delivery learning and governance. It is not used for individual performance scoring.
```

---

## 14. GitHub Actions Enforcement Example

The full, copyable workflow is in [examples/github-actions/ai-metadata-enforcement.yml](examples/github-actions/ai-metadata-enforcement.yml). It enforces AI metadata in Phase 4 and always honours the `emergency-fix` override label, which is recorded in the policy audit log (event shape in [docs/risk-policy-engine.md](docs/risk-policy-engine.md)).

---

## 15. Risk-Based Reviewer Assignment Example

The full, copyable workflow is in [examples/github-actions/risk-reviewer-assignment.yml](examples/github-actions/risk-reviewer-assignment.yml). It is a starter heuristic; the authoritative risk model is in [docs/risk-policy-engine.md](docs/risk-policy-engine.md).

---

## 16. Recommended MVP Data Model

The full schema (tables, indexes, cost configuration, retention and migration ownership) is in [docs/data-model.md](docs/data-model.md).

---

## 17. API Design for Platform Tool

The full endpoint list and response examples are in [docs/api-spec.md](docs/api-spec.md). Layered test-strategy and observability contracts are in [docs/testing-and-observability.md](docs/testing-and-observability.md).

---

## 18. Data Confidence Rules

> Superseded: confidence scoring now uses the multi-factor model in [docs/data-confidence.md](docs/data-confidence.md) (volume, freshness, completeness, stability), with a two-gate behaviour: warn at >= 70, block only at >= 90, withhold below 70.

Each metric must carry both a data confidence label and a 0-100 Data Confidence Score.

| Score | Label | Meaning | Usage |
|---:|---|---|---|
| 90-100 | High | Full automation, direct source data, no manual steps | Can drive automated decisions |
| 70-89 | Medium-high | Mostly automated with minor derivation gaps | Can guide decisions; review before hard enforcement |
| 50-69 | Medium-low | Significant derivation or incomplete source coverage | Directional only |
| < 50 | Low | Manual estimate, self-report or weak linkage | Trend only; no hard decisions |

Decision rule:

```text
Metrics with Data Confidence Score below 70 must not trigger hard enforcement automatically.
Metrics from 50-69 may trigger review, conversation or trend monitoring.
Metrics below 50 must be labelled "not suitable for decision-making".
```

Calculation methodology:

```text
Data Confidence Score =
100 - (Derivation Penalty + Coverage Penalty + Timeliness Penalty)
```

Derivation penalty:

| Source method | Penalty |
|---|---:|
| Direct instrumented source | 0 |
| Derived from timestamps | 15 |
| Derived from linked data | 25 |
| Manual input | 40 |

Coverage penalty:

| Coverage state | Penalty |
|---|---:|
| All expected events captured | 0 |
| Minor gaps under 5% | 5 |
| Significant gaps from 5-20% | 15 |
| Major gaps over 20% | 30 |

Timeliness penalty:

| Freshness | Penalty |
|---|---:|
| Real-time | 0 |
| Under 1 hour delay | 5 |
| Under 1 day delay | 10 |
| Over 1 day delay | 20 |

Example:

```text
Review debt from GitHub timestamps with minor webhook gaps and under 1 hour delay:
100 - (15 + 5 + 5) = 75
```

Confidence issue example:

```text
AI Review Debt confidence is 72.
Reason: review timestamps are derived from GitHub events rather than active review time.
Recommendation: enable review engagement tracking or run a 2-week calibration study.
```

Platform teams should see confidence warnings alongside the metric, not buried in documentation.

---

## 19. Cultural Adoption Guardrails

The tool must protect trust.

### 19.1 Non-Negotiable Principles

```text
No individual AI productivity ranking
No use in performance reviews
Team-level reporting by default
Blameless retrospectives
Transparent data usage policy
Developers can challenge metric interpretation
AI usage disclosure must not be punished
```

### 19.2 Psychological Safety Pulse

Monthly pulse questions:

```text
I feel safe declaring AI usage in my work.
I feel safe challenging AI-generated output.
I feel safe saying I do not understand AI-generated code.
Our team reviews AI-assisted code carefully.
AI metrics are used to improve the system, not blame individuals.
I do not feel pressured to use AI when it is not helpful.
```

Scoring:

```text
1 = strongly disagree
5 = strongly agree
```

Action rule:

```text
If average score is below 3.5, pause metric expansion and run a team retro before adding further enforcement.
```

---

## 20. Operating Mode Rollout Model

Physical implementation phases are defined in [Section 27](#27-implementation-phase-packages) and the `phase-packages/` documents. This section defines runtime operating modes.

### Readiness Gate

```text
Confirm platform team owner
Confirm one pilot team
Confirm GitHub/Jira access
Confirm legal/privacy review for developer activity data
Confirm psychological safety statement
Confirm psychological safety statement reviewed with HR or People team
Confirm no individual performance usage
Confirm PR template adoption
```

### Observation Mode

Duration: 2 sprints

```text
Collect data
Do not show developer-facing PR warnings
Do not enforce hard policies
Show team-level dashboard only when data is labelled with confidence
Validate metric definitions
Gather developer feedback
```

### Warning Mode and Recommendation Mode

Duration: 2 sprints

```text
Add PR warnings
Add recommendation engine
Add review debt alerts
Start Estimated Net AI Delivery Value reporting with confidence label and assumptions
```

Optional experiment mode:

```text
Randomly assign 50% of eligible AI-assisted PRs to receive soft warnings.
Keep the other 50% as a control group.
Compare warning and control outcomes after 2 sprints.
Measure PR split rate, review wait time, defect linkage and developer feedback.
Use results to tune policies before Enforcement Mode.
```

Experiment mode must remain team-level and non-punitive. It is used to validate whether a policy improves outcomes before enforcement, not to compare individual developers.

### Enforcement Mode

Duration: after trust is established

```text
Require AI metadata
Require senior review for high-risk PRs
Warn on large AI PRs
Apply dynamic AI WIP limits
Allow Emergency Override
```

Only metrics with Data Confidence Score >= 70 may trigger blocking enforcement. Metrics below 70 may warn only. Metrics below 50 are trend-only and not decision-grade.

### Enterprise Rollout Mode

```text
Multi-team dashboards
Governance forum
Executive reporting
Security integrations
Policy-as-code
```

---

## 21. Platform Team MVP Backlog

### Epic 1: GitHub Integration

```text
Collect PR metadata
Collect PR review timestamps
Collect changed files and changed lines
Collect labels
Parse AI metadata from PR template
Parse AI assistance confidence
Store webhook events
Capture emergency override labels
```

### Epic 2: Jira Integration

```text
Collect issues
Link Jira issues to PRs
Identify post-merge defects
Map severity
Map sprint/team
```

### Epic 3: Metrics Engine

```text
AI-assisted PR rate
AI Review Debt
Post-merge defect rate
Human Validation Cost
Net AI Delivery Value
AI ROI calculation
Counterfactual value input
Data confidence scoring
Human validation calibration study
```

### Epic 4: Risk Engine

```text
PR size risk
Security path risk
Domain criticality risk
Reviewer load risk
Codebase familiarity risk
Change freshness risk
Ownership boundary risk
Contextual AI risk score
```

### Epic 5: Policy Engine

```text
AI metadata required
Large PR warning
High-risk reviewer recommendation
Review debt threshold alert
Dynamic WIP recommendation
Emergency override audit logging
Prompt-sensitive data policy warning
```

### Epic 6: Dashboard

```text
Platform view
Team lead view
Executive summary view
Security and compliance view
Risky PR list
Recommendations list
Trend charts
Data confidence warnings
```

### Epic 7: Alerts

```text
Slack alert for review debt warning
Jira ticket for critical threshold breach
PR comment for large AI-assisted PR
Weekly email summary for platform team
Alert for low-confidence metrics used in policy decisions
```

---

## 22. Recommended Repository Structure

This documentation repository is organised as: `phase-packages/` (stage-gate files), `docs/` (canonical technical and operational references), `examples/` (copyable templates and configs), plus the root product docs (README, PRD, quick-start, faq). The eventual platform/service repository structure (collectors, engines, dashboard) is a Stage 2+ concern.

---

## 23. Example Team Configuration

The copyable team configuration is in [examples/team-config.yaml](examples/team-config.yaml). Field semantics are described in [docs/risk-policy-engine.md](docs/risk-policy-engine.md) and [docs/governance-and-privacy.md](docs/governance-and-privacy.md).

---

## 24. Pilot Success Criteria

A pilot is successful if, after 4-6 sprints:

```text
1. AI-assisted PRs can be reliably identified.
2. AI Review Debt can be measured with Data Confidence Score >= 70.
3. Post-merge defects can be linked to PRs with acceptable accuracy.
4. Engineering managers find the recommendations useful.
5. Developers do not report psychological safety concerns.
6. At least one actionable improvement is made from the dashboard.
7. Net AI Delivery Value can be estimated with Data Confidence Score >= 70.
8. Human Validation Cost has been calibrated with a small manual study or accepted as directional.
```

Business success criteria:

```text
Net AI Delivery Value is positive or trending positive.
AI-assisted defect rate is not materially worse than comparable non-AI work.
Review debt does not exceed 2x normal PR wait time for more than one sprint.
```

---

## 25. Pilot Failure / Pause Criteria

Pause or rollback the pilot if:

```text
AI-assisted defect rate exceeds 2x comparable baseline for 2 consecutive sprints.
AI Review Debt exceeds 2x normal PR wait time for 2 consecutive sprints.
Psychological safety score drops below 3.5.
Developers begin hiding AI usage.
Core metric confidence remains below 70 after 3 sprints.
Managers attempt to use metrics for individual performance scoring.
Emergency overrides become frequent without retro review.
```

Rollback action:

```text
Disable hard enforcement.
Return to observation mode.
Run blameless retro.
Review metric definitions.
Adjust policy and communication.
Restart with smaller scope.
```

---

## 26. Enterprise Roadmap

### Stage 1: MVP

```text
GitHub + Jira
Five core metrics
Team dashboard
Basic recommendations
Slack alerts
Confidence scoring
Policy override logging
```

### Stage 2: Platform Expansion

```text
GitLab support
SonarQube integration
CI/CD integration
Dynamic WIP automation
Policy-as-code
Multi-team reporting
Lightweight experiment mode
Codebase familiarity and churn risk
```

### Stage 3: Governance Expansion

```text
Security integrations
IP/licence scanning
Audit trails
Data retention controls
Compliance reports
Executive dashboards
Prompt-sensitive data scanning
Data stewardship workflows
```

### Stage 4: Intelligence Expansion

```text
Trend forecasting
Trust calibration
Maintainability tracking
Prompt efficiency
Model provider comparison
Advanced recommendation engine
AI Dependency Risk
AI-assisted PR Quality Gap
Cognitive Load Index
```

### Stage 5: Enterprise Control Plane

```text
SSO/RBAC
Multi-org support
Custom policies
Custom dashboards
API access
Report builder
Regulated sector packs
```

---

## 27. Implementation Phase Packages

The implementation plan is physically split into phase-package documents under:

```text
phase-packages/
```

Each phase is a mini-project with clear entry criteria, exit criteria, fail criteria and required deliverables. A phase does not start until the previous phase has passed its gate.

Master package index:

```text
phase-packages/README.md
```

Phase packages:

| Phase | Name | Package |
|---:|---|---|
| 0 | Groundwork and Legal Assurance | [phase-0-groundwork-and-legal-assurance.md](phase-packages/phase-0-groundwork-and-legal-assurance.md) |
| 1 | Data Architecture and Raw Collection | [phase-1-data-architecture-and-raw-collection.md](phase-packages/phase-1-data-architecture-and-raw-collection.md) |
| 2 | Metrics and Risk Engine | [phase-2-metrics-and-risk-engine.md](phase-packages/phase-2-metrics-and-risk-engine.md) |
| 3 | Soft Landing and Experiment | [phase-3-soft-landing-and-experiment.md](phase-packages/phase-3-soft-landing-and-experiment.md) |
| 4 | Automated Guardrails | [phase-4-automated-guardrails.md](phase-packages/phase-4-automated-guardrails.md) |
| 5 | Enterprise Rollout | [phase-5-enterprise-rollout.md](phase-packages/phase-5-enterprise-rollout.md) |

Required deliverables for every phase:

```text
[PhaseX]_exit_report.pdf
[PhaseX]_data_dictionary.json
[PhaseX]_config_changes.yaml
```

Stage-gate rule:

```text
At the end of every phase, the Platform Lead and pilot-team Engineering Manager must answer:
"Would moving to the next phase destabilise the current system?"

If the answer is yes, the current phase repeats or a bug-fix sprint is inserted.
Exit criteria must not be relaxed because the calendar is tight.
```

---

## 28. Investment Logic for Platform Teams

### 28.1 Cost of Not Acting

Estimated hidden costs of ungoverned AI adoption per 100 developers:

| Cost factor | Annual impact |
|---|---:|
| AI-generated defect rework | £180,000 - £400,000 |
| Senior over-validation | £250,000 - £600,000 |
| AI review debt and delays | £100,000 - £250,000 |
| Security incident remediation | £500,000 - £2,000,000 |
| Total | £1,000,000 - £3,000,000+ |

These numbers are directional. They should be replaced with organisation-specific rates after the MVP collects local validation, defect and review debt data.

### 28.2 ROI of a Control Plane

Potential annual savings:

| Benefit | Annual savings |
|---|---:|
| Reduced defect rework by 20-40% | £36,000 - £160,000 |
| More efficient validation | £50,000 - £200,000 |
| Faster safe AI adoption | £100,000 - £500,000 |
| Reduced incident risk | £100,000 - £500,000+ |
| Total potential savings | £286,000 - £1,360,000+ |

Example platform team cost:

```text
£200,000 - £600,000 per year
```

Expected ROI:

```text
1.5x - 3x after local calibration
```

The strongest business case is not "developers type faster". It is:

```text
The organisation can safely expand AI-assisted delivery without losing control of validation capacity, defect risk or senior engineering focus.
```

---

## 29. Adoption Value Staircase

The product should make the adoption path explicit:

| Level | Capability | Platform team question |
|---:|---|---|
| 1 | Measure | Where and how is AI being used? |
| 2 | Triage | Which teams or workflows show risk hotspots? |
| 3 | Govern | Which guardrails improve outcomes without damaging trust? |
| 4 | Optimise | How do we balance speed, validation and risk at scale? |
| 5 | Transform | How should AI change our delivery strategy? |

This staircase helps teams understand why the MVP starts with measurement rather than hard enforcement.

---

## 30. Team AI Delivery Maturity Model

| Maturity | AI-assisted PR rate | Goal | Platform focus |
|---|---:|---|---|
| Laggard | < 10% | Increase adoption through training | Remove barriers and improve enablement |
| Explorer | 10-30% | Understand quality impact | Measure review debt and defects |
| Adopter | 30-50% | Optimise governance | Tune Dynamic AI WIP and policy thresholds |
| Leader | > 50% | Demonstrate business value | Report Net AI Delivery Value and AI ROI |

Maturity must not be treated as a ranking. The labels are descriptive, not prescriptive.

```text
A team's AI adoption rate depends on domain, risk tolerance, legacy constraints, regulatory exposure and team composition.
```

A low-adoption team in a high-security regulated domain may be making a rational governance decision. A high-adoption team in a low-risk internal tooling domain may be making a rational speed decision. Neither team is inherently more mature.

The classification exists solely to help platform teams choose support actions, not to pressure teams into uniform AI usage. If the labels create status anxiety, replace them in the product UI with neutral bands:

```text
Low AI adoption
Moderate AI adoption
High AI adoption
Very high AI adoption
```

---

## 31. AI-Assisted Delivery Onboarding Checklist

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

This checklist ensures AI governance becomes part of normal engineering onboarding rather than a separate bureaucratic process.

---

## 32. Quarterly Governance Review

Platform teams should run a quarterly AI Delivery Governance Review.

Attendees:

```text
Platform team owner
Engineering managers from participating teams
Security and compliance representative
Data steward
Selected tech leads from pilot or high-adoption teams
```

Agenda:

```text
1. AI adoption health: AI-assisted PR rate by team.
2. Validation trends: review debt and validation cost.
3. Quality trends: defect rate and AI Quality Gap.
4. Economic trends: Net AI Delivery Value and AI ROI.
5. Risk posture: dependency risk, security signals and policy overrides.
6. Psychological safety pulse trend.
7. Policy adjustments: thresholds, new policies and retired policies.
8. 90-day roadmap.
```

Outputs:

```text
Updated policy configuration
Team-level action plans
Updated risk register
Investment recommendations
Data confidence improvement actions
```

The review should focus on system learning. It should not review individual developer performance.

Manager misuse escalation:

```text
If a manager attempts to use individual AI metrics for performance scoring:
1. Pause expansion for that team.
2. Notify Platform Lead and HR/People Lead.
3. Re-brief the manager and team.
4. Remove individual-level exports from that manager's access.
5. Resume only after misuse risk is addressed.
```

---

## 33. Incident Integration Use Case

Incident management closes the loop between delivery governance and production outcomes.

When an incident occurs:

```text
1. Identify AI-assisted PRs merged in the 30 days before the incident.
2. Flag those PRs for post-incident review.
3. If an AI-assisted PR contributed to the incident:
   - increment the AI-assisted defect rate,
   - flag similar AI-assisted PRs for re-review,
   - trigger a policy review for the affected workflow.
```

MVP implementation:

```text
1. Manually link a PagerDuty or Opsgenie incident ID to a Jira issue.
2. Automatically identify AI-assisted PRs linked to that Jira issue.
3. Add those PRs to the incident review report.
4. Update AI-assisted defect metrics only after the incident review confirms contribution.
```

Incident linkage creates candidates for human review, not automatic blame or automatic defect attribution. Do not assume a nearby AI-assisted PR caused the incident.

---

## 34. Safe AI Usage Examples

Good usage:

```text
Using AI to generate test cases for a well-specified API.
Using AI to refactor a known pattern with an existing test suite.
Using AI to generate documentation from existing code.
Using AI to find an edge case the developer had not considered.
Using AI to explain unfamiliar code before making a human-owned change.
```

Caution:

```text
Using AI to generate business logic before writing the specification.
Using AI to implement a security-sensitive change without independent verification.
Using AI to translate code from one language to another without deep understanding.
Using AI to change high-churn files without checking recent ownership context.
Using AI to create tests without checking whether the tests assert meaningful behaviour.
```

Problematic usage:

```text
Submitting AI-generated code the author does not understand.
Using AI to bypass normal design and review processes.
Pasting internal, customer or regulated data into public AI models.
Accepting generated security or payment logic without human validation.
Treating AI-generated tests as proof of correctness without review.
```

These examples should be reviewed quarterly and updated as team usage patterns evolve.

---

## 35. Pricing Hypothesis

Potential commercial positioning:

### Team Plan

```text
£200 - £500 / month
1-3 teams
GitHub/Jira integration
Core metrics
Basic dashboard
```

### Scale-up Plan

```text
£1,000 - £3,000 / month
Multi-team dashboards
Policy engine
Slack alerts
SonarQube integration
```

### Enterprise Plan

```text
£20,000 - £100,000+ / year
SSO/RBAC
Audit logs
Custom policies
Compliance reports
Security integrations
Enterprise support
```

### Consulting / Assessment Package

```text
AI Delivery Readiness Assessment
2-4 weeks
£15,000 - £50,000+
```

Outputs:

```text
AI usage heatmap
AI review debt analysis
Governance maturity score
Net AI Delivery Value estimate
Risk register
90-day roadmap
```

---

## 36. Final v1.8 Positioning Statement

The Validated AI Delivery Framework v1.8 defines a platform-team-owned control plane for AI-assisted software delivery.

It helps organisations move from:

```text
AI is making developers faster.
```

to:

```text
AI is creating validated, measurable, secure and economically meaningful delivery value.
```

The correct first product is not a full enterprise governance suite.

The correct first product is:

> A GitHub/Jira-based AI Delivery Dashboard that shows whether AI-assisted PRs are creating real value or shifting cost into review, rework and defects.

That is the sharp MVP.

Everything else should be added after the platform team proves that the dashboard changes engineering decisions.

---

## 37. Implementation Sign-Off

v1.8 is implementation-ready.

Final readiness assessment:

| Dimension | Rating | Notes |
|---|---|---|
| Problem definition | Excellent | Clear enterprise pain point and differentiated thesis |
| Product positioning | Excellent | Platform engineering control plane is the right category |
| MVP scope | Excellent | Focused GitHub/Jira dashboard with clear non-goals |
| Metric definitions | Excellent | Formulas, thresholds, confidence rules and examples are operational |
| Risk engine | Excellent | Practical proxies for familiarity, churn and ownership boundaries |
| Policy engine | Very good | Enforcement examples, override paths and retrospective rules are defined |
| Cultural safety | Excellent | Non-punitive design, psychological safety pulse and non-ranking language |
| Governance | Very good | Onboarding, quarterly review and incident integration are defined |
| Implementation guidance | Very good | Data model, APIs, config examples, timeline and backlog are present |
| Business case | Good | Conservative and ready for local calibration during MVP |

Pre-implementation checks:

```text
Confirm platform team owner.
Confirm pilot team.
Confirm GitHub/Jira access.
Confirm legal/privacy review for developer activity data.
Confirm HR or People review of the psychological safety statement.
Confirm data retention policy alignment.
Confirm PR template adoption.
```

Final recommendation:

```text
Proceed to implementation.
```

Optional future refinements for v1.9:

```text
Add enterprise exclude paths for prompt leakage scanning.
Add Cognitive Load Index sample-size confidence markers.
Add Phase 4 automated incident-to-Jira-to-PR linkage.
Consider making SonarQube recommended for pilot teams where maintainability metrics are important.
Refine counterfactual value capping after activity-based evidence is available.
```

---

## 38. One-Line Summary

> Validated AI Delivery Platform is an AI Delivery Control Plane for platform teams: it measures whether AI-assisted engineering is producing real delivery value, or merely increasing validation cost, risk and review debt.
