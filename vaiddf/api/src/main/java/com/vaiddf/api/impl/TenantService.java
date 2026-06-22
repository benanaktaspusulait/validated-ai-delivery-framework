package com.vaiddf.api.impl;

import com.vaiddf.core.model.Tenant;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TenantService {

    private final Map<String, Tenant> tenants = new ConcurrentHashMap<>();

    public TenantService() {
        tenants.put("default", new Tenant(
            "default", "Default Tenant", "vaiddf",
            Set.of("default", "production"),
            new Tenant.TenantConfig(true, 100, true, Set.of("scikit-learn", "tensorflow")),
            Instant.now()
        ));
        tenants.put("enterprise", new Tenant(
            "enterprise", "Enterprise Tenant", "vaiddf-enterprise",
            Set.of("production", "staging", "development"),
            new Tenant.TenantConfig(true, 1000, true,
                Set.of("scikit-learn", "tensorflow", "pytorch", "jax")),
            Instant.now()
        ));
    }

    public List<Tenant> findAll() {
        return List.copyOf(tenants.values());
    }

    public Tenant findById(String id) {
        return tenants.get(id);
    }

    public Tenant create(Tenant tenant) {
        tenants.put(tenant.id(), tenant);
        return tenant;
    }

    public Tenant update(String id, Tenant tenant) {
        tenants.put(id, tenant);
        return tenant;
    }

    public boolean delete(String id) {
        return tenants.remove(id) != null;
    }
}
