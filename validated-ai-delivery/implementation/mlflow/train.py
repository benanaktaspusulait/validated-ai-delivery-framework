"""Example training script with MLflow tracking."""

import mlflow
import mlflow.sklearn
from sklearn.datasets import load_iris
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score
import json
import os

# Configuration
EXPERIMENT_NAME = os.getenv("MLFLOW_EXPERIMENT_NAME", "iris-classification")
MODEL_NAME = os.getenv("MODEL_NAME", "default-model")
RANDOM_STATE = 42


def train_and_log(n_estimators: int = 100, max_depth: int = 5, min_samples_split: int = 2):
    """Train a model and log everything to MLflow."""

    mlflow.set_experiment(EXPERIMENT_NAME)

    with mlflow.start_run(run_name=f"rf-n{n_estimators}-d{max_depth}"):
        # 1. Load data
        iris = load_iris()
        X_train, X_test, y_train, y_test = train_test_split(
            iris.data, iris.target, test_size=0.2, random_state=RANDOM_STATE
        )

        # 2. Log parameters
        params = {
            "n_estimators": n_estimators,
            "max_depth": max_depth,
            "min_samples_split": min_samples_split,
            "random_state": RANDOM_STATE,
            "n_features": X_train.shape[1],
            "train_size": len(X_train),
            "test_size": len(X_test),
        }
        mlflow.log_params(params)

        # 3. Log dataset info
        mlflow.log_param("dataset", "iris")
        mlflow.log_param("dataset_version", "1.0")

        # 4. Train model
        model = RandomForestClassifier(
            n_estimators=n_estimators,
            max_depth=max_depth,
            min_samples_split=min_samples_split,
            random_state=RANDOM_STATE,
        )
        model.fit(X_train, y_train)

        # 5. Evaluate
        y_pred = model.predict(X_test)
        metrics = {
            "accuracy": accuracy_score(y_test, y_pred),
            "precision": precision_score(y_test, y_pred, average="weighted"),
            "recall": recall_score(y_test, y_pred, average="weighted"),
            "f1": f1_score(y_test, y_pred, average="weighted"),
        }

        # 6. Log metrics
        mlflow.log_metrics(metrics)

        # 7. Log model
        mlflow.sklearn.log_model(
            sk_model=model,
            artifact_path="model",
            registered_model_name=MODEL_NAME,
            input_example=X_train[:5],
        )

        # 8. Log evaluation report
        report = {
            "metrics": metrics,
            "params": params,
            "feature_names": list(iris.feature_names),
            "target_names": list(iris.target_names),
        }
        mlflow.log_dict(report, "evaluation_report.json")

        # 9. Log fairness baseline (simplified)
        # In a real project, compute fairness metrics per protected group
        fairness = {
            "bias_check": "simplified_iris_demo",
            "groups_evaluated": len(iris.target_names),
        }
        mlflow.log_dict(fairness, "fairness_report.json")

        print(f"Experiment: {EXPERIMENT_NAME}")
        print(f"Run ID: {mlflow.active_run().info.run_id}")
        print(f"Metrics: {json.dumps(metrics, indent=2)}")

        return model, metrics


def run_hyperparameter_search():
    """Run multiple experiments with different hyperparameters."""

    configs = [
        {"n_estimators": 50, "max_depth": 3, "min_samples_split": 2},
        {"n_estimators": 100, "max_depth": 5, "min_samples_split": 2},
        {"n_estimators": 200, "max_depth": 10, "min_samples_split": 5},
        {"n_estimators": 100, "max_depth": None, "min_samples_split": 2},
    ]

    results = []
    for config in configs:
        _, metrics = train_and_log(**config)
        results.append({"config": config, "metrics": metrics})

    # Print comparison
    print("\n=== Experiment Comparison ===")
    for i, r in enumerate(results):
        print(f"Run {i+1}: accuracy={r['metrics']['accuracy']:.4f} "
              f"f1={r['metrics']['f1']:.4f} "
              f"config={r['config']}")


if __name__ == "__main__":
    import sys
    if len(sys.argv) > 1 and sys.argv[1] == "search":
        run_hyperparameter_search()
    else:
        train_and_log()
