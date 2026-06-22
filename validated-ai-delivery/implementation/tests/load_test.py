"""Load testing for the model API using Locust."""

from locust import HttpUser, task, between
import random
import json


class MLModelUser(HttpUser):
    """Simulate users calling the model API."""

    wait_time = between(0.1, 0.5)

    # Iris feature ranges
    FEATURE_RANGES = [
        (4.0, 8.0),   # sepal length
        (2.0, 4.5),   # sepal width
        (1.0, 7.0),   # petal length
        (0.1, 2.5),   # petal width
    ]

    def on_start(self):
        """Check API health on start."""
        response = self.client.get("/health")
        if response.status_code != 200:
            print(f"API health check failed: {response.status_code}")

    @task(3)
    def predict(self):
        """Send prediction requests."""
        features = [
            random.uniform(low, high)
            for low, high in self.FEATURE_RANGES
        ]
        self.client.post(
            "/predict",
            json={"features": features},
            name="/predict",
        )

    @task(1)
    def health_check(self):
        """Check API health."""
        self.client.get("/health", name="/health")

    @task(1)
    def metrics(self):
        """Check metrics endpoint."""
        self.client.get("/metrics", name="/metrics")
