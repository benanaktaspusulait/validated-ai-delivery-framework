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

v1.8 keeps six implementation decisions explicit:

```text
1. Narrow MVP: GitHub + Jira first, with VDT shown as a correlational trend rather than causal ROI.
2. Data-confidence labels gate every operational decision and every executive signal.
3. Risk and policy rules stay configurable, explainable and confidence-gated.
4. Dependency, prompt leakage, bias and override signals trigger mitigation playbooks, not blame.
5. Governance uses onboarding, quarterly review, incident linkage and manager-misuse escalation.
6. Cultural safety remains non-negotiable: no individual rankings and no personal productivity dashboard.
```

---

## 2. Core Thesis

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
Validated AI Delivery = AI-assisted output that has been reviewed, tested, understood,
governed and successfully delivered without unacceptable downstream cost or risk.
```

---

## 3. Product Positioning

### 3.1 Recommended Product Category

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

> Measure the real value and risk of AI-assisted software delivery.

Alternative for technical buyers:

> The control plane for AI-assisted software delivery.

Competitive differentiation: see [docs/market-landscape.md](docs/market-landscape.md).

---

## 4. Who Uses This Platform?

| Persona | Goal | Primary surface |
|---|---|---|
| Platform / DevEx Engineer | Operate the system; keep data trustworthy | Platform view, Integrations, Policy Settings |
| Engineering Manager | Manage team delivery health without surveilling individuals | Team Dashboard, Recommendations |
| Tech Lead | Catch risky AI-assisted PRs early | PR Risk View, in-PR guidance |
| CTO / VP Engineering | Decide AI investment and rollout pace | Executive Summary |
| Security / Compliance | Oversee AI usage risk and prompt safety | Security signals, policy overrides |
| Developer | Merge safely with light, in-PR guidance | GitHub PR (no personal dashboard) |

Detailed role-based views and screen-to-phase mapping: [docs/ui-ux-spec.md](docs/ui-ux-spec.md).
Use-case scenarios: [docs/use-cases.md](docs/use-cases.md).

---

## 5. What This Product Is Not

```text
Not a developer surveillance tool  — we help teams understand delivery value, not track individuals.
Not an AI coding assistant         — it measures and governs the delivery impact of tools like Copilot.
Not just a DORA dashboard          — it adds AI-specific dimensions: validation cost, review debt, trust calibration.
Not a static compliance checklist  — thresholds adapt by team maturity, defects, and review capacity.
```

---

## 6. MVP Scope

### 6.1 MVP Problem Statement

> Engineering leaders do not know whether AI-assisted PRs are creating real delivery value or merely shifting cost into review, rework and defects.

### 6.2 MVP Integrations

```text
Required: GitHub + Jira.
Optional: SonarQube, Slack.
Avoid starting with IDE telemetry (privacy, consent, change-management complexity).
```

### 6.3 MVP Metrics

Five core metrics, each with Data Confidence Score:

| # | Metric | Canonical definition |
|---|---|---|
| 1 | AI-assisted PR Rate | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |
| 2 | AI Review Debt Age Ratio | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |
| 3 | Post-Merge Defect Rate | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |
| 4 | Human Validation Cost | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |
| 5 | Validated Delivery Trend (VDT) | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |

### 6.4 MVP Non-Goals

```text
IDE telemetry, full EU AI Act automation, model drift monitoring,
prompt repository, multi-model evaluation, advanced security scanning,
custom report builder, full A/B testing engine.
```

### 6.5 MVP Dashboard Screens

```text
Overview, Team, PR Risk, Metrics Detail     — Phase 2
Developer in-PR view                         — Phase 3
Policy Settings, Recommendations/Playbook    — Phase 4
Executive Summary, multi-team navigation     — Phase 5
```

Screen-to-phase detail: [docs/ui-ux-spec.md](docs/ui-ux-spec.md).

---

## 7. Expanded Metrics

Beyond the five core MVP metrics, later stages may include:

| Metric | Definition |
|---|---|
| Cognitive Load Index | [docs/metrics-catalogue.md](docs/metrics-catalogue.md) |
| AI Dependency Risk | Formula and mitigation in this document (Section 7.1 below) |
| AI-assisted PR Quality Gap | Formula in this document (Section 7.2 below) |
| Trust Calibration Gap | Stage 4+ |
| Prompt Efficiency Score | Stage 4+ |
| AI Test Quality Score | Stage 4+ |
| Maintainability Risk Score | Stage 4+ |
| Dynamic AI WIP Limit | [docs/risk-policy-engine.md](docs/risk-policy-engine.md) |

### 7.1 AI Dependency Risk

```text
AI Dependency Risk = (AI-assisted PR Rate x AI Ownership Confidence Risk) / Team Size
Where: AI Ownership Confidence Risk = 1 - Ownership Confidence Score
       Ownership Confidence Score = developer understanding + edge-case test evidence
```

| Signal | Meaning |
|---|---|
| High AI usage + low ownership confidence | AI dependency risk is rising |
| Moderate AI usage + high ownership confidence | Healthy augmentation |
| Low AI usage + low ownership confidence | Training or workflow problem, not AI scale problem |

Teams with AI Dependency Risk > 0.5 should run a quarterly ownership review.

### 7.2 AI-assisted PR Quality Gap

```text
AI-assisted PR Quality Gap = AI-assisted PR Defect Rate - Comparable Non-AI PR Defect Rate
AI Quality Gap Improvement = Previous 6-month Gap - Current 6-month Gap
```

| Trend | Meaning |
|---|---|
| Gap shrinking | AI-assisted delivery quality is improving |
| Gap flat | AI quality is not materially changing |
| Gap widening | AI-assisted work is diverging from expected quality |

---

## 8. Risk and Policy Engine

Risk scoring, policy enforcement, Dynamic AI WIP and emergency override rules:

> [docs/risk-policy-engine.md](docs/risk-policy-engine.md)

Copyable configs: [examples/policy-config.yaml](examples/policy-config.yaml)
GitHub Actions workflows: [examples/github-actions/](examples/github-actions/)

---

## 9. Data Model and APIs

Schema, indexes, cost configuration, retention and migration ownership:

> [docs/data-model.md](docs/data-model.md)

API endpoints and response examples:

> [docs/api-spec.md](docs/api-spec.md)

---

## 10. Data Confidence

Multi-factor confidence scoring (volume, freshness, completeness, stability), score bands and automatic degradation:

> [docs/data-confidence.md](docs/data-confidence.md)

---

## 11. Architecture

System flow, reference stack, collector interface, backpressure, webhook security, platform SLOs:

> [docs/architecture.md](docs/architecture.md)

---

## 12. Governance, Privacy and Cultural Safety

Non-negotiable rules, data retention, psychological safety, maturity bands, manager-misuse escalation:

> [docs/governance-and-privacy.md](docs/governance-and-privacy.md)
> [docs/psychological-safety.md](docs/psychological-safety.md)

---

## 13. Rollout and Operating Model

Operating modes, RACI, pause criteria, governance forum, confidence-by-phase:

> [docs/rollout-operating-model.md](docs/rollout-operating-model.md)

---

## 14. Platform Team MVP Backlog

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

---

## 15. Pilot Success Criteria

A pilot is successful if, after 4-6 sprints:

```text
1. AI-assisted PRs can be reliably identified (declared + inferred).
2. AI Review Debt can be measured with Data Confidence Score >= 70.
3. Post-merge defects can be linked to PRs with acceptable accuracy.
4. Engineering managers find the recommendations useful.
5. Developers do not report psychological safety concerns.
6. At least one actionable improvement is made from the dashboard.
7. VDT can be computed with at least Medium data confidence (>= 70).
8. Human Validation Cost has been calibrated with a small manual study or accepted as directional.
```

Business success criteria:

```text
VDT is non-negative or improving over the trailing 90 days.
AI-assisted defect rate is not materially worse than comparable non-AI work.
Review debt does not exceed 2x normal PR wait time for more than one sprint.
```

---

## 16. Pilot Failure / Pause Criteria

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
Disable hard enforcement. Return to observation mode. Run blameless retro.
Review metric definitions. Adjust policy and communication. Restart with smaller scope.
```

---

## 17. Enterprise Roadmap

| Stage | Focus | Key additions |
|---|---|---|
| 1 (MVP) | GitHub + Jira, five core metrics, team dashboard | Confidence scoring, policy override logging |
| 2 | Platform expansion | GitLab, SonarQube, CI/CD, dynamic WIP automation, policy-as-code |
| 3 | Governance expansion | Security integrations, audit trails, compliance reports, executive dashboards |
| 4 | Intelligence expansion | Trust calibration, maintainability tracking, AI Dependency Risk, Cognitive Load Index |
| 5 | Enterprise control plane | SSO/RBAC, multi-org, custom policies, API access, regulated-sector packs |

Full roadmap with community process: [ROADMAP.md](ROADMAP.md)

---

## 18. Investment Logic

### 18.1 Cost of Not Acting

Estimated hidden costs of ungoverned AI adoption per 100 developers:

| Cost factor | Annual impact |
|---|---:|
| AI-generated defect rework | £180,000 - £400,000 |
| Senior over-validation | £250,000 - £600,000 |
| AI review debt and delays | £100,000 - £250,000 |
| Security incident remediation | £500,000 - £2,000,000 |
| **Total** | **£1,000,000 - £3,000,000+** |

These numbers are directional. Replace with organisation-specific rates after the MVP collects local data.

### 18.2 ROI of a Control Plane

| Benefit | Annual savings |
|---|---:|
| Reduced defect rework by 20-40% | £36,000 - £160,000 |
| More efficient validation | £50,000 - £200,000 |
| Faster safe AI adoption | £100,000 - £500,000 |
| Reduced incident risk | £100,000 - £500,000+ |
| **Total potential savings** | **£286,000 - £1,360,000+** |

Expected ROI: 1.5x - 3x after local calibration.

Full costing and pricing model: [docs/costing-pricing.md](docs/costing-pricing.md).

---

## 19. Adoption Value Staircase

| Level | Capability | Platform team question |
|---:|---|---|
| 1 | Measure | Where and how is AI being used? |
| 2 | Triage | Which teams or workflows show risk hotspots? |
| 3 | Govern | Which guardrails improve outcomes without damaging trust? |
| 4 | Optimise | How do we balance speed, validation and risk at scale? |
| 5 | Transform | How should AI change our delivery strategy? |

This staircase helps teams understand why the MVP starts with measurement rather than hard enforcement.

---

## 20. Safe AI Usage Examples

**Good:**
```text
Using AI to generate test cases for a well-specified API.
Using AI to refactor a known pattern with an existing test suite.
Using AI to generate documentation from existing code.
Using AI to find an edge case the developer had not considered.
Using AI to explain unfamiliar code before making a human-owned change.
```

**Caution:**
```text
Using AI to generate business logic before writing the specification.
Using AI to implement a security-sensitive change without independent verification.
Using AI to translate code from one language to another without deep understanding.
Using AI to change high-churn files without checking recent ownership context.
Using AI to create tests without checking whether the tests assert meaningful behaviour.
```

**Problematic:**
```text
Submitting AI-generated code the author does not understand.
Using AI to bypass normal design and review processes.
Pasting internal, customer or regulated data into public AI models.
Accepting generated security or payment logic without human validation.
Treating AI-generated tests as proof of correctness without review.
```

---

## 21. AI-Assisted Delivery Onboarding Checklist

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

---

## 22. Non-Negotiable Rules

These rules hold across every version. No release relaxes them.

```text
No individual AI productivity ranking, personal dashboard, "who uses AI most" view or performance-review export.
No raw prompt storage in MVP.
No hard enforcement before Phase 4.
No blocking enforcement except from High-confidence metrics (Data Confidence Score >= 90).
No causal claims stronger than the data supports.
```

Full governance detail: [docs/governance-and-privacy.md](docs/governance-and-privacy.md).

---

## 23. Implementation Phase Packages

| Phase | Name | Duration | Mode | Gate |
|---:|---|---:|---|---|
| 0 | Groundwork and Legal Assurance | 2 weeks | Readiness | Legal/HR sign-off + safety baseline >= 3.5 |
| 1 | Data Architecture and Raw Collection | 3 weeks | Observation | Data Confidence Score >= 75 |
| 2 | Metrics and Risk Engine | 3 weeks | Observation | Core metrics validated |
| 3 | Soft Landing and Experiment | 4 weeks | Warning / Recommendation | Psychological safety > 3.5 |
| 4 | Automated Guardrails | 4 weeks | Enforcement | Positive VDT trend |
| 5 | Enterprise Rollout | Continuous | Staged | >= 30% teams active |

Each phase is a mini-project with entry criteria, exit criteria, fail criteria and required deliverables:

> [phase-packages/README.md](phase-packages/README.md)

Implementation readiness checklist:

```text
Confirm platform team owner.
Confirm pilot team.
Confirm GitHub/Jira access.
Confirm legal/privacy review for developer activity data.
Confirm HR or People review of the psychological safety statement.
Confirm data retention policy alignment.
Confirm PR template adoption.
```

---

## 24. Final Positioning Statement

The Validated AI Delivery Framework v1.8 defines a platform-team-owned control plane for AI-assisted software delivery.

It helps organisations move from:

```text
AI is making developers faster.
```

to:

```text
AI is creating validated, measurable, secure and economically meaningful delivery value.
```

The correct first product is:

> A GitHub/Jira-based AI Delivery Dashboard that shows whether AI-assisted PRs are creating real value or shifting cost into review, rework and defects.

Everything else should be added after the platform team proves that the dashboard changes engineering decisions.

---

## 25. One-Line Summary

> Validated AI Delivery Platform is an AI Delivery Control Plane for platform teams: it measures whether AI-assisted engineering is producing real delivery value, or merely increasing validation cost, risk and review debt.
