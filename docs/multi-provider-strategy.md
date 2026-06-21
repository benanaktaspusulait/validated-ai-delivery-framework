# Multi-Provider Integration Strategy

How the platform extends beyond GitHub to GitLab, Azure DevOps and Bitbucket without changing the metrics, risk or policy layers. The MVP ships GitHub + Jira (see `architecture.md`); this document is the design that keeps later providers a configuration-and-adapter exercise rather than a rewrite.

## Principle

```text
One normalized data model. Many provider adapters.
Adapters translate provider events into the platform schema (docs/data-model.md).
Nothing above the collector layer knows which provider a PR came from.
```

## SourceConnector capability specification

Every provider adapter must satisfy the same capability contract. Expressed conceptually (no code):

| Capability | Purpose | Inputs | Expected result |
|---|---|---|---|
| Authenticate | Establish a trusted session with the provider | Credential (PAT or OAuth token), org/project scope | Verified, scoped session or a clear auth error |
| Subscribe webhooks | Register for the events the platform needs | Repository/project identifiers, callback endpoint, secret | Active subscriptions for PR, review and issue events |
| Fetch backfill | Retrieve recent history at onboarding | Lookback window (default 30 days), scope | A stream of historical events in provider format |
| Parse payload | Convert a raw event into normalized events | Raw event body, event type | Zero or more normalized events for the platform schema |
| Verify signature | Confirm a webhook is genuine | Raw body, provider signature, shared secret | Accept or reject |
| Report health | Expose connector liveness and rate-limit headroom | None | Status, remaining quota, last successful sync |

```text
Reliability requirements (rate limits, pagination, idempotency, dead-letter, reconciliation) are identical across providers and are defined once in docs/architecture.md.
```

## Webhook event mapping

The platform consumes a small set of normalized event types. Each provider names the same concept differently.

| Platform event | GitHub | GitLab | Azure DevOps | Bitbucket |
|---|---|---|---|---|
| pr.opened / updated | pull_request (opened, synchronize) | merge_request (open, update) | git.pullrequest.created / updated | pullrequest:created / :updated |
| pr.merged | pull_request (closed + merged) | merge_request (merge) | git.pullrequest.merged | pullrequest:fulfilled |
| pr.review | pull_request_review | merge_request note / approval | git.pullrequest.reviewer.vote | pullrequest:approved / changes_requested |
| pr.comment | pull_request_review_comment / issue_comment | note (on merge_request) | pullrequest comment thread | pullrequest:comment_created |
| issue.created / updated | (via Jira) | issue events | workitem.created / updated | issue events |
| label/metadata change | labeled / edited | merge_request (label update) | workitem tag update | pullrequest update |

```text
The PR template and AI metadata mechanism (examples/pr-template.md) work on any provider that supports a PR/MR description body.
Where a provider lacks a native concept (for example, draft state semantics differ), the adapter maps to the closest equivalent and records the approximation as a confidence note.
```

## Normalization rules

```text
Author and reviewer identities are mapped to a pseudonymised platform identifier (see governance-and-privacy.md).
Timestamps are normalized to UTC and to platform field names (created_at_source, merged_at_source, first_review_at, approved_at).
Size signals (changed_files, changed_lines) are read from the provider's diff summary; if absent, the adapter computes them from the diff metadata, not the file contents.
Severity and issue-type vocabularies are mapped to the platform's defect-severity weights (metrics-catalogue.md).
Any field a provider cannot supply is left null and lowers that metric's Data Confidence Score (data-confidence.md) rather than being guessed.
```

## Authentication comparison

| Provider | Recommended method | Token scope to request | Notes |
|---|---|---|---|
| GitHub | GitHub App | Read PRs, reviews, contents metadata, webhooks | Fine-grained, org-installable, best audit trail |
| GitLab | OAuth app or group access token | api (read), read_repository, webhooks | Group token simplifies multi-project setup |
| Azure DevOps | OAuth or PAT | Code (read), Work items (read), Service hooks | PAT is simplest for pilots; rotate regularly |
| Bitbucket | OAuth consumer or repository access token | Pull requests (read), webhooks | App passwords are deprecated; prefer access tokens |

```text
Least-privilege is mandatory: request read scopes plus webhook management only.
Credentials are stored in the platform secret store, never in configuration files.
Each provider's onboarding follows the same admin flow described in quick-start.md.
```

## Phasing for a new provider

```text
1. Implement and verify the adapter capability contract against a sandbox org.
2. Run the standard 30-day backfill and nightly reconciliation; confirm gap < 5%.
3. Onboard one pilot team on the new provider in Observation Mode.
4. Validate metrics against a 50-PR manual sample, exactly as in Phase 2.
5. Only then allow Warning and Enforcement modes for that provider.
```
