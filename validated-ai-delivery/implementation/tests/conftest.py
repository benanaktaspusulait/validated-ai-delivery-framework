"""Shared test fixtures."""

import pytest
import os

# Set default test environment
os.environ.setdefault("MLFLOW_TRACKING_URI", "http://localhost:5000")
os.environ.setdefault("MODEL_NAME", "default-model")
os.environ.setdefault("API_URL", "http://localhost:8000")
