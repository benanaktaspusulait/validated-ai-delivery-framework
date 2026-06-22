package com.vaiddf.api.impl;

import com.vaiddf.api.entity.ModelEntity;
import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@ApplicationScoped
public class ReactiveModelRegistry {

    private static final ObjectMapper mapper = new ObjectMapper();

    public Uni<Model> register(Model model) {
        ModelEntity entity = toEntity(model);
        entity.modelId = UUID.randomUUID().toString();
        entity.status = ModelStatus.REGISTERED.name();
        entity.createdAt = Instant.now();
        entity.updatedAt = Instant.now();
        return Panache.withTransaction(entity::persist)
            .map(e -> toModel(e));
    }

    public Uni<Model> update(Model model) {
        return ModelEntity.find("modelId = ?1", model.id()).firstResult()
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().failure(new IllegalArgumentException("Model not found: " + model.id()));
                }
                existing.name = model.name();
                existing.version = model.version();
                existing.registry = model.registry();
                existing.updatedAt = Instant.now();
                if (model.metadata() != null) {
                    existing.metadata = mapToJson(model.metadata());
                }
                if (model.governance() != null) {
                    existing.governanceDriftCheck = model.governance().driftCheck();
                    existing.governanceFairnessRequired = model.governance().fairnessRequired();
                    existing.governanceMaxDriftPsi = model.governance().maxDriftPSI();
                    existing.governancePolicy = model.governance().policy();
                    existing.governanceMinConfidenceScore = model.governance().minConfidenceScore();
                }
                return Panache.withTransaction(existing::persist)
                    .map(e -> toModel(e));
            });
    }

    public Uni<Model> findById(String id) {
        return ModelEntity.find("modelId = ?1", id).firstResult()
            .map(e -> e != null ? toModel(e) : null);
    }

    public Uni<List<Model>> findByName(String name) {
        return ModelEntity.find("name = ?1", name).list()
            .map(list -> list.stream().map(this::toModel).toList());
    }

    public Uni<List<Model>> listByRegistry(String registry) {
        return ModelEntity.find("registry = ?1", registry).list()
            .map(list -> list.stream().map(this::toModel).toList());
    }

    public Uni<List<Model>> listAll() {
        return ModelEntity.findAll().list()
            .map(list -> list.stream().map(this::toModel).toList());
    }

    public Uni<Boolean> delete(String id) {
        return ModelEntity.find("modelId = ?1", id).firstResult()
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().item(false);
                }
                return Panache.withTransaction(existing::delete)
                    .map(v -> true);
            });
    }

    public Uni<Model> transitionStatus(String id, ModelStatus newStatus) {
        return ModelEntity.find("modelId = ?1", id).firstResult()
            .flatMap(existing -> {
                if (existing == null) {
                    return Uni.createFrom().failure(new IllegalArgumentException("Model not found: " + id));
                }
                existing.status = newStatus.name();
                existing.updatedAt = Instant.now();
                return Panache.withTransaction(existing::persist)
                    .map(e -> toModel(e));
            });
    }

    private ModelEntity toEntity(Model model) {
        ModelEntity entity = new ModelEntity();
        entity.modelId = model.id();
        entity.name = model.name();
        entity.version = model.version();
        entity.status = model.status() != null ? model.status().name() : ModelStatus.DRAFT.name();
        entity.registry = model.registry();
        entity.createdAt = model.createdAt() != null ? model.createdAt() : Instant.now();
        entity.updatedAt = model.updatedAt() != null ? model.updatedAt() : Instant.now();
        if (model.metadata() != null) {
            entity.metadata = mapToJson(model.metadata());
        }
        if (model.governance() != null) {
            entity.governanceDriftCheck = model.governance().driftCheck();
            entity.governanceFairnessRequired = model.governance().fairnessRequired();
            entity.governanceMaxDriftPsi = model.governance().maxDriftPSI();
            entity.governancePolicy = model.governance().policy();
            entity.governanceMinConfidenceScore = model.governance().minConfidenceScore();
        }
        return entity;
    }

    private Model toModel(ModelEntity entity) {
        Map<String, String> metadata = entity.metadata != null ? jsonToMap(entity.metadata) : Map.of();
        Model.GovernanceConfig governance = new Model.GovernanceConfig(
            entity.governanceDriftCheck,
            entity.governanceFairnessRequired,
            entity.governanceMaxDriftPsi,
            entity.governancePolicy,
            entity.governanceMinConfidenceScore
        );
        return new Model(
            entity.modelId,
            entity.name,
            entity.version,
            ModelStatus.valueOf(entity.status),
            entity.registry,
            entity.createdAt,
            entity.updatedAt,
            metadata,
            governance
        );
    }

    private String mapToJson(Map<String, String> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            return "{}";
        }
    }

    private Map<String, String> jsonToMap(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            Map<String, String> map = new java.util.HashMap<>();
            node.fields().forEachRemaining(entry ->
                map.put(entry.getKey(), entry.getValue().asText()));
            return map;
        } catch (Exception e) {
            return Map.of();
        }
    }
}
