package com.mlplatform.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigRoot(phase = ConfigPhase.RUNTIME)
@ConfigMapping(prefix = "mlflow")
public interface MlflowConfig {

    @WithName("tracking-uri")
    @WithDefault("http://mlflow:5000")
    String trackingUri();

    @WithName("model-name")
    @WithDefault("default-model")
    String modelName();

    @WithName("model-stage")
    @WithDefault("Production")
    String modelStage();
}
