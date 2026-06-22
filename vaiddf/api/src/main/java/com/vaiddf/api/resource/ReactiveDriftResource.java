package com.vaiddf.api.resource;

import com.vaiddf.api.impl.PSIDetector;
import com.vaiddf.api.impl.ReactiveDriftHistoryService;
import com.vaiddf.core.model.DriftHistory;
import com.vaiddf.core.model.DriftResult;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/api/v1/drift")
public class ReactiveDriftResource {

    @Inject
    PSIDetector psiDetector;

    @Inject
    ReactiveDriftHistoryService historyService;

    @GET
    @Path("/detectors")
    public Uni<List<String>> listDetectors() {
        return Uni.createFrom().item(List.of("psi"));
    }

    @GET
    @Path("/history")
    public Uni<List<DriftHistory>> getHistory() {
        return historyService.getAll();
    }

    @GET
    @Path("/history/{model}")
    public Uni<List<DriftHistory>> getHistoryByModel(@QueryParam("model") String modelName) {
        return historyService.getByModel(modelName);
    }

    @GET
    @Path("/stats")
    public Uni<DriftStats> getStats() {
        return Uni.combine().all().unis(
            historyService.totalCount(),
            historyService.countDetected()
        ).asTuple()
        .map(tuple -> new DriftStats(tuple.getItem1(), tuple.getItem2()));
    }

    @POST
    @Path("/check")
    public Uni<DriftResult> check(DriftCheckRequest request) {
        DriftResult result = psiDetector.check(request.reference(), request.current());
        return historyService.record("unknown", result)
            .map(v -> result);
    }

    @POST
    @Path("/check/{modelName}")
    public Uni<DriftResult> checkForModel(String modelName, DriftCheckRequest request) {
        DriftResult result = psiDetector.check(request.reference(), request.current());
        return historyService.record(modelName, result)
            .map(v -> result);
    }

    public record DriftStats(long totalChecks, long driftDetected) {}
}
