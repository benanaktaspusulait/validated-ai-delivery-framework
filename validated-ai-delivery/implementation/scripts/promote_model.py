"""Promote model between registry stages."""

import mlflow
import os
import sys

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://localhost:5000")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")


def promote_model(version: int, from_stage: str, to_stage: str):
    """Promote a model version to a new stage."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    client = mlflow.MlflowClient()

    # Transition the model version
    client.transition_model_version_stage(
        name=MODEL_NAME,
        version=version,
        stage=to_stage,
    )

    print(f"Promoted {MODEL_NAME} v{version}: {from_stage} -> {to_stage}")


if __name__ == "__main__":
    if len(sys.argv) < 4:
        print("Usage: python promote_model.py <version> <from_stage> <to_stage>")
        print("Stages: None, Staging, Production, Archived")
        sys.exit(1)

    promote_model(
        version=int(sys.argv[1]),
        from_stage=sys.argv[2],
        to_stage=sys.argv[3],
    )
