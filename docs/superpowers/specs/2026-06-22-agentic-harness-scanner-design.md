# Agentic Harness Scanner Design

## Goal

Add an automated Agentic Harness Maturity scanner to `vaiddf` so the platform can inspect a repository, collect evidence about its agent harness, calculate a team/repository-level maturity score and recommend enablement actions without creating enforcement blocks or individual scoring.

## Context

The documentation now defines Agentic Harness Maturity as the readiness of the shared layer around coding agents: instructions, skills, scripts, hooks, workflow lanes, quality gates, specialization and drift checks. The current `vaiddf` codebase has Java 21, Maven, Quarkus REST resources, Qute templates and in-memory services. There is no existing persistence layer for this type of assessment, so the first implementation should follow the existing in-memory API/dashboard pattern used by drift history and model registry.

## Chosen Approach

Implement the full scanner path, not a manual-only score:

```text
Repository path -> scanner -> evidence checklist -> maturity score -> recommendations -> REST API -> dashboard summary
```

The scanner reads only repository metadata and configuration-like files. It must not read source code contents for model prompts, secrets or developer performance. It reports repository/team readiness, never a person-level score.

## Scope

Create core domain types:

```text
HarnessMaturityDimension
HarnessMaturityEvidence
HarnessMaturityAssessment
HarnessMaturityLevel
HarnessMaturityRecommendation
```

Create API/service components:

```text
HarnessMaturityScanner
HarnessMaturityService
HarnessMaturityResource
HarnessMaturityAssessRequest
```

Create UI:

```text
/harness page with latest assessments, dimension evidence and recommendations
Dashboard card showing latest harness score and level
Navbar link to Harness
```

Add tests:

```text
Core scoring unit tests
Scanner fixture tests using temporary repositories
Resource test for POST /api/v1/harness-maturity/assess
```

## Scanner Evidence Rules

The scanner maps filesystem evidence to the checklist from `docs/ai-operating-model.md`.

| Dimension | Evidence detected |
|---|---|
| Core instruction file | `AGENTS.md`, `.agents/AGENTS.md`, `CLAUDE.md`, `.codex/AGENTS.md`, `.github/copilot-instructions.md` |
| Repo-local override policy | instruction file contains "override", "local wins", "core first", "inherit" or "extends" |
| Shared skill or script registry | `skills/`, `.codex/skills/`, `.agents/skills/`, `scripts/agent/`, `agent-scripts/` |
| Local wrapper pattern | scripts containing "wrapper", "delegate", "core", "shared" in agent-related script paths |
| Workflow lanes | files under `.github/workflows/`, `.agents/workflows/`, `workflows/`, or docs mentioning feature/bugfix/hotfix lanes |
| Quality hooks | `pre-commit`, `.githooks/`, `.husky/`, `.claude/hooks/`, `.codex/hooks/`, scripts with "hook", "guardrail", "quality-gate" |
| Freshness or drift check | files or scripts with "freshness", "drift", "sync", "checksum", "stale" near agent/skill/script paths |
| Agent-specific specialization | files or directories naming more than one agent runtime, for example `codex`, `claude`, `copilot`, `cursor` |
| Harness owner and backup | `CODEOWNERS`, `OWNERS`, `MAINTAINERS`, or instruction text containing owner/backup terms |
| Harness change reviewed | pull request template, review workflow, CODEOWNERS or docs mentioning harness review |

Each dimension records:

```text
dimension
present
confidence: HIGH | MEDIUM | LOW
evidencePaths
evidenceSummary
recommendation
```

## Scoring

Calculate the maturity level from detected evidence:

```text
score = number of present dimensions
coverage = score / 10

0 present: ABSENT
1-3 present: EMERGING
4-6 present: MANAGED
7-8 present: GOVERNED
9-10 present: ADAPTIVE
```

The public numeric maturity remains 0-4:

```text
ABSENT = 0
EMERGING = 1
MANAGED = 2
GOVERNED = 3
ADAPTIVE = 4
```

## API Design

```text
GET /api/v1/harness-maturity
Returns all assessments, latest first.

GET /api/v1/harness-maturity/latest
Returns the latest assessment or 404 if none exists.

POST /api/v1/harness-maturity/assess
Body:
{
  "repositoryName": "vaiddf",
  "repositoryPath": "/absolute/or/relative/path"
}
Returns the assessment.
```

Path safety:

```text
Reject blank paths.
Resolve paths to canonical form.
Allow only paths below the workspace by default.
Reject non-directory paths.
Do not follow symlinks outside the scanned repository.
Limit recursive scan depth and file count.
```

## Data Flow

```text
HarnessMaturityResource receives request.
HarnessMaturityService validates and resolves repository path.
HarnessMaturityScanner scans allowed files and builds evidence.
HarnessMaturityAssessment calculates level and recommendations.
HarnessMaturityService stores assessment in memory.
Resource returns assessment JSON.
HarnessDashboardResource renders the latest assessments.
```

## UI Design

The UI follows the existing VAIDDF dashboard style. It adds:

```text
Dashboard stat card: Harness Maturity, latest numeric score and level.
Quick action: Harness Maturity.
/harness page:
  - latest score cards
  - assessment form with repository path
  - dimension evidence table
  - recommendation table
  - recent assessment history
```

No charts are required for the first implementation.

## Error Handling

```text
400 for blank path or non-directory path.
400 for path outside allowed workspace.
500 only for unexpected scan errors.
Assessment recommendations explain missing dimensions; missing evidence is not an error.
```

## Testing

Use JUnit 5 and the existing Maven/Quarkus setup.

Test cases:

```text
Scoring:
  zero dimensions -> ABSENT score 0
  three dimensions -> EMERGING score 1
  six dimensions -> MANAGED score 2
  eight dimensions -> GOVERNED score 3
  ten dimensions -> ADAPTIVE score 4

Scanner:
  fixture with AGENTS.md, skills, hooks and workflows detects expected dimensions
  empty temp directory returns ABSENT with recommendations
  symlink outside repo is ignored

Resource:
  POST assess returns assessment JSON
  GET latest returns latest assessment after POST
  blank path returns 400
```

## Non-Goals

```text
No enforcement or blocking policy from harness maturity in this release.
No developer-level scoring.
No raw prompt collection.
No full static analysis of product source code.
No persistent database migration yet.
No automatic modification of the scanned repository.
```

## Open Implementation Notes

The scanner should live in `vaiddf/api` because it is filesystem/API-adjacent, while the assessment records live in `vaiddf/core` because they are domain objects. If persistence is added later, `HarnessMaturityService` can move from in-memory storage to a repository adapter without changing resource or UI contracts.
