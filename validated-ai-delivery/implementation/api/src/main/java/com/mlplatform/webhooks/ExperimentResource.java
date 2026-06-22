package com.mlplatform.webhooks;

import com.mlplatform.config.MlflowConfig;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ExperimentResource {

    @Inject
    MlflowConfig mlflowConfig;

    @POST
    @Path("/experiments/assign")
    public Response assignExperiment(AssignmentRequest request) {
        // Deterministic assignment based on PR number hash
        int hash = Math.abs(request.prId().hashCode()) % 100;
        String group = hash < 50 ? "treatment" : "control";

        return Response.ok(Map.of(
            "pr_id", request.prId(),
            "group", group,
            "model_version", "current"
        )).build();
    }

    @GET
    @Path("/experiments/{experimentId}/results")
    public Response getResults(String experimentId) {
        // Placeholder: in production, query experiment results from MLflow
        return Response.ok(Map.of(
            "experiment_id", experimentId,
            "status", "running",
            "message", "Experiment results will be available after sufficient data collection"
        )).build();
    }

    public record AssignmentRequest(String prId, String teamId) {}
}
