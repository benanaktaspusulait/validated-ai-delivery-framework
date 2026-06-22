# Feature Store

Managing features for reproducibility, reuse and consistency between training and serving.

## Why a feature store

```text
Problems without a feature store:
  - Training-serving skew: features computed differently in training vs. production.
  - Duplicate work: same feature engineering repeated across teams.
  - Inconsistency: different teams compute "customer age" differently.
  - Latency: complex features computed on-the-fly in production.

A feature store solves these by providing:
  - Centralised feature definitions.
  - Consistent computation across training and serving.
  - Feature versioning and metadata.
  - Online and offline serving.
```

## Tool options

| Tool | Type | Best for |
|---|---|---|
| Feast | Open-source | Self-hosted, simple, Kubernetes-native |
| Tecton | SaaS | Enterprise, real-time features |
| Hopsworks | Open-source + SaaS | Full platform, feature store + ML |
| Vertex AI Feature Store | Managed | GCP-native |
| SageMaker Feature Store | Managed | AWS-native |

## Feature metadata

```text
Every feature must have:
  - Name and description.
  - Data type and range.
  - Owner (team or individual).
  - Source (which pipeline produces it).
  - Version.
  - Freshness (how recently updated).
  - Statistics (mean, stddev, nulls, distribution).
  - Used by (which models consume it).
```

## Feature definitions

```yaml
features:
  - name: customer_age
    entity: customer
    dtype: int
    description: "Customer age in years"
    owner: data-team
    source: customer_pipeline
    freshness: 24h
    stats:
      min: 0
      max: 120
      mean: 35.2
      null_rate: 0.01

  - name: transaction_count_30d
    entity: customer
    dtype: int
    description: "Number of transactions in last 30 days"
    owner: data-team
    source: transaction_pipeline
    freshness: 1h
```

## Online vs. offline

```text
Offline store (for training):
  - Historical feature values.
  - Stored in data lake or warehouse (S3, BigQuery).
  - Used for batch training and backfilling.

Online store (for serving):
  - Current feature values.
  - Stored in low-latency database (DynamoDB, Redis).
  - Used for real-time inference.
  - Must be updated within the freshness SLA.
```
