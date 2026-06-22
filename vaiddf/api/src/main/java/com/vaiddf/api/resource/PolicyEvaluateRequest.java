package com.vaiddf.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaiddf.core.model.Model;
import java.util.Map;

public record PolicyEvaluateRequest(
    @JsonProperty("model") Model model,
    @JsonProperty("context") Map<String, String> context
) {}
