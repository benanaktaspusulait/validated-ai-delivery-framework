package com.vaiddf.api.impl;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.PolicyRule;
import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class OpaPolicyEngine implements PolicyEngine {

    private final CopyOnWriteArrayList<PolicyRule> rules = new CopyOnWriteArrayList<>();

    public OpaPolicyEngine() {
        rules.add(new PolicyRule(
            "rule-1", "No deprecated models",
            "Deny deployment of deprecated models",
            "deny { input.status == \"DEPRECATED\" }",
            PolicyRule.PolicyAction.DENY, true, java.time.Instant.now()
        ));
        rules.add(new PolicyRule(
            "rule-2", "Require governance",
            "Require governance config for production",
            "deny { not input.governance }",
            PolicyRule.PolicyAction.DENY, true, java.time.Instant.now()
        ));
        rules.add(new PolicyRule(
            "rule-3", "Fairness required",
            "Warn if fairness check not enabled",
            "warn { input.governance.fairnessRequired == false }",
            PolicyRule.PolicyAction.WARN, true, java.time.Instant.now()
        ));
    }

    @Override
    public PolicyResult evaluate(Model model, Map<String, String> context) {
        for (PolicyRule rule : rules) {
            if (!rule.enabled()) continue;

            if (evaluateRule(rule, model, context)) {
                return new PolicyResult(
                    rule.name(),
                    rule.action() == PolicyRule.PolicyAction.ALLOW,
                    rule.description(),
                    Map.of("ruleId", rule.id(), "action", rule.action().name())
                );
            }
        }
        return PolicyResult.allow("default");
    }

    @Override
    public PolicyResult evaluate(String policyName, Model model, Map<String, String> context) {
        return evaluate(model, context);
    }

    private boolean evaluateRule(PolicyRule rule, Model model, Map<String, String> context) {
        String rego = rule.rego().toLowerCase();
        if (rego.contains("deprecated") && model.status() != null &&
            model.status().name().equals("DEPRECATED")) {
            return true;
        }
        if (rego.contains("not input.governance") && model.governance() == null) {
            return true;
        }
        if (rego.contains("fairnessrequired") && model.governance() != null &&
            !model.governance().fairnessRequired()) {
            return true;
        }
        return false;
    }

    public List<PolicyRule> listRules() {
        return List.copyOf(rules);
    }

    public PolicyRule addRule(PolicyRule rule) {
        rules.add(rule);
        return rule;
    }

    public boolean removeRule(String id) {
        return rules.removeIf(r -> r.id().equals(id));
    }
}
