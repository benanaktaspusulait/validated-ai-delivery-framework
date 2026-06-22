# Risk & Policy Engine

Canonical reference for contextual risk scoring, the policy engine, Dynamic AI WIP and the recommendation/playbook mapping. Risk scoring is built read-only in Phase 2; policy enforcement activates in Phase 4. Copyable configs live in `examples/policy-config.yaml`; workflows in `examples/github-actions/`.

## Contextual risk score

```text
AI Contextual Risk Score =
  (Task Type Weight x W_task)
+ (Developer Experience Weight x W_exp)
+ (Domain Criticality Weight x W_domain)
+ (Change Size Weight x W_size)
+ (Security Sensitivity Weight x W_security)
+ (Cross-team Impact Weight x W_cross)
+ (Reviewer Load Weight x W_reviewer)
+ (Codebase Familiarity Weight x W_familiarity)
+ (Change Freshness Weight x W_freshness)
+ (Ownership Boundary Weight x W_boundary)

Where the sum of all weights equals 1.0:
  W_task = 0.15, W_exp = 0.05, W_domain = 0.15, W_size = 0.15,
  W_security = 0.15, W_cross = 0.10, W_reviewer = 0.05,
  W_familiarity = 0.10, W_freshness = 0.05, W_boundary = 0.05
```

### Factor scoring tables

Task type (what kind of change):

| Task Type | Low (0) | Medium (1) | High (2) |
|---|---:|---:|---:|
| Documentation | 0 | - | - |
| Test generation | 0 | 1 | - |
| Internal tooling code | 0 | 1 | 2 |
| Business logic | 1 | 2 | 3 |
| Payment/auth/security | 2 | 3 | 5 |
| Cross-service contract | 2 | 3 | 5 |

Developer experience (of the PR author):

| Experience | Score |
|---|---:|
| Senior (> 5 years) | 0 |
| Mid-level (2-5 years) | 1 |
| Junior (< 2 years) | 2 |

Domain criticality (from team config):

| Criticality | Score |
|---|---:|
| Low (internal tooling) | 0 |
| Medium (standard product) | 1 |
| High (payment, auth, security, regulated) | 3 |

Change size (PR size):

| Changed Lines | Score |
|---|---:|
| < 50 | 0 |
| 50-150 | 1 |
| 150-300 | 2 |
| > 300 | 3 |

Security sensitivity (file paths):

| Sensitive Paths Matched | Score |
|---|---:|
| None | 0 |
| Infrastructure/infra paths | 1 |
| Auth/payment/security paths | 3 |

Cross-team impact:

| Impact | Score |
|---|---:|
| No cross-team files | 0 |
| Shared library or module | 1 |
| Cross-service contract (openapi/proto) | 2 |

Reviewer load (current open PRs per reviewer):

| Load | Score |
|---|---:|
| Light (< 3 open PRs per reviewer) | 0 |
| Normal (3-5) | 1 |
| Heavy (> 5) | 2 |

Codebase familiarity (proxy: commits in similar path area over 6 months):

| Familiarity | Score |
|---|---:|
| Many local examples (> 20 commits) | 0 |
| Some comparable patterns (5-20) | 1 |
| Few or no comparable examples (< 5) | 2 |

Change freshness (file churn):

| Freshness | Score |
|---|---:|
| Stable area (no recent changes) | 0 |
| Modified within 30 days | 1 |
| High churn (> 10 modifications in 3 months) or recently refactored | 2 |

Ownership boundary:

| Boundary | Score |
|---|---:|
| Same team | 0 |
| Shared module | 1 |
| Multiple teams or service owners | 2 |

### Risk interpretation

| Score | Meaning | Action |
|---:|---|---|
| 0-3 | Low risk | Normal review |
| 4-7 | Medium risk | Add reviewer context or local pattern check |
| 8-12 | High risk | Require explicit owner review and consider splitting PR |
| > 12 | Very high risk | Require senior + security review; block if confidence is High |

## Policy engine

The enabled policy set and security controls are in `examples/policy-config.yaml`. Rules of operation:

```text
Every policy keeps an exception path. Emergency production fixes must never be blocked by missing AI metadata.
Every override is recorded (policy_overrides), reviewed and trend-monitored.
Only rules backed by High-confidence metrics (Data Confidence Score >= 90) may use a blocking action; 70-89 may warn only; below 70 the metric is withheld and the rule is auto-disabled.
The Security Lead owns the sensitive-path map; the Platform Lead wires it into policy checks.
Risk-sensitive paths add score; security-sensitive ownership rules assign required reviewers.
```

### Policy precedence

When multiple policies apply to the same PR, evaluation follows this order:

```text
1. Emergency override check: if the emergency-fix label is present, all blocking policies are bypassed. Override is logged.
2. Confidence gate: any policy backed by a metric with Confidence Score < 70 is skipped entirely.
3. Blocking policies: all blocking policies must pass for the merge to proceed (AND logic).
4. Warning policies: warnings are additive; all triggered warnings are posted as PR comments.
5. When a blocking policy and a warning policy conflict (e.g. block on missing metadata, but warning says "split PR"):
   both are applied: the block prevents merge, and the warning comment guides the developer on what to fix.
```

Emergency override audit event:

```json
{
  "event_type": "ai_policy_override",
  "pull_request_id": "provider-pr-id",
  "override_label": "emergency-fix",
  "overridden_by": "github_actor",
  "reason": "Production urgency",
  "created_at": "source_timestamp"
}
```

### Emergency override rate limiting

```text
Soft limit: after 3 emergency overrides per sprint, subsequent overrides require a second approver (tech lead or platform admin).
Hard limit: after 5 emergency overrides per sprint, further overrides are blocked and an escalation email is sent to the engineering director.
Both limits are configurable per team in team-config.yaml.
Override retro rule still triggers at 3 per sprint (see below).
```

Override retro rule:

```text
Any team exceeding 3 emergency overrides per sprint must schedule a 15-minute retro.
1. Are we using emergency-fix correctly?
2. Is there a workflow improvement that would prevent future emergencies?
3. Should our policy thresholds be adjusted?
Frequent overrides are not inherently bad. The retro detects system problems, not blame.
```

## Prompt leakage scanning (comment-only mode, MVP)

```text
Implement as a GitHub Action running on pull_request events.
MVP scanner checks PR title, body and comments only; it does not inspect raw prompts from AI tools.

Sensitive patterns (defined in examples/policy-config.yaml):
  password, secret, api_key, token, auth, internal, pii
  email-like strings (regex: [a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})
  phone-like strings (regex: \+?[\d\s\-\(\)]{7,15})

Expected false positive rate: < 5% (tune patterns based on pilot feedback).
Pattern updates: maintained by the Security Lead; reviewed quarterly.

Integration point: GitHub Action on pull_request event, posts a comment.
Add a PR comment when a pattern may indicate sensitive data. Do not block. Do not store raw matches.
Comment: "This PR contains text that may contain sensitive data. Please verify before merging. The platform has not stored the raw match."
Prefer existing provider scanning (GitHub secret scanning, Gitleaks, TruffleHog, Snyk) over building a full scanner in MVP.
```

## Dynamic AI WIP recommendation

```text
Base AI WIP Limit = (Senior x 2) + (Mid x 1) + (Junior x 0.5)

Current AI Defect Rate Ratio = Current AI-assisted weighted defect rate / comparable non-AI weighted defect baseline
Defect Rate Baseline Deviation = Current AI Defect Rate Ratio - 1
Current AI Review Debt Ratio = AI Review Debt Age Ratio
Review Debt Age Deviation = Current AI Review Debt Ratio - 1

Dynamic AI WIP Limit =
  Base AI WIP Limit
- (Defect Rate Baseline Deviation x 0.5)
- (Review Debt Age Deviation x 0.3)
+ (Team Seniority Ratio x 0.2)

Always round down when quality risk is increasing.
```

High-risk multiplicative penalty:

```text
If Current AI Defect Rate Ratio > 1.5 AND Current AI Review Debt Ratio > 1.2:
High-Risk Dynamic AI WIP = Base AI WIP Limit x 0.7 x (1 - min(Defect Rate Baseline Deviation, 3) / 10)
```

Guardrails:

```text
Default minimum WIP per team: 2 (configurable in team-config.yaml, must be >= 1 and <= base WIP limit).
When calculated WIP < minimum: set to minimum and add recommendation "WIP at configured floor; investigate quality signals."
Never use low-confidence defect data for automatic high-risk penalties.
Only High-confidence metrics (Data Confidence Score >= 90) may trigger blocking enforcement; 70-89 may warn only; below 70 the metric is withheld and any dependent policy is auto-disabled (see docs/data-confidence.md).
Show the reason for the WIP recommendation to the team.
Review penalty thresholds monthly during pilot rollout.
```

Worked examples (Base limit, then adjusted):

| Team | Composition | Base limit | Situation | Adjusted recommendation |
|---|---|---:|---|---:|
| A | 3 senior, 4 mid, 2 junior | (3x2)+(4x1)+(2x0.5) = 11 | Healthy: defects and review debt near baseline | ~11 (round down on any quality risk) |
| B | 2 senior, 3 mid, 1 junior | (2x2)+(3x1)+(1x0.5) = 7.5 | Review debt ratio 1.6, defects near baseline | 7.5 - (0.6 x 0.3) ≈ 7.3 → 7 |
| C | 1 senior, 4 mid, 3 junior | (1x2)+(4x1)+(3x0.5) = 7.5 | Defect ratio 1.8 and review debt ratio 1.4: both high | High-risk penalty: 7.5 x 0.7 x (1 - 0.8/10) ≈ 4.8 → 4 |

```text
Read the base limit as "concurrent AI-assisted PRs the team can absorb at healthy seniority".
The high-risk multiplicative penalty (team C) only triggers when defect AND review-debt deviations are both elevated.
Always round down when quality risk is rising, and never below the team's configured minimum.
```

## Recommendation mapping

Surfaced as PR comments (Phase 3) and in the Playbook view (Phase 4). Recommendations are guidance, not enforcement.

| Signal | Recommendation |
|---|---|
| AI Review Debt high | Reduce AI WIP limit next sprint |
| PR > 300 LOC | Split PR or add second reviewer |
| High-risk domain change | Require senior + security review |
| Defect rate rising | Pause AI expansion for affected team |
| Low ownership confidence | Require explainable review |
| High prompt iteration count | Re-refine story before coding |
| Low data confidence | Use metric for trend only, not decision |

## Playbook view

| Problem | Severity | Recommended action | Owner |
|---|---|---|---|
| Review debt high | High | Reduce AI WIP by 20% next sprint | Team Lead |
| Defect rate rising | Critical | Require senior review for AI PRs | Tech Lead |
| Metadata missing | Medium | Enable CI metadata enforcement | Platform |
| Low confidence data | Medium | Use metric for trend only | Platform |
| High cognitive load | High | Split PRs > 300 LOC | Developer / Reviewer |
