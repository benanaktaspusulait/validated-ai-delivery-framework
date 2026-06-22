package com.vaiddf.api.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "drift_history")
public class DriftHistoryEntity extends PanacheEntity {

    @Column(name = "record_id", nullable = false, unique = true)
    public String recordId;

    @Column(name = "model_name", nullable = false)
    public String modelName;

    @Column(name = "psi_score", nullable = false)
    public double psiScore;

    @Column(name = "severity", nullable = false)
    public String severity;

    @Column(name = "drift_detected", nullable = false)
    public boolean driftDetected;

    @Column(name = "checked_at", nullable = false)
    public Instant checkedAt;

    @Column(name = "checked_by")
    public String checkedBy;
}
