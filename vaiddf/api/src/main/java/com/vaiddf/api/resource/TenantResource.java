package com.vaiddf.api.resource;

import com.vaiddf.api.impl.TenantService;
import com.vaiddf.core.model.Tenant;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/v1/tenants")
public class TenantResource {

    @Inject
    TenantService tenantService;

    @GET
    public List<Tenant> list() {
        return tenantService.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(String id) {
        Tenant tenant = tenantService.findById(id);
        return tenant != null
            ? Response.ok(tenant).build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response create(Tenant tenant) {
        Tenant created = tenantService.create(tenant);
        return Response.created(URI.create("/api/v1/tenants/" + created.id()))
            .entity(created)
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(String id, Tenant tenant) {
        Tenant existing = tenantService.findById(id);
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Tenant updated = tenantService.update(id, tenant);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(String id) {
        boolean deleted = tenantService.delete(id);
        return deleted
            ? Response.noContent().build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }
}
