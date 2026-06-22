# Costing and Pricing Model

Conceptual cost structure and pricing options. All figures are illustrative planning ranges to be replaced with organisation-specific data; this document does not commit to prices.

## Cost drivers

| Driver | What it depends on | Scaling behaviour |
|---|---|---|
| Compute | Collectors, engines, dashboard services | Grows with event volume and concurrent dashboard users |
| Database | Operational store plus read replicas | Grows with retained detailed data (12-month window) |
| Event stream / queue | Webhook throughput and replay retention | Grows with peak burst, not average |
| Storage | Raw events, snapshots, backups | Bounded by retention policy; aggregates kept 24 months |
| Token and model usage | Approved AI tooling, agent workflows, context/eval runs | Variable; depends on model tier, prompt/context size and usage intensity |
| Operations | Platform team time, support, on-call | Grows stepwise with team count (see scalability-sla.md) |

```text
The largest non-infrastructure cost is platform-team time, not cloud spend, at MVP scale.
Retention policy is the main lever on storage cost; purging detailed data at 12 months keeps it bounded.
Token FinOps should be reported by team, workflow, tool and model tier, never as a developer-level cost leaderboard.
```

## Illustrative infrastructure cost bands

For an internal single-tenant deployment, order-of-magnitude monthly ranges (replace with a real cloud estimate):

| Scale | Teams | Indicative monthly infrastructure |
|---|---:|---|
| Pilot | 1-5 | Low: a small managed database, modest compute, minimal queue |
| Department | ~50 | Moderate: replicas, partitioning, steady event throughput |
| Enterprise | ~500 | Higher: scaled collectors, pre-aggregation pipelines, redundancy |

```text
Costs are dominated by the database and continuous event processing.
Pre-aggregation and time-partitioning (scalability-sla.md) keep per-team marginal cost low as teams are added.
```

## Pricing options (if offered as a product)

| Tier | Intended for | Indicative planning range | Boundary |
|---|---|---:|---|
| Free / community | Small teams, evaluation, open-source users | Free | Up to a small team count (for example, 10 teams); community support; core metrics only |
| Team | Single department adopting seriously | GBP 200 - GBP 500 / month | Per-team subscription; full MVP feature set; standard support targets |
| Scale-up | Multi-department adoption | GBP 1,000 - GBP 3,000 / month | Per-team pricing with volume discount; adds SonarQube, CI/CD, Slack integration; priority support |
| Enterprise | Org-wide governance | GBP 20,000 - GBP 100,000+ / year | Custom; adds SSO/RBAC at scale, audit, retention controls, named support and SLA |
| Assessment engagement | One-off readiness review | GBP 15,000 - GBP 50,000+ one-off | Fixed-scope analysis producing an adoption and risk report |

```text
Any paid tier must preserve every non-negotiable rule for free; governance and privacy are not premium features.
Pricing should reflect value delivered (validated delivery insight), not the number of developers surveilled.
```

Assessment engagement outputs:

```text
AI usage heatmap
AI review debt analysis
Governance maturity score
VDT baseline with confidence label
Risk register
AI ownership map
Shadow AI inventory
Agentic harness maturity score
Context registry readiness
Token FinOps baseline
90-day roadmap
```

## Build cost (if developed as a product)

```text
Estimate by phase using the phase durations in phase-packages/README.md as the planning skeleton.
The dominant cost is a small, senior platform/full-stack team across Phases 1-5, plus part-time design, data and security input.
Treat the MVP as internal tooling first; defer the cost of multi-tenancy, billing and SOC2-grade controls until value is proven.
```

### Labour cost breakdown

```text
Phase 0 (2 weeks):     0.5 FTE platform engineer + legal/HR time (not counted here)
Phase 1 (3 weeks):     1 FTE platform engineer
Phase 2 (3 weeks):     1 FTE platform engineer + 0.25 FTE data scientist
Phase 3 (4 weeks):     1 FTE platform engineer + pilot team time (reviewers for calibration study)
Phase 4 (4 weeks):     1 FTE platform engineer + 0.25 FTE security engineer
Phase 5 (continuous):  0.5 FTE platform engineer (operations and support)

Total to MVP (Phases 1-4): ~14 person-weeks of platform engineering = ~3.5 months of 1 FTE.
Total including Phase 0 and ongoing Phase 5: ~16-20 person-weeks.
As adoption scales, plan for 1-2 named AI platform responsibilities inside the existing platform team rather than a separate AI delivery silo.
```

### Illustrative labour cost (UK rates)

```text
Platform engineer:  GBP 600 - 900 / day  (senior contractor or internal allocation)
Data scientist:     GBP 700 - 1,000 / day
Security engineer:  GBP 650 - 950 / day

MVP build cost estimate (Phases 1-4):
  Low:  14 weeks x GBP 600/day x 5 days = GBP 42,000
  High: 14 weeks x GBP 900/day x 5 days = GBP 63,000

Ongoing operations (Phase 5+):
  0.5 FTE = ~GBP 60,000 - 90,000 / year
```

Planning baseline:

```text
Labour cost:  GBP 42,000 - 63,000 (from above)
Infrastructure: GBP 8,000 - 37,000 (cloud compute, database, storage for pilot)
Total MVP build cost: GBP 50,000 - 100,000
Annual operations cost: GBP 80,000 - 150,000 (0.5 FTE + infrastructure)
Potential ROI range after local calibration: 1.5x - 3x. Do not infer this from VDT alone.
```

## Cost-of-not-acting (the counter-case)

```text
Ungoverned AI adoption has its own cost: rework from AI-assisted defects, senior over-validation, review-debt delays and incident remediation.
The framework's value case is avoided cost and safer scaling, not seat licences.
Quantify this locally during the pilot using VDT, validation-cost and defect signals (metrics-catalogue.md) before committing to any pricing.
```
