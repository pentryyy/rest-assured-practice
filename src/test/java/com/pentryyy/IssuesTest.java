package com.pentryyy;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.request.Issue;
import com.pentryyy.dto.request.ProjectRef;
import com.pentryyy.dto.response.ResponseItem;

import io.restassured.http.ContentType;

import static org.awaitility.Awaitility.await;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class IssuesTest extends BaseTest {

    private static Issue issue;

    @BeforeAll
    static void createProject(){

        String createdProjectId =
            given()
                .contentType(ContentType.JSON)
                .body(project)
            .when()
                .post(UrlPaths.CREATE_PROJECT.toString())
            .then()
                .statusCode(200)
                .extract().path("id");

        project.setCreatedProjectId(createdProjectId);
    }

    @Test
    @Order(1)
    void testCreateIssue() {

        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String summary     = "Тестовый заголовок " + timestamp; 
        String description = "Тестовое описание "  + timestamp;     

        issue = Issue.builder()
                     .summary(summary)
                     .description(description)
                     .project(ProjectRef
                        .builder()
                        .id(project.getCreatedProjectId())
                        .build()
                     )
                     .build();

        ResponseItem projectItem = 
            given()
                .contentType(ContentType.JSON)
                .body(issue)
            .when()
                .post(UrlPaths.CREATE_ISSUE.toString())
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        issue.setCreatedIssueId(projectItem.getId());

        assertNotNull(projectItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(projectItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(3)
    void testDeleteProject() {

        given()
        .when()
            .delete(UrlPaths.DELETE_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
        .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> 
                given()
                .when()
                    .get(UrlPaths.FIND_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
                .then()
                    .statusCode(404)
        );
    }

    @AfterAll
    static void deleteProject(){

        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
        .then()
            .statusCode(200);
    }
}
