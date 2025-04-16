package com.pentryyy;

import org.junit.jupiter.api.Test;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;

import io.restassured.RestAssured;

public class IssuesTest extends BaseTest{
    
    /*
    Проверка, что страница открывается
    тест по большей части токена
    */
    @Test
    void testCeateIssue() {
        RestAssured
        .given()
        .when()
            .get(UrlPaths.ISSUES_URL.toString())
        .then()
            .statusCode(200);
    }
}
