"""API integration tests."""

import pytest
import requests
import os

API_URL = os.getenv("API_URL", "http://localhost:8000")


class TestAPIIntegration:
    """Test the model serving API."""

    def test_health_endpoint(self):
        """Health endpoint returns 200."""
        response = requests.get(f"{API_URL}/health")
        assert response.status_code == 200
        data = response.json()
        assert "status" in data
        assert "model_loaded" in data

    def test_predict_valid_input(self):
        """Prediction with valid input returns 200."""
        response = requests.post(
            f"{API_URL}/predict",
            json={"features": [5.1, 3.5, 1.4, 0.2]},
        )
        assert response.status_code == 200
        data = response.json()
        assert "prediction" in data
        assert "probability" in data
        assert "model_version" in data

    def test_predict_invalid_input(self):
        """Prediction with invalid input returns error."""
        response = requests.post(
            f"{API_URL}/predict",
            json={"features": [1, 2]},  # wrong number of features
        )
        assert response.status_code in [400, 422, 500]

    def test_predict_response_format(self):
        """Prediction response has correct format."""
        response = requests.post(
            f"{API_URL}/predict",
            json={"features": [5.1, 3.5, 1.4, 0.2]},
        )
        data = response.json()
        assert isinstance(data["prediction"], int)
        assert isinstance(data["probability"], list)
        assert isinstance(data["model_version"], str)
        assert isinstance(data["timestamp"], str)

    def test_metrics_endpoint(self):
        """Metrics endpoint returns model info."""
        response = requests.get(f"{API_URL}/metrics")
        assert response.status_code == 200
        data = response.json()
        assert "model_name" in data
        assert "model_version" in data
