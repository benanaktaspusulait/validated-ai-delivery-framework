#!/bin/sh
set -e

API_URL="http://api:8080"

echo "============================================="
echo "  VAIDDF Quickstart — Credit Risk Model"
echo "============================================="
echo ""

# Wait for API
echo "[1/5] Waiting for API to be ready..."
until curl -sf "$API_URL/q/health" > /dev/null 2>&1; do
    sleep 2
done
echo "      ✓ API is ready"
echo ""

# Register credit risk model
echo "[2/5] Registering credit risk model..."
curl -sf -X POST "$API_URL/api/v1/models" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "credit-risk-v1",
    "name": "credit-risk-model",
    "version": "1.0.0",
    "registry": "production",
    "metadata": {
      "framework": "scikit-learn",
      "algorithm": "RandomForest",
      "dataset": "german-credit",
      "features": "age,amount,housing,job,duration"
    },
    "governance": {
      "driftCheck": true,
      "fairnessRequired": true,
      "maxDriftPSI": 0.2,
      "policy": "credit-risk-prod",
      "minConfidenceScore": 80
    }
  }' > /dev/null
echo "      ✓ Model registered: credit-risk-v1"
echo ""

# Register a second model
echo "[3/5] Registering fraud detection model..."
curl -sf -X POST "$API_URL/api/v1/models" \
  -H "Content-Type: application/json" \
  -d '{
    "id": "fraud-detect-v1",
    "name": "fraud-detection-model",
    "version": "1.0.0",
    "registry": "production",
    "metadata": {
      "framework": "tensorflow",
      "algorithm": "NeuralNetwork",
      "dataset": "transactions",
      "features": "amount,merchant,time,location"
    },
    "governance": {
      "driftCheck": true,
      "fairnessRequired": false,
      "maxDriftPSI": 0.15,
      "policy": "fraud-detect-prod",
      "minConfidenceScore": 85
    }
  }' > /dev/null
echo "      ✓ Model registered: fraud-detect-v1"
echo ""

# Deploy credit risk model
echo "[4/5] Deploying credit risk model..."
curl -sf -X POST "$API_URL/api/v1/models/credit-risk-v1/deploy" > /dev/null
echo "      ✓ credit-risk-v1 deployed to production"
echo ""

# Run drift check
echo "[5/5] Running drift check..."
RESULT=$(curl -sf -X POST "$API_URL/api/v1/drift/check" \
  -H "Content-Type: application/json" \
  -d '{"reference": [5.1,4.9,4.7,4.6,5.0,5.4,4.6,5.0,4.4,4.9], "current": [5.2,5.0,4.8,4.7,5.1,5.5,4.7,5.1,4.5,5.0]}')
echo "      ✓ Drift check completed"
echo ""

echo "============================================="
echo "  Quickstart Complete!"
echo "============================================="
echo ""
echo "  Dashboard:  http://localhost:8080/"
echo "  Models:     http://localhost:8080/models"
echo "  Drift:      http://localhost:8080/drift"
echo "  Health:     http://localhost:8080/q/health"
echo "  API Docs:   http://localhost:8080/q/openapi"
echo ""
echo "  Try it:"
echo "    curl http://localhost:8080/api/v1/models"
echo "    curl http://localhost:8080/api/v1/drift/check \\"
echo "      -H 'Content-Type: application/json' \\"
echo "      -d '{\"reference\":[1,2,3],\"current\":[1.1,2.1,3.1]}'"
echo ""
