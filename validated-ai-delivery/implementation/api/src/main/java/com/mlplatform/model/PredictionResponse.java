package com.mlplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionResponse(
    @JsonProperty("prediction") int prediction,
    @JsonProperty("probability") double[] probability,
    @JsonProperty("model_version") String modelVersion,
    @JsonProperty("timestamp") String timestamp
) {}
