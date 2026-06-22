package com.vaiddf.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a model for automatic drift monitoring.
 * Drift checks are performed at configured intervals.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DriftMonitored {

    /**
     * Drift detection algorithm to use.
     * Supported: "psi" (Population Stability Index), "ks" (Kolmogorov-Smirnov), "chi2"
     */
    String algorithm() default "psi";

    /**
     * Check interval in hours.
     */
    int checkIntervalHours() default 24;

    /**
     * Alert threshold. Drift above this triggers notification.
     */
    double alertThreshold() default 0.2;

    /**
     * Critical threshold. Drift above this triggers automatic rollback.
     */
    double criticalThreshold() default 0.4;
}
