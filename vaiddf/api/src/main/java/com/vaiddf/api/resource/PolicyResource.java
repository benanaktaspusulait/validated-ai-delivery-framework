package com.vaiddf.api.resource;

import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/api/v1/policies")
public class PolicyResource {

    @Inject
    PolicyEngine policyEngine;

    @POST
    @Path("/evaluate")
    public PolicyResult evaluate(PolicyEvaluateRequest request) {
        return policyEngine.evaluate(request.model(), request.context());
    }
}
