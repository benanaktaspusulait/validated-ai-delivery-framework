package com.mlplatform.service;

import com.mlplatform.config.MlflowConfig;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.mlflow.tracking.MlflowClient;
import org.mlflow.tracking.MlflowHttpConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ModelService {

    private static final Logger LOG = LoggerFactory.getLogger(ModelService.class);

    @Inject
    MlflowConfig mlflowConfig;

    private MlflowClient mlflowClient;
    private final Map<String, Object> modelCache = new ConcurrentHashMap<>();
    private String loadedModelVersion;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("Connecting to MLflow at {}", mlflowConfig.trackingUri());
        try {
            mlflowClient = new MlflowClient(mlflowConfig.trackingUri());
            loadModel();
        } catch (Exception e) {
            LOG.error("Failed to connect to MLflow: {}", e.getMessage());
        }
    }

    public void loadModel() {
        try {
            // Search for the model version in the target stage
            var versions = mlflowClient.searchModelVersions("name='" + mlflowConfig.modelName() + "'");
            for (var version : versions) {
                if (mlflowConfig.modelStage().equals(version.getStage())) {
                    loadedModelVersion = version.getVersion();
                    // In a real implementation, load the model artifact here
                    // For now, we track the version metadata
                    modelCache.put("version", loadedModelVersion);
                    modelCache.put("run_id", version.getRunId());
                    LOG.info("Loaded model {} v{}", mlflowConfig.modelName(), loadedModelVersion);
                    return;
                }
            }
            LOG.warn("No model version found in stage {}", mlflowConfig.modelStage());
        } catch (Exception e) {
            LOG.error("Failed to load model: {}", e.getMessage());
        }
    }

    public boolean isModelLoaded() {
        return loadedModelVersion != null;
    }

    public String getModelVersion() {
        return loadedModelVersion;
    }

    public Map<String, Object> getModelInfo() {
        return Map.of(
            "model_name", mlflowConfig.modelName(),
            "model_version", loadedModelVersion != null ? loadedModelVersion : "unknown",
            "model_stage", mlflowConfig.modelStage(),
            "loaded", isModelLoaded()
        );
    }
}
