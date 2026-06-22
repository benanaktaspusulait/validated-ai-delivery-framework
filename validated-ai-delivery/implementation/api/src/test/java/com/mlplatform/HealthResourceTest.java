package com.mlplatform;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class HealthResourceTest {

    @Test
    void healthEndpointReturns200() {
        given()
            .when().get("/api/v1/health")
            .then()
            .statusCode(200)
            .body("status", notNullValue())
            .body("model_loaded", notNullValue());
    }

    @Test
    void metricsEndpointReturns200() {
        given()
            .when().get("/api/v1/metrics")
            .then()
            .statusCode(200)
            .body("model_name", notNullValue())
            .body("model_stage", notNullValue());
    }

    @Test
    void reloadEndpointReturns200() {
        given()
            .when().post("/api/v1/reload")
            .then()
            .statusCode(200)
            .body("status", equalTo("reloaded"));
    }
}
