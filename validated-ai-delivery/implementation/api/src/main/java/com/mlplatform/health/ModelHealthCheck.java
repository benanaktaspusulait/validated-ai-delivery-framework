package com.mlplatform.health;

import com.mlplatform.service.ModelService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@Liveness
class ModelLivenessCheck implements HealthCheck {

    @Inject
    ModelService modelService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("model-liveness");
        // Model service is alive if it can respond (even if model isn't loaded)
        return builder.up().build();
    }
}

@Readiness
class ModelReadinessCheck implements HealthCheck {

    @Inject
    ModelService modelService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder builder = HealthCheckResponse.named("model-readiness");
        if (modelService.isModelLoaded()) {
            return builder.up().withData("model_version", modelService.getModelVersion()).build();
        } else {
            return builder.down().withData("reason", "Model not loaded").build();
        }
    }
}
