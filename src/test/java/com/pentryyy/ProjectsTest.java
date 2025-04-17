package com.pentryyy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.component.UserManager;
import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.request.User;
import com.pentryyy.dto.response.ProjectItem;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class ProjectsTest extends BaseTest {
    
    private static User    leader;
    private static Project project;

    private static String createdProjectId;

    @BeforeAll
    static void initDTO() {

        String timestamp = String.valueOf(System.currentTimeMillis());

        String id        = "test-project-" + timestamp;
        String shortName = "TP" + timestamp;
        String name      = "Test Project " + timestamp;

        leader = User.builder()
                     .id(UserManager.getId())
                     .type(UserManager.getType())
                     .build();

        project = Project.builder()
                         .id(id)
                         .shortName(shortName)
                         .name(name)
                         .leader(leader)
                         .description("Project created via tests")
                         .archived(false)
                         .template(false)
                         .build();
    }

    @Test
    @Order(1)
    void testCreateProject() {

        ProjectItem projectItem = 
            given()
                .contentType(ContentType.JSON)
                .body(project)
            .when()
                .post(UrlPaths.CREATE_PROJECT.toString())
            .then()
                .statusCode(200)
                .extract().as(ProjectItem.class);

        createdProjectId = projectItem.getId();

        assertNotNull(projectItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(projectItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    void testFindCurrentProject() {

        ProjectItem project = 
            given()
            .when()
                .get(UrlPaths.FIND_PROJECT_BY_ID.withId(createdProjectId))
            .then()
                .statusCode(200)
                .extract().as(ProjectItem.class);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");

        assertEquals(project.getId(), createdProjectId, "Поля 'id' не соответствуют");
    }

    @Test
    @Order(2)
    void testFindAllProjects() {

        List<ProjectItem> projects = 
            given()
            .when()
                .get(UrlPaths.FIND_ALL_PROJECTS.toString())
            .then()
                .extract().jsonPath().getList(".", ProjectItem.class);

        assertFalse(projects.isEmpty());

        ProjectItem project = projects.get(0);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");
    }
    
    @Test
    @Order(3)
    void testDeleteProject() {

        given()
        .when()
            .delete(UrlPaths.DELETE_PROJECT_BY_ID.withId(createdProjectId))
        .then()
            .statusCode(200);

        given()
        .when()
            .get(UrlPaths.FIND_PROJECT_BY_ID.withId(createdProjectId))
        .then()
            .statusCode(404);
    }
}
