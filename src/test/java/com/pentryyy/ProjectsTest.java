package com.pentryyy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.dto.response.ResponseItem;
import com.pentryyy.steps.ProjectSteps;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

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

        ResponseItem responseItem = ProjectSteps.createProject(project);

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    @Story("Поиск проектов")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Поиск созданного проекта по ID и проверка корректности данных")
    void testFindCurrentProject() {

        ResponseItem responseItem = ProjectSteps.findProjectById(project);

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

        List<ResponseItem> projects = ProjectSteps.findAllProjects();

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
        ProjectSteps.deleteProject(project);
        ProjectSteps.waitUntilProjectIsDeleted(project, 5);
    }
}
