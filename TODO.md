# Implementation TODO

Tracks remaining work after the documentation refactor. Canonical implementation detail lives in `docs/`, phase gates live in `phase-packages/`, and copyable artefacts live in `examples/`.

## Open

### Build-time follow-ups

- [ ] Assign Stage-2 notification connectors (Slack/Teams app, weekly email) when they move from "platform channel" to first-class integrations.
- [ ] Build the PRD into delivery tickets per phase once engineering starts.
- [ ] Confirm whether SonarQube should be recommended for pilot teams where maintainability metrics are important.
- [ ] Add enterprise exclude paths for prompt-leakage scanning.
- [ ] Add Cognitive Load Index sample-size confidence markers before Stage 4 intelligence metrics.
- [ ] Add Phase 4 automated incident-to-Jira-to-PR linkage once the manual incident review loop is proven.
- [ ] Refine counterfactual-value caps after activity-based evidence is available.

## Per phase

```text
Phase 0: confirm reusable templates for legal/privacy and HR sign-off artefacts.
Phase 1: implement schema from docs/data-model.md including all indexes.
Phase 2: expose docs/api-spec.md Phase 2 endpoints; wire cost_config.
Phase 3: keep MVP quality linkage Jira-defect-based; coverage/CI are Stage 2+.
Phase 4: regression-test confidence-gated enforcement (already specified in docs/testing-and-observability.md).
Phase 5: implement retention purge, RBAC and self-service onboarding tests.
```
