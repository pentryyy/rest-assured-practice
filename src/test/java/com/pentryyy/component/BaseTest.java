package com.pentryyy.component;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.request.User;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

@Owner("pentryyy")
@Tag("Infrastructure")
@Epic("Базовые настройки тестов")
@Feature("Инициализация тестового окружения")
public class BaseTest {
    
    protected static User    leader;
    protected static Project project;

    @BeforeAll
    @Step("Инициализация тестовых данных")
    @Description("Создание базовых сущностей для тестов: пользователь и проект")
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
    @Step("Настройка REST клиента")
    @Description("Конфигурация базовых параметров для REST Assured")
    public static void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://193.233.193.42:9091")
            .addHeader("Authorization", "Bearer " + TokenManager.getToken())
            .build();
    }
}
