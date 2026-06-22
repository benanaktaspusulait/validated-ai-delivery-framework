package com.vaiddf.core.spi;

import com.vaiddf.core.model.FairnessResult;
import java.util.Map;

public interface FairnessDetector {
    String name();
    FairnessResult check(String modelId, double[] predictions, double[] actuals,
                         int[] protectedAttribute, Map<String, double[]> groupData);
}
