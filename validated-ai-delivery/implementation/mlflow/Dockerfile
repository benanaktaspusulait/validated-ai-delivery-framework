FROM python:3.11-slim

WORKDIR /app

COPY conda.yaml .
RUN pip install --no-cache-dir \
    mlflow==2.10.0 \
    scikit-learn==1.3.2 \
    boto3

EXPOSE 5000

CMD ["mlflow", "server", \
     "--backend-store-uri", "postgresql://mlflow:mlflow@postgres:5432/mlflow", \
     "--default-artifact-root", "s3://mlflow-artifacts", \
     "--host", "0.0.0.0", \
     "--port", "5000"]
