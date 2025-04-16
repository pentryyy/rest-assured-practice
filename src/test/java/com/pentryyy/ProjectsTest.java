package com.pentryyy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.pentryyy.component.BaseTest;
import com.pentryyy.component.TokenManager;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.component.UserManager;
import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.request.User;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class ProjectsTest extends BaseTest {
    
    private static User    leader;
    private static Project project;

    @BeforeAll
    static void setup() {

        String timestamp = String.valueOf(System.currentTimeMillis());

        String id        = "test-project-" + timestamp;
        String shortName = "TP" + timestamp;
        String name      = "Test Project " + timestamp;

        leader = User.builder()
                     .id(UserManager.getId())
                     .login(UserManager.getLogin())
                     .name(UserManager.getName())
                     .type(UserManager.getType())
                     .build();

        project = Project.builder()
                         .id(id)
                         .shortName(shortName)
                         .name(name)
                         .leader(leader)
                         .description("Project created via tests")
                         .archived(false)
                         .build();
    }

    @Test
    void testCreateProject() {

        given()
            .header("Authorization", "Bearer " + TokenManager.getToken())
            .contentType(ContentType.JSON)
            .body(project)
        .when()
            .post(UrlPaths.CREATE_PROJECT_PATH.toString())
        .then()
            .statusCode(200);
    }
}
