package com.mlplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PredictionRequest(
    @JsonProperty("features") double[] features
) {}
