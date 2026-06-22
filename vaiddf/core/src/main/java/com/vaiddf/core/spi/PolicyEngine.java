package com.vaiddf.core.spi;

import com.vaiddf.core.model.Model;
import java.util.Map;

/**
 * SPI for policy evaluation engine.
 * Determines whether a model is allowed to be deployed.
 */
public interface PolicyEngine {

    /**
     * Evaluate all policies for a model deployment.
     *
     * @param model the model to evaluate
     * @param context additional context (e.g., target environment, requester)
     * @return policy evaluation result
     */
    PolicyResult evaluate(Model model, Map<String, String> context);

    /**
     * Evaluate a specific named policy.
     *
     * @param policyName the policy to evaluate
     * @param model the model to evaluate
     * @param context additional context
     * @return policy evaluation result
     */
    PolicyResult evaluate(String policyName, Model model, Map<String, String> context);
}
