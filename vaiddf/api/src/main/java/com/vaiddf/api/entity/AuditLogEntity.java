package com.vaiddf.api.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "audit_log")
public class AuditLogEntity extends PanacheEntity {

    @Column(name = "event_type", nullable = false)
    public String eventType;

    @Column(name = "entity_type", nullable = false)
    public String entityType;

    @Column(name = "entity_id", nullable = false)
    public String entityId;

    @Column(name = "action", nullable = false)
    public String action;

    @Column(name = "old_value")
    public String oldValue;

    @Column(name = "new_value")
    public String newValue;

    @Column(name = "performed_by", nullable = false)
    public String performedBy;

    @Column(name = "performed_at", nullable = false)
    public Instant performedAt;

    @Column(name = "ip_address")
    public String ipAddress;

    @Column(name = "metadata")
    public String metadata;
}
