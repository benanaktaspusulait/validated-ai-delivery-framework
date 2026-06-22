package com.vaiddf.api.resource;

import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.Map;

@Path("/api/v1/policies")
public class ReactivePolicyResource {

    @Inject
    PolicyEngine policyEngine;

    @POST
    @Path("/evaluate")
    public Uni<PolicyResult> evaluate(PolicyEvaluateRequest request) {
        return Uni.createFrom().item(policyEngine.evaluate(request.model(), request.context()));
    }
}
