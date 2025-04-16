package com.pentryyy.component;

import org.junit.jupiter.api.BeforeEach;

import io.restassured.RestAssured;

public class BaseTest {
    
    @BeforeEach
    void beforeTest() {
        RestAssured.baseURI = "http://193.233.193.42:9091";
    }
}
