package com.mlplatform.webhooks;

import com.mlplatform.model.PredictionRequest;
import com.mlplatform.model.PredictionResponse;
import com.mlplatform.service.ModelService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PredictResource {

    @Inject
    ModelService modelService;

    @POST
    @Path("/predict")
    public Response predict(PredictionRequest request) {
        if (!modelService.isModelLoaded()) {
            return Response.status(503)
                .entity(new ErrorResponse("Model not loaded"))
                .build();
        }

        try {
            // Validate input
            if (request.features() == null || request.features().length == 0) {
                return Response.status(400)
                    .entity(new ErrorResponse("Features array required"))
                    .build();
            }

            // Simulate prediction (in production, call the loaded model)
            int prediction = ThreadLocalRandom.current().nextInt(0, 3);
            double[] probabilities = generateProbabilities(prediction);

            return Response.ok(new PredictionResponse(
                prediction,
                probabilities,
                modelService.getModelVersion(),
                Instant.now().toString()
            )).build();

        } catch (Exception e) {
            return Response.status(500)
                .entity(new ErrorResponse(e.getMessage()))
                .build();
        }
    }

    private double[] generateProbabilities(int predictedClass) {
        double[] probs = new double[3];
        ThreadLocalRandom rng = ThreadLocalRandom.current();
        double sum = 0;
        for (int i = 0; i < 3; i++) {
            probs[i] = rng.nextDouble();
            sum += probs[i];
        }
        for (int i = 0; i < 3; i++) {
            probs[i] = Math.round(probs[i] / sum * 1000.0) / 1000.0;
        }
        probs[predictedClass] = Math.round((1.0 - sum + probs[predictedClass]) * 1000.0) / 1000.0;
        return probs;
    }

    public record ErrorResponse(String error) {}
}
