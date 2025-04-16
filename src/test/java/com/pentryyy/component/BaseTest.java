package com.pentryyy.component;

import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;

public class BaseTest {
    
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://193.233.193.42:9091";
        
        RestAssured
            .given()
                .auth().oauth2(TokenManager.getToken())
            .when()
                .post("/api/token")
            .then()
                .statusCode(200);
    }
}
