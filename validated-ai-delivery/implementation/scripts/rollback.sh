#!/bin/bash
# Rollback to previous model version

set -e

MODEL_NAME=${MODEL_NAME:-default-model}
MLFLOW_TRACKING_URI=${MLFLOW_TRACKING_URI:-http://localhost:5000}

echo "Rolling back model: $MODEL_NAME"

# Get current and previous versions
CURRENT_VERSION=$(python -c "
import mlflow
mlflow.set_tracking_uri('$MLFLOW_TRACKING_URI')
client = mlflow.MlflowClient()
versions = client.search_model_versions(\"name='$MODEL_NAME'\")
prod = [v for v in versions if v.current_stage == 'Production']
if prod:
    print(prod[0].version)
else:
    print('None')
")

if [ "$CURRENT_VERSION" = "None" ]; then
    echo "No production model found. Nothing to rollback."
    exit 1
fi

PREVIOUS_VERSION=$((CURRENT_VERSION - 1))

if [ "$PREVIOUS_VERSION" -lt 1 ]; then
    echo "No previous version to rollback to."
    exit 1
fi

echo "Current: v$CURRENT_VERSION -> Rolling back to v$PREVIOUS_VERSION"

# Archive current version
python -c "
import mlflow
mlflow.set_tracking_uri('$MLFLOW_TRACKING_URI')
client = mlflow.MlflowClient()
client.transition_model_version_stage('$MODEL_NAME', $CURRENT_VERSION, 'Archived')
print('Archived v$CURRENT_VERSION')
"

# Promote previous version to production
python -c "
import mlflow
mlflow.set_tracking_uri('$MLFLOW_TRACKING_URI')
client = mlflow.MlflowClient()
client.transition_model_version_stage('$MODEL_NAME', $PREVIOUS_VERSION, 'Production')
print('Promoted v$PREVIOUS_VERSION to Production')
"

# Reload API model
curl -X POST http://localhost:8000/reload

echo "Rollback complete. Now serving v$PREVIOUS_VERSION"
