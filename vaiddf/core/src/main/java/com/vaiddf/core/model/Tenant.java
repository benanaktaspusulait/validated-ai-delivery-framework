package com.vaiddf.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Tenant(
    String id,
    String name,
    String keycloakRealm,
    Set<String> allowedRegistries,
    TenantConfig config,
    Instant createdAt
) {
    public record TenantConfig(
        boolean enabled,
        int maxModels,
        boolean auditLogRequired,
        Set<String> allowedFrameworks
    ) {}
}
