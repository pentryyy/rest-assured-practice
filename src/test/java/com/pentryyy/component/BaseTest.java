package com.pentryyy.component;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class BaseTest {
    
    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://193.233.193.42:9091")
            .addHeader("Authorization", "Bearer " + TokenManager.getToken())
            .build();
    }
}
