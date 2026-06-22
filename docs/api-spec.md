# API Specification

Canonical API surface for the AI Delivery Control Plane, annotated with the phase that introduces each endpoint.

## Conventions

```text
Base URL: /api/v1/
All read endpoints return each metric with its data_confidence_score and label.
Write endpoints (policies, overrides) require platform-admin or data-steward roles via Keycloak RBAC.
Webhook endpoints validate provider signatures and are idempotent (see docs/architecture.md).
All responses use JSON content type.
Timestamps are ISO 8601 with timezone (TIMESTAMPTZ).
```

## Versioning

```text
Breaking changes (response shape removal, field rename, type change) require a new API version (v2).
Non-breaking changes (new optional fields, new endpoints) are added to the current version.
Deprecated versions receive Sunset and Deprecation headers for 6 months before removal.
Minimum supported version: one major version behind current.
```

## Pagination

```text
All list endpoints support pagination via query parameters:
  limit   - items per page (default: 20, max: 100)
  offset  - number of items to skip (default: 0)

Response includes:
  total_count - total number of matching items
  limit       - the limit used
  offset      - the offset used
  has_more    - boolean, true if more items exist beyond this page
```

## Rate limiting

```text
Read endpoints:  100 requests per minute per API key.
Write endpoints: 30 requests per minute per API key.
Webhook endpoints: 1000 requests per minute per provider.

Rate limit headers included in every response:
  X-RateLimit-Limit     - maximum requests per window
  X-RateLimit-Remaining - remaining requests in current window
  X-RateLimit-Reset     - UTC epoch seconds when the window resets

When rate limited: 429 Too Many Requests with Retry-After header.
```

## Error response schema

```json
{
  "error": {
    "code": "METRIC_NOT_FOUND",
    "message": "No metrics found for team 'payments-team'",
    "details": {}
  }
}
```

Standard error codes:

```text
400 Bad Request          - invalid parameters or malformed request body
401 Unauthorized         - missing or invalid authentication
403 Forbidden            - authenticated but insufficient role
404 Not Found            - resource does not exist
409 Conflict             - resource already exists (e.g. duplicate team)
422 Unprocessable Entity - valid JSON but semantically invalid (e.g. missing required fields)
429 Too Many Requests    - rate limit exceeded
500 Internal Server Error - unexpected server failure
503 Service Unavailable   - temporary degradation (backpressure, downstream failure)
```

## Phase 1 — ingestion and teams

```yaml
/api/v1/teams:
  get:
    description: List teams and AI delivery health summaries (may be sparse in Phase 1)
    parameters:
      - name: limit
        in: query
        type: integer
        default: 20
      - name: offset
        in: query
        type: integer
        default: 0
    response:
      200: { teams: [...], total_count, limit, offset, has_more }
      401: { error: UNAUTHORIZED }
      429: { error: RATE_LIMITED, Retry-After: seconds }

/api/v1/webhooks/github:
  post:
    description: Receive GitHub webhook events
    headers:
      X-Hub-Signature-256: required (HMAC-SHA256)
    request:
      content: raw GitHub webhook payload
    response:
      202: { status: "accepted", event_id: "uuid" }
      400: { error: INVALID_PAYLOAD }
      401: { error: INVALID_SIGNATURE }
      503: { error: SERVICE_UNAVAILABLE, Retry-After: seconds }

/api/v1/webhooks/jira:
  post:
    description: Receive Jira webhook events
    headers:
      Atlassian-Connect-Token: required
    request:
      content: raw Jira webhook payload
    response:
      202: { status: "accepted", event_id: "uuid" }
      400: { error: INVALID_PAYLOAD }
      401: { error: INVALID_TOKEN }
      503: { error: SERVICE_UNAVAILABLE, Retry-After: seconds }
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
      - name: limit
        in: query
        type: integer
        default: 20
      - name: offset
        in: query
        type: integer
        default: 0
    response:
      200: { team_id, metrics: [...], total_count, limit, offset, has_more }
      404: { error: TEAM_NOT_FOUND }

/api/v1/pull-requests/{prId}:
  get:
    description: Retrieve full pull request details including AI metadata and confidence scores
    parameters:
      - name: prId
        in: path
        required: true
        type: string
    response:
      200: { pull_request: { id, title, author_id, ai_assisted, ai_usage_types, risk_score, ... } }
      404: { error: PR_NOT_FOUND }

/api/v1/pull-requests/{prId}/risk:
  get:
    description: Retrieve detailed risk analysis for a specific PR
    parameters:
      - name: prId
        in: path
        required: true
        type: string
    response:
      200: { pull_request_id, ai_contextual_risk_score, signals, required_reviewers }
      404: { error: PR_NOT_FOUND }

/api/v1/teams/{teamId}/recommendations:
  get:
    description: List active recommendations for a team (read-only)
    parameters:
      - name: teamId
        in: path
        required: true
        type: string
      - name: limit
        in: query
        type: integer
        default: 20
      - name: offset
        in: query
        type: integer
        default: 0
    response:
      200: { team_id, recommendations: [...], total_count, limit, offset, has_more }
      404: { error: TEAM_NOT_FOUND }
```

## Phase 3 — soft landing, experiment and PR comments

```yaml
/api/v1/experiments/assign:
  post:
    description: Assign a PR to treatment or control group for A/B testing
    request:
      body: { pr_id, team_id }
    response:
      200: { pr_id, group: "treatment" | "control", model_version }
    notes: "Deterministic assignment based on PR number hash."

/api/v1/experiments/{experimentId}/results:
  get:
    description: Get A/B experiment results (review time, rework, defects per group)
    parameters:
      - name: experimentId
        in: path
        required: true
    response:
      200: { experiment_id, treatment_n, control_n, primary_metric: { control, treatment, p_value, significant } }

/api/v1/teams/{teamId}/pr-comments:
  get:
    description: List PR comments posted by the bot for a team
    parameters:
      - name: teamId
        in: path
        required: true
    response:
      200: { comments: [...] }

/api/v1/human-validation-calibration:
  post:
    description: Submit calibration study results (logged review time vs estimated)
    request:
      body: { pr_id, actual_hours, estimated_hours, reviewer }
    response:
      201: { calibration_id }
```

## Phase 4 — policies, overrides and recommendation management

```yaml
/api/v1/policies:
  get:
    description: List all configured policies
    response:
      200: { policies: [...] }
  post:
    description: Create or update a policy rule
    request:
      body: { policy_name, enabled, failure_mode, ... }
    response:
      201: { policy: { ... } }
      422: { error: INVALID_POLICY_CONFIG }

/api/v1/policy-overrides:
  get:
    description: List policy override events for auditing
    parameters:
      - name: limit
        in: query
        type: integer
        default: 20
      - name: offset
        in: query
        type: integer
        default: 0
    response:
      200: { overrides: [...], total_count, limit, offset, has_more }
  post:
    description: Record a new policy override (called by CI/CD when emergency-fix is used)
    request:
      body: { pull_request_id, policy_name, override_label, overridden_by, reason }
    response:
      201: { override: { ... } }
      422: { error: MISSING_REQUIRED_FIELDS }

/api/v1/teams/{teamId}/recommendations/{recId}:
  patch:
    description: Update recommendation status (e.g. resolve, dismiss)
    parameters:
      - name: teamId
        in: path
        required: true
      - name: recId
        in: path
        required: true
    request:
      body: { status: "resolved", resolution_note: "..." }
    response:
      200: { recommendation: { ... } }
      404: { error: RECOMMENDATION_NOT_FOUND }
      422: { error: INVALID_STATUS_TRANSITION }
```

## Response examples

`GET /api/v1/teams?limit=2`

```json
{
  "teams": [
    {
      "id": "payments-team",
      "name": "Payments Team",
      "ai_assisted_pr_rate": 0.32,
      "data_confidence_score": 84
    }
  ],
  "total_count": 1,
  "limit": 2,
  "offset": 0,
  "has_more": false
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
      "confidence": "medium",
      "confidence_score": 78,
      "decision_grade": false
    }
  ],
  "total_count": 1,
  "limit": 20,
  "offset": 0,
  "has_more": false
}
```

`GET /api/v1/pull-requests/{prId}`

```json
{
  "pull_request": {
    "id": "pr_123",
    "title": "Add payment validation",
    "author_id": "dev_abc123",
    "ai_assisted": true,
    "ai_usage_inferred": false,
    "ai_usage_types": ["code_suggestions", "test_generation"],
    "ai_assistance_confidence": "high",
    "changed_files": 8,
    "changed_lines": 245,
    "risk_score": 7
  }
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
      "id": "rec_456",
      "type": "split_large_ai_prs",
      "severity": "warning",
      "message": "Large AI-assisted PRs are associated with longer review waits under matched comparison.",
      "status": "open"
    }
  ],
  "total_count": 1,
  "limit": 20,
  "offset": 0,
  "has_more": false
}
```

`Error example`

```json
{
  "error": {
    "code": "TEAM_NOT_FOUND",
    "message": "No team found with id 'unknown-team'",
    "details": {}
  }
}
```
