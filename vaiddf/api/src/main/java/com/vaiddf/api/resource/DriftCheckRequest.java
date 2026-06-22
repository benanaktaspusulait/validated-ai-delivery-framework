package com.vaiddf.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DriftCheckRequest(
    @JsonProperty("reference") double[] reference,
    @JsonProperty("current") double[] current
) {}
