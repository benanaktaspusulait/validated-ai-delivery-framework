# Contributing

Thank you for helping improve the Validated AI Delivery Framework. This repository is a documentation set: a thesis, a product definition, stage-gate phase packages, technical references and copyable artefacts. Most contributions are written, not coded.

Read [README.md](README.md) first to understand the document map, then this guide.

## Ways to contribute

| Contribution type | What it looks like | Where it lands |
|---|---|---|
| Bug report | A factual error, broken link, or contradiction between documents | GitHub Issue (template: Documentation Defect) |
| Clarification request | A section that is ambiguous or too abstract to act on | GitHub Issue (template: Clarify) |
| Feature proposal | A new capability, metric, policy or doc section | GitHub Issue, then an RFC if accepted (see ROADMAP.md) |
| Documentation improvement | Tightening wording, adding examples, fixing structure | Pull Request |
| New connector design | A conceptual integration plan for a new Git/issue provider | PR against `docs/multi-provider-strategy.md` |
| Use case | A real adoption scenario and how the framework applies | PR against `docs/use-cases.md` |
| Translation | A localised version of a document | PR adding a `*.<lang>.md` file |

## Working environment

No build toolchain is required. You need:

```text
Git and a GitHub account.
A Markdown editor with live preview.
Optional: a Markdown linter and a Mermaid preview extension to check tables and diagrams.
```

There is no platform code in this repository yet. Do not add source code, build scripts or runnable configuration; keep contributions at the conceptual and documentation level. Copyable artefacts belong in `examples/` and are illustrative, not a build target.

## Branching and commits

```text
Branch from main using a descriptive name: docs/<topic>, fix/<topic> or proposal/<topic>.
Keep one logical change per branch.
```

Use Conventional Commit prefixes so history stays readable:

| Prefix | Use for |
|---|---|
| docs: | Adding or editing documentation |
| fix: | Correcting an error or broken reference |
| refactor: | Restructuring documents without changing meaning |
| feat: | A genuinely new document or framework concept |
| chore: | Repository housekeeping |

Example: `docs: add webhook event mapping table for GitLab`.

## Pull request process

```text
1. Open or link an Issue describing the gap before large changes.
2. Keep the PR focused; large reorganisations should be split or flagged in advance.
3. Update the document map in README.md if you add or move a file.
4. Update TODO.md if your change opens or closes a tracked item.
5. Run a link and Markdown check locally; fix broken relative links.
6. Fill in the PR description: what changed, why, and which documents are affected.
```

## Review expectations

```text
At least one maintainer review is required to merge.
Reviewers check: factual accuracy, internal consistency, single-source-of-truth (no duplicated specs), and tone.
Discussion stays respectful and focused on the document, not the author.
Disagreements escalate to a maintainer decision, recorded in the PR thread.
```

## Documentation style

Match the existing voice: formal, clear and actionable.

```text
One canonical home per concept. Reference docs/ rather than re-explaining a spec.
Prefer prose, tables and Mermaid diagrams. Keep illustrative artefacts in examples/.
Use British spelling to match existing documents.
Never introduce individual-ranking, leaderboard or surveillance language; the non-negotiable rules in docs/governance-and-privacy.md are binding.
State estimates as estimates and attach a confidence framing where relevant.
```

## Code of conduct

All participation is governed by [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md). By contributing you agree to uphold it.
