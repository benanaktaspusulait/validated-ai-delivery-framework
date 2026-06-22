package com.vaiddf.api.impl;

import com.vaiddf.core.model.FairnessResult;
import com.vaiddf.core.spi.FairnessDetector;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DemographicParityDetector implements FairnessDetector {

    private static final double DEFAULT_THRESHOLD = 0.8;

    @Override
    public String name() {
        return "demographic_parity";
    }

    @Override
    public FairnessResult check(String modelId, double[] predictions, double[] actuals,
                                 int[] protectedAttribute, Map<String, double[]> groupData) {
        if (predictions.length == 0 || protectedAttribute.length == 0) {
            return FairnessResult.failed(modelId, name(), 0.0, "Empty data");
        }

        Map<Integer, Double> groupPositiveRates = new HashMap<>();
        for (int i = 0; i < predictions.length; i++) {
            int group = protectedAttribute[i];
            groupPositiveRates.merge(group, predictions[i], Double::sum);
        }

        int totalGroups = groupPositiveRates.size();
        if (totalGroups < 2) {
            return FairnessResult.passed(modelId, name());
        }

        double[] rates = groupPositiveRates.values().stream().mapToDouble(Double::doubleValue).toArray();
        double minRate = Double.MAX_VALUE;
        double maxRate = Double.MIN_VALUE;
        for (double rate : rates) {
            if (rate > 0) {
                minRate = Math.min(minRate, rate);
                maxRate = Math.max(maxRate, rate);
            }
        }

        double disparityRatio = maxRate > 0 ? minRate / maxRate : 1.0;
        boolean passed = disparityRatio >= DEFAULT_THRESHOLD;

        Map<String, Double> groupScores = new HashMap<>();
        groupPositiveRates.forEach((group, rate) ->
            groupScores.put("group_" + group, rate / predictions.length));

        if (passed) {
            return new FairnessResult(modelId, name(), true, disparityRatio,
                groupScores, FairnessResult.FairnessSeverity.PASS,
                java.time.Instant.now(),
                String.format("Disparity ratio %.2f >= %.2f threshold", disparityRatio, DEFAULT_THRESHOLD));
        } else {
            return new FairnessResult(modelId, name(), false, disparityRatio,
                groupScores, disparityRatio < 0.5 ? FairnessResult.FairnessSeverity.FAIL : FairnessResult.FairnessSeverity.WARNING,
                java.time.Instant.now(),
                String.format("Disparity ratio %.2f < %.2f threshold", disparityRatio, DEFAULT_THRESHOLD));
        }
    }
}
