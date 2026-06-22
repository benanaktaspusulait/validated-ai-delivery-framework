package com.vaiddf.api.resource;

import com.vaiddf.api.impl.DemographicParityDetector;
import com.vaiddf.core.model.FairnessResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.List;

@Path("/api/v1/fairness")
public class FairnessResource {

    @Inject
    DemographicParityDetector detector;

    @GET
    @Path("/detectors")
    public List<String> listDetectors() {
        return List.of("demographic_parity");
    }

    @POST
    @Path("/check")
    public FairnessResult check(FairnessCheckRequest request) {
        return detector.check(
            request.modelId(),
            request.predictions(),
            request.actuals(),
            request.protectedAttribute(),
            request.groupData() != null ? request.groupData() : java.util.Map.of()
        );
    }

    public record FairnessCheckRequest(
        String modelId,
        double[] predictions,
        double[] actuals,
        int[] protectedAttribute,
        java.util.Map<String, double[]> groupData
    ) {}
}
