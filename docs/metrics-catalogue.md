# Metrics Catalogue

Canonical definitions for every metric the platform computes. Implemented in Phase 2. Confidence handling lives in `docs/data-confidence.md`; risk and WIP live in `docs/risk-policy-engine.md`.

## Five core MVP metrics

```text
1. AI-assisted PR Rate
2. AI Review Debt / AI Review Debt Age Ratio
3. Post-Merge Defect Rate / Weighted Defect Rate
4. Human Validation Cost
5. Validated Delivery Trend (VDT) — correlational; replaces the absolute "Net AI Delivery Value" framing
```

## AI usage classification

Before any metric can be computed, every PR must be classified. This classification is the foundation of all comparative metrics.

```text
Classification states:
  declared_ai     - PR template or label explicitly says AI was used.
  declared_non_ai - PR template explicitly says "No AI assistance used."
  inferred_ai     - no explicit declaration, but signals detected (commit messages, file patterns, PR body).
                   confidence: low (1 signal) or medium (2+ signals).
  unknown         - no declaration and no signals; excluded from both cohorts.

AI-assisted PR cohort: declared_ai + inferred_ai (with confidence annotation).
Non-AI PR cohort:      declared_non_ai ONLY (unknown excluded to prevent bias).
```

The `pull_requests` table carries `ai_assisted` (boolean), `ai_usage_inferred` (boolean) and `ai_usage_inference_confidence` (text) to support this classification.

Minimum cohort sizes for valid comparisons:

```text
AI Review Debt baseline:   at least 10 non-AI PRs in the baseline window (last 3-6 sprints).
                           If below minimum: extend the window or use a platform-wide baseline.
                           Annotate with confidence_issue = "thin_baseline".
VDT comparison:            at least 20 comparable PRs per cohort in the 90-day window.
                           If below minimum: VDT is not computed; metric is marked Low confidence.
Defect rate comparison:    at least 20 comparable PRs per cohort (existing rule).
```

### 1. AI-assisted PR Rate

```text
AI-assisted PR Rate = (declared_ai + inferred_ai) / Total PR count (all classifications)
```

For MVP the rate is primarily metadata-driven. Inferred PRs are included but annotated.

### 2. AI Review Debt

```text
AI Review Debt Age Ratio = Average AI PR Review Wait Time / Normal PR Review Wait Time Baseline
Baseline = median first-review wait time for non-AI PRs over the last 3-6 sprints (use median, not average).
Minimum baseline: 10 non-AI PRs in the window. If below minimum, extend window or use platform-wide baseline.
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

### 5. Validated Delivery Trend (VDT)

VDT replaces the earlier "Net AI Delivery Value" metric. It makes no causal or absolute-ROI claim. It reports the correlational difference, over time, between AI-assisted and comparable non-AI PRs.

```text
Do not say: "AI made us X% faster" or "AI saved GBP N".
Do say: "Over the last 90 days, the delivery trend for AI-assisted PRs moved in direction X relative to comparable non-AI PRs."
```

Method (matched cohorts only; see the validity guardrails below):

```text
Sign convention: each difference is improvement-oriented, so a positive number always means "AI-assisted is better".

Lead Time Difference (LT_Diff)   = mean non-AI lead time - mean AI-assisted lead time
Defect Difference (Defect_Diff)  = non-AI defect rate   - AI-assisted defect rate

Signal Score = (LT_Diff / NonAI_LT_StdDev) + (Defect_Diff / NonAI_Defect_StdDev)
```

Each difference is normalised by the non-AI standard deviation, so the signal is expressed in standard-deviation units and is comparable across teams and cohorts.

| Signal Score | Reading | Action |
|---:|---|---|
| > 0.5 | Positive signal: the trend suggests AI is reducing lead time and/or defects | Continue; reinforce what is working |
| -0.5 to 0.5 | Neutral: no statistically meaningful difference, or insufficient data | Keep observing; do not claim an effect |
| < -0.5 | Negative signal: the trend suggests AI PRs take longer or carry more defects | Investigate; consider intervention |

### VDT temporal bucketing

```text
Chart granularity: per-sprint (not daily) to reduce noise.
Rolling window: 90 calendar days (approximately 6 sprints for 2-week sprints).
Missing sprints: do not interpolate; show gaps on the chart. Annotation: "insufficient data."
Minimum data: VDT is not computed until at least 2 sprints of data exist in the window.
```

### VDT confidence interval

```text
The VDT Signal Score is a point estimate. Display it with a 95% confidence interval.
When the confidence interval crosses zero, annotate: "Not statistically significant."
Minimum statistical power: require at least 20 comparable PRs per cohort per sprint.
When n < 20: widen the neutral band to [-1.0, 1.0] and annotate "low sample size".
```

Worked example (matched cohort, last 90 days):

```text
Lead time: mean non-AI 34h, mean AI 28h, non-AI StdDev 12h -> LT_Diff = 6h,   normalised 6/12   = 0.5
Defects:   non-AI 0.22/PR, AI 0.18/PR, non-AI StdDev 0.08   -> Defect_Diff = 0.04, normalised 0.04/0.08 = 0.5
Signal Score = 0.5 + 0.5 = 1.0  ->  positive signal
95% CI: [0.3, 1.7] -> does not cross zero, statistically significant
Reading: over this window, AI-assisted PRs trend toward shorter lead time and fewer defects than comparable non-AI PRs. This is a correlation, not proof.
```

Reporting and visualisation rules:

```text
VDT must never be shown as a single dashboard KPI number.
It is presented only as a 90-day moving-average line chart (per-sprint granularity).
Every VDT chart carries this mandatory disclaimer:
"This chart shows a correlational trend. Observed differences may be caused by team maturity, changes in task mix, or other external factors. It cannot be used as a causal return-on-investment (ROI) calculation. Management decisions should combine this signal with qualitative evidence such as developer surveys."
```

Directional cost context (optional, never a board KPI):

```text
A monetary estimate (time saved minus validation, rework, defect and tooling cost, using cost_config) may be computed for internal context only.
It is directional, carries the same disclaimer, and is never reported as an accounting figure or a causal ROI.
How to present this to leadership without overclaiming: see the CFO / board guidance in faq.md.
```

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
AI tool versioning: when the AI tool changes (e.g. Copilot upgrade), annotate the chart.
  Different tools produce code of different quality; tool changes may cause false improvements or regressions.
Optional: add ai_tool field to pull_requests for stratified analysis.
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

## Expanded metrics (Stage 2+)

Additional metrics defined in the framework document or to be specified before Stage 4:

| Metric | Definition location | Stage |
|---|---|---|
| AI Dependency Risk | validated-ai-delivery-framework-v1.8.md (Section 7.1) | Stage 2+ |
| AI-assisted PR Quality Gap | validated-ai-delivery-framework-v1.8.md (Section 7.2) | Stage 2+ |
| Trust Calibration Gap | To be specified | Stage 4 |
| Prompt Efficiency Score | To be specified | Stage 4 |
| AI Test Quality Score | To be specified | Stage 4 |
| Maintainability Risk Score | To be specified | Stage 4 |

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
- similar AI tool (when known)
```

Sample-size rules:

```text
Do not interpret AI vs non-AI defect comparisons with fewer than 20 comparable PRs per cohort.
Do not read the Validated Delivery Trend (VDT) for investment decisions until at least 2-3 sprints of data exist.
Do not draw trend conclusions from a single sprint.
Do not compute AI Review Debt baseline with fewer than 10 non-AI PRs in the window.
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
