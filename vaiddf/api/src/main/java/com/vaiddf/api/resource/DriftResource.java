package com.vaiddf.api.resource;

import com.vaiddf.api.impl.PSIDetector;
import com.vaiddf.core.model.DriftResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;

@Path("/api/v1/drift")
public class DriftResource {

    @Inject
    PSIDetector psiDetector;

    @GET
    @Path("/detectors")
    public List<String> listDetectors() {
        return List.of("psi");
    }

    @POST
    @Path("/check")
    public DriftResult check(DriftCheckRequest request) {
        return psiDetector.check(request.reference(), request.current());
    }
}
