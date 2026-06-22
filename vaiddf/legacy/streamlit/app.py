"""Streamlit demo UI for model exploration."""

import streamlit as st
import mlflow
import os
import json
import pandas as pd
import numpy as np

MLFLOW_TRACKING_URI = os.getenv("MLFLOW_TRACKING_URI", "http://mlflow:5000")
mlflow.set_tracking_uri(MLFLOW_TRACKING_URI)

st.set_page_config(page_title="ML Model Explorer", layout="wide")
st.title("ML Model Explorer")

# Sidebar
st.sidebar.header("Configuration")

# Load experiments
try:
    experiments = mlflow.search_experiments()
    experiment_names = [e.name for e in experiments]
    selected_experiment = st.sidebar.selectbox("Experiment", experiment_names)
except Exception as e:
    st.error(f"Cannot connect to MLflow: {e}")
    st.stop()

# Load runs
try:
    runs = mlflow.search_runs(experiment_names=[selected_experiment])
    if runs.empty:
        st.warning("No runs found for this experiment.")
        st.stop()
except Exception as e:
    st.error(f"Cannot load runs: {e}")
    st.stop()

# Run comparison table
st.subheader("Experiment Runs")
display_cols = ["run_id", "start_time", "status"]
metric_cols = [c for c in runs.columns if c.startswith("metrics.")]
param_cols = [c for c in runs.columns if c.startswith("params.")]
st.dataframe(runs[display_cols + metric_cols + param_cols].head(20))

# Run detail
st.subheader("Run Detail")
run_ids = runs["run_id"].tolist()
selected_run = st.selectbox("Select Run", run_ids)

if selected_run:
    run_data = runs[runs["run_id"] == selected_run].iloc[0]

    col1, col2 = st.columns(2)

    with col1:
        st.markdown("**Parameters**")
        params = {k.replace("params.", ""): v for k, v in run_data.items() if k.startswith("params.")}
        st.json(params)

    with col2:
        st.markdown("**Metrics**")
        metrics = {k.replace("metrics.", ""): float(v) for k, v in run_data.items() if k.startswith("metrics.")}
        st.json(metrics)

    # Visualize metrics
    if metrics:
        st.subheader("Metrics Comparison")
        all_runs_metrics = []
        for _, row in runs.iterrows():
            run_metrics = {k.replace("metrics.", ""): float(v) for k, v in row.items() if k.startswith("metrics.")}
            run_metrics["run_id"] = row["run_id"][:8]
            all_runs_metrics.append(run_metrics)

        df_metrics = pd.DataFrame(all_runs_metrics)
        st.bar_chart(df_metrics.set_index("run_id"))

# Model Registry
st.subheader("Model Registry")
try:
    client = mlflow.MlflowClient()
    registered_models = client.search_registered_models()
    if registered_models:
        for model in registered_models:
            with st.expander(f"Model: {model.name}"):
                st.write(f"Latest version: {model.latest_versions[-1].version if model.latest_versions else 'N/A'}")
                st.write(f"Stage: {model.latest_versions[-1].current_stage if model.latest_versions else 'N/A'}")
    else:
        st.info("No registered models found.")
except Exception as e:
    st.warning(f"Cannot access model registry: {e}")

# Inference demo
st.subheader("Inference Demo")
st.markdown("Enter feature values to get a prediction (Iris dataset demo)")

col1, col2, col3, col4 = st.columns(4)
with col1:
    sepal_length = st.number_input("Sepal Length", 4.0, 8.0, 5.1)
with col2:
    sepal_width = st.number_input("Sepal Width", 2.0, 4.5, 3.5)
with col3:
    petal_length = st.number_input("Petal Length", 1.0, 7.0, 1.4)
with col4:
    petal_width = st.number_input("Petal Width", 0.1, 2.5, 0.2)

if st.button("Predict"):
    try:
        import requests
        response = requests.post(
            "http://api:8000/predict",
            json={"features": [sepal_length, sepal_width, petal_length, petal_width]},
        )
        if response.status_code == 200:
            result = response.json()
            st.success(f"Prediction: {result['prediction']}")
            st.write(f"Probabilities: {result['probability']}")
            st.write(f"Model version: {result['model_version']}")
        else:
            st.error(f"API error: {response.status_code}")
    except Exception as e:
        st.error(f"Cannot reach API: {e}")
