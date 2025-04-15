package com.pentryyy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginTest {
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://193.233.193.42:9091";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @ParameterizedTest(name = "[{index}] {0}, {1}")
    @CsvFileSource(
        resources = "/LoginTestData.csv",
        numLinesToSkip = 1,
        delimiter = ';',
        nullValues = {"NULL", "N/A"}
    )
    void testLoginWithDifferentCredentials(
        String username,
        String password,
        String expectedResult
    ) {
        username = processEmptyValue(username);
        password = processEmptyValue(password);

        given()
            .contentType(ContentType.URLENC)
            .formParam("username", username)
            .formParam("password", password)
        .when()
            .post("/login")
        .then()
            .assertThat()
            .statusCode(getExpectedStatusCode(expectedResult))
            .body(
                expectedResult.equals("success") 
                    ? "access_token" 
                    : "error", 
                expectedResult.equals("success") 
                    ? notNullValue() 
                    : equalTo("Invalid credentials")
            );
    }

    private String processEmptyValue(String value) {
        return "Empty".equalsIgnoreCase(value.trim()) ? "" : value;
    }

    private int getExpectedStatusCode(String expectedResult) {
        return "success".equals(expectedResult) ? 200 : 401;
    }
}
