"""Carbon footprint tracking with CodeCarbon."""

try:
    from codecarbon import EmissionsTracker
    CODECARBON_AVAILABLE = True
except ImportError:
    CODECARBON_AVAILABLE = False

import mlflow
import os
import json
from datetime import datetime


def start_tracking(experiment_name: str = "carbon-tracked"):
    """Start carbon emissions tracking."""
    if not CODECARBON_AVAILABLE:
        print("CodeCarbon not installed. Install with: pip install codecarbon")
        return None

    tracker = EmissionsTracker(
        output_dir="./emissions",
        output_file=f"emissions_{datetime.now().strftime('%Y%m%d_%H%M%S')}.csv",
        project_name=experiment_name,
    )
    tracker.start()
    return tracker


def stop_tracking(tracker, mlflow_run: bool = True):
    """Stop tracking and log emissions."""
    if tracker is None:
        return None

    emissions = tracker.stop()

    result = {
        "co2_kg": emissions,
        "timestamp": datetime.utcnow().isoformat(),
    }

    if mlflow_run:
        mlflow.set_tag("carbon_emissions_kg", str(emissions))

    print(f"Carbon emissions: {emissions:.6f} kg CO2")
    return result


class CarbonTracker:
    """Context manager for carbon tracking."""

    def __init__(self, experiment_name: str = "ml-training"):
        self.experiment_name = experiment_name
        self.tracker = None
        self.result = None

    def __enter__(self):
        self.tracker = start_tracking(self.experiment_name)
        return self

    def __exit__(self, *args):
        self.result = stop_tracking(self.tracker, mlflow_run=False)
