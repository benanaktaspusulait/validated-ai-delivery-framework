# Phase 4 - Automated Guardrails

## Purpose

```text
Enable selective enforcement after warnings have been calibrated.
Emergency override remains available at all times.
```

## Duration

```text
4 weeks
```

## Owners

```text
Platform Engineers
Platform Lead
Security Lead
Tech Lead
Pilot-team Engineering Manager
```

## Work Tracks (this phase)

```text
Engineering: AI Metadata Blocker, risk-based reviewer assignment, prompt leakage comment-mode scanner (dominant track).
Metrics and risk: Dynamic AI WIP recommendation including the high-risk multiplicative penalty.
Governance and reporting: emergency override audit log and override retro.
Culture and people: keep enforcement explainable; watch for hidden AI usage.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| AI Metadata Blocker | Platform Engineer | Platform Lead | Tech Lead | Pilot team |
| High-risk reviewer assignment | Platform Engineer | Platform Lead | Security Lead, Tech Lead | Engineering Manager |
| Dynamic AI WIP recommendation | Platform Engineer | Platform Lead | Engineering Manager | Pilot team |
| Emergency override audit + retro | Pilot-team EM | Platform Lead | Tech Lead | Data Steward |
| Prompt leakage comment-mode scanner | Platform Engineer | Security Lead | Platform Lead | Pilot team |

## Entry Criteria

```text
Phase 3 exit criteria passed.
Warning thresholds are calibrated.
Emergency override logging is implemented.
```

## Actions

```text
Week 11: Enable AI Metadata Blocker. If AI is used, usage type and Low/Medium/High confidence are required before merge.
Week 12: Require code owner and senior reviewer for risk score >= 10.
Week 13: Publish Dynamic AI WIP recommendation to Jira or GitHub Projects.
Week 14: Run override retro if emergency-fix was used more than 3 times in the sprint.
Enable prompt leakage scanning in comment-only mode.
Ship the Policy Settings rule-editor screen and the Recommendations / Playbook screen.
```

## Technical Specifications

### Policy configuration enabled in this phase

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

Every policy must keep an exception path. Emergency production fixes must not be blocked by missing AI metadata, but every override is recorded, reviewed and trend-monitored.

Sensitive path ownership rules:

```yaml
security:
  sensitive_paths:
    - pattern: "**/auth/**"
      required_reviewers:
        - appsec
        - code_owner
    - pattern: "**/payment/**"
      required_reviewers:
        - senior_engineer
        - domain_owner
```

```text
The Security Lead owns the path map.
The Platform Lead owns wiring the path map into policy checks.
Risk-sensitive paths add score; security-sensitive ownership rules assign required reviewers.
```

### AI Metadata Blocker (GitHub Actions)

```yaml
name: AI Metadata Enforcement

on:
  pull_request:
    types: [opened, edited, synchronize, reopened]

permissions:
  contents: read
  pull-requests: write
  issues: write

jobs:
  validate-ai-metadata:
    runs-on: ubuntu-latest
    steps:
      - name: Allow emergency override
        id: emergency_override
        uses: actions/github-script@v7
        with:
          script: |
            const labels = context.payload.pull_request.labels.map(label => label.name);
            const hasOverride = labels.includes("emergency-fix");
            if (hasOverride) {
              core.notice("Emergency override: AI metadata is not required for this PR. Record the override in the policy audit log.");
              core.setOutput("skip_metadata_check", "true");
              return;
            }
            core.setOutput("skip_metadata_check", "false");

      - name: Check PR body for AI metadata
        if: steps.emergency_override.outputs.skip_metadata_check != 'true'
        uses: actions/github-script@v7
        with:
          script: |
            const body = context.payload.pull_request.body || "";
            const hasNoAI = body.includes("[x] No AI assistance used") || body.includes("[X] No AI assistance used");
            const aiOptions = [
              "AI used for code suggestions",
              "AI used for test generation",
              "AI used for documentation",
              "AI used for refactoring",
              "AI used for debugging or explanation"
            ];
            const hasAI = aiOptions.some(option =>
              body.includes(`[x] ${option}`) || body.includes(`[X] ${option}`)
            );
            const confidenceOptions = ["Low", "Medium", "High"];
            const hasConfidence = confidenceOptions.some(option =>
              body.includes(`[x] ${option}`) || body.includes(`[X] ${option}`)
            );
            if (!hasNoAI && !hasAI) {
              core.setFailed("AI assistance metadata is required. Select either 'No AI assistance used' or one or more AI usage categories.");
            }
            if (hasAI && !hasConfidence) {
              core.setFailed("AI assistance confidence is required when AI assistance is selected.");
            }
```

### Override audit log event

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

Store overrides in the `policy_overrides` table:

```sql
CREATE TABLE policy_overrides (
  id UUID PRIMARY KEY,
  pull_request_id UUID REFERENCES pull_requests(id),
  policy_name TEXT NOT NULL,
  override_label TEXT,
  overridden_by TEXT NOT NULL,
  reason TEXT,
  created_at TIMESTAMPTZ DEFAULT now()
);
```

Override retro rule:

```text
Any team exceeding 3 emergency overrides per sprint must schedule a 15-minute retro.
Retro questions:
1. Are we using emergency-fix correctly?
2. Is there a workflow improvement that would prevent future emergencies?
3. Should our policy thresholds be adjusted?
Frequent overrides are not inherently bad. The retro detects system problems, not blame.
```

### Risk-based reviewer assignment (GitHub Actions)

```yaml
name: AI Risk Reviewer Assignment

on:
  pull_request:
    types: [opened, synchronize, labeled]

permissions:
  contents: read
  pull-requests: write
  issues: write

jobs:
  assign-reviewers:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
        with:
          fetch-depth: 0

      - name: Calculate simple risk score
        id: risk
        run: |
          git fetch origin "${{ github.base_ref }}" --depth=1
          BASE_REF="origin/${{ github.base_ref }}"
          CHANGED_LINES=$(git diff --numstat "$BASE_REF"...HEAD | awk '{added+=$1; deleted+=$2} END {print added+deleted+0}')
          RISK=0
          if [ "$CHANGED_LINES" -gt 300 ]; then RISK=$((RISK+5)); fi
          if git diff --name-only "$BASE_REF"...HEAD | grep -E '(auth|security|payment|terraform|infra)' ; then RISK=$((RISK+5)); fi
          echo "risk=$RISK" >> $GITHUB_OUTPUT
          if [ "$RISK" -ge 10 ]; then
            echo "high_risk=true" >> $GITHUB_OUTPUT
          else
            echo "high_risk=false" >> $GITHUB_OUTPUT
          fi

      - name: Add warning comment for high risk PR
        if: steps.risk.outputs.high_risk == 'true'
        uses: actions/github-script@v7
        with:
          script: |
            github.rest.issues.createComment({
              owner: context.repo.owner,
              repo: context.repo.repo,
              issue_number: context.issue.number,
              body: "High AI delivery risk detected. Please assign a senior reviewer and consider splitting the PR."
            })
```

### Dynamic AI WIP recommendation

```text
Base AI WIP Limit = (Senior x 2) + (Mid x 1) + (Junior x 0.5)

Current AI Defect Rate Ratio =
Current AI-assisted weighted defect rate / comparable non-AI weighted defect baseline

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
Only metrics with Data Confidence Score >= 70 may trigger blocking enforcement.
Metrics below 70 may warn only; metrics below 50 are trend-only.
Show the reason for the WIP recommendation to the team.
Review penalty thresholds monthly during pilot rollout.
```

### Prompt leakage scanning (comment-only mode)

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

```text
Implement as a pre-commit hook or GitHub Action comment.
MVP scanner checks PR title, body and comments only.
It does not inspect raw prompts from AI tools.
Scan PR description and comments for sensitive patterns plus email-like and phone-like strings.
Add a PR comment when a pattern may indicate sensitive data. Do not block the PR. Do not store raw matches.
Suggested comment:
"This PR contains text that may contain sensitive data. Please verify before merging. The platform has not stored the raw match."
```

Use existing provider secret scanning where available:

```text
GitHub secret scanning
Gitleaks
TruffleHog
Snyk
```

The platform should reference these tools rather than building a full secret scanner in MVP.

### Policy Settings UI

Platform teams manage rules from a single screen. Example rule set:

```yaml
rules:
  - if: ai_assisted == true and changed_lines > 300
    action: warn_and_request_split
  - if: ai_assisted == true and domain == payment
    action: require_senior_reviewer
  - if: security_sensitive_files_changed == true
    action: require_appsec_review
  - if: ai_review_debt_age > 2x_normal
    action: reduce_ai_wip_limit
  - if: metadata_missing == true
    action: block_merge
```

```text
The Policy Settings screen turns the product from a passive dashboard into a control plane.
Each rule maps to a CI/CD check or GitHub Check that warns or blocks.
Only rules backed by metrics with Data Confidence Score >= 70 may use a blocking action; the rest warn only.
```

### Test Strategy and Observability

Test strategy:

```text
Unit-test policy rule evaluation, confidence gates and Emergency Override handling.
Integration-test GitHub Actions metadata enforcement against sample PR bodies.
Integration-test high-risk reviewer assignment with representative changed-file sets.
Regression-test that low-confidence defect data cannot reduce Dynamic AI WIP or block a PR.
Exercise metadata_missing behaviour separately from inferred metric confidence because metadata completeness can block in Enforcement Mode.
```

Observability:

```text
Emit policy_evaluations_total by rule, action and result.
Emit policy_blocks_total, policy_warnings_total and emergency_overrides_total.
Emit policy_false_positive_reports_total from pilot feedback.
Emit dynamic_ai_wip_recommendation changes with before, after and reason.
Alert when actionable policy alerts exceed 10 per team per week or PR bot comments exceed 20% of PRs.
```

### Recommendations / Playbook View

Surfaces what to do, with an owner, not just that a problem exists:

| Problem | Severity | Recommended action | Owner |
|---|---|---|---|
| Review debt high | High | Reduce AI WIP by 20% next sprint | Team Lead |
| Defect rate rising | Critical | Require senior review for AI PRs | Tech Lead |
| Metadata missing | Medium | Enable CI metadata enforcement | Platform |
| Low confidence data | Medium | Use metric for trend only | Platform |
| High cognitive load | High | Split PRs > 300 LOC | Developer / Reviewer |

### Measurement confidence at this phase

```text
Layer: enforcement based on calibrated thresholds.
Confidence: only metrics with Data Confidence Score >= 70 may drive hard enforcement; lower-confidence signals warn only.
Use: selective blocking with an always-available emergency override.
```

## Deliverables

```text
[Phase4]_exit_report.pdf
[Phase4]_data_dictionary.json
[Phase4]_config_changes.yaml
AI Metadata Blocker
Emergency override audit log
High-risk reviewer assignment
Dynamic AI WIP recommendation
Override retro notes where applicable
Prompt leakage comment-mode scanner
Policy Settings rule-editor screen
Recommendations / Playbook screen
Policy test suite and low-confidence enforcement regression tests
Policy observability counters and alert thresholds
```

## Exit Criteria

```text
Pilot team's Net AI Delivery Value is positive or trending positive.
Emergency-fix usage is below 5% of total PRs.
No production emergency fix has been delayed by the metadata blocker.
Dynamic AI WIP recommendation is visible and explainable.
```

## Fail Criteria

```text
Dynamic AI WIP repeatedly falls below the configured minimum of 2.
Emergency fixes are delayed.
Emergency-fix usage exceeds 5% of total PRs without a clear operational reason.
Developers start hiding AI usage.
```

## Fail Action

```text
Pause enforcement.
Return affected policy to warning mode.
Revisit WIP formula, override process or communication.
```

## Gate Decision

```text
Proceed to Phase 5 only if guardrails improve safety without blocking emergency delivery or causing hidden AI usage.
```

## Master Reference Map

```text
Section 7.4  - Policy engine and emergency override retro
Section 8    - Security/compliance view and prompt leakage scanning
Section 11   - Dynamic AI WIP limit and high-risk penalty
Section 14   - GitHub Actions AI metadata enforcement
Section 15   - Risk-based reviewer assignment
Section 21   - MVP backlog: Epic 5 (Policy)
```
