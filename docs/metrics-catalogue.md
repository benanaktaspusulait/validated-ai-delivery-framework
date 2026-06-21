# Metrics Catalogue

Canonical definitions for every metric the platform computes. Implemented in Phase 2. Confidence handling lives in `docs/data-confidence.md`; risk and WIP live in `docs/risk-policy-engine.md`.

## Five core MVP metrics

```text
1. AI-assisted PR Rate
2. AI Review Debt / AI Review Debt Age Ratio
3. Post-Merge Defect Rate / Weighted Defect Rate
4. Human Validation Cost
5. Net AI Delivery Value
```

### 1. AI-assisted PR Rate

```text
AI-assisted PR Rate = AI-assisted PR count / Total PR count
```

For MVP an AI-assisted PR is identified by explicit PR metadata and labels.

### 2. AI Review Debt

```text
AI Review Debt Age Ratio = Average AI PR Review Wait Time / Normal PR Review Wait Time Baseline
Baseline = median first-review wait time for non-AI PRs over the last 3-6 sprints (use median, not average).
```

| Ratio | Meaning | Action |
|---:|---|---|
| < 1.0 | Healthy | Continue |
| 1.0 - 1.5 | Watch | Monitor |
| 1.5 - 2.0 | Warning | Add reviewer capacity |
| > 2.0 | Critical | Reduce AI WIP / split PRs |

### 3. Post-Merge Defect Rate

```text
Post-Merge Defect Rate = Defects linked to merged PRs / Merged PR count
Weighted Defect Rate = Σ Defect Severity Weight / Merged PR count
```

| Severity | Weight |
|---|---:|
| Cosmetic | 0.25 |
| Minor | 0.5 |
| Medium | 1.0 |
| Major | 2.0 |
| Critical | 5.0 |

Always compare AI-assisted PRs against comparable non-AI PRs, not against all PRs. For the MVP, defect linkage uses Jira defects tied to a merged PR's story/component identifiers (coverage and CI signals are Stage 2+).

### 4. Human Validation Cost

```text
Human Validation Cost = Review Hours x Reviewer Hourly Cost

Estimated Review Hours =
  Base Review Estimate
+ Comment Thread Factor
+ Change Request Factor
+ Re-review Round Factor
+ PR Size Factor
+ Risk Factor

Fallback proxy = approval timestamp - first review timestamp
```

Engagement signals (comments, change requests, threads, multiple rounds) raise the estimate; approval without comments lowers confidence. Directional for MVP; calibrate via the Phase 3 study. Never use for individual performance measurement.

### 5. Net AI Delivery Value

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

Estimated Gross AI Time Saving Value = (Estimated Manual Baseline Effort - AI-assisted Effort) x Blended Hourly Rate

AI ROI = (Estimated Gross AI Time Saving Value + Counterfactual Value) / Total AI Delivery Cost
```

Every Net AI Delivery Value view must show estimated value, confidence label, input assumptions and baseline method. Counterfactual value is directional; cap it at the estimated gross AI time saving until stronger evidence exists, and never use self-reported counterfactual value for hard enforcement. Cost inputs come from `cost_config` (see `docs/data-model.md`).

## Cognitive Load Index

```text
Cognitive Load Index =
(AI PR Review Time / AI PR Size) / (Baseline Review Time for Non-AI PRs / Baseline Non-AI PR Size)
```

| Index | Meaning | Action |
|---:|---|---|
| < 0.8 | AI PRs appear easier to review | Validate sample quality; do not assume this is healthy |
| 0.8 - 1.5 | Comparable to non-AI PRs | Continue monitoring |
| > 1.5 | AI PRs are harder to review | Split PRs, improve prompts, require clearer explanations |

```text
Complexity filter (required): only include PRs where changed_lines > 20 AND changed_files > 1.
Stratify comparisons: small feature, medium feature, test-only, refactoring, security-sensitive.
```

## Supporting signals (not core metrics)

These help interpret the five core metrics; they do not replace them.

```text
PR Size Risk               (changed lines + files + critical paths)
Review Load                (open PRs per reviewer)
Policy Violations          (missing metadata, large PR, missing reviewer, coverage drop)
Cognitive Load Index       (review effort vs comparable non-AI PRs)
Post-Merge Defect Linkage  (bugs linked to a merged PR's component/story/release)
```

## Scientific-validity guardrails

Causation vs correlation:

```text
AI-assisted work may cluster in easy tasks, which can make AI look faster than it is.
Compare AI and non-AI PRs within matched cohorts:
- same team
- similar PR size
- similar story type
- similar domain criticality
- similar complexity bucket
- similar time window
```

Sample-size rules:

```text
Do not interpret AI vs non-AI defect comparisons with fewer than 20 comparable PRs per cohort.
Do not use Net AI Delivery Value for investment decisions until at least 2-3 sprints of data exist.
Do not draw trend conclusions from a single sprint.
```

Outlier handling:

```text
Use median for baseline review wait time.
Winsorise or separately flag extreme PRs.
Large incident-driven PRs should not define normal AI delivery behaviour.
```

Language:

```text
Produce decision-grade operational signals, not laboratory-grade causal proof.
Use: "AI-assisted work is associated with X under matched comparison."
Avoid: "AI caused X."
```
