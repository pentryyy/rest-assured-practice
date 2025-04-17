package com.pentryyy;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.request.CustomFields;
import com.pentryyy.dto.request.Issue;
import com.pentryyy.dto.request.IssueComment;
import com.pentryyy.dto.request.ProjectRef;
import com.pentryyy.dto.request.UpdateIssue;
import com.pentryyy.dto.request.Value;
import com.pentryyy.dto.response.ResponseItem;

import io.restassured.http.ContentType;

import static org.awaitility.Awaitility.await;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class IssuesTest extends BaseTest {

    private static Issue issue;

    @BeforeAll
    static void createProject() {

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

        ResponseItem responseItem = 
            given()
                .contentType(ContentType.JSON)
                .body(issue)
            .when()
                .post(UrlPaths.CREATE_ISSUE.toString())
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        issue.setCreatedIssueId(responseItem.getId());

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    void testFindCurrentIssue() {

        ResponseItem responseItem = 
            given()
            .when()
                .get(UrlPaths.FIND_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");

        assertEquals(responseItem.getId(), issue.getCreatedIssueId(), "Поля 'id' не соответствуют");
    }

    @Test
    @Order(2)
    void testFindAllIssues() {

        List<ResponseItem> issues = 
            given()
            .when()
                .get(UrlPaths.FIND_ALL_ISSUES.toString())
            .then()
                .extract().jsonPath().getList(".", ResponseItem.class);

        assertFalse(issues.isEmpty());

        ResponseItem project = issues.get(0);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");
    }
    
    @Test
    @Order(2)
    void testCreateIssueComment() {
        
        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String text = "Комментарий к задаче " + timestamp;  

        IssueComment issueComment = IssueComment.builder()
                                                .text(text)
                                                .build();
                                                
        ResponseItem responseItem = 
            given()
                .contentType(ContentType.JSON)
                .body(issueComment)
            .when()
                .post(UrlPaths.CREATE_ISSUE_COMMENT.withId(issue.getCreatedIssueId()))
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Disabled
    @Order(3)
    void testUpdateIssue() {

         String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String summary     = "Новый тестовый заголовок " + timestamp; 
        String description = "Новое тестовое описание "  + timestamp;    

        UpdateIssue updateIssue = UpdateIssue.builder()
            .summary(summary)
            .description(description)
            .project(ProjectRef.builder()
                .id(project.getCreatedProjectId())
                .type("Project").build()
            )
            .customFields(CustomFields.builder()
                .name("Priority")
                .value(Value.builder()
                    .name("Critical").build()
                )
                .type("SingleEnumIssueCustomField").build()
            )
            .type("Issue").build();
                                                
        ResponseItem responseItem = 
            given()
                .contentType(ContentType.JSON)
                .body(updateIssue)
            .when()
                .put(UrlPaths.UPDATE_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        issue.setCreatedIssueId(responseItem.getId());

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");

    }

    @Test
    @Order(4)
    void testDeleteIssue() {

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
    static void deleteProject() {

        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
        .then()
            .statusCode(200);
    }
}
