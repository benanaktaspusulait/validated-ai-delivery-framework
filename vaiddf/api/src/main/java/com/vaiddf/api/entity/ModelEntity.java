package com.vaiddf.api.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "models")
public class ModelEntity extends PanacheEntity {

    @Column(name = "model_id", nullable = false, unique = true)
    public String modelId;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "version", nullable = false)
    public String version;

    @Column(name = "status", nullable = false)
    public String status;

    @Column(name = "registry", nullable = false)
    public String registry;

    @Column(name = "created_at", nullable = false)
    public Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    public Instant updatedAt;

    @Column(name = "metadata")
    public String metadata;

    @Column(name = "governance_drift_check")
    public boolean governanceDriftCheck;

    @Column(name = "governance_fairness_required")
    public boolean governanceFairnessRequired;

    @Column(name = "governance_max_drift_psi")
    public double governanceMaxDriftPsi;

    @Column(name = "governance_policy")
    public String governancePolicy;

    @Column(name = "governance_min_confidence_score")
    public int governanceMinConfidenceScore;
}
