# Scalability and SLA Targets

Capacity assumptions, service-level targets and the growth path from a single pilot to enterprise scale. These are planning targets to validate during rollout, not guarantees; calibrate them with real data as teams onboard.

## Volume assumptions (planning baseline)

Per active team, per working day, as an order-of-magnitude starting point:

| Signal | Small team | Typical team | Heavy team |
|---|---:|---:|---:|
| Pull requests opened | 3 | 10 | 30 |
| Review events (comments, approvals) | 15 | 60 | 200 |
| Webhook deliveries | 40 | 150 | 500 |
| Linked Jira issues | 5 | 20 | 60 |

```text
At 50 teams (typical), expect roughly 7,500 webhook deliveries/day and a few hundred PRs/day.
At 500 teams, plan for ~75,000 webhook deliveries/day; this is the threshold where the growth-plan changes below apply.
Volumes are bursty around working hours; size for peak, not average.
```

## Service-level targets

| Dimension | Target | Rationale |
|---|---|---|
| Webhook ingestion lag (p95) | Under 1 hour | Already an exit criterion in Phase 1 |
| Event processing throughput | Sustain 3x average daily peak without backlog growth | Absorbs working-hour bursts |
| Dashboard read latency (p95) | Within target under 50+ concurrent team views | Matches Phase 5 load test |
| Metric freshness | Snapshot available within one collection cycle of source events | Keeps decisions current |
| Source reconciliation gap | Under 5% nightly | Already an exit criterion in Phase 1 |
| Platform availability | 99.5% for the pilot, revisited before enterprise scale | Internal tool tolerance; raise as adoption grows |

```text
SLAs are observation-gated: a metric is only "decision-grade" when its Data Confidence Score is >= 70, regardless of how fresh it is.
```

## Performance and data strategy (conceptual)

```text
Read the indexes already defined in docs/data-model.md before adding new ones; they target the hot paths (team + metric + time, PR risk lookups, recommendation status).
Partition the highest-volume tables (raw events, review analytics) by time so that retention purges drop whole partitions cheaply.
Pre-aggregate team-level metric snapshots per sprint rather than recomputing from raw events on every dashboard view.
Keep raw event payloads out of dashboard query paths; serve dashboards from normalized and snapshot tables only.
```

## Queue and stream sizing (conceptual)

```text
Size the event stream for peak burst, not daily average; partition by repository or team so one noisy repo cannot starve others.
Set retention on the stream long enough to support replay-based reprocessing during an incident, bounded by the privacy retention policy.
Process collectors idempotently so that replay and at-least-once delivery do not create duplicate records (see docs/architecture.md).
```

## Growth plan: 50 to 500 teams

| Concern | At ~50 teams | At ~500 teams |
|---|---|---|
| Ingestion | Single collector deployment | Horizontally scaled collectors, partitioned by org/team |
| Database | Single primary with read replica | Read replicas for dashboards; time-partitioned high-volume tables |
| Aggregation | On-demand plus nightly snapshots | Scheduled pre-aggregation pipelines; dashboards read snapshots only |
| Multi-tenancy | Internal single-tenant | Tenant isolation evaluated (still out of MVP scope) |
| Onboarding | Admin-assisted | Fully self-service with guardrails (Phase 5) |
| Support load | Under 5 tickets/week target | Tiered support model (see support-model.md) |

```text
Each scale step is gated the same way phases are: do not scale out while data confidence, support load or psychological safety are degraded (see rollout-operating-model.md pause criteria).
```
