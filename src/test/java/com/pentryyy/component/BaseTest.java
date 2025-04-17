package com.pentryyy.component;

import org.junit.jupiter.api.BeforeAll;

import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.request.User;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class BaseTest {
    
    protected static User    leader;
    protected static Project project;

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

    @BeforeAll
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://193.233.193.42:9091")
            .addHeader("Authorization", "Bearer " + TokenManager.getToken())
            .build();
    }
}
