package com.vaiddf.api.impl;

import com.vaiddf.api.entity.AuditLogEntity;
import io.quarkus.hibernate.reactive.panache.Panache;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletionStage;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class AuditLogService {

    public Uni<AuditLogEntity> log(String eventType, String entityType, String entityId,
                                    String action, String oldValue, String newValue,
                                    String performedBy, String ipAddress) {
        AuditLogEntity entry = new AuditLogEntity();
        entry.eventType = eventType;
        entry.entityType = entityType;
        entry.entityId = entityId;
        entry.action = action;
        entry.oldValue = oldValue;
        entry.newValue = newValue;
        entry.performedBy = performedBy != null ? performedBy : "system";
        entry.performedAt = Instant.now();
        entry.ipAddress = ipAddress;

        return Panache.withTransaction(entry::persist);
    }

    public Uni<List<AuditLogEntity>> findAll() {
        return AuditLogEntity.findAll().list();
    }

    public Uni<List<AuditLogEntity>> findByEntity(String entityType, String entityId) {
        return AuditLogEntity.find("entityType = ?1 and entityId = ?2 order by performedAt desc",
            entityType, entityId).list();
    }

    public Uni<List<AuditLogEntity>> findRecent(int limit) {
        return AuditLogEntity.findAll().page(0, limit).list();
    }
}
