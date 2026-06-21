# Phase 5 - Enterprise Rollout

## Purpose

```text
Turn the validated pilot into an internal platform product.
```

## Duration

```text
Continuous after an initial 2-week rollout setup.
```

## Owners

```text
Platform Team
Developer Experience Team
Security Lead
Data Steward
Engineering Managers
```

## Work Tracks (this phase)

```text
Engineering: self-service onboarding, retention purge job, multi-team dashboards.
Governance and reporting: quarterly governance forum, incident integration, executive reporting (dominant track).
Culture and people: every new team starts in observation mode; maturity labels stay non-ranking.
Metrics and risk: expand metrics per the enterprise roadmap as confidence allows.
```

## RACI (this phase)

| Activity | Responsible | Accountable | Consulted | Informed |
|---|---|---|---|---|
| Self-service onboarding package | DevEx Team | Platform Lead | Engineering Managers | New teams |
| Quarterly governance forum | Platform Lead | Platform team owner | Security Lead, Data Steward | CTO/VP Eng |
| Incident integration | Platform Engineer | Platform Lead | Security Lead | Engineering Managers |
| Retention purge job | Platform Engineer | Data Steward | Legal/privacy counsel | Platform Lead |
| Executive reporting | Platform Lead | Platform team owner | Engineering Managers | CTO/VP Eng |

## Entry Criteria

```text
Phase 4 exit criteria passed.
Pilot team recommends continuation.
Platform support load is manageable.
```

## Actions

```text
Publish self-service team-config.yaml onboarding.
Create quick-start.md and faq.md for new teams.
Run the first quarterly governance forum with CTO, Security Lead and Engineering Managers.
Present Phase 0-4 results, policy recommendations and budget needs.
Enable retention purge for detailed data after 12 months.
Ship the Executive summary screen, multi-team navigation and RBAC settings, and the self-service onboarding journey.
```

## Technical Specifications

### Self-service team configuration

```yaml
team:
  id: payments-team
  name: Payments Team
  domain_criticality: high
  senior_engineers: 3
  mid_engineers: 4
  junior_engineers: 2

repositories:
  - provider: github
    owner: example-org
    name: payments-api

risk:
  sensitive_paths:
    - "src/main/**/payment/**"
    - "src/main/**/auth/**"
    - "infra/**"
  high_risk_threshold: 10
  codebase_familiarity_required_for_low_risk: medium
  high_churn_window_days: 30
  cross_team_contract_paths:
    - "openapi/**"
    - "proto/**"

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

wip:
  dynamic_ai_wip_enabled: true
  min_limit: 2
  max_limit: 12
  high_risk_penalty_enabled: true

policies:
  ai_metadata_required: true
  large_pr_warning_threshold_lines: 300
  high_risk_requires_senior_review: true
  emergency_override_label: emergency-fix
  override_reason_required: true
  prompt_sensitive_data_scanning: true

data:
  detailed_retention_months: 12
  aggregate_retention_months: 24
  raw_prompt_storage: disabled

notifications:
  slack_channel: "#payments-delivery-health"
```

### Onboarding rule for new teams

```text
Every new team starts in observation mode (equivalent to Phase 1-2 behaviour).
No team moves directly into guardrail enforcement.
Use the AI-assisted delivery onboarding checklist for developers new to a team using AI.
```

#### AI-Assisted Delivery Onboarding Checklist

Refer to the master document (Section 31) for the full checklist. Key items for the self-service package:
- [ ] Platform SSO/RBAC access granted.
- [ ] Team-to-repository mapping confirmed.
- [ ] AI usage declaration guidelines reviewed with the team.
- [ ] "No-punitive-use" guarantee acknowledged by the Engineering Manager.
- [ ] Emergency override process understood by all developers.

### Test Strategy and Observability

Test strategy:

```text
Unit-test retention purge logic: verify detailed data is deleted and aggregates are preserved at the 12/24 month boundaries.
Integration-test RBAC enforcement: verify that CTO, EM, and Developer roles see only the intended data levels.
Integration-test self-service onboarding: verify that submitting a new team-config.yaml correctly initializes a team in observation mode.
Regression-test onboarding failure paths: ensure invalid repo mappings or missing Jira keys block onboarding with a clear error.
Load-test multi-team dashboards with 50+ concurrent team views.
```

Observability:

```text
Emit retention_purge_records_deleted_total by table and team.
Emit rbac_access_denied_total by role and resource.
Emit onboarding_success_rate and onboarding_failure_reason_total.
Track dashboard_load_latency_p95 for multi-team views.
Monitor support_ticket_volume_total (integrated with Jira/Zendesk if possible).
```

### Retention purge job

```text
Purge detailed PR and review analytics after 12 months.
Retain aggregated team-level trend metrics for 24 months.
Never retain raw prompt content. Keep only derived prompt safety signals.
Data Steward signs off the purge job before scale-out.
```

### Quarterly AI Delivery Governance Review

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
4. Economic trends: Net AI Delivery Value and AI ROI.
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

Manager misuse escalation:

```text
Apply the manager-misuse escalation procedure defined in Phase 0 if individual AI metrics are used for performance scoring.
The quarterly forum is the standing checkpoint for detecting and acting on that misuse across teams.
```

### Incident integration

```text
1. Manually link a PagerDuty or Opsgenie incident ID to a Jira issue.
2. Automatically identify AI-assisted PRs linked to that Jira issue.
3. Add those PRs to the incident review report.
4. Update AI-assisted defect metrics only after the incident review confirms contribution.
```

```text
Incident linkage creates candidates for human review, not automatic blame or automatic defect attribution.
Do not assume a nearby AI-assisted PR caused the incident.
```

### Maturity bands (descriptive, never a ranking)

| Maturity | AI-assisted PR rate | Platform focus |
|---|---:|---|
| Laggard | < 10% | Remove barriers and improve enablement |
| Explorer | 10-30% | Measure review debt and defects |
| Adopter | 30-50% | Tune Dynamic AI WIP and policy thresholds |
| Leader | > 50% | Report Net AI Delivery Value and AI ROI |

```text
If the labels create status anxiety, replace them in the UI with neutral bands:
Low / Moderate / High / Very high AI adoption.
A low-adoption regulated team and a high-adoption internal-tooling team can both be making rational decisions.
```

### Enterprise roadmap beyond the MVP

```text
Stage 2 Platform Expansion: GitLab, SonarQube, CI/CD, Dynamic WIP automation, policy-as-code, multi-team reporting, experiment mode, familiarity/churn risk.
Stage 3 Governance Expansion: security integrations, IP/licence scanning, audit trails, retention controls, compliance reports, executive dashboards, prompt-sensitive data scanning, data stewardship workflows.
Stage 4 Intelligence Expansion: trend forecasting, trust calibration, maintainability tracking, prompt efficiency, model provider comparison, AI Dependency Risk, AI-assisted PR Quality Gap, Cognitive Load Index.
Stage 5 Enterprise Control Plane: SSO/RBAC, multi-org, custom policies, custom dashboards, API access, report builder, regulated sector packs.
```

### Executive / CTO View

```text
A single summary screen for leadership, with no PR-level detail.
Shows: Net AI Delivery Value (positive or trending?), AI adoption by team, risk trend, quality trend,
governance maturity, highest-risk areas and an ROI estimate with its confidence label.
```

### Multi-team and product navigation

```text
Full navigation: Dashboard, Teams, Pull Requests, Metrics, Risks, Policies, Recommendations, Integrations, Reports, Settings.
The Teams Dashboard compares many teams to find where support is needed, not to rank or punish.
Settings includes RBAC via Keycloak so each role sees only the right view.
```

### Self-service onboarding journey (target flow)

```text
1. Platform admin signs in via Keycloak / SSO.
2. Connect the GitHub organisation (GitHub App install).
3. Connect Jira (API token or OAuth).
4. Map teams to repositories and Jira projects.
5. Select starting policies (metadata required, large-PR warning, high-risk senior review).
6. Dashboard begins showing data; confidence is low for the first 1-2 sprints, then stabilises.
7. The system starts generating recommendations per team.
```

```text
Every new team starts in observation mode and graduates through the same phase gates as the pilot.
```

### Measurement confidence at this phase

```text
Layer: advanced intelligence (prompt/IDE telemetry, trust calibration, model comparison) added only as confidence allows.
Confidence: variable. Keep advanced signals clearly labelled and out of hard enforcement until validated.
```

## Deliverables

```text
[Phase5]_exit_report.pdf
[Phase5]_data_dictionary.json
[Phase5]_config_changes.yaml
Self-service onboarding package
quick-start.md
faq.md
Quarterly governance pack
Retention purge job
Executive summary screen
Multi-team navigation and RBAC (Keycloak) settings
Self-service integration onboarding journey
```

## Exit Criteria

```text
At least 30% of engineering teams or 10 teams send active data into the platform.
Platform support tickets are below 5 per week.
Every new team starts in observation mode.
No team moves directly into guardrail enforcement.
```

## Fail Criteria

```text
New teams skip observation mode.
Support tickets exceed platform capacity.
Governance forum cannot agree on policy ownership.
Retention purge is not operational before scale-out.
```

## Fail Action

```text
Pause expansion to new teams.
Fix onboarding, support, ownership or data-retention gaps.
Resume rollout in batches.
```

## Gate Decision

```text
Continue expansion only while support load, data confidence and psychological safety remain healthy.
```

## Master Reference Map

```text
Section 12   - Platform-team operating model and Data Steward responsibilities
Section 22   - Recommended repository structure (quick-start, faq and docs)
Section 23   - Example team configuration
Section 26   - Enterprise roadmap (Stages 2-5)
Section 30   - Team AI delivery maturity model
Section 32   - Quarterly governance review
Section 33   - Incident integration use case
```
