package com.vaiddf.core.model.harness;

import java.time.Instant;
import java.util.List;

public record HarnessMaturityAssessment(
    String id,
    String repositoryName,
    String repositoryPath,
    int presentDimensions,
    int totalDimensions,
    double coverage,
    HarnessMaturityLevel level,
    int numericScore,
    List<HarnessMaturityEvidence> evidence,
    List<HarnessMaturityRecommendation> recommendations,
    Instant assessedAt
) {}
