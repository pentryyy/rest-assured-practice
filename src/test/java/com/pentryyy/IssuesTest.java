package com.pentryyy;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;

import io.restassured.http.ContentType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class IssuesTest extends BaseTest {

    @BeforeAll
    static void createProject(){
        given()
            .contentType(ContentType.JSON)
            .body(project)
        .when()
            .post(UrlPaths.CREATE_PROJECT.toString())
        .then()
            .statusCode(200);
    }

    @AfterAll
    static void deleteProject(){
        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(createdProjectId))
        .then()
            .statusCode(200);
    }

}
