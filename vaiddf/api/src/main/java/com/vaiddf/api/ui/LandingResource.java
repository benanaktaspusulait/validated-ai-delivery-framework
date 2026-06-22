package com.vaiddf.api.ui;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class LandingResource {

    @Inject
    Template landing;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String landing() {
        return landing.instance().render();
    }
}
