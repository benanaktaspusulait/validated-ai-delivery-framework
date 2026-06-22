package com.vaiddf.core.model.harness;

import java.util.List;

public record HarnessMaturityEvidence(
    String dimension,
    boolean present,
    Confidence confidence,
    List<String> evidencePaths,
    String evidenceSummary,
    String recommendation
) {
    public enum Confidence {
        HIGH, MEDIUM, LOW
    }
}
