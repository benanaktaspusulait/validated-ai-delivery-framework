# Rollout and Operating Model

Canonical reference for operating modes, the weekly operating cadence, the governance forum and the post-MVP roadmap.

## Operating modes

```text
Observation Mode    = collect and calculate only; no developer-facing warnings or blocks.
Warning Mode        = non-blocking PR comments and soft guidance.
Recommendation Mode = team-level recommendations and playbooks.
Enforcement Mode    = calibrated blocking checks with Emergency Override.
```

| Phase | Operating mode |
|---:|---|
| 0 | Readiness only |
| 1 | Observation Mode |
| 2 | Observation Mode |
| 3 | Warning Mode and Recommendation Mode |
| 4 | Enforcement Mode |
| 5 | Staged rollout across modes |

## Measurement confidence by phase

The product produces decision-grade operational signals, not laboratory-grade causal proof. Directly measurable data is shown as fact, estimates carry a confidence label, and weak signals are trend-only (see `docs/data-confidence.md`).

| Phase | Measurement layer | Confidence |
|---:|---|---|
| 1 | Observable delivery metrics (GitHub/Jira events) | High |
| 2 | AI usage metadata plus computed metrics and risk | Medium-high (estimates labelled) |
| 3 | Quality linkage plus calibrated review cost | Medium |
| 4 | Enforcement on High-confidence metrics (>= 90); 70-89 warn only | Decision-grade only |
| 5 | Advanced intelligence (telemetry, trust calibration) | Variable, labelled |

## Weekly RACI

| Role | Weekly responsibility |
|---|---|
| Platform Engineer | Build the week's collector, engine, API, dashboard or policy work |
| Platform Lead | Monitor data confidence, triage feedback and decide threshold changes |
| Engineering Manager | Interpret team-level metrics, protect psychological safety and run retros |
| Tech Lead | Review high-risk PRs, validate recommendations and coach explainable AI usage |
| Data Steward | Review data retention, access and override logs |
| Security Lead | Review security-sensitive paths, prompt leakage signals and incident linkage |

## Weekly pause criteria

Do not move to the next phase if any of these are true:

```text
Data Confidence Score is below 70 for 2 consecutive weeks.
Psychological safety score is below 3.5.
Platform alert volume exceeds 10 actionable alerts per week.
Developers report that AI metadata feels punitive.
Emergency overrides exceed 3 per sprint without retro review.
Required phase deliverables are incomplete.
```

Pause action:

```text
Hold the current phase for 1 week.
Fix connector quality, communication, thresholds or policy design.
Run a blameless retro if the issue affects team trust.
Resume only after the Platform Lead can explain what changed.
```

## If a mode transition fails

A transition is moving a team to a more active mode (Observation to Warning, or Warning to Enforcement). Treat the move as reversible.

Rollback signals:

```text
Psychological safety score drops by a meaningful margin (for example, a fall of 30% or to below 3.5).
Developers begin hiding AI usage or describe the tooling as surveillance.
Review time on warned/enforced PRs rises materially without a quality gain.
Alert volume exceeds 10 actionable alerts per team per week (alert fatigue).
A backing metric falls below Data Confidence Score 70 while it is driving enforcement.
Any production emergency fix is delayed by a platform block.
```

Stop and revert criteria:

```text
On any rollback signal, immediately downgrade the affected team to the previous mode (Enforcement to Warning, or Warning to Observation).
Use the fast reversal paths in docs/disaster-recovery.md: per-team downgrade or the global enforcement-off switch.
Keep the emergency-fix override available throughout.
```

Recovery:

```text
Run a blameless retro to find the cause (thresholds, copy, communication or data quality).
Fix the cause and re-communicate before attempting the transition again.
Re-enter the higher mode only after the original gate criteria pass again.
```

## Quarterly AI Delivery Governance Review

Attendees:

```text
Platform team owner
Engineering managers from participating teams
Security and compliance representative
Data steward
Selected tech leads from pilot or high-adoption teams
```

Agenda:

```text
1. AI adoption health: AI-assisted PR rate by team.
2. Validation trends: review debt and validation cost.
3. Quality trends: defect rate and AI Quality Gap.
4. Delivery trend: Validated Delivery Trend (VDT) signal (correlational, not ROI).
5. Risk posture: dependency risk, security signals and policy overrides.
6. Psychological safety pulse trend.
7. Policy adjustments: thresholds, new policies and retired policies.
8. 90-day roadmap.
```

Outputs:

```text
Updated policy configuration
Team-level action plans
Updated risk register
Investment recommendations
Data confidence improvement actions
```

The review focuses on system learning, not individual developer performance.

## Onboarding rule for new teams

```text
Every new team starts in Observation Mode and graduates through the same phase gates as the pilot.
No team moves directly into Enforcement Mode.
```

## Enterprise roadmap beyond the MVP

```text
Stage 2 Platform Expansion: GitLab, SonarQube, CI/CD, Dynamic WIP automation, policy-as-code, multi-team reporting, experiment mode, familiarity/churn risk.
Stage 3 Governance Expansion: security integrations, IP/licence scanning, audit trails, retention controls, compliance reports, executive dashboards, prompt-sensitive data scanning, data stewardship workflows.
Stage 4 Intelligence Expansion: trend forecasting, trust calibration, maintainability tracking, prompt efficiency, model provider comparison, AI Dependency Risk, AI-assisted PR Quality Gap, Cognitive Load Index.
Stage 5 Enterprise Control Plane: SSO/RBAC, multi-org, custom policies, custom dashboards, API access, report builder, regulated sector packs.
```
