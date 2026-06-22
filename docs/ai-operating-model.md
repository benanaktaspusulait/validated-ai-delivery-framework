# AI Operating Model

Canonical reference for scaling AI-assisted software delivery from a successful pilot into a repeatable operating model. This document complements the measurement, risk and governance specs; it defines who owns the surrounding system of roles, context, tools, review depth and token cost.

## Principle

```text
Central governance, distributed execution.
```

The platform team owns the control plane: standards, approved tooling, integration patterns, measurement, policy configuration, data confidence, security defaults and rollback paths. Domain teams own the work: use-case selection, domain context, local implementation, review judgement and production accountability.

This is the default model because the two simpler models both fail at scale:

| Model | Strength | Scale risk |
|---|---|---|
| Central AI factory | Strong standards and fast initial enablement | Domain knowledge bottleneck; teams wait for the centre |
| Fully distributed adoption | Fast experimentation and local fit | Inconsistent governance, context drift, tool sprawl and Shadow AI |
| Central governance + distributed execution | Balanced governance and domain ownership | Requires explicit roles, cadence and coordination |

The control plane should make the desired path easier than Shadow AI: approved tools, safe sandboxes, clear data-classification rules and lightweight in-PR guidance.

## Ownership Boundaries

| Area | Accountable | Responsible | Evidence |
|---|---|---|---|
| Approved AI tool catalogue | Platform Lead | AI Platform Engineer, Security Lead | Approved-tools list, risk notes, review date |
| AI delivery control plane | Platform Lead | Platform Engineer | Metrics, policies, data confidence, dashboards |
| Agent tooling and evaluation pipeline | Platform Lead | AI Platform Engineer | Eval suite, tool registry, release notes |
| Context artifact registry | Tech Lead | Context Engineer | Versioned context entries, owner, data classification, last review |
| Domain context quality | Tech Lead | Context Engineer | Eval results, stale-context issues, change history |
| AI-assisted PR ownership | Tech Lead | Developer | PR declaration, explanation, tests, review responses |
| Review depth for AI-assisted PRs | Tech Lead | Agent Reviewer | Risk score, ownership confidence, reviewer assignment |
| Token FinOps | Platform Lead | AI Platform Engineer | Team/workflow cost trend, model usage, budget alerts |
| Shadow AI inventory | Platform Lead | Engineering Manager, Security Lead | Survey output, approved replacement path, risk register |

Every production-affecting AI artifact must have an owner and a backup. A pilot that depends on one champion engineer is not ready to scale.

## Roles

These roles do not have to be new job titles. In most organisations they start as named responsibilities assigned to existing platform engineers, senior engineers and tech leads.

| Role | Primary home | Responsibility |
|---|---|---|
| AI Platform Engineer | Platform team hub | Operates agent tooling, eval pipeline, AI gateway/provider abstraction, token observability and approved-tool catalogue |
| Context Engineer | Domain team spoke | Turns domain knowledge into versioned context artifacts; measures quality, freshness and auditability |
| Agent Reviewer | Domain team spoke | Reviews AI-assisted changes using domain knowledge, risk score and ownership confidence; calibrates review depth |
| Platform Lead | Platform team hub | Owns policy, confidence gates, rollout pace, operating-model health and escalation |
| Tech Lead | Domain team spoke | Owns domain review standards, context-owner coverage and production accountability |

Developers remain responsible for code they merge. AI assistance changes the production mechanism, not the ownership principle.

## Context Development Lifecycle

Context artifacts are treated as governed delivery artifacts, not private notes on a laptop.

```text
1. Discover: identify source systems, domain rules, examples and exclusions.
2. Structure: create a context artifact with scope, owner, data classification and intended use.
3. Version: store changes with review history and rollback path.
4. Evaluate: test against representative tasks, edge cases and known failure modes.
5. Publish: make the artifact available through the approved registry or tooling path.
6. Monitor: track stale context, quality regressions, false positives and usage.
7. Retire: archive context that is obsolete, unsafe or no longer used.
```

Minimum metadata for a context artifact:

```text
context_id
domain
owner
backup_owner
source_references
data_classification
approved_tools
version
last_reviewed_at
eval_suite_reference
known_limitations
```

MVP constraint: do not store raw prompts. Store artifact metadata, ownership and evaluation evidence only.

## Review Depth Model

The risk engine decides where scarce review capacity should go; domain reviewers decide what quality means for their area.

| Signal | Review depth |
|---|---|
| Low contextual risk and high ownership confidence | Normal review plus automated checks |
| Medium contextual risk or unclear local pattern | Add reviewer context or ask for an explanation of AI-assisted design choices |
| High contextual risk, low ownership confidence or critical domain | Agent Reviewer or Tech Lead review required; consider splitting the PR |
| Security-sensitive path or regulated workflow | Required owner review plus Security Lead or AppSec review, subject to confidence gate |

The decision must remain confidence-gated. A low-confidence metric can guide conversation, but it cannot justify blocking enforcement.

## Token FinOps

Token cost is a platform cost signal, not an individual productivity signal. Track it at team, workflow, model and tool level.

Recommended observability dimensions:

```text
team_id
workflow_type
tool_or_agent
model_provider
model_name
context_artifact_id
input_tokens
output_tokens
estimated_cost
latency_ms
policy_decision
error_type
```

Governance rules:

```text
Report token cost as a team/workflow operating trend.
Never publish developer-level token leaderboards.
Alert on unusual cost spikes, provider errors and model-tier drift.
Use model-tier guidance before blunt budget caps.
Review token trends in the quarterly governance forum.
```

## Shadow AI and Passive Resistance

The operating model should reduce unsafe unofficial use without turning the platform into a surveillance tool.

Mitigations:

```text
Maintain an approved tool catalogue and explain why each tool is approved.
Provide safe sandbox paths for experimentation.
Survey actual AI usage patterns to estimate the detection gap.
Communicate that honest AI declaration is not punished.
Offer clear role paths for context, review and platform responsibilities.
Treat reluctance as a change-management signal, not a compliance failure.
```

If developers hide AI usage or managers resist adoption because the purpose is unclear, pause rollout and repair the operating model before adding enforcement.

## First 90 Days

| Time | Organisational step | Visible output |
|---|---|---|
| Weeks 1-2 | Build an ownership map: who runs the pilot, who owns context, where agent configuration lives, who reviews AI-assisted code | Single-person dependencies and missing owners are visible |
| Month 1 | Name 1-2 AI-focused platform responsibilities; publish approved tools; run Shadow AI inventory | Hub responsibilities and approved tool path are clear |
| Month 2 | Assign Context Engineer and Agent Reviewer responsibilities in the pilot domain; introduce confidence-based review depth | Domain spokes are active and review bottlenecks are targeted |
| Month 3 | Start context-quality feedback, token FinOps reporting and operating-model retro cadence | The pilot process is repeatable without a single champion |

The goal is not a large reorganisation. The goal is to move AI capability from individuals into lightweight roles, artifacts and repeatable routines.

## Agentic Harness Maturity

The harness is the load-bearing layer around coding agents: instructions, skills, scripts, hooks, workflow lanes, quality gates and freshness checks. It does not ship to end users, but it shapes how product software is built. Treat it as a platform asset with its own ownership, versioning and review path.

Agentic Harness Maturity is not a developer productivity score. It is a team or repository-level readiness signal that explains whether AI-assisted delivery has enough structure to scale safely.

| Dimension | What good looks like | Risk when weak |
|---|---|---|
| Harness ownership | A named owner and backup maintain the shared agent harness | Rules and scripts drift across repositories |
| Instruction inheritance | Repositories inherit a core instruction file and override only local exceptions | Every repo carries a different copy of the same rules |
| Shared skills/scripts | Skills, scripts and hooks live once and are reused or wrapped locally | Guardrails are copy-pasted, stale or missing |
| Workflow lanes | Common work types have reusable phases and quality gates | Feature, bugfix and hotfix work all rely on ad hoc judgement |
| Agent specialization | Runtime-specific mechanics are separated from shared intent | Claude/Codex or other agent rules diverge silently |
| Drift and freshness checks | Changes to skills/scripts verify distribution and detect stale platform copies | A local agent runs old rules without anyone noticing |
| Evidence trail | Harness changes are reviewed, versioned and linked to observed failure modes | Improvements become tribal knowledge |

Scoring guidance:

```text
0 = absent: no shared harness; instructions and scripts are local and informal.
1 = emerging: shared instructions exist, but scripts, hooks or skills are copied manually.
2 = managed: shared instructions, skills and scripts exist with clear local override patterns.
3 = governed: workflow lanes, quality gates, freshness checks and owner review are active.
4 = adaptive: observed failures feed a controlled improvement loop for harness changes.
```

Recommended survey/checklist inputs:

```text
core_instruction_file_present
repo_local_override_policy_present
shared_skill_or_script_registry_present
local_wrapper_pattern_used
workflow_lanes_defined
quality_hooks_enabled
freshness_or_drift_check_enabled
agent_specific_specialization_documented
harness_owner_and_backup_named
harness_change_reviewed
```

Use the score to decide enablement work, not enforcement. A low harness maturity score should lead to platform support, starter harness templates or training before a team receives stricter AI delivery policies.

## Phase Mapping

| Phase | Operating-model work |
|---:|---|
| 0 | Ownership map, approved-tool draft, Shadow AI inventory, no-individual-use commitment, harness ownership check |
| 1 | Capture the metadata needed for team-level ownership and confidence; no developer-facing interpretation |
| 2 | Show risk, ownership-boundary, reviewer-load and harness-maturity signals in read-only mode |
| 3 | Use warnings and experiments to calibrate review depth with domain reviewers |
| 4 | Activate confidence-gated policies while preserving emergency override and review-owner escalation |
| 5 | Roll out hub/spoke ownership, context registry hygiene, harness freshness checks and token FinOps across teams |

## Non-Goals

```text
Do not create a separate AI factory as the default delivery owner.
Do not use token cost, AI usage or review effort for individual performance scoring.
Do not store raw prompts in the MVP.
Do not replace human ownership of AI-assisted code with model/provider accountability.
Do not move a team into enforcement because a pilot champion can make the process work manually.
Do not make harness maturity a blocker until starter templates and platform support exist.
```

## Related References

```text
docs/rollout-operating-model.md  - modes, RACI, pause criteria and governance forum
docs/governance-and-privacy.md   - non-negotiable privacy and misuse rules
docs/risk-policy-engine.md       - contextual risk, review assignment and policy gates
docs/training-change-management.md - role training and adoption sequence
docs/costing-pricing.md          - cost model and Token FinOps planning
```
