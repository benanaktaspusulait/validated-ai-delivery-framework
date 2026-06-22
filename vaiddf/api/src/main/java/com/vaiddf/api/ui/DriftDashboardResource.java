package com.vaiddf.api.ui;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/drift")
@Produces(MediaType.TEXT_HTML)
public class DriftDashboardResource {

    @Inject
    Template drift;

    @GET
    public TemplateInstance driftDashboard() {
        return drift.instance();
    }
}
