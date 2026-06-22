package com.vaiddf.core.spi;

import java.util.Map;

/**
 * Result of a policy evaluation.
 */
public record PolicyResult(
    String policyName,
    boolean allowed,
    String reason,
    Map<String, String> details
) {

    public static PolicyResult allow(String policyName) {
        return new PolicyResult(policyName, true, "Policy passed", Map.of());
    }

    public static PolicyResult deny(String policyName, String reason) {
        return new PolicyResult(policyName, false, reason, Map.of());
    }
}
