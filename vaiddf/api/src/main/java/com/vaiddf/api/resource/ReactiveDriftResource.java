package com.vaiddf.api.resource;

import com.vaiddf.api.entity.DriftHistoryEntity;
import com.vaiddf.api.impl.ReactiveDriftHistoryService;
import com.vaiddf.api.impl.PSIDetector;
import com.vaiddf.core.model.DriftHistory;
import com.vaiddf.core.model.DriftResult;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
        return Panache.withTransaction(() ->
            DriftHistoryEntity.findAll().list()
                .map(list -> list.stream().map(this::toHistory).toList())
        );
    }

    @GET
    @Path("/history/{model}")
    public Uni<List<DriftHistory>> getHistoryByModel(String modelName) {
        return Panache.withTransaction(() ->
            DriftHistoryEntity.find("modelName = ?1 order by checkedAt desc", modelName).list()
                .map(list -> list.stream().map(this::toHistory).toList())
        );
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

    private DriftHistory toHistory(Object entity) {
        var e = (com.vaiddf.api.entity.DriftHistoryEntity) entity;
        return new DriftHistory(
            e.recordId, e.modelName, e.psiScore,
            DriftResult.DriftSeverity.valueOf(e.severity),
            e.driftDetected, e.checkedAt, e.checkedBy
        );
    }
}
