package com.vaiddf.api.resource;

import com.vaiddf.core.model.DriftResult;
import com.vaiddf.core.spi.DriftDetector;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/api/v1/drift")
public class DriftResource {

    @Inject
    List<DriftDetector> detectors;

    @GET
    @Path("/detectors")
    public List<String> listDetectors() {
        return detectors.stream()
            .map(DriftDetector::name)
            .toList();
    }

    @POST
    @Path("/check")
    public DriftResult check(
        @QueryParam("detector") String detectorName,
        double[] reference,
        double[] current
    ) {
        DriftDetector detector = detectors.stream()
            .filter(d -> d.name().equals(detectorName != null ? detectorName : "psi"))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown detector: " + detectorName));

        return detector.check(reference, current);
    }
}
