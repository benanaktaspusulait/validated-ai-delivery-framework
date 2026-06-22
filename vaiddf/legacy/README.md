# Legacy Python Code

**DEPRECATED** — This directory contains the original Python implementation that has been replaced by Java/Quarkus.

Do not add new features here. Use the Java implementation in `../api/`, `../core/`, and `../extensions/`.

## Files

- `drift/` — Original Evidently AI drift monitor (replaced by `PSIDetector` in Java)
- `mlflow/` — Original MLflow training script (replaced by Java MLflow client)
- `streamlit/` — Original Streamlit demo UI (replaced by Quarkus + Qute)

## Migration Status

| Component | Python | Java |
|-----------|--------|------|
| Drift Detection | Evidently AI | PSI (Apache Commons Math) |
| Training | MLflow Python | MLflow Java Client |
| UI | Streamlit | Quarkus + Qute |
| API | FastAPI | Quarkus REST |
