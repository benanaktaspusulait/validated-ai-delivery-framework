package com.vaiddf.api.resource;

import com.vaiddf.api.impl.HarnessMaturityScanner;
import com.vaiddf.api.impl.HarnessMaturityService;
import com.vaiddf.core.model.harness.HarnessMaturityAssessment;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/api/v1/harness-maturity")
public class HarnessMaturityResource {

    @Inject
    HarnessMaturityScanner scanner;

    @Inject
    HarnessMaturityService service;

    @GET
    public List<HarnessMaturityAssessment> findAll() {
        return service.findAll();
    }

    @GET
    @Path("/latest")
    public Response findLatest() {
        HarnessMaturityAssessment latest = service.findLatest();
        return latest != null
            ? Response.ok(latest).build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("/assess")
    public Response assess(HarnessAssessRequest request) {
        if (request.repositoryPath() == null || request.repositoryPath().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "repositoryPath is required"))
                .build();
        }

        java.nio.file.Path path = java.nio.file.Path.of(request.repositoryPath());
        if (!java.nio.file.Files.isDirectory(path)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Path is not a directory: " + request.repositoryPath()))
                .build();
        }

        HarnessMaturityAssessment assessment = scanner.scan(
            request.repositoryName() != null ? request.repositoryName() : path.getFileName().toString(),
            request.repositoryPath()
        );
        service.store(assessment);

        return Response.created(URI.create("/api/v1/harness-maturity/" + assessment.id()))
            .entity(assessment)
            .build();
    }

    public record HarnessAssessRequest(String repositoryName, String repositoryPath) {}
}
