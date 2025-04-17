package com.pentryyy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.response.ResponseItem;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class ProjectsTest extends BaseTest {

    @Test
    @Order(1)
    void testCreateProject() {

        ResponseItem projectItem = 
            given()
                .contentType(ContentType.JSON)
                .body(project)
            .when()
                .post(UrlPaths.CREATE_PROJECT.toString())
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        project.setCreatedProjectId(projectItem.getId());

        assertNotNull(projectItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(projectItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    void testFindCurrentProject() {

        ResponseItem projectItem = 
            given()
            .when()
                .get(UrlPaths.FIND_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
            .then()
                .statusCode(200)
                .extract().as(ResponseItem.class);

        assertNotNull(projectItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(projectItem.getType(), "Поле '$type' отсутствует");

        assertEquals(projectItem.getId(), project.getCreatedProjectId(), "Поля 'id' не соответствуют");
    }

    @Test
    @Order(2)
    void testFindAllProjects() {

        List<ResponseItem> projects = 
            given()
            .when()
                .get(UrlPaths.FIND_ALL_PROJECTS.toString())
            .then()
                .extract().jsonPath().getList(".", ResponseItem.class);

        assertFalse(projects.isEmpty());

        ResponseItem project = projects.get(0);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");
    }
    
    @Test
    @Order(3)
    void testDeleteProject() {

        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
        .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> 
                given()
                .when()
                    .get(UrlPaths.FIND_PROJECT_BY_ID.withId(project.getCreatedProjectId()))
                .then()
                    .statusCode(404)
        );
    }
}
