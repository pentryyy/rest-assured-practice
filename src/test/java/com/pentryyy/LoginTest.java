package com.pentryyy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

import static org.hamcrest.Matchers.*;

public class LoginTest {
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://193.233.193.42:9091";
    }
    
    @Test
    void testSuccessfulAuthAndRedirect() {
        RestAssured
            .given()
                .queryParam("response_type", "token")
                .queryParam("client_id", "cf6f74d5-c1b8-457f-9d4b-2348fe19440f")
                .queryParam("redirect_uri", "http://193.233.193.42:9091/oauth")
                .queryParam("scope", "cf6f74d5-c1b8-457f-9d4b-2348fe19440f Upsource TeamCity YouTrack%20Slack%20Integration 0-0-0-0-0")
                .queryParam("state", "5fc6f8b5-e3b6-457d-9e8a-40c7c9b8a6df")
                
                .formParam("username", "volikov_mikhail")
                .formParam("password", "2104youtrack")
                
                .redirects().follow(false)
            .when()
                .post("/hub/auth/login")
            .then()
                .statusCode(302)
                .header("Location", containsString("/oauth"));
    }
}
