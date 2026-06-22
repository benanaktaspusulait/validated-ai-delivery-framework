package com.vaiddf.api.ui;

import com.vaiddf.api.impl.ReactiveModelRegistry;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/models")
@Produces(MediaType.TEXT_HTML)
public class ReactiveModelsResource {

    @Inject
    Template models;

    @Inject
    ReactiveModelRegistry registry;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> listModels() {
        return registry.listAll()
            .map(allModels -> models.data("models", allModels).render());
    }
}
