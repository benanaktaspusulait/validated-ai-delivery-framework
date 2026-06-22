package com.vaiddf.core.model;

import java.time.Instant;
import java.util.Map;

/**
 * Result of a drift detection check.
 */
public record DriftResult(
    String modelId,
    String detectorName,
    boolean driftDetected,
    double overallScore,
    Map<String, Double> featureScores,
    DriftSeverity severity,
    Instant checkedAt,
    String reportUrl
) {

    public enum DriftSeverity {
        NONE,
        LOW,
        MODERATE,
        HIGH,
        CRITICAL
    }

    public static DriftResult noDrift(String modelId, String detectorName) {
        return new DriftResult(
            modelId,
            detectorName,
            false,
            0.0,
            Map.of(),
            DriftSeverity.NONE,
            Instant.now(),
            null
        );
    }
}
