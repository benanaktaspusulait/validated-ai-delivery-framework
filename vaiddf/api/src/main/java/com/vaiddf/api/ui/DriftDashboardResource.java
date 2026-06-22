package com.vaiddf.api.ui;

import com.vaiddf.api.impl.DriftHistoryService;
import com.vaiddf.core.model.DriftHistory;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/drift")
@Produces(MediaType.TEXT_HTML)
public class DriftDashboardResource {

    @Inject
    Template drift;

    @Inject
    DriftHistoryService historyService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String driftDashboard() {
        List<DriftHistory> history = historyService.getAll();
        long detectedCount = historyService.countDetected();
        long totalCount = history.size();

        return drift.data("history", history)
            .data("detectedCount", detectedCount)
            .data("totalCount", totalCount)
            .render();
    }
}
