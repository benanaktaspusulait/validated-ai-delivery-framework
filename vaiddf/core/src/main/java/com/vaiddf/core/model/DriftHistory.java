package com.vaiddf.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DriftHistory(
    String id,
    String modelName,
    double psiScore,
    DriftResult.DriftSeverity severity,
    boolean driftDetected,
    Instant checkedAt,
    String checkedBy
) {}
