"""Automatic model card generation from MLflow metadata."""

import mlflow
import json
import os
from datetime import datetime

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")


def generate_model_card(model_name: str, version: int = None) -> str:
    """Generate a model card from MLflow registry metadata."""

    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    client = mlflow.MlflowClient()

    # Get model version
    if version:
        mv = client.get_model_version(model_name, version)
    else:
        versions = client.search_model_versions(f"name='{model_name}'")
        mv = versions[-1] if versions else None

    if not mv:
        return f"# Model Card: {model_name}\n\nNo versions found."

    run = client.get_run(mv.run_id)

    # Extract metadata
    params = run.data.params
    metrics = run.data.metrics
    tags = run.data.tags

    card = f"""# Model Card: {model_name}

## Model Overview

| Property | Value |
|---|---|
| Model name | {model_name} |
| Version | {mv.version} |
| Stage | {mv.current_stage} |
| Created | {datetime.fromtimestamp(mv.creation_timestamp / 1000).isoformat()} |
| Last updated | {datetime.fromtimestamp(mv.last_updated_timestamp / 1000).isoformat()} |
| Run ID | {mv.run_id} |

## Training Details

| Property | Value |
|---|---|
"""
    for key, value in params.items():
        card += f"| {key} | {value} |\n"

    card += """
## Evaluation Results

| Metric | Value |
|---|---|
"""
    for key, value in metrics.items():
        card += f"| {key} | {value:.4f} |\n"

    card += f"""
## Intended Use

- Primary use case: Classification task
- Intended users: ML engineers, data scientists
- Out-of-scope uses: High-stakes decisions without human oversight

## Limitations

- Model trained on specific dataset; may not generalise to out-of-distribution data
- Fairness evaluation required before production deployment
- See validation report for detailed analysis

## Fairness

_To be completed after fairness evaluation (see api/fairness.py)_

## Explainability

_Method: SHAP/LIME (see api/explainability.py)_

## Approval

| Role | Name | Date | Decision |
|---|---|---|---|
| ML lead | | | |
| Compliance | | | |

_Generated automatically on {datetime.utcnow().isoformat()}_
"""

    return card


def save_model_card(model_name: str, output_path: str, version: int = None):
    """Generate and save model card to file."""
    card = generate_model_card(model_name, version)
    with open(output_path, "w") as f:
        f.write(card)
    print(f"Model card saved to {output_path}")


if __name__ == "__main__":
    import sys
    model_name = sys.argv[1] if len(sys.argv) > 1 else "default-model"
    output = sys.argv[2] if len(sys.argv) > 2 else "model_card.md"
    save_model_card(model_name, output)
