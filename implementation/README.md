# Implementation

Task-level breakdown for coding the AI Delivery Control Plane. Each phase has its own directory with a README and individual task files.

## Structure

```text
implementation/
├── phase-0/   (8 tasks)  — Legal/HR, setup, no code
├── phase-1/   (11 tasks) — Collectors, ETL, confidence scoring
├── phase-2/   (17 tasks) — Metrics engine, risk scoring, dashboards
├── phase-3/   (10 tasks) — PR bot, experiment mode, calibration
├── phase-4/   (14 tasks) — Metadata blocker, WIP, scanner, policy UI
└── phase-5/   (13 tasks) — Onboarding, RBAC, purge, pen test
```

**Total: 73 tasks across 6 phases.**

## How to use

1. Start with `phase-0/README.md` — this phase has no code, only setup tasks.
2. Each task file contains: objective, acceptance criteria, technical notes, test cases, references.
3. Tasks have dependencies listed in the "Depends on" field. Respect the order.
4. Each phase has a gate — do not proceed to the next phase until all exit criteria pass.

## Task file format

Each task file contains enough detail for a developer to start coding immediately:

```markdown
# T{phase}.{number} — {Title}

**Phase:** {0-5}
**Owner:** {role}
**Estimate:** {time}
**Depends on:** {task IDs}

## Objective
{What this task achieves — one sentence}

## Project setup (if first task in phase)
{Database migrations, environment variables, tech stack decisions}

## File structure
{Exact file paths to create}

## Implementation steps
{Step-by-step: class names, method signatures, SQL queries, API contracts}

## Test cases
{Unit tests with @Test annotations, integration tests with expected behavior}

## Validation checklist
[ ] Checkbox list of what must work before marking done

## References
{Exact docs/ files to read for full spec}
```

**Task detail levels:**
- **Level 1 (setup tasks):** Acceptance criteria + validation only (e.g. T0.1, T0.2)
- **Level 2 (implementation tasks):** Full file structure + implementation steps + test cases (e.g. T1.1, T1.5, T2.1, T2.6)
- **Level 3 (UI tasks):** Screen layout + component descriptions + acceptance criteria (e.g. T2.10, T4.8)
- **Level 4 (process tasks):** Acceptance criteria + validation only (e.g. T0.6, T3.5, T5.10)

## Phase overview

| Phase | Duration | Mode | Tasks | What gets built |
|---:|---|---|---:|---|
| 0 | 2 weeks | Readiness | 8 | GitHub App, Jira token, PR template, branch protection, safety baseline |
| 1 | 3 weeks | Observation | 11 | Webhook handlers, backfill, ETL, confidence job, admin shell |
| 2 | 3 weeks | Observation | 17 | 5 metric functions, risk scoring, VDT, 4 dashboard screens, APIs |
| 3 | 4 weeks | Warning | 10 | PR Comment Bot, experiment mode, A/B analysis, HVC calibration |
| 4 | 4 weeks | Enforcement | 13 | Metadata blocker, WIP, override audit, scanner, policy UI |
| 5 | Continuous | Staged | 13 | Onboarding, RBAC, purge, incident integration, pen test |
