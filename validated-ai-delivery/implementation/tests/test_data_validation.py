"""Data validation tests using Great Expectations patterns."""

import pytest
import pandas as pd
import numpy as np
from sklearn.datasets import load_iris


@pytest.fixture
def iris_data():
    """Load iris dataset as a DataFrame."""
    iris = load_iris()
    df = pd.DataFrame(iris.data, columns=iris.feature_names)
    df["target"] = iris.target
    return df


class TestDataValidation:
    """Validate data quality expectations."""

    def test_schema_completeness(self, iris_data):
        """All expected columns are present."""
        expected_columns = [
            "sepal length (cm)",
            "sepal width (cm)",
            "petal length (cm)",
            "petal width (cm)",
            "target",
        ]
        assert all(col in iris_data.columns for col in expected_columns)

    def test_no_nulls(self, iris_data):
        """No critical fields are null."""
        assert iris_data.isnull().sum().sum() == 0

    def test_unique_targets(self, iris_data):
        """Target values are within expected range."""
        assert iris_data["target"].nunique() == 3
        assert set(iris_data["target"].unique()) == {0, 1, 2}

    def test_numeric_ranges(self, iris_data):
        """Numeric values within expected bounds."""
        for col in iris_data.columns[:-1]:  # exclude target
            assert iris_data[col].min() >= 0, f"{col} has negative values"
            assert iris_data[col].max() < 10, f"{col} has unexpectedly large values"

    def test_sufficient_samples(self, iris_data):
        """Dataset has minimum required samples."""
        assert len(iris_data) >= 100

    def test_class_balance(self, iris_data):
        """No class is severely underrepresented."""
        class_counts = iris_data["target"].value_counts()
        min_count = class_counts.min()
        max_count = class_counts.max()
        ratio = min_count / max_count
        assert ratio > 0.5, f"Class imbalance too severe: ratio={ratio:.2f}"

    def test_no_data_leakage(self, iris_data):
        """No duplicate rows that could leak between splits."""
        assert len(iris_data) == len(iris_data.drop_duplicates())
