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

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class DashboardResource {

    @Inject
    Template index;

    @Inject
    ModelRegistry registry;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String dashboard() {
        List<Model> models = registry.listAll();
        long deployedCount = models.stream()
            .filter(m -> m.status().toString().equals("DEPLOYED"))
            .count();

        return index.data("models", models)
            .data("deployedCount", deployedCount)
            .data("driftAlerts", 0)
            .render();
    }
}
