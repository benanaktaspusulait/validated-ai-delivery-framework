package com.vaiddf.api.impl;

import com.vaiddf.core.model.DriftHistory;
import com.vaiddf.core.model.DriftResult;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class DriftHistoryService {

    private final CopyOnWriteArrayList<DriftHistory> history = new CopyOnWriteArrayList<>();

    public DriftHistoryService() {
        seedData();
    }

    private void seedData() {
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "credit-risk-model",
            0.05, DriftResult.DriftSeverity.NONE, false,
            Instant.now().minusSeconds(86400 * 6), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "credit-risk-model",
            0.12, DriftResult.DriftSeverity.LOW, false,
            Instant.now().minusSeconds(86400 * 5), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "fraud-detection-model",
            0.03, DriftResult.DriftSeverity.NONE, false,
            Instant.now().minusSeconds(86400 * 4), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "credit-risk-model",
            0.25, DriftResult.DriftSeverity.MODERATE, true,
            Instant.now().minusSeconds(86400 * 3), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "credit-risk-model",
            0.08, DriftResult.DriftSeverity.NONE, false,
            Instant.now().minusSeconds(86400 * 2), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "fraud-detection-model",
            0.45, DriftResult.DriftSeverity.HIGH, true,
            Instant.now().minusSeconds(86400), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "credit-risk-model",
            0.06, DriftResult.DriftSeverity.NONE, false,
            Instant.now().minusSeconds(3600 * 12), "system"
        ));
        history.add(new DriftHistory(
            UUID.randomUUID().toString(), "fraud-detection-model",
            0.15, DriftResult.DriftSeverity.LOW, false,
            Instant.now().minusSeconds(3600 * 6), "system"
        ));
    }

    public DriftHistory record(String modelName, DriftResult result) {
        DriftHistory entry = new DriftHistory(
            UUID.randomUUID().toString(),
            modelName,
            result.overallScore(),
            result.severity(),
            result.driftDetected(),
            Instant.now(),
            "api"
        );
        history.add(entry);
        return entry;
    }

    public List<DriftHistory> getAll() {
        return history.stream()
            .sorted(Comparator.comparing(DriftHistory::checkedAt).reversed())
            .toList();
    }

    public List<DriftHistory> getByModel(String modelName) {
        return history.stream()
            .filter(h -> h.modelName().equals(modelName))
            .sorted(Comparator.comparing(DriftHistory::checkedAt).reversed())
            .toList();
    }

    public long countDetected() {
        return history.stream().filter(DriftHistory::driftDetected).count();
    }
}
