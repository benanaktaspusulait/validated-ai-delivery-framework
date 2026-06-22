package com.vaiddf.api.impl;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Map;

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
