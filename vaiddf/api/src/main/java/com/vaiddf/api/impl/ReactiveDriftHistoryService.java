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

        return Panache.withTransaction(entity::persist)
            .map(e -> toHistory(e));
    }

    public Uni<List<DriftHistory>> getAll() {
        return DriftHistoryEntity.findAll().list()
            .map(list -> list.stream().map(this::toHistory).toList());
    }

    public Uni<List<DriftHistory>> getByModel(String modelName) {
        return DriftHistoryEntity.find("modelName = ?1 order by checkedAt desc", modelName).list()
            .map(list -> list.stream().map(this::toHistory).toList());
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
        DriftHistoryEntity e1 = createEntry("credit-risk-model", 0.05, "NONE", false, 6);
        DriftHistoryEntity e2 = createEntry("credit-risk-model", 0.12, "LOW", false, 5);
        DriftHistoryEntity e3 = createEntry("fraud-detection-model", 0.03, "NONE", false, 4);
        DriftHistoryEntity e4 = createEntry("credit-risk-model", 0.25, "MODERATE", true, 3);
        DriftHistoryEntity e5 = createEntry("credit-risk-model", 0.08, "NONE", false, 2);
        DriftHistoryEntity e6 = createEntry("fraud-detection-model", 0.45, "HIGH", true, 1);
        DriftHistoryEntity e7 = createEntry("credit-risk-model", 0.06, "NONE", false, 0);

        return Panache.withTransaction(() ->
            Uni.createFrom().item(null)
                .chain(v -> e1.persist())
                .chain(v -> e2.persist())
                .chain(v -> e3.persist())
                .chain(v -> e4.persist())
                .chain(v -> e5.persist())
                .chain(v -> e6.persist())
                .chain(v -> e7.persist())
        ).replaceWithVoid();
    }

    private DriftHistoryEntity createEntry(String modelName, double psi, String severity,
                                            boolean driftDetected, int daysAgo) {
        DriftHistoryEntity entity = new DriftHistoryEntity();
        entity.recordId = UUID.randomUUID().toString();
        entity.modelName = modelName;
        entity.psiScore = psi;
        entity.severity = severity;
        entity.driftDetected = driftDetected;
        entity.checkedAt = Instant.now().minusSeconds(86400L * daysAgo);
        entity.checkedBy = "system";
        return entity;
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
