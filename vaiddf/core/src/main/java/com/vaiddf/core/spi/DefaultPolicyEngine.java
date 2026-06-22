package com.vaiddf.core.spi;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

/**
 * Default policy engine implementation.
 * Enforces basic governance rules.
 */
@ApplicationScoped
public class DefaultPolicyEngine implements PolicyEngine {

    @Override
    public PolicyResult evaluate(Model model, Map<String, String> context) {
        if (model.status() == ModelStatus.DEPLOYED) {
            return PolicyResult.deny(model.name(), "Model is already deployed");
        }

        if (model.status() == ModelStatus.DEPRECATED) {
            return PolicyResult.deny(model.name(), "Model is deprecated");
        }

        if (model.governance() == null) {
            return PolicyResult.allow(model.name());
        }

        if (model.governance().fairnessRequired() && model.status() != ModelStatus.APPROVED) {
            return PolicyResult.deny(model.name(), "Fairness check required but not completed");
        }

        return PolicyResult.allow(model.name());
    }

    @Override
    public PolicyResult evaluate(String policyName, Model model, Map<String, String> context) {
        return evaluate(model, context);
    }
}
