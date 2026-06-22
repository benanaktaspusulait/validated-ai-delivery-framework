# Security (MLSecOps)

Protecting models, data and endpoints throughout the lifecycle.

## Threat model

| Threat | Phase | Mitigation |
|---|---|---|
| Data poisoning | Data / Training | Data validation, anomaly detection, provenance tracking |
| Model theft | Training / Deployment | Access control, encrypted weights, no public endpoints |
| Adversarial evasion | Inference | Adversarial testing, input validation, robustness training |
| Model inversion | Inference | Rate limiting, output perturbation, differential privacy |
| Prompt injection | Inference (LLMs) | Input sanitisation, output filtering, sandboxing |
| Supply chain | All | Dependency scanning, signed artefacts, reproducible builds |
| Unauthorised access | All | RBAC, API keys, OAuth, audit logging |

## Security checklist

```text
Data security:
  [ ] Data encrypted at rest (AES-256 or equivalent).
  [ ] Data encrypted in transit (TLS 1.2+).
  [ ] Access to training data logged and auditable.
  [ ] PII handled according to privacy policy (see compliance.md).
  [ ] Data retention enforced; expired data deleted.

Model security:
  [ ] Model weights stored in access-controlled storage.
  [ ] No model weights in public repositories.
  [ ] Model serving endpoint authenticated (API key or OAuth).
  [ ] Rate limiting on inference API.
  [ ] Input validation (schema, range, type checks).

Infrastructure security:
  [ ] RBAC for all platform components.
  [ ] Secrets in a secrets manager (not in code or config files).
  [ ] Container images scanned for vulnerabilities.
  [ ] Network policies restrict inter-service communication.
  [ ] Audit logging for all administrative actions.

Adversarial robustness:
  [ ] Adversarial testing performed (ART or equivalent).
  [ ] Input perturbation tests pass.
  [ ] Out-of-distribution detection active.
  [ ] Model does not leak training data through outputs.
```

## RBAC roles

| Role | Access |
|---|---|
| Data Engineer | Data pipelines, feature store, data quality |
| ML Engineer | Training, experiment tracking, model registry |
| ML Ops | Deployment, monitoring, rollback |
| Security Auditor | Audit logs, security reports (read-only) |
| Admin | Full access (break-glass only) |
