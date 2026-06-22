# Data

Stage 2: acquire, validate, version and prepare data for model development.

## Purpose

```text
Ensure data is reliable, representative, versioned and ethically sourced before model training begins.
```

## Activities

```text
1. Data sourcing
   - Identify all data sources (databases, APIs, files, streams).
   - Document data lineage: source → transformation → storage.
   - Confirm legal basis for data use (GDPR, consent, legitimate interest).

2. Data quality
   - Validate completeness: no critical fields missing.
   - Validate accuracy: values within expected ranges.
   - Validate consistency: no conflicting records.
   - Validate timeliness: data is recent enough for the use case.
   - Use Great Expectations or equivalent for automated validation.

3. Data versioning
   - Every dataset used for training must be versioned (DVC, Delta Lake, or similar).
   - Version metadata: source, timestamp, row count, schema version, quality score.
   - Never modify a versioned dataset; create a new version instead.

4. Labelling strategy
   - Define labelling protocol (who labels, how disagreements are resolved).
   - Measure inter-annotator agreement (Cohen's kappa, Fleiss' kappa).
   - Store label provenance: who labelled what, when, with what instructions.

5. Data splitting
   - Train / validation / test split strategy (random, temporal, stratified).
   - Ensure no data leakage between splits.
   - Document split ratios and rationale.

6. Privacy and anonymisation
   - Pseudonymise personal identifiers.
   - Apply k-anonymity or differential privacy where required.
   - Document what data is retained and for how long.
```

## Gate criteria (before proceeding to Model)

```text
- [ ] Data quality report completed (use templates/data_report_template.md).
- [ ] All critical quality checks pass.
- [ ] Dataset versioned with metadata.
- [ ] Labelling protocol documented and IAA measured.
- [ ] Data splits defined and leakage checked.
- [ ] Privacy review completed.
- [ ] Feature engineering decisions documented.
```

## Implementation reference

```text
Concrete implementations for this stage:
  - Data validation tests: implementation/tests/test_data_validation.py
  - Data versioning: implementation/dvc-config.md (DVC setup)
  - Great Expectations: pip install great_expectations; see test_data_validation.py for patterns
```

## Deliverables

```text
- Completed data_report_template.md
- Versioned dataset with metadata
- Data quality dashboard
```

## References

```text
principles.md - reproducibility, fairness by design
feature-store.md - feature metadata management
auditability.md - data lineage tracking
```
