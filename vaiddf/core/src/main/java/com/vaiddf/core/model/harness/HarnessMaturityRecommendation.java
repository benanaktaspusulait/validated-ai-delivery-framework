package com.vaiddf.core.model.harness;

public record HarnessMaturityRecommendation(
    String dimension,
    String action,
    Priority priority
) {
    public enum Priority {
        HIGH, MEDIUM, LOW
    }
}
