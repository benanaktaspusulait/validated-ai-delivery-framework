package com.vaiddf.api.resource;

import com.vaiddf.api.impl.DriftHistoryService;
import com.vaiddf.api.impl.PSIDetector;
import com.vaiddf.core.model.DriftHistory;
import com.vaiddf.core.model.DriftResult;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.List;

@Path("/api/v1/drift")
public class DriftResource {

    @Inject
    PSIDetector psiDetector;

    @Inject
    DriftHistoryService historyService;

    @GET
    @Path("/detectors")
    public List<String> listDetectors() {
        return List.of("psi");
    }

    @GET
    @Path("/history")
    public List<DriftHistory> getHistory() {
        return historyService.getAll();
    }

    @GET
    @Path("/history/{model}")
    public List<DriftHistory> getHistoryByModel(@QueryParam("model") String modelName) {
        return historyService.getByModel(modelName);
    }

    @POST
    @Path("/check")
    public DriftResult check(DriftCheckRequest request) {
        DriftResult result = psiDetector.check(request.reference(), request.current());
        historyService.record("unknown", result);
        return result;
    }

    @POST
    @Path("/check/{modelName}")
    public DriftResult checkForModel(String modelName, DriftCheckRequest request) {
        DriftResult result = psiDetector.check(request.reference(), request.current());
        historyService.record(modelName, result);
        return result;
    }
}
