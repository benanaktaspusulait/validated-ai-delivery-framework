package com.vaiddf.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record FairnessResult(
    String modelId,
    String metric,
    boolean passed,
    double score,
    Map<String, Double> groupScores,
    FairnessSeverity severity,
    Instant checkedAt,
    String details
) {
    public enum FairnessSeverity {
        PASS, WARNING, FAIL
    }

    public static FairnessResult passed(String modelId, String metric) {
        return new FairnessResult(modelId, metric, true, 1.0, Map.of(),
            FairnessSeverity.PASS, Instant.now(), "All groups within threshold");
    }

    public static FairnessResult failed(String modelId, String metric, double score, String details) {
        return new FairnessResult(modelId, metric, false, score, Map.of(),
            score < 0.5 ? FairnessSeverity.FAIL : FairnessSeverity.WARNING,
            Instant.now(), details);
    }
}
