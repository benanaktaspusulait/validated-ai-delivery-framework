package com.vaiddf.api.resource;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.spi.ModelRegistry;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/v1/models")
public class ModelResource {

    @Inject
    ModelRegistry registry;

    @GET
    public List<Model> list() {
        return registry.listByRegistry("default");
    }

    @GET
    @Path("/{id}")
    public Response getById(String id) {
        return registry.findById(id)
            .map(model -> Response.ok(model).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(Model model) {
        Model registered = registry.register(model);
        return Response.created(URI.create("/api/v1/models/" + registered.id()))
            .entity(registered)
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(String id, Model model) {
        try {
            Model updated = registry.update(model);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(String id) {
        boolean deleted = registry.delete(id);
        return deleted
            ? Response.noContent().build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/{id}/deploy")
    public Response deploy(String id) {
        try {
            Model deployed = registry.transitionStatus(id, Model.Status.DEPLOYED);
            return Response.ok(deployed).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{id}/rollback")
    public Response rollback(String id) {
        try {
            Model rolledBack = registry.transitionStatus(id, Model.Status.ROLLED_BACK);
            return Response.ok(rolledBack).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
