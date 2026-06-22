package com.vaiddf.api.ui;

import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/eu-ai-act")
@Produces(MediaType.TEXT_HTML)
public class EuAiActResource {

    @Inject
    Template euAiAct;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String euAiAct() {
        return euAiAct.instance().render();
    }
}
