package com.vaiddf.api.resource;

import com.vaiddf.api.impl.AuditLogService;
import com.vaiddf.api.impl.ReactiveModelRegistry;
import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import io.smallrye.mutiny.Uni;
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
public class ReactiveModelResource {

    @Inject
    ReactiveModelRegistry registry;

    @Inject
    AuditLogService auditLogService;

    @GET
    public Uni<List<Model>> list() {
        return registry.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getById(String id) {
        return registry.findById(id)
            .map(model -> model != null
                ? Response.ok(model).build()
                : Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Uni<Response> create(Model model) {
        return registry.register(model)
            .flatMap(registered ->
                auditLogService.log("MODEL_CREATED", "model", registered.id(), "CREATE",
                    null, toJson(registered), "api", null)
                    .map(v -> registered))
            .map(registered ->
                Response.created(URI.create("/api/v1/models/" + registered.id()))
                    .entity(registered)
                    .build());
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> update(String id, Model model) {
        return registry.findById(id)
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND).build());
                }
                return registry.update(model)
                    .flatMap(updated ->
                        auditLogService.log("MODEL_UPDATED", "model", id, "UPDATE",
                            toJson(existing), toJson(updated), "api", null)
                            .map(v -> Response.ok(updated).build()));
            });
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(String id) {
        return registry.findById(id)
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND).build());
                }
                return registry.delete(id)
                    .flatMap(deleted ->
                        auditLogService.log("MODEL_DELETED", "model", id, "DELETE",
                            toJson(existing), null, "api", null)
                            .map(v -> Response.noContent().build()));
            });
    }

    @POST
    @Path("/{id}/deploy")
    public Uni<Response> deploy(String id) {
        return registry.findById(id)
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND).build());
                }
                return registry.transitionStatus(id, ModelStatus.DEPLOYED)
                    .flatMap(deployed ->
                        auditLogService.log("MODEL_DEPLOYED", "model", id, "DEPLOY",
                            toJson(existing), toJson(deployed), "api", null)
                            .map(v -> Response.ok(deployed).build()));
            });
    }

    @POST
    @Path("/{id}/rollback")
    public Uni<Response> rollback(String id) {
        return registry.findById(id)
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().item(Response.status(Response.Status.NOT_FOUND).build());
                }
                return registry.transitionStatus(id, ModelStatus.ROLLED_BACK)
                    .flatMap(rolledBack ->
                        auditLogService.log("MODEL_ROLLED_BACK", "model", id, "ROLLBACK",
                            toJson(existing), toJson(rolledBack), "api", null)
                            .map(v -> Response.ok(rolledBack).build()));
            });
    }

    private String toJson(Model model) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(model);
        } catch (Exception e) {
            return "{}";
        }
    }
}
