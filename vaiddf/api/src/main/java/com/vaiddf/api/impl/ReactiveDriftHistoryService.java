package com.vaiddf.api.impl;

import com.vaiddf.api.entity.DriftHistoryEntity;
import com.vaiddf.core.model.DriftHistory;
import com.vaiddf.core.model.DriftResult;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ReactiveDriftHistoryService {

    public Uni<DriftHistory> record(String modelName, DriftResult result) {
        DriftHistoryEntity entity = new DriftHistoryEntity();
        entity.recordId = UUID.randomUUID().toString();
        entity.modelName = modelName;
        entity.psiScore = result.overallScore();
        entity.severity = result.severity().name();
        entity.driftDetected = result.driftDetected();
        entity.checkedAt = Instant.now();
        entity.checkedBy = "api";

        return Panache.withTransaction(() -> entity.persist())
            .map(v -> toHistory(entity));
    }

    public Uni<List<DriftHistory>> getAll() {
        return DriftHistoryEntity.findAll().list()
            .map(list -> list.stream().map(e -> toHistory((DriftHistoryEntity) e)).toList());
    }

    public Uni<List<DriftHistory>> getByModel(String modelName) {
        return DriftHistoryEntity.find("modelName = ?1 order by checkedAt desc", modelName).list()
            .map(list -> list.stream().map(e -> toHistory((DriftHistoryEntity) e)).toList());
    }

    public Uni<Long> countDetected() {
        return DriftHistoryEntity.count("driftDetected = true");
    }

    public Uni<Long> totalCount() {
        return DriftHistoryEntity.count();
    }

    public Uni<Void> seedData() {
        return DriftHistoryEntity.count()
            .flatMap(count -> {
                if (count > 0) {
                    return Uni.createFrom().voidItem();
                }
                return createSeedEntries();
            });
    }

    private Uni<Void> createSeedEntries() {
        return Panache.withTransaction(() -> {
            persist(0.05, "NONE", false, "credit-risk-model", 6);
            persist(0.12, "LOW", false, "credit-risk-model", 5);
            persist(0.03, "NONE", false, "fraud-detection-model", 4);
            persist(0.25, "MODERATE", true, "credit-risk-model", 3);
            persist(0.08, "NONE", false, "credit-risk-model", 2);
            persist(0.45, "HIGH", true, "fraud-detection-model", 1);
            persist(0.06, "NONE", false, "credit-risk-model", 0);
            return Uni.createFrom().voidItem();
        });
    }

    private void persist(double psi, String severity, boolean driftDetected, String model, int daysAgo) {
        DriftHistoryEntity entity = new DriftHistoryEntity();
        entity.recordId = UUID.randomUUID().toString();
        entity.modelName = model;
        entity.psiScore = psi;
        entity.severity = severity;
        entity.driftDetected = driftDetected;
        entity.checkedAt = Instant.now().minusSeconds(86400L * daysAgo);
        entity.checkedBy = "system";
        entity.persist().subscribe().with(x -> {}, e -> {});
    }

    private DriftHistory toHistory(DriftHistoryEntity entity) {
        return new DriftHistory(
            entity.recordId,
            entity.modelName,
            entity.psiScore,
            DriftResult.DriftSeverity.valueOf(entity.severity),
            entity.driftDetected,
            entity.checkedAt,
            entity.checkedBy
        );
    }
}
