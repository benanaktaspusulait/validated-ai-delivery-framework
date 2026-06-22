package com.mlplatform.webhooks;

import com.mlplatform.service.ModelService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.util.Map;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class HealthResource {

    @Inject
    ModelService modelService;

    @GET
    @Path("/health")
    public Response health() {
        return Response.ok(Map.of(
            "status", modelService.isModelLoaded() ? "healthy" : "degraded",
            "model_loaded", modelService.isModelLoaded(),
            "model_version", modelService.getModelVersion() != null ? modelService.getModelVersion() : "unknown"
        )).build();
    }

    @GET
    @Path("/metrics")
    public Response metrics() {
        return Response.ok(modelService.getModelInfo()).build();
    }

    @POST
    @Path("/reload")
    public Response reload() {
        modelService.loadModel();
        return Response.ok(Map.of(
            "status", "reloaded",
            "model_version", modelService.getModelVersion() != null ? modelService.getModelVersion() : "unknown"
        )).build();
    }
}
