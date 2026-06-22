package com.vaiddf.core.model.harness;

public enum HarnessMaturityLevel {
    ABSENT(0, "No agent harness detected"),
    EMERGING(1, "Basic agent setup in progress"),
    MANAGED(2, "Structured agent harness with workflows"),
    GOVERNED(3, "Comprehensive harness with quality gates"),
    ADAPTIVE(4, "Full harness with drift checks and specialization");

    private final int numericScore;
    private final String description;

    HarnessMaturityLevel(int numericScore, String description) {
        this.numericScore = numericScore;
        this.description = description;
    }

    public int getNumericScore() {
        return numericScore;
    }

    public String getDescription() {
        return description;
    }

    public static HarnessMaturityLevel fromPresentDimensions(int count) {
        if (count == 0) return ABSENT;
        if (count <= 3) return EMERGING;
        if (count <= 6) return MANAGED;
        if (count <= 8) return GOVERNED;
        return ADAPTIVE;
    }
}
