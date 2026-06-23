package com.vaiddf.api.ui;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/models")
@Produces(MediaType.TEXT_HTML)
public class ModelsResource {

    @Inject
    Template models;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance models() {
        return models.instance();
    }
}
