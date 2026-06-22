package com.vaiddf.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a model or endpoint as governed by the AI Delivery Control Plane.
 * Runtime interceptors enforce governance rules defined by this annotation.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface ModelGoverned {

    /**
     * Registry name where this model is registered.
     */
    String registry() default "default";

    /**
     * Whether drift monitoring is enabled for this model.
     */
    boolean driftCheck() default true;

    /**
     * Whether fairness checking is required before deployment.
     */
    boolean fairnessRequired() default false;

    /**
     * Maximum PSI (Population Stability Index) threshold for drift detection.
     * Exceeding this triggers a drift alert.
     */
    double maxDriftPSI() default 0.2;

    /**
     * Policy name to evaluate before deployment.
     */
    String policy() default "";

    /**
     * Minimum confidence score (0-100) required for decisions.
     * Metrics below this are withheld from enforcement.
     */
    int minConfidenceScore() default 70;
}
