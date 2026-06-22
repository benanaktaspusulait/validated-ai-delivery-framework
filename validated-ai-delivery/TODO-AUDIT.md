# validated-ai-delivery Audit TODO

Audit date: 2025-06-22. Total issues found: 67 (20 ai-project-plan/docs, 12 framework/examples, 15 implementation, 20 validated-ai-delivery).

## Completed (fixed in this session)

- [x] Port drift: API port 8000 → 8080 in prometheus.yml, cd.yml, rollback.sh, deployment.md, api-reference.md
- [x] application.yaml config prefix: model.name → mlflow.model-name (matches MlflowConfig.java)
- [x] Python file references removed from principles.md (5 references → Java equivalents)
- [x] cd.yml: pytest reference → Maven verify command
- [x] framework/08-enterprise-roadmap.md: broken table fixed
- [x] CHANGELOG.md: "FastAPI" → "Quarkus (Java 21)"
- [x] TODO.md: stale Python references updated
- [x] security.md: expanded with MLSecOps topics
- [x] Grafana provisioning YAML files created
- [x] docker-compose.yml: healthchecks, env vars, alerts mount
- [x] CI workflow: JDK 21 + Maven setup
- [x] All API modules wired into Quarkus main.py via CDI

## Open

### High priority
- [ ] Fix TODO.md stale reference in ai-project-plan/README.md (TODO.md doesn't exist at root)
- [ ] Fix faq.md "AI Signal Trend" → "Validated Delivery Trend" naming contradiction
- [ ] Fix docs/ wrong relative paths (10 instances using docs/ prefix inside docs/)
- [ ] Fix implementation/ task count mismatches (phase-4: 13→14, total: 72→73)
- [ ] Fix phase-4 T4.12 circular dependency (depends on itself)
- [ ] Fix phase-0 T0.1 Python JWT code → Java equivalent
- [ ] Fix phase-1 T1.1 FastAPI reference → Java-only options
- [ ] Add execution order sections to phase-2 README

### Medium priority
- [ ] Add missing "Related references" sections to 4 docs/ files
- [ ] Add cross-references from templates back to lifecycle stage files
- [ ] Standardize section naming (Dependency flow → Execution order) across phase READMEs
- [ ] Fix VDT gate phrasing contradiction across 3 framework files
- [ ] Add missing example file references to framework/05-technical-references.md
- [ ] Add glossary.md to phase-packages/README.md cross-references

### Low priority
- [ ] Add Kubernetes HPA manifests
- [ ] Add ONNX model conversion example
- [ ] Add feature store (Feast) integration example
- [ ] Convert remaining Python components to Java (training, drift, tests)
