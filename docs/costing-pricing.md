# Costing and Pricing Model

Conceptual cost structure and pricing options. All figures are illustrative planning ranges to be replaced with organisation-specific data; this document does not commit to prices.

## Cost drivers

| Driver | What it depends on | Scaling behaviour |
|---|---|---|
| Compute | Collectors, engines, dashboard services | Grows with event volume and concurrent dashboard users |
| Database | Operational store plus read replicas | Grows with retained detailed data (12-month window) |
| Event stream / queue | Webhook throughput and replay retention | Grows with peak burst, not average |
| Storage | Raw events, snapshots, backups | Bounded by retention policy; aggregates kept 24 months |
| Operations | Platform team time, support, on-call | Grows stepwise with team count (see scalability-sla.md) |

```text
The largest non-infrastructure cost is platform-team time, not cloud spend, at MVP scale.
Retention policy is the main lever on storage cost; purging detailed data at 12 months keeps it bounded.
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

| Tier | Intended for | Boundary |
|---|---|---|
| Free / community | Small teams, evaluation, open-source users | Up to a small team count (for example, 10 teams); community support; core metrics only |
| Team | Single department adopting seriously | Per-team subscription; full MVP feature set; standard support targets |
| Enterprise | Org-wide governance | Custom; adds SSO/RBAC at scale, audit, retention controls, named support and SLA |
| Assessment engagement | One-off readiness review | Fixed-scope analysis producing an adoption and risk report |

```text
Any paid tier must preserve every non-negotiable rule for free; governance and privacy are not premium features.
Pricing should reflect value delivered (validated delivery insight), not the number of developers surveilled.
```

## Build cost (if developed as a product)

```text
Estimate by phase using the phase durations in phase-packages/README.md as the planning skeleton.
The dominant cost is a small, senior platform/full-stack team across Phases 1-5, plus part-time design, data and security input.
Treat the MVP as internal tooling first; defer the cost of multi-tenancy, billing and SOC2-grade controls until value is proven.
```

## Cost-of-not-acting (the counter-case)

```text
Ungoverned AI adoption has its own cost: rework from AI-assisted defects, senior over-validation, review-debt delays and incident remediation.
The framework's value case is avoided cost and safer scaling, not seat licences.
Quantify this locally during the pilot using the net-value and defect signals (metrics-catalogue.md) before committing to any pricing.
```
