package com.vaiddf.api.ui;

import com.vaiddf.api.impl.ReactiveDriftHistoryService;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/drift")
@Produces(MediaType.TEXT_HTML)
public class ReactiveDriftDashboardResource {

    @Inject
    Template drift;

    @Inject
    ReactiveDriftHistoryService historyService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Uni<String> driftDashboard() {
        return Uni.combine().all().unis(
            historyService.getAll(),
            historyService.countDetected(),
            historyService.totalCount()
        ).asTuple()
        .map(tuple -> {
            var history = tuple.getItem1();
            long detectedCount = tuple.getItem2();
            long totalCount = tuple.getItem3();
            return drift.data("history", history)
                .data("detectedCount", detectedCount)
                .data("totalCount", totalCount)
                .render();
        });
    }
}
