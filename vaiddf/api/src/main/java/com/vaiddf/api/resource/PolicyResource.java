package com.vaiddf.api.resource;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.spi.PolicyEngine;
import com.vaiddf.core.spi.PolicyResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.Map;

@Path("/api/v1/policies")
public class PolicyResource {

    @Inject
    PolicyEngine policyEngine;

    @POST
    @Path("/evaluate")
    public PolicyResult evaluate(Model model, Map<String, String> context) {
        return policyEngine.evaluate(model, context);
    }
}
