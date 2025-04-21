package com.pentryyy.api;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.List;

import com.pentryyy.component.HttpMethod;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.request.Issue;
import com.pentryyy.dto.response.ResponseItem;

import io.restassured.http.ContentType;

public class IssueApiClient {
    
    public static <T> ResponseItem actionWithItem(T requestBody, String urlPath, HttpMethod httpMethod) {
        return  
            given()
                .contentType(ContentType.JSON)
                .body(requestBody)
            .when()
                .request(httpMethod.name(), urlPath)
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);
    }

    public static ResponseItem findCurrentIssue(Issue issue) {
        return
            given()
            .when()
                .get(UrlPaths.FIND_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);
    }

    public static List<ResponseItem> findAllIssues() {
        return
            given()
            .when()
                .get(UrlPaths.FIND_ALL_ISSUES.toString())
            .then()
                .extract().jsonPath().getList(".", ResponseItem.class);
    }

    public static void deleteIssue(Issue issue) {
        given()
        .when()
            .delete(UrlPaths.DELETE_ISSUE_BY_ID.withId(issue.getCreatedIssueId()))
        .then()
            .statusCode(200);
    }

    public static void waitUntilIssueIsDeleted(Issue issue, long timeoutSeconds) {
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
}
