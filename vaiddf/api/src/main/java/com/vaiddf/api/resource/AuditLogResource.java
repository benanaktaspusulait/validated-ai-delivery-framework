package com.vaiddf.api.resource;

import com.vaiddf.api.impl.AuditLogService;
import com.vaiddf.api.entity.AuditLogEntity;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/api/v1/audit")
public class AuditLogResource {

    @Inject
    AuditLogService auditLogService;

    @GET
    public Uni<List<AuditLogEntity>> findAll() {
        return auditLogService.findAll();
    }

    @GET
    @Path("/entity")
    public Uni<List<AuditLogEntity>> findByEntity(
        @QueryParam("type") String entityType,
        @QueryParam("id") String entityId) {
        return auditLogService.findByEntity(entityType, entityId);
    }

    @GET
    @Path("/recent")
    public Uni<List<AuditLogEntity>> findRecent(
        @QueryParam("limit") int limit) {
        return auditLogService.findRecent(limit > 0 ? limit : 50);
    }
}
