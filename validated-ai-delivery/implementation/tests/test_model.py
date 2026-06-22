"""Model behaviour tests."""

import pytest
import mlflow
import numpy as np
import os


MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://localhost:5000")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")


@pytest.fixture
def model():
    """Load model from MLflow registry."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    client = mlflow.MlflowClient()
    versions = client.search_model_versions(f"name='{MODEL_NAME}'")
    if not versions:
        pytest.skip("No model versions found")

    model_uri = f"models:/{MODEL_NAME}/Production"
    try:
        return mlflow.pyfunc.load_model(model_uri)
    except Exception:
        # Fallback to latest version
        model_uri = f"models:/{MODEL_NAME}/{versions[-1].version}"
        return mlflow.pyfunc.load_model(model_uri)


class TestModelBehaviour:
    """Validate model predictions and behaviour."""

    def test_prediction_shape(self, model):
        """Model returns correct output shape."""
        input_data = np.array([[5.1, 3.5, 1.4, 0.2]])
        prediction = model.predict(input_data)
        assert prediction.shape == (1,)

    def test_prediction_type(self, model):
        """Model returns integer predictions."""
        input_data = np.array([[5.1, 3.5, 1.4, 0.2]])
        prediction = model.predict(input_data)
        assert prediction.dtype in [np.int64, np.int32, int]

    def test_valid_class_range(self, model):
        """Predictions are within valid class range."""
        input_data = np.array([[5.1, 3.5, 1.4, 0.2]])
        prediction = model.predict(input_data)
        assert 0 <= prediction[0] <= 2

    def test_handles_edge_cases(self, model):
        """Model handles extreme but valid inputs."""
        edge_cases = [
            np.array([[0.1, 0.1, 0.1, 0.1]]),  # minimum
            np.array([[8.0, 5.0, 8.0, 3.0]]),  # maximum
            np.array([[5.0, 3.5, 1.5, 0.5]]),  # average
        ]
        for case in edge_cases:
            prediction = model.predict(case)
            assert 0 <= prediction[0] <= 2

    def test_deterministic_predictions(self, model):
        """Same input produces same output."""
        input_data = np.array([[5.1, 3.5, 1.4, 0.2]])
        pred1 = model.predict(input_data)
        pred2 = model.predict(input_data)
        assert np.array_equal(pred1, pred2)

    def test_batch_prediction(self, model):
        """Model handles batch inputs."""
        input_data = np.array([
            [5.1, 3.5, 1.4, 0.2],
            [6.2, 2.9, 4.3, 1.3],
            [7.7, 3.0, 6.1, 2.3],
        ])
        predictions = model.predict(input_data)
        assert predictions.shape == (3,)
        assert all(0 <= p <= 2 for p in predictions)
