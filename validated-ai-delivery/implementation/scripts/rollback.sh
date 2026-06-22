#!/bin/bash
# Rollback to previous model version
set -e

MODEL_NAME=${MODEL_NAME:-default-model}
PREV_VERSION=${1:?Usage: ./rollback.sh <previous_version>}

echo "Rolling back $MODEL_NAME to version $PREV_VERSION"

python -c "
import mlflow
client = mlflow.MlflowClient()

# Archive current production version
versions = client.search_model_versions(\"name='$MODEL_NAME'\")
for v in versions:
    if v.current_stage == 'Production':
        client.transition_model_version_stage('$MODEL_NAME', v.version, 'Archived')
        print(f'Archived v{v.version}')

# Promote target version to Production
client.transition_model_version_stage('$MODEL_NAME', $PREV_VERSION, 'Production')
print(f'Promoted v$PREV_VERSION to Production')
"

curl -s -X POST http://localhost:8000/reload > /dev/null && echo "API model reloaded" || echo "API reload failed"
echo "Rollback complete. Now serving v$PREV_VERSION"
