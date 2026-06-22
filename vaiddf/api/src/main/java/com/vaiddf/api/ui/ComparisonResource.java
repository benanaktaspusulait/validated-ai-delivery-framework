package com.vaiddf.api.ui;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/comparison")
@Produces(MediaType.TEXT_HTML)
public class ComparisonResource {

    @Inject
    Template comparison;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String comparison() {
        return comparison.instance().render();
    }
}
