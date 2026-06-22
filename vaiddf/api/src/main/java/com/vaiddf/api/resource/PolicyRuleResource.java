package com.vaiddf.api.resource;

import com.vaiddf.api.impl.OpaPolicyEngine;
import com.vaiddf.core.model.PolicyRule;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/v1/policy-rules")
public class PolicyRuleResource {

    @Inject
    OpaPolicyEngine policyEngine;

    @GET
    public List<PolicyRule> listRules() {
        return policyEngine.listRules();
    }

    @POST
    public Response addRule(PolicyRule rule) {
        PolicyRule created = policyEngine.addRule(rule);
        return Response.created(URI.create("/api/v1/policy-rules/" + created.id()))
            .entity(created)
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRule(String id) {
        boolean deleted = policyEngine.removeRule(id);
        return deleted
            ? Response.noContent().build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }
}
