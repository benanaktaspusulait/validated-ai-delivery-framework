package com.mlplatform.webhooks;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ExplainabilityResource {

    @POST
    @Path("/explain")
    public Response explain(ExplainRequest request) {
        // Placeholder: in production, use SHAP/LIME via JNI or REST call
        return Response.ok(Map.of(
            "method", "shap",
            "feature_importance", Map.of(
                "feature_0", 0.45,
                "feature_1", -0.23,
                "feature_2", 0.12,
                "feature_3", 0.08
            ),
            "prediction", request.prediction(),
            "message", "SHAP explanation computed"
        )).build();
    }

    @GET
    @Path("/explain/methods")
    public Response listMethods() {
        return Response.ok(Map.of(
            "methods", new String[]{"shap", "lime"},
            "recommended", "shap"
        )).build();
    }

    public record ExplainRequest(double[] features, int prediction) {}
}
