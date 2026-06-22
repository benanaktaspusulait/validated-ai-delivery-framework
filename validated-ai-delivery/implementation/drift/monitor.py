"""Drift monitoring with Evidently AI."""

import mlflow
import pandas as pd
import numpy as np
from evidently.report import Report
from evidently.metric_preset import DataDriftPreset, TargetDriftPreset
from evidently.test_preset import DataDriftTestPreset
import os
import time
import logging
from datetime import datetime

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")
CHECK_INTERVAL_HOURS = int(os.getenv("CHECK_INTERVAL_HOURS", "24"))

# Thresholds
PSI_SIGNIFICANT = 0.2
PSI_CRITICAL = 0.4


def load_reference_data():
    """Load reference (training) data from MLflow."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    client = mlflow.MlflowClient()

    try:
        versions = client.search_model_versions(f"name='{MODEL_NAME}'")
        if not versions:
            logger.warning("No model versions found")
            return None

        # Get latest production version
        prod_version = None
        for v in versions:
            if v.current_stage == "Production":
                prod_version = v
                break
        if not prod_version:
            prod_version = versions[-1]

        # Load reference data from run artifacts
        run = client.get_run(prod_version.run_id)
        artifact_uri = run.info.artifact_uri
        # In a real setup, load the actual training data
        # For demo, generate synthetic reference data
        from sklearn.datasets import load_iris
        iris = load_iris()
        df = pd.DataFrame(iris.data, columns=iris.feature_names)
        df["target"] = iris.target
        return df

    except Exception as e:
        logger.error(f"Failed to load reference data: {e}")
        return None


def generate_current_data():
    """Simulate current production data (in real setup, query from feature store or API logs)."""
    from sklearn.datasets import load_iris
    iris = load_iris()
    # Add some noise to simulate drift
    np.random.seed(int(time.time()) % 100)
    data = iris.data + np.random.normal(0, 0.1, iris.data.shape)
    df = pd.DataFrame(data, columns=iris.feature_names)
    df["target"] = iris.target
    return df


def run_drift_check():
    """Run drift detection and log results."""
    logger.info("Starting drift check...")

    reference_data = load_reference_data()
    if reference_data is None:
        logger.warning("No reference data available, skipping drift check")
        return

    current_data = generate_current_data()

    # Run drift report
    report = Report(metrics=[DataDriftPreset()])
    report.run(reference_data=reference_data, current_data=current_data)
    report.save_html("drift_report.html")

    # Extract results
    result = report.as_dict()
    drift_detected = result["metrics"][0]["result"]["dataset_drift"]

    # Log to MLflow
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    with mlflow.start_run(run_name=f"drift-check-{datetime.now().strftime('%Y%m%d-%H%M')}"):
        mlflow.log_metric("drift_detected", int(drift_detected))

        # Log per-feature drift
        for i, metric in enumerate(result["metrics"][0]["result"]["drift_by_columns"]):
            mlflow.log_metric(f"drift_{metric['column_name']}", int(metric["drift_detected"]))

        mlflow.log_artifact("drift_report.html")

    if drift_detected:
        logger.warning("DRIFT DETECTED! Review drift report.")
    else:
        logger.info("No drift detected.")

    return drift_detected


if __name__ == "__main__":
    logger.info(f"Drift monitor started. Checking every {CHECK_INTERVAL_HOURS} hours.")

    while True:
        try:
            run_drift_check()
        except Exception as e:
            logger.error(f"Drift check failed: {e}")

        time.sleep(CHECK_INTERVAL_HOURS * 3600)
