package com.vaiddf.core.spi;

import com.vaiddf.core.model.Model;
import com.vaiddf.core.model.ModelStatus;
import java.util.List;
import java.util.Optional;

/**
 * SPI for model registry operations.
 * Implementations provide storage and retrieval of model metadata.
 */
public interface ModelRegistry {

    /**
     * Register a new model.
     */
    Model register(Model model);

    /**
     * Update an existing model.
     */
    Model update(Model model);

    /**
     * Find a model by ID.
     */
    Optional<Model> findById(String id);

    /**
     * Find models by name.
     */
    List<Model> findByName(String name);

    /**
     * List all models in a registry.
     */
    List<Model> listByRegistry(String registry);

    /**
     * List all models across all registries.
     */
    List<Model> listAll();

    /**
     * Delete a model by ID.
     */
    boolean delete(String id);

    /**
     * Transition model to a new status.
     */
    Model transitionStatus(String id, ModelStatus newStatus);
}
