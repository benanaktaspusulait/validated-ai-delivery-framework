FROM python:3.11-slim

WORKDIR /app

RUN pip install --no-cache-dir \
    mlflow==2.14.0 \
    psycopg2-binary==2.9.9 \
    boto3==1.34.69

EXPOSE 5000

CMD ["mlflow", "server", \
     "--backend-store-uri", "postgresql://mlflow:mlflow@postgres:5432/mlflow", \
     "--default-artifact-root", "s3://mlflow-artifacts", \
     "--host", "0.0.0.0", \
     "--port", "5000"]
