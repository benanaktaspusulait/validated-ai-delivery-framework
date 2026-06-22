# Testing Strategy

Comprehensive test plan for ML systems — data tests, model tests, integration tests and operational tests.

## Test types

| Test | What it validates | When | Tools |
|---|---|---|---|
| Data validation | Schema, completeness, accuracy, distribution | Every pipeline run | Great Expectations, Pandera |
| Model unit tests | Prediction logic, edge cases | Every code change | pytest, custom |
| Model integration tests | End-to-end pipeline from data to prediction | Every deployment | pytest, Docker |
| Fairness tests | Bias across protected groups | Every model version | Fairlearn, AIF360 |
| Robustness tests | Adversarial, OOD, missing data | Before validation gate | ART, custom |
| A/B tests | Business outcome comparison | Before production | Statistical framework |
| Shadow tests | Output comparison with current model | Before production | Custom |
| Load tests | Latency and throughput under load | Before production | Locust, k6 |
| Security tests | Vulnerabilities, injection, auth | Before production | OWASP ZAP, custom |
| Regression tests | Previous capabilities still work | Every deployment | pytest |

## Data validation tests

```text
Run on every data pipeline execution:
  - Schema check: all expected columns present with correct types.
  - Completeness: no column has > X% nulls (configurable per column).
  - Range check: numeric values within expected bounds.
  - Uniqueness: primary keys are unique.
  - Freshness: data is no older than Y hours/days.
  - Distribution: no significant drift from training distribution (PSI < 0.2).

Example (Great Expectations):
  expect_column_values_to_not_be_null("customer_id")
  expect_column_values_to_be_between("age", min_value=0, max_value=120)
  expect_column_values_to_be_unique("transaction_id")
```

## Model tests

```text
Unit tests (every code change):
  - Prediction function returns expected output shape.
  - Prediction function handles edge cases (empty input, null features).
  - Feature transformation produces expected schema.
  - Model loading from registry succeeds.

Integration tests (every deployment):
  - End-to-end: raw data → features → prediction → response.
  - Model serves predictions within latency SLA.
  - Model handles concurrent requests.
  - Graceful degradation when dependencies fail.
```

## Test automation

```text
CI pipeline (implementation/.github/workflows/ci.yml):
  1. Code linting (ruff check).
  2. Unit tests (pytest implementation/tests/test_data_validation.py).
  3. Unit tests (pytest implementation/tests/test_model.py).
  4. Security scanning (Trivy).

CD pipeline (implementation/.github/workflows/cd.yml):
  1. Build Docker images.
  2. Deploy to staging.
  3. Integration tests (pytest implementation/tests/test_api.py).
  4. Load tests (implementation/tests/load_test.py).
  5. Promote to production (manual approval).
```

## Concrete test files

| Test file | What it tests |
|---|---|
| `implementation/tests/test_data_validation.py` | Schema, nulls, ranges, balance, leakage (7 tests) |
| `implementation/tests/test_model.py` | Prediction shape, type, edge cases, determinism, batch (6 tests) |
| `implementation/tests/test_api.py` | Health, predict, invalid input, response format (5 tests) |
| `implementation/tests/load_test.py` | Locust load test with weighted tasks |
