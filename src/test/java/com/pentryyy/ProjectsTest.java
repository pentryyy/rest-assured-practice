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

import com.pentryyy.api.ProjectApiClient;
import com.pentryyy.component.BaseTest;
import com.pentryyy.dto.response.ResponseItem;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import static org.awaitility.Awaitility.await;

@Epic("Управление проектами")
@Feature("Полный жизненный цикл проекта")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class ProjectsTest extends BaseTest {

    @Test
    @Order(1)
    @Story("Создание проекта")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка успешного создания нового проекта с валидными данными")
    void testCreateProject() {

        ResponseItem responseItem = ProjectApiClient.createProject(project);

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    @Story("Поиск проектов")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Поиск созданного проекта по ID и проверка корректности данных")
    void testFindCurrentProject() {

        ResponseItem responseItem = ProjectApiClient.findProjectById(
            project, 
            200
        );

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");

        assertEquals(
            responseItem.getId(), 
            project.getCreatedProjectId(), 
            "Поля 'id' не соответствуют"
        );
    }

    @Test
    @Order(2)
    @Story("Поиск проектов")
    @Severity(SeverityLevel.NORMAL)
    @Description("Получение полного списка проектов в системе")
    void testFindAllProjects() {

        List<ResponseItem> projects = ProjectApiClient.findAllProjects();

        assertFalse(projects.isEmpty());

        ResponseItem project = projects.get(0);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");
    }
    
    @Test
    @Order(3)
    @Story("Удаление проекта")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка корректного удаления проекта и его отсутствия в системе")
    void testDeleteProject() {

        ProjectApiClient.deleteProject(project);

        await().atMost(Duration.ofSeconds(5))
            .pollInterval(Duration.ofSeconds(1))
            .untilAsserted(() -> {
                try {
                    ProjectApiClient.findProjectById(
                        project, 
                        404
                    );
                } catch (Exception e) {}
            }
        );
    }
}
