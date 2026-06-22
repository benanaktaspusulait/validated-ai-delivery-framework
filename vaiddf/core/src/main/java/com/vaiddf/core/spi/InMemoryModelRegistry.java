package com.vaiddf.core.spi;

import com.vaiddf.core.model.Model;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of ModelRegistry for development and testing.
 * Production should use a persistent implementation (PostgreSQL, etc.).
 */
@ApplicationScoped
public class InMemoryModelRegistry implements ModelRegistry {

    private final Map<String, Model> models = new ConcurrentHashMap<>();

    @Override
    public Model register(Model model) {
        Model registered = new Model(
            model.id(),
            model.name(),
            model.version(),
            Model.Status.REGISTERED,
            model.registry(),
            Instant.now(),
            Instant.now(),
            model.metadata(),
            model.governance()
        );
        models.put(registered.id(), registered);
        return registered;
    }

    @Override
    public Model update(Model model) {
        Model existing = models.get(model.id());
        if (existing == null) {
            throw new IllegalArgumentException("Model not found: " + model.id());
        }
        Model updated = new Model(
            existing.id(),
            model.name(),
            model.version(),
            existing.status(),
            model.registry(),
            existing.createdAt(),
            Instant.now(),
            model.metadata(),
            model.governance()
        );
        models.put(updated.id(), updated);
        return updated;
    }

    @Override
    public Optional<Model> findById(String id) {
        return Optional.ofNullable(models.get(id));
    }

    @Override
    public List<Model> findByName(String name) {
        return models.values().stream()
            .filter(m -> m.name().equals(name))
            .toList();
    }

    @Override
    public List<Model> listByRegistry(String registry) {
        return models.values().stream()
            .filter(m -> m.registry().equals(registry))
            .toList();
    }

    @Override
    public boolean delete(String id) {
        return models.remove(id) != null;
    }

    @Override
    public Model transitionStatus(String id, Model.Status newStatus) {
        Model existing = models.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Model not found: " + id);
        }
        Model updated = new Model(
            existing.id(),
            existing.name(),
            existing.version(),
            newStatus,
            existing.registry(),
            existing.createdAt(),
            Instant.now(),
            existing.metadata(),
            existing.governance()
        );
        models.put(id, updated);
        return updated;
    }
}
