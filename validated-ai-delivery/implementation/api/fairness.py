"""Fairness evaluation using Fairlearn."""

import numpy as np
from fairlearn.metrics import MetricFrame, demographic_parity_difference, equalized_odds_difference
from sklearn.metrics import accuracy_score
import json
from datetime import datetime


def evaluate_fairness(
    y_true: np.ndarray,
    y_pred: np.ndarray,
    sensitive_features: np.ndarray,
    feature_names: list[str],
) -> dict:
    """Compute fairness metrics across protected groups."""

    results = {
        "timestamp": datetime.utcnow().isoformat(),
        "n_samples": len(y_true),
        "groups": {},
        "overall_metrics": {},
    }

    # Overall accuracy
    results["overall_metrics"]["accuracy"] = float(accuracy_score(y_true, y_pred))

    # Per-group metrics
    metric_frame = MetricFrame(
        metrics={"accuracy": accuracy_score},
        y_true=y_true,
        y_pred=y_pred,
        sensitive_features=sensitive_features,
    )

    for group_val in np.unique(sensitive_features):
        mask = sensitive_features == group_val
        group_acc = accuracy_score(y_true[mask], y_pred[mask])
        results["groups"][str(group_val)] = {
            "accuracy": float(group_acc),
            "count": int(mask.sum()),
        }

    # Disparity ratios
    accuracies = [g["accuracy"] for g in results["groups"].values()]
    if len(accuracies) >= 2:
        min_acc = min(accuracies)
        max_acc = max(accuracies)
        results["disparity_ratio"] = min_acc / max_acc if max_acc > 0 else 0
        results["disparity_acceptable"] = results["disparity_ratio"] > 0.8

    # Demographic parity difference
    try:
        dp_diff = demographic_parity_difference(y_true, y_pred, sensitive_features=sensitive_features)
        results["demographic_parity_difference"] = float(dp_diff)
    except Exception:
        results["demographic_parity_difference"] = None

    # Equalized odds difference
    try:
        eo_diff = equalized_odds_difference(y_true, y_pred, sensitive_features=sensitive_features)
        results["equalized_odds_difference"] = float(eo_diff)
    except Exception:
        results["equalized_odds_difference"] = None

    return results


# Example usage
if __name__ == "__main__":
    from sklearn.datasets import load_iris
    from sklearn.ensemble import RandomForestClassifier
    from sklearn.model_selection import train_test_split

    iris = load_iris()
    X_train, X_test, y_train, y_test = train_test_split(iris.data, iris.target, test_size=0.2)

    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)
    y_pred = model.predict(X_test)

    # Simulate sensitive feature (e.g. petal length as proxy)
    sensitive = (X_test[:, 2] > X_test[:, 2].median()).astype(int)

    results = evaluate_fairness(y_test, y_pred, sensitive, list(iris.feature_names))
    print(json.dumps(results, indent=2))
