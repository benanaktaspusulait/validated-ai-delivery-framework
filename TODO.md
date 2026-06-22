# Implementation TODO

Tracks remaining work. The documentation refactor (gate files + `docs/` references + `examples/`) is complete; see "Completed" below.

## Open

### Build-time follow-ups (existing)

- [ ] Optional further framework thinning: sections 7.3, 7.4, 10, 11 and 18 still hold detail now canonical in `docs/`. Consider reducing them to pointers in a later pass to fully eliminate overlap (kept for now as the thesis-level catalogue).
- [ ] Assign Stage-2 notification connectors (Slack/Teams app, weekly email) when they move from "platform channel" to first-class integrations.
- [ ] Build the PRD into delivery tickets per phase once engineering starts.

## Next document

- [ ] None blocking. PRD, quick-start and faq now exist and are role-aware. Revisit the PRD's analytics events and permission model with engineering before Phase 2 build.

## Per phase (content is complete; these are build-time follow-ups)

```text
Phase 0: confirm reusable templates for legal/privacy and HR sign-off artefacts.
Phase 1: implement schema from docs/data-model.md including all indexes.
Phase 2: expose docs/api-spec.md Phase 2 endpoints; wire cost_config.
Phase 3: keep MVP quality linkage Jira-defect-based; coverage/CI are Stage 2+.
Phase 4: regression-test confidence-gated enforcement (already specified in docs/testing-and-observability.md).
Phase 5: implement retention purge, RBAC and self-service onboarding tests.
```

## Completed (documentation refactor)

- [x] Created `docs/` canonical references: architecture, data-model, data-confidence, metrics-catalogue, risk-policy-engine, ui-ux-spec, governance-and-privacy, psychological-safety, rollout-operating-model, api-spec, testing-and-observability.
- [x] Created `examples/`: pr-template.md, team-config.yaml, policy-config.yaml, github-actions/ai-metadata-enforcement.yml, github-actions/risk-reviewer-assignment.yml, phase0-exit-report-template.md.
- [x] Slimmed all six phase files to gate documents (~120 lines each) with a Reference Docs section.
- [x] Thinned the framework: sections 14, 15, 16, 17, 22, 23 now point to docs/examples instead of embedding code.
- [x] Added the `recommendations` table, cost-input config, and the Phase 2/4 API endpoints (now in `docs/`).
- [x] Consolidated operating modes, RACI, pause criteria and confidence-by-phase into `docs/rollout-operating-model.md`; removed the duplicates from `phase-packages/README.md`.
- [x] Rewrote `README.md` as the directive entry point and `phase-packages/README.md` as a lean stage-gate map.
- [x] Strengthened `PRD.md` (personas, journeys, FRs/NFRs, RBAC, analytics events, acceptance criteria, milestones) and expanded `quick-start.md` and `faq.md`.
- [x] Created Stage 2+ reference docs: use-cases, market-landscape, training-change-management, multi-provider-strategy, costing-pricing, scalability-sla, disaster-recovery, support-model.
- [x] Created glossary.md, CONTRIBUTING.md, CODE_OF_CONDUCT.md.

## Completed (audit fixes)

- [x] Added Stage 2+ reference docs section to README.md (use-cases, market-landscape, training-change-management, multi-provider-strategy, costing-pricing, scalability-sla, disaster-recovery, support-model).
- [x] Linked CONTRIBUTING.md and glossary.md in README.md "Where to go" table.
- [x] Updated validated-ai-delivery-framework-v1.8.md table description to "thesis, formulas, positioning".
- [x] Reworded faq.md "Net AI Delivery Value" entry to remove confusing "the old" parenthetical.
- [x] Created examples/phase0-exit-report-template.md with 2-page skeleton (Decision, Evidence, Risks, Sign-off).
- [x] Aligned phase-packages/README.md Phase 0 gate with concrete measurable criteria (legal/HR sign-off + safety baseline >= 3.5).
- [x] Aligned phase-packages/README.md Phase 5 gate with concrete criteria (>= 30% teams, support < 5/wk, observation mode).
- [x] Added "illustrative" note to docs/architecture.md reference stack.
- [x] Added Phase 0 prerequisite (legal, HR, safety baseline) to quick-start.md.
- [x] Added psychological-safety.md cross-reference to quick-start.md Support section.
- [x] Marked team-config.yaml notifications.slack_channel as future/planned (Stage 2+).
- [x] Added contact email to CODE_OF_CONDUCT.md.
- [x] Expanded .gitignore with common patterns (.DS_Store, .env, node_modules, etc.).
- [x] Added note to pricing hypothesis section (Section 35) indicating it belongs in docs/costing-pricing.md.
- [x] Verified PR template (6 checkboxes) matches ai-metadata-enforcement.yml (5 AI options + "No AI" handled separately) — no mismatch.
- [x] Verified Dynamic AI WIP min_limit: 2 consistency across risk-policy-engine.md, team-config.yaml and Phase 4 exit criteria — no action needed.
- [x] Verified faq.md VDT disclaimer aligns with docs/metrics-catalogue.md — already consistent.
