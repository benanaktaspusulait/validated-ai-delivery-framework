package com.mlplatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HealthResponse(
    @JsonProperty("status") String status,
    @JsonProperty("model_loaded") boolean modelLoaded,
    @JsonProperty("model_version") String modelVersion
) {}
