"""FastAPI model serving with model registry integration."""

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import mlflow
import mlflow.pyfunc
import os
import logging
from datetime import datetime

app = FastAPI(title="ML Model API", version="1.0.0")
logger = logging.getLogger(__name__)

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")
MODEL_STAGE = os.getenv("MODEL_STAGE", "Production")

mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)


class PredictionRequest(BaseModel):
    features: list[float]


class PredictionResponse(BaseModel):
    prediction: int
    probability: list[float]
    model_version: str
    timestamp: str


class HealthResponse(BaseModel):
    status: str
    model_loaded: bool
    model_version: str | None


# Global model cache
_model = None
_model_version = None


def load_model():
    """Load model from MLflow Model Registry."""
    global _model, _model_version
    try:
        model_uri = f"models:/{MODEL_NAME}/{MODEL_STAGE}"
        _model = mlflow.pyfunc.load_model(model_uri)
        # Get version info
        client = mlflow.MlflowClient()
        versions = client.search_model_versions(f"name='{MODEL_NAME}'")
        for v in versions:
            if v.current_stage == MODEL_STAGE:
                _model_version = v.version
                break
        logger.info(f"Loaded model {MODEL_NAME} v{_model_version}")
    except Exception as e:
        logger.error(f"Failed to load model: {e}")
        _model = None
        _model_version = None


@app.on_event("startup")
async def startup():
    load_model()


@app.get("/health", response_model=HealthResponse)
async def health():
    return HealthResponse(
        status="healthy" if _model else "degraded",
        model_loaded=_model is not None,
        model_version=_model_version,
    )


@app.post("/predict", response_model=PredictionResponse)
async def predict(request: PredictionRequest):
    if _model is None:
        raise HTTPException(status_code=503, detail="Model not loaded")

    try:
        import numpy as np
        input_data = np.array([request.features])
        prediction = _model.predict(input_data)
        probabilities = _model.predict_proba(input_data).tolist()

        return PredictionResponse(
            prediction=int(prediction[0]),
            probability=probabilities[0],
            model_version=_model_version or "unknown",
            timestamp=datetime.utcnow().isoformat(),
        )
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/reload")
async def reload_model():
    """Force model reload from registry."""
    load_model()
    return {"status": "reloaded", "model_version": _model_version}


@app.get("/metrics")
async def metrics():
    """Return model metadata for monitoring."""
    return {
        "model_name": MODEL_NAME,
        "model_version": _model_version,
        "model_stage": MODEL_STAGE,
        "loaded": _model is not None,
    }
