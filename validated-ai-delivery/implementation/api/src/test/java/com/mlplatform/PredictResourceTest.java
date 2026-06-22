package com.mlplatform;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class PredictResourceTest {

    @Test
    void predictWithValidInput() {
        given()
            .contentType("application/json")
            .body("{\"features\": [5.1, 3.5, 1.4, 0.2]}")
            .when().post("/api/v1/predict")
            .then()
            .statusCode(200)
            .body("prediction", notNullValue())
            .body("probability", notNullValue())
            .body("model_version", notNullValue())
            .body("timestamp", notNullValue());
    }

    @Test
    void predictWithEmptyFeatures() {
        given()
            .contentType("application/json")
            .body("{\"features\": []}")
            .when().post("/api/v1/predict")
            .then()
            .statusCode(400);
    }

    @Test
    void predictWithNullFeatures() {
        given()
            .contentType("application/json")
            .body("{\"features\": null}")
            .when().post("/api/v1/predict")
            .then()
            .statusCode(400);
    }
}
