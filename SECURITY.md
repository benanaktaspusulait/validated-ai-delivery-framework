# Security Policy

## Reporting a vulnerability

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

## Scope

```text
In scope:
  - Authentication and authorisation bypasses
  - Data leakage through model outputs
  - Injection attacks on API endpoints
  - Privilege escalation in the platform
  - Supply chain vulnerabilities in dependencies

Out of scope:
  - Social engineering
  - Physical attacks
  - Issues in third-party services (report to them directly)
```

## Security measures

```text
- API key and SSO authentication on all endpoints
- Container vulnerability scanning (Trivy) in CI pipeline
- Input validation on all API endpoints
- Model access control via MLflow registry stages
- Webhook signature validation (HMAC-SHA256)
- No raw prompt storage by design
- Audit logging for all administrative actions
```
