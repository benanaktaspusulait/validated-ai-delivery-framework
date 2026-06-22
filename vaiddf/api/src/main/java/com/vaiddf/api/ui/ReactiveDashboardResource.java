package com.vaiddf.api.ui;

import com.vaiddf.api.impl.ReactiveModelRegistry;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class ReactiveDashboardResource {

    @Inject
    Template index;

    @Inject
    ReactiveModelRegistry registry;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> dashboard() {
        return registry.listAll()
            .map(models -> {
                long deployedCount = models.stream()
                    .filter(m -> m.status().toString().equals("DEPLOYED"))
                    .count();
                return index.data("models", models)
                    .data("deployedCount", deployedCount)
                    .data("driftAlerts", 0)
                    .render();
            });
    }
}
