# API Specification

Canonical API surface for the AI Delivery Control Plane, annotated with the phase that introduces each endpoint.

## Phase 1 — ingestion and teams

```yaml
/api/v1/teams:
  get:
    description: List teams and AI delivery health summaries (may be sparse in Phase 1)

/api/v1/webhooks/github:
  post:
    description: Receive GitHub webhook events

/api/v1/webhooks/jira:
  post:
    description: Receive Jira webhook events
```

## Phase 2 — metrics, risk and recommendations (read-only)

```yaml
/api/v1/teams/{teamId}/metrics:
  get:
    description: Retrieve metrics for a team (AI-assisted rate, review debt, defect rate, value)
    parameters:
      - name: teamId
        in: path
        required: true
        type: string

/api/v1/pull-requests/{prId}/risk:
  get:
    description: Retrieve detailed risk analysis for a specific PR
    parameters:
      - name: prId
        in: path
        required: true
        type: string

/api/v1/teams/{teamId}/recommendations:
  get:
    description: List active recommendations for a team (read-only)
    parameters:
      - name: teamId
        in: path
        required: true
        type: string
```

## Phase 4 — policies and overrides

```yaml
/api/v1/policies:
  get:
    description: List all configured policies
  post:
    description: Create or update a policy rule

/api/v1/policy-overrides:
  get:
    description: List policy override events for auditing
  post:
    description: Record a new policy override (called by CI/CD when emergency-fix is used)
```

## Conventions

```text
All read endpoints return each metric with its data_confidence_score and label.
Write endpoints (policies, overrides) require platform-admin or data-steward roles via Keycloak RBAC.
Webhook endpoints validate provider signatures and are idempotent (see docs/architecture.md).
```

## Response examples

`GET /api/v1/teams`

```json
{
  "teams": [
    {
      "id": "payments-team",
      "name": "Payments Team",
      "ai_assisted_pr_rate": 0.32,
      "data_confidence_score": 84
    }
  ]
}
```

`GET /api/v1/teams/{teamId}/metrics`

```json
{
  "team_id": "payments-team",
  "metrics": [
    {
      "name": "AI Review Debt Age Ratio",
      "value": 1.4,
      "confidence": "medium-high",
      "confidence_score": 78,
      "decision_grade": true
    }
  ]
}
```

`GET /api/v1/pull-requests/{prId}/risk`

```json
{
  "pull_request_id": "pr_123",
  "ai_contextual_risk_score": 11,
  "signals": ["large_pr", "security_sensitive_path"],
  "required_reviewers": ["senior_engineer", "code_owner"]
}
```

`GET /api/v1/teams/{teamId}/recommendations`

```json
{
  "team_id": "payments-team",
  "recommendations": [
    {
      "type": "split_large_ai_prs",
      "severity": "warning",
      "message": "Large AI-assisted PRs are associated with longer review waits under matched comparison.",
      "status": "open"
    }
  ]
}
```
