package com.vaiddf.api.ui;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.spi.ModelRegistry;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/models")
@Produces(MediaType.TEXT_HTML)
public class ModelsResource {

    @Inject
    Template models;

    @Inject
    ModelRegistry registry;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listModels() {
        List<Model> allModels = registry.listAll();
        return models.data("models", allModels).render();
    }
}
