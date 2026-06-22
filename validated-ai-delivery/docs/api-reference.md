# API Reference

Model serving API endpoints.

Base URL: `http://localhost:8080`

Authentication: API key via `X-API-Key` header.

## Endpoints

### GET /health

Health check.

**Response 200:**
```json
{
  "status": "healthy",
  "model_loaded": true,
  "model_version": "1"
}
```

### POST /predict

Get a model prediction.

**Request:**
```json
{
  "features": [5.1, 3.5, 1.4, 0.2]
}
```

**Response 200:**
```json
{
  "prediction": 0,
  "probability": [0.95, 0.03, 0.02],
  "model_version": "1",
  "timestamp": "2025-01-15T10:30:00"
}
```

**Error 503:**
```json
{
  "detail": "Model not loaded"
}
```

### POST /predict/ab

A/B test routing. Randomly routes to model A or B.

**Request:** same as /predict

**Response 200:**
```json
{
  "prediction": 0,
  "group": "A",
  "model": "model-v1"
}
```

### POST /predict/shadow

Shadow deployment. Returns champion prediction, logs both.

**Request:** same as /predict

**Response 200:**
```json
{
  "prediction": 0,
  "shadow_challenger_prediction": 0,
  "agreement": true
}
```

### POST /reload

Force model reload from MLflow registry.

**Response 200:**
```json
{
  "status": "reloaded",
  "model_version": "1"
}
```

### GET /metrics

Model metadata for monitoring.

**Response 200:**
```json
{
  "model_name": "default-model",
  "model_version": "1",
  "model_stage": "Production",
  "loaded": true
}
```

## Error codes

| Code | Meaning |
|---|---|
| 200 | Success |
| 400 | Bad request (invalid input) |
| 401 | Unauthorised (missing or invalid API key) |
| 404 | Not found |
| 422 | Validation error (wrong feature count) |
| 500 | Internal server error |
| 503 | Model not available |
