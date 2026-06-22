-- VAIDDF Database Schema
-- Flyway Migration V1

CREATE TABLE models (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    version VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    registry VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    metadata JSONB,
    governance_drift_check BOOLEAN DEFAULT false,
    governance_fairness_required BOOLEAN DEFAULT false,
    governance_max_drift_psi DOUBLE PRECISION DEFAULT 0.2,
    governance_policy VARCHAR(255),
    governance_min_confidence_score INTEGER DEFAULT 70
);

CREATE INDEX idx_models_registry ON models(registry);
CREATE INDEX idx_models_status ON models(status);
CREATE INDEX idx_models_name ON models(name);

CREATE TABLE drift_history (
    id VARCHAR(255) PRIMARY KEY,
    model_name VARCHAR(255) NOT NULL,
    psi_score DOUBLE PRECISION NOT NULL,
    severity VARCHAR(50) NOT NULL,
    drift_detected BOOLEAN NOT NULL DEFAULT false,
    checked_at TIMESTAMP NOT NULL DEFAULT NOW(),
    checked_by VARCHAR(255) DEFAULT 'system'
);

CREATE INDEX idx_drift_history_model ON drift_history(model_name);
CREATE INDEX idx_drift_history_checked_at ON drift_history(checked_at);

CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    entity_id VARCHAR(255) NOT NULL,
    action VARCHAR(50) NOT NULL,
    old_value JSONB,
    new_value JSONB,
    performed_by VARCHAR(255) NOT NULL DEFAULT 'system',
    performed_at TIMESTAMP NOT NULL DEFAULT NOW(),
    ip_address VARCHAR(45),
    metadata JSONB
);

CREATE INDEX idx_audit_log_entity ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_performed_at ON audit_log(performed_at);
CREATE INDEX idx_audit_log_event_type ON audit_log(event_type);

-- Immutable: no UPDATE or DELETE grants for application role
-- This will be enforced at the application level
