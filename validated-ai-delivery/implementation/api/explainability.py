"""SHAP and LIME explainability integration."""

import shap
import numpy as np
import mlflow
import os
import json
from datetime import datetime

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")


def explain_with_shap(model, X_train: np.ndarray, X_explain: np.ndarray, feature_names: list) -> dict:
    """Generate SHAP explanations for predictions."""
    explainer = shap.TreeExplainer(model)
    shap_values = explainer.shap_values(X_explain)

    explanations = []
    for i, (instance, values) in enumerate(zip(X_explain, shap_values)):
        # For binary/multiclass, shap_values may be a list of arrays
        if isinstance(values, list):
            values = values[1]  # Use positive class

        feature_importance = sorted(
            zip(feature_names, values, instance),
            key=lambda x: abs(x[1]),
            reverse=True,
        )

        explanations.append({
            "instance_index": i,
            "prediction": int(model.predict(instance.reshape(1, -1))[0]),
            "top_features": [
                {"name": name, "shap_value": float(shap_val), "feature_value": float(feat_val)}
                for name, shap_val, feat_val in feature_importance[:5]
            ],
            "base_value": float(explainer.expected_value[1] if isinstance(explainer.expected_value, list) else explainer.expected_value),
        })

    return {"method": "shap", "explanations": explanations, "timestamp": datetime.utcnow().isoformat()}


def explain_with_lime(model, X_train: np.ndarray, X_explain: np.ndarray, feature_names: list) -> dict:
    """Generate LIME explanations for predictions."""
    from lime.lime_tabular import LimeTabularExplainer

    explainer = LimeTabularExplainer(
        X_train,
        feature_names=feature_names,
        class_names=["class_0", "class_1", "class_2"],
        mode="classification",
    )

    explanations = []
    for i, instance in enumerate(X_explain):
        exp = explainer.explain_instance(
            instance,
            model.predict_proba,
            num_features=len(feature_names),
        )

        explanations.append({
            "instance_index": i,
            "prediction": int(model.predict(instance.reshape(1, -1))[0]),
            "feature_weights": [
                {"feature": feat, "weight": float(weight)}
                for feat, weight in exp.as_list()
            ],
        })

    return {"method": "lime", "explanations": explanations, "timestamp": datetime.utcnow().isoformat()}


def log_explanations(explanations: dict, run_name: str = "explainability"):
    """Log explanations to MLflow."""
    mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)
    with mlflow.start_run(run_name=run_name):
        mlflow.log_dict(explanations, "explanations.json")
        mlflow.set_tag("explainability_method", explanations["method"])


# Example usage
if __name__ == "__main__":
    from sklearn.datasets import load_iris
    from sklearn.ensemble import RandomForestClassifier
    from sklearn.model_selection import train_test_split

    iris = load_iris()
    X_train, X_test, y_train, y_test = train_test_split(iris.data, iris.target, test_size=0.2)

    model = RandomForestClassifier(n_estimators=100, random_state=42)
    model.fit(X_train, y_train)

    # Explain first 3 test instances
    explanations = explain_with_shap(model, X_train, X_test[:3], list(iris.feature_names))
    print(json.dumps(explanations, indent=2))
