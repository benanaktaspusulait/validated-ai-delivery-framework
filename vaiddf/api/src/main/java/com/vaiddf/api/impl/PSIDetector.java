package com.vaiddf.api.impl;

import com.vaiddf.core.model.DriftResult;
import com.vaiddf.core.spi.DriftDetector;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PSIDetector implements DriftDetector {

    private static final int BINS = 10;

    @Override
    public String name() {
        return "psi";
    }

    @Override
    public DriftResult check(double[] reference, double[] current) {
        double[] refBins = binData(reference, BINS);
        double[] curBins = binData(current, BINS);

        double psi = 0.0;
        for (int i = 0; i < BINS; i++) {
            double refPct = refBins[i] / reference.length;
            double curPct = curBins[i] / current.length;

            if (refPct == 0) refPct = 0.0001;
            if (curPct == 0) curPct = 0.0001;

            psi += (curPct - refPct) * Math.log(curPct / refPct);
        }

        DriftResult.DriftSeverity severity = classifySeverity(psi);

        return new DriftResult(
            "single-feature",
            name(),
            psi > 0.2,
            psi,
            Map.of("psi", psi),
            severity,
            Instant.now(),
            null
        );
    }

    @Override
    public DriftResult checkMultiFeature(
        double[][] reference,
        double[][] current,
        String[] featureNames
    ) {
        Map<String, Double> featureScores = new HashMap<>();
        double totalPsi = 0.0;
        boolean anyDrift = false;

        for (int i = 0; i < reference.length; i++) {
            DriftResult result = check(reference[i], current[i]);
            featureScores.put(featureNames[i], result.overallScore());
            totalPsi += result.overallScore();
            if (result.driftDetected()) {
                anyDrift = true;
            }
        }

        double avgPsi = totalPsi / reference.length;
        DriftResult.DriftSeverity severity = classifySeverity(avgPsi);

        return new DriftResult(
            "multi-feature",
            name(),
            anyDrift,
            avgPsi,
            featureScores,
            severity,
            Instant.now(),
            null
        );
    }

    private double[] binData(double[] data, int bins) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (double v : data) {
            if (v < min) min = v;
            if (v > max) max = v;
        }

        double[] counts = new double[bins];
        double range = max - min;
        if (range == 0) range = 1.0;

        for (double v : data) {
            int bin = (int) ((v - min) / range * (bins - 1));
            if (bin >= bins) bin = bins - 1;
            if (bin < 0) bin = 0;
            counts[bin]++;
        }

        return counts;
    }

    private DriftResult.DriftSeverity classifySeverity(double psi) {
        if (psi < 0.1) return DriftResult.DriftSeverity.NONE;
        if (psi < 0.2) return DriftResult.DriftSeverity.LOW;
        if (psi < 0.4) return DriftResult.DriftSeverity.MODERATE;
        if (psi < 0.6) return DriftResult.DriftSeverity.HIGH;
        return DriftResult.DriftSeverity.CRITICAL;
    }
}
