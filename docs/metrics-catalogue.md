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

Worked example (matched cohort, last 90 days):

```text
Lead time: mean non-AI 34h, mean AI 28h, non-AI StdDev 12h -> LT_Diff = 6h,   normalised 6/12   = 0.5
Defects:   non-AI 0.22/PR, AI 0.18/PR, non-AI StdDev 0.08   -> Defect_Diff = 0.04, normalised 0.04/0.08 = 0.5
Signal Score = 0.5 + 0.5 = 1.0  ->  positive signal
Reading: over this window, AI-assisted PRs trend toward shorter lead time and fewer defects than comparable non-AI PRs. This is a correlation, not proof.
```

Reporting and visualisation rules:

```text
VDT must never be shown as a single dashboard KPI number.
It is presented only as a 90-day moving-average line chart.
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
Do not read the Validated Delivery Trend (VDT) for investment decisions until at least 2-3 sprints of data exist.
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
