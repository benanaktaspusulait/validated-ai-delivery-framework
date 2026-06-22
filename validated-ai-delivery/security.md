# Security Policy

## Reporting a vulnerability

If you discover a security vulnerability in this project, please report it responsibly.

**Do NOT open a public GitHub issue for security vulnerabilities.**

Instead, please email: security@example.com

Include:
- Description of the vulnerability
- Steps to reproduce
- Potential impact
- Suggested fix (if any)

## Response timeline

```text
Acknowledgement: within 48 hours
Initial assessment: within 1 week
Fix or mitigation: within 30 days for critical, 90 days for others
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

## Security measures in this project

```text
- API key authentication on model serving endpoints
- Container vulnerability scanning (Trivy) in CI pipeline
- Input validation on all API endpoints
- Model access control via MLflow registry stages
- Webhook signature validation (where applicable)
- No raw prompt storage by design
```

## Dependencies

We use automated dependency scanning. If you find a vulnerable dependency, please report it using the process above.
