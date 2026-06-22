"""Register a trained model in MLflow Model Registry."""

import mlflow
import os
import sys

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://localhost:5000")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")


def register_model(run_id: str):
    """Register a model from an MLflow run."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    client = mlflow.MlflowClient()

    model_uri = f"runs:/{run_id}/model"
    result = client.create_model_version(
        name=MODEL_NAME,
        source=model_uri,
        run_id=run_id,
    )

    print(f"Registered model: {MODEL_NAME} v{result.version}")
    print(f"Stage: {result.current_stage}")
    return result


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python register_model.py <run_id>")
        sys.exit(1)

    register_model(sys.argv[1])
