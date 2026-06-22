# Root Cause Analysis

Structured process for diagnosing model failures, data issues and system errors.

## RCA triggers

```text
Immediate RCA required:
  - Model prediction error affecting users.
  - SLO breach (latency, availability, error rate).
  - Data pipeline failure.
  - Fairness concern raised.
  - Security incident.

Scheduled RCA:
  - Drift alert (non-critical).
  - Performance degradation trend.
  - Cost anomaly.
```

## RCA process

```text
1. Contain (0-15 minutes)
   - Stop the bleeding: rollback, disable endpoint, fallback to rules.
   - Notify stakeholders.
   - Preserve evidence (logs, metrics, data snapshots).

2. Diagnose (15 min - 4 hours)
   - Timeline: when did it start? what changed?
   - Scope: how many users affected? which predictions wrong?
   - Category: data issue? model issue? infrastructure issue? configuration issue?
   - Root cause: use 5 Whys or fishbone diagram.

3. Remediate (4-48 hours)
   - Fix the immediate cause.
   - Add tests to prevent recurrence.
   - Update monitoring/alerting if gap found.
   - Document in incident postmortem template.

4. Prevent (1-2 weeks)
   - Systemic fix: process change, automation, test addition.
   - Update runbook if procedure changed.
   - Share learnings with team.
```

## 5 Whys example

```text
Problem: Model predictions are 20% worse than baseline.

Why? The input data distribution has shifted.
Why? A new data source was added without updating the pipeline.
Why? The data pipeline does not validate schema changes.
Why? There is no automated schema validation in the pipeline.
Why? Data validation was not prioritised in the initial build.

Root cause: Missing data validation test.
Fix: Add Great Expectations schema validation to the pipeline.
Prevention: Data validation is now a mandatory gate in the CI pipeline.
```

## Incident postmortem

```text
Use templates/incident_postmortem_template.md:
  - Incident summary (what, when, impact).
  - Timeline of events.
  - Root cause.
  - What went well.
  - What went wrong.
  - Action items with owners and deadlines.
  - Lessons learned.
```
