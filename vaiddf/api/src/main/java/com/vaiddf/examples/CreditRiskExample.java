package com.vaiddf.examples;

import com.vaiddf.api.impl.PSIDetector;
import com.vaiddf.core.model.DriftResult;
import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import com.vaiddf.core.spi.ModelRegistry;
import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;

/**
 * Credit Risk Model Example
 * 
 * Demonstrates:
 * - Model registration with governance
 * - Drift detection
 * - Policy evaluation
 * - Model deployment workflow
 */
@Path("/examples/credit-risk")
@ApplicationScoped
public class CreditRiskExample {

    @Inject
    ModelRegistry registry;

    @Inject
    PolicyEngine policyEngine;

    @Inject
    PSIDetector psiDetector;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getExample() {
        return Map.of(
            "name", "Credit Risk Model Example",
            "description", "Demonstrates VAIDDF governance for a credit risk model",
            "steps", Map.of(
                "1_register", "POST /examples/credit-risk/register",
                "2_evaluate", "POST /examples/credit-risk/evaluate",
                "3_drift", "POST /examples/credit-risk/drift-check",
                "4_deploy", "POST /examples/credit-risk/deploy"
            )
        );
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Model registerModel() {
        Model model = new Model(
            "credit-risk-demo",
            "credit-risk-model",
            "1.0.0",
            ModelStatus.DRAFT,
            "demo",
            null,
            null,
            Map.of(
                "framework", "scikit-learn",
                "algorithm", "RandomForest",
                "dataset", "german-credit"
            ),
            new Model.GovernanceConfig(
                true,   // driftCheck
                true,   // fairnessRequired
                0.2,    // maxDriftPSI
                "credit-risk-prod",
                80      // minConfidenceScore
            )
        );

        return registry.register(model);
    }

    @POST
    @Path("/evaluate")
    @Produces(MediaType.APPLICATION_JSON)
    public PolicyResult evaluatePolicy() {
        Model model = registry.findById("credit-risk-demo")
            .orElseThrow(() -> new RuntimeException("Model not found. Run /register first."));

        return policyEngine.evaluate(model, Map.of("env", "production"));
    }

    @POST
    @Path("/drift-check")
    @Produces(MediaType.APPLICATION_JSON)
    public DriftResult checkDrift() {
        // Reference data (from training)
        double[] reference = {5.1, 4.9, 4.7, 4.6, 5.0, 5.4, 4.6, 5.0, 4.4, 4.9};

        // Current data (from production, slightly different)
        double[] current = {5.2, 5.0, 4.8, 4.7, 5.1, 5.5, 4.7, 5.1, 4.5, 5.0};

        return psiDetector.check(reference, current);
    }

    @POST
    @Path("/deploy")
    @Produces(MediaType.APPLICATION_JSON)
    public Model deployModel() {
        return registry.transitionStatus("credit-risk-demo", ModelStatus.APPROVED);
    }
}
