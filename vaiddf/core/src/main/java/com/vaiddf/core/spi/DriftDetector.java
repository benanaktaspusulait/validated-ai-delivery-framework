package com.vaiddf.core.spi;

import com.vaiddf.core.model.DriftResult;

/**
 * SPI for drift detection algorithms.
 * Implementations provide specific drift detection strategies.
 */
public interface DriftDetector {

    /**
     * Unique name for this detector (e.g., "psi", "ks", "chi2").
     */
    String name();

    /**
     * Check for drift between reference and current data.
     *
     * @param reference baseline data from training
     * @param current production data to compare
     * @return drift detection result
     */
    DriftResult check(double[] reference, double[] current);

    /**
     * Check for drift across multiple features.
     *
     * @param reference baseline data (columns as arrays)
     * @param current production data (columns as arrays)
     * @param featureNames names of features for reporting
     * @return drift detection result with per-feature scores
     */
    DriftResult checkMultiFeature(
        double[][] reference,
        double[][] current,
        String[] featureNames
    );
}
