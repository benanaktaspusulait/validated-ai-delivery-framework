package com.vaiddf.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PolicyRule(
    String id,
    String name,
    String description,
    String rego,
    PolicyAction action,
    boolean enabled,
    Instant createdAt
) {
    public enum PolicyAction {
        ALLOW, DENY, WARN, LOG
    }
}
