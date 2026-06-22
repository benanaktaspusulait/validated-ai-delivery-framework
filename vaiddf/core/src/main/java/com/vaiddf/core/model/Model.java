package com.vaiddf.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Map;

/**
 * Represents a registered model in the control plane.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Model(
    String id,
    String name,
    String version,
    ModelStatus status,
    String registry,
    Instant createdAt,
    Instant updatedAt,
    Map<String, String> metadata,
    GovernanceConfig governance
) {

    public record GovernanceConfig(
        boolean driftCheck,
        boolean fairnessRequired,
        double maxDriftPSI,
        String policy,
        int minConfidenceScore
    ) {}
}
