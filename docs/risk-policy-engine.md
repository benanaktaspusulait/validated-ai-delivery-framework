# Risk & Policy Engine

Canonical reference for contextual risk scoring, the policy engine, Dynamic AI WIP and the recommendation/playbook mapping. Risk scoring is built read-only in Phase 2; policy enforcement activates in Phase 4. Copyable configs live in `examples/policy-config.yaml`; workflows in `examples/github-actions/`.

## Contextual risk score

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

| Factor | Low | Medium | High |
|---|---:|---:|---:|
| Documentation | 1 | - | - |
| Test generation | 2 | 3 | - |
| Internal tooling code | 2 | 3 | 4 |
| Business logic | 3 | 4 | 5 |
| Payment/auth/security | 5 | 7 | 10 |
| Cross-service contract | 5 | 7 | 10 |
| Codebase familiarity | Many local examples | Some comparable patterns | Few or no comparable examples |
| Change freshness | Stable area | Modified within 30 days | High churn or recently refactored |
| Ownership boundary | Same team | Shared module | Multiple teams or service owners |

Codebase familiarity proxy:

```text
Compare PR file paths to commits from the previous 6 months.
Low familiarity:    fewer than 5 commits in a similar path area.
Medium familiarity: 5-20 commits in a similar path area.
High familiarity:   more than 20 commits in a similar path area.
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

| Score | Meaning | Action |
|---:|---|---|
| 0 | Stable area | Normal risk weighting |
| 1-2 | Some recent activity | Add reviewer context or local pattern check |
| 3+ | High churn or ownership conflict | Require explicit owner review and consider splitting PR |

## Policy engine

The enabled policy set and security controls are in `examples/policy-config.yaml`. Rules of operation:

```text
Every policy keeps an exception path. Emergency production fixes must never be blocked by missing AI metadata.
Every override is recorded (policy_overrides), reviewed and trend-monitored.
Only rules backed by metrics with Data Confidence Score >= 70 may use a blocking action; the rest warn only.
The Security Lead owns the sensitive-path map; the Platform Lead wires it into policy checks.
Risk-sensitive paths add score; security-sensitive ownership rules assign required reviewers.
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
Implement as a pre-commit hook or GitHub Action comment.
MVP scanner checks PR title, body and comments only; it does not inspect raw prompts from AI tools.
Scan for the sensitive_patterns in examples/policy-config.yaml plus email-like and phone-like strings.
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
Never reduce below the configured team minimum automatically.
Never use low-confidence defect data for automatic high-risk penalties.
Only metrics with Data Confidence Score >= 70 may trigger blocking enforcement; below 70 warn only; below 50 trend-only.
Show the reason for the WIP recommendation to the team.
Review penalty thresholds monthly during pilot rollout.
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
| High coverage but low mutation score | Improve test quality gate |
| Low data confidence | Use metric for trend only, not decision |

## Playbook view

| Problem | Severity | Recommended action | Owner |
|---|---|---|---|
| Review debt high | High | Reduce AI WIP by 20% next sprint | Team Lead |
| Defect rate rising | Critical | Require senior review for AI PRs | Tech Lead |
| Metadata missing | Medium | Enable CI metadata enforcement | Platform |
| Low confidence data | Medium | Use metric for trend only | Platform |
| High cognitive load | High | Split PRs > 300 LOC | Developer / Reviewer |
