# Auditability and Audit Logs

Recording every significant action for traceability, compliance and debugging.

## What to log

```text
Every significant action must be logged:
  - Who: user or service account identity.
  - When: timestamp (UTC).
  - What: action performed and object affected.
  - Why: justification or change request reference.
  - Before/after: what changed (for modifications).

Log categories:
  - Data access: who read/wrote which dataset.
  - Model changes: training, registration, promotion, rollback.
  - Configuration changes: pipeline, threshold, policy.
  - Deployment actions: deploy, rollback, scale.
  - Access control: role changes, permission grants.
```

## Audit log schema

```yaml
audit_log:
  timestamp: "2025-02-15T10:30:00Z"
  actor: "ml-engineer@company.com"
  action: "model.promote"
  object_type: "model"
  object_id: "credit-scoring-v2:2.3.1"
  before:
    stage: "staging"
  after:
    stage: "production"
  justification: "Passed validation gates. Approved by ML lead."
  metadata:
    approval_ticket: "JIRA-1234"
    validation_report: "reports/validation-credit-v2.3.1.pdf"
```

## Storage and retention

```text
Storage:
  - Append-only log store (never delete or modify entries).
  - Options: dedicated audit log database, cloud audit logs, or immutable S3 bucket.

Retention:
  - Minimum 3 years for regulatory compliance.
  - 5 years for high-risk AI systems (EU AI Act).
  - Align with organisational data retention policy.

Access:
  - Read-only for auditors and compliance.
  - Write-only for logging services.
  - No individual can modify their own audit entries.
```

## Audit queries

```text
Common queries:
  - "Who promoted model X to production?" -> filter by action=model.promote, object_id=X
  - "What changed in the last 7 days?" -> filter by timestamp range
  - "All actions by user Y" -> filter by actor=Y
  - "All deployment actions" -> filter by action LIKE "model.%"
  - "All access to dataset Z" -> filter by object_id=Z, action LIKE "data.%"
```
