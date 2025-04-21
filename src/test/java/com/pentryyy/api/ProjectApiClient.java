package com.pentryyy.api;

import static io.restassured.RestAssured.given;

import java.util.List;

import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.response.ResponseItem;

import io.restassured.http.ContentType;

public class ProjectApiClient {

    public static ResponseItem createProject(Project project) {
        ResponseItem responseItem = 
            given()
                .contentType(ContentType.JSON)
                .body(project)
            .when()
                .post(UrlPaths.CREATE_PROJECT.toString())
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        project.setCreatedProjectId(responseItem.getId());

        return responseItem;
    }

    public static ResponseItem findProjectById(Project project, int expectedStatusCode) {
        return 
            given()
            .when()
                .get(UrlPaths.FIND_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
            .then()
                .statusCode(expectedStatusCode)
                .extract().as(ResponseItem.class);
    }

    public static  List<ResponseItem> findAllProjects() {
        return
            given()
            .when()
                .get(UrlPaths.FIND_ALL_PROJECTS.toString())
            .then()
                .extract().jsonPath().getList(".", ResponseItem.class);
    }

    public static void deleteProject(Project project) {
        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
        .then()
            .statusCode(200);
    }
}
