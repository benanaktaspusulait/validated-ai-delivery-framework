# Security

## Vulnerability reporting

If you discover a security vulnerability, please report it responsibly.

**Do NOT open a public GitHub issue for security vulnerabilities.**

Email: security@example.com

Include: description, steps to reproduce, potential impact, suggested fix.

```text
Acknowledgement: within 48 hours
Initial assessment: within 1 week
Fix or mitigation: within 30 days (critical), 90 days (others)
Disclosure: coordinated with reporter
```

## Threat model

| Threat | Layer | Mitigation |
|---|---|---|
| Data poisoning | Data / Training | Data validation (Great Expectations), anomaly detection, provenance tracking |
| Model theft | Training / Deployment | Access control via MLflow registry stages, encrypted weights, no public endpoints |
| Adversarial evasion | Inference | Adversarial testing (ART), input validation, robustness training |
| Model inversion | Inference | Rate limiting, output perturbation, differential privacy |
| Prompt injection | Inference (LLMs) | Input sanitisation, output filtering, sandboxing |
| Supply chain | All | Dependency scanning (Trivy, Snyk), signed artefacts, reproducible builds |
| Unauthorised access | All | RBAC (Keycloak), API keys, OAuth, audit logging |

## API security

```text
Authentication:
  - API key via X-API-Key header (model serving endpoints).
  - Keycloak SSO with OAuth 2.0 for dashboard and admin UI.
  - Webhook signature validation (HMAC-SHA256 for GitHub, Atlassian token for Jira).

Authorisation:
  - RBAC roles: Platform Admin, Data Steward, EM, Tech Lead, Executive, Developer.
  - Role-to-data-scope mapping enforced at API middleware layer.
  - No individual developer data visible to any role.

Input validation:
  - All API endpoints validate request schema (Pydantic models).
  - Feature count and value ranges checked before model inference.
  - SQL injection prevented by parameterised queries.
  - XSS prevented by output escaping.

Rate limiting:
  - Read endpoints: 100 req/min per API key.
  - Write endpoints: 30 req/min per API key.
  - Webhook endpoints: 1000 req/min per provider.
```

## Container security

```text
- Base images: official Docker Hub images only (python:3.11-slim, postgres:16, etc.).
- Vulnerability scanning: Trivy in CI pipeline (CRITICAL and HIGH severity).
- No running as root: Dockerfiles should use non-root users where possible.
- Secrets: stored in environment variables or secrets manager, never in images or code.
- Network: services communicate via Docker network; external access only through exposed ports.
```

## Model security

```text
- Model weights stored in MLflow artefact store (MinIO/S3), access-controlled by registry stages.
- No model weights in public repositories or container images.
- Inference endpoint requires API key authentication.
- Model versioning and rollback via MLflow Model Registry.
- No raw prompt storage by design; only derived metadata retained.
```

## Adversarial robustness (MVP scope)

```text
- Input validation: reject malformed or out-of-range feature values.
- Out-of-distribution detection: flag predictions on inputs far from training distribution.
- Model does not leak training data through outputs (verified in validation phase).
- Full adversarial testing (ART, FGSM, PGD) deferred to Stage 2+.
```

## Data security

```text
- Pseudonymisation of developer identifiers before storage.
- Encryption at rest (PostgreSQL, MinIO) and in transit (TLS).
- Data retention enforced: 12 months detailed, 24 months aggregates.
- No raw prompt content stored; only derived safety signals.
- Audit logging for all administrative actions (audit_log table).
```

## Dependency management

```text
- Automated scanning: Trivy (containers), pip-audit (Python packages).
- pinned versions in requirements.txt and Dockerfiles.
- Renovate or Dependabot for automated update PRs.
- Critical vulnerabilities must be fixed within 30 days.
```
