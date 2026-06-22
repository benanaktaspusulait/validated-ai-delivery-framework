package com.vaiddf.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a model as requiring fairness evaluation before deployment.
 * Fairness checks validate bias across protected groups.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FairnessChecked {

    /**
     * Protected attributes to check for bias.
     */
    String[] protectedAttributes() default {};

    /**
     * Fairness metric to use.
     * Supported: "demographic_parity", "equalized_odds", "calibration"
     */
    String metric() default "demographic_parity";

    /**
     * Maximum acceptable disparity ratio (0.0-1.0).
     * Values closer to 1.0 are fairer.
     */
    double maxDisparityRatio() default 0.8;

    /**
     * Whether to block deployment if fairness check fails.
     */
    boolean blockOnFailure() default true;
}
