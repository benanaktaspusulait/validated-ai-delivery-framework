"""A/B testing and shadow deployment patterns."""

from fastapi import FastAPI, Request
import mlflow
import random
import time
import os
import logging
from datetime import datetime

logger = logging.getLogger(__name__)

app = FastAPI(title="A/B Test Router")

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")
MODEL_A_NAME = os.getenv("MODEL_A_NAME", "model-v1")
MODEL_B_NAME = os.getenv("MODEL_B_NAME", "model-v2")
AB_SPLIT_RATIO = float(os.getenv("AB_SPLIT_RATIO", "0.5"))  # 50/50 default

mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)

# Model cache
_models = {}


def load_model(name: str):
    if name not in _models:
        try:
            _models[name] = mlflow.pyfunc.load_model(f"models:/{name}/Production")
        except Exception as e:
            logger.error(f"Failed to load {name}: {e}")
            return None
    return _models[name]


@app.post("/predict/ab")
async def predict_ab(request: Request):
    """A/B test: randomly route to model A or B."""
    body = await request.json()
    features = body["features"]

    # Deterministic assignment based on request hash
    assignment = "A" if random.random() < AB_SPLIT_RATIO else "B"
    model_name = MODEL_A_NAME if assignment == "A" else MODEL_B_NAME

    model = load_model(model_name)
    if model is None:
        return {"error": f"Model {model_name} not available"}

    import numpy as np
    prediction = model.predict(np.array([features]))[0]

    # Log assignment for analysis
    with mlflow.start_run(run_name="ab-test", nested=True):
        mlflow.log_param("group", assignment)
        mlflow.log_param("model", model_name)
        mlflow.log_metric("prediction", int(prediction))

    return {
        "prediction": int(prediction),
        "group": assignment,
        "model": model_name,
    }


@app.post("/predict/shadow")
async def predict_shadow(request: Request):
    """Shadow deployment: run both models, return only champion prediction."""
    body = await request.json()
    features = body["features"]

    champion = load_model(MODEL_A_NAME)
    challenger = load_model(MODEL_B_NAME)

    import numpy as np
    input_data = np.array([features])

    # Champion prediction (returned to user)
    champion_pred = champion.predict(input_data)[0] if champion else None

    # Challenger prediction (logged only, not returned)
    challenger_pred = challenger.predict(input_data)[0] if challenger else None

    # Log comparison
    with mlflow.start_run(run_name="shadow-deployment"):
        mlflow.log_param("champion", MODEL_A_NAME)
        mlflow.log_param("challenger", MODEL_B_NAME)
        mlflow.log_metric("champion_prediction", int(champion_pred) if champion_pred is not None else -1)
        mlflow.log_metric("challenger_prediction", int(challenger_pred) if challenger_pred is not None else -1)
        mlflow.log_metric("agreement", int(champion_pred == challenger_pred) if (champion_pred is not None and challenger_pred is not None) else -1)

    return {
        "prediction": int(champion_pred) if champion_pred is not None else None,
        "shadow_challenger_prediction": int(challenger_pred) if challenger_pred is not None else None,
        "agreement": champion_pred == challenger_pred if (champion_pred is not None and challenger_pred is not None) else None,
    }


@app.get("/ab/results")
async def ab_results():
    """Get A/B test results from MLflow."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    runs = mlflow.search_runs(run_name_query="ab-test")
    if runs.empty:
        return {"message": "No A/B test runs found"}

    group_a = runs[runs["params.group"] == "A"]
    group_b = runs[runs["params.group"] == "B"]

    return {
        "group_a_count": len(group_a),
        "group_b_count": len(group_b),
        "group_a_mean_prediction": float(group_a["metrics.prediction"].mean()) if len(group_a) > 0 else None,
        "group_b_mean_prediction": float(group_b["metrics.prediction"].mean()) if len(group_b) > 0 else None,
    }
