# Scalability and Infrastructure

Scaling training and inference workloads efficiently.

## Training scalability

```text
Small models (< 100M parameters):
  - Single GPU or CPU is sufficient.
  - Use managed training (SageMaker, Vertex AI) for simplicity.

Medium models (100M - 1B parameters):
  - Multi-GPU training (data parallelism).
  - Use distributed training frameworks (PyTorch DDP, Horovod).

Large models (> 1B parameters):
  - Model parallelism + data parallelism.
  - GPU cluster with high-bandwidth interconnect (NVLink, InfiniBand).
  - Consider cloud GPU instances (p4d, A100, H100).
```

## Inference scalability

```text
Batch inference:
  - Process large volumes offline (nightly, hourly).
  - Use Spark, Ray or batch prediction APIs.
  - Cost-effective for non-real-time use cases.

Real-time inference:
  - API endpoint with auto-scaling.
  - Target: p95 latency < 100ms (adjust per use case).
  - Use model optimisation: ONNX, TensorRT, quantisation.
  - Container-based deployment (Kubernetes HPA).

Streaming inference:
  - Process events in real-time (Kafka, Kinesis).
  - Use beam or Flink for stream processing.
  - Stateful inference for time-series models.
```

## Model optimisation techniques

| Technique | Benefit | Trade-off |
|---|---|---|
| Quantisation (INT8) | 2-4x speedup, smaller models | Slight accuracy loss |
| ONNX conversion | Framework-agnostic, optimised runtime | Conversion effort |
| TensorRT optimisation | GPU-optimised inference | NVIDIA-only |
| Pruning | Smaller models, faster inference | Retraining needed |
| Knowledge distillation | Smaller student model | Accuracy loss |
| Caching | Reduced inference for repeated inputs | Staleness risk |

## Cost-performance trade-off

```text
Rule of thumb: optimise for cost AFTER the model is validated, not during development.
During development: use the simplest infrastructure that works.
In production: measure cost per prediction; optimise if cost is a concern.
Always benchmark: optimised model accuracy must be within 1% of original.
```
