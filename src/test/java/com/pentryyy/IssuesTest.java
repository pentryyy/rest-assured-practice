package com.pentryyy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.pentryyy.component.BaseTest;
import com.pentryyy.dto.request.CustomFields;
import com.pentryyy.dto.request.Issue;
import com.pentryyy.dto.request.IssueComment;
import com.pentryyy.dto.request.ProjectRef;
import com.pentryyy.dto.request.UpdateIssue;
import com.pentryyy.dto.request.Value;
import com.pentryyy.dto.response.ResponseItem;
import com.pentryyy.steps.IssueSteps;
import com.pentryyy.steps.ProjectSteps;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("Управление задачами")
@Feature("Полный цикл работы с задачами")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class IssuesTest extends BaseTest {

    private static Issue issue;

    @BeforeAll
    @Step("Создание тестового проекта")
    @Description("Создание проекта перед всеми тестами для обеспечения изолированного окружения")
    static void createProject() {
       ProjectSteps.createProject(project);
    }

    @Test
    @Order(1)
    @Story("Создание новой задачи")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка успешного создания задачи с валидными данными")
    void testCreateIssue() {

        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String summary     = "Тестовый заголовок " + timestamp; 
        String description = "Тестовое описание "  + timestamp;     

        issue = Issue.builder()
            .summary(summary)
            .description(description)
            .project(ProjectRef.builder()
                .id(project.getCreatedProjectId())
                .type("Project").build()
            ).build();

        ResponseItem responseItem = IssueSteps.createIssue(issue);

        issue.setCreatedIssueId(responseItem.getId());

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(2)
    @Story("Поиск задач")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Поиск созданной задачи по ID")
    void testFindCurrentIssue() {

        ResponseItem responseItem = IssueSteps.findCurrentIssue(issue);

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");

        assertEquals(
            responseItem.getId(), 
            issue.getCreatedIssueId(),
            "Поля 'id' не соответствуют"
        );
    }

    @Test
    @Order(2)
    @Story("Поиск задач")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Получение полного списка задач в системе")
    void testFindAllIssues() {

        List<ResponseItem> issues = IssueSteps.findAllIssues();

        assertFalse(issues.isEmpty());

        ResponseItem project = issues.get(0);

        assertNotNull(project.getId(), "Поле 'id' отсутствует");
        assertNotNull(project.getType(), "Поле '$type' отсутствует");
    }
    
    @Test
    @Order(2)
    @Story("Работа с комментариями")
    @Severity(SeverityLevel.NORMAL)
    @Description("Добавление комментария к существующей задаче")
    void testCreateIssueComment() {
        
        String timestamp = LocalDateTime
            .now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String text = "Комментарий к задаче " + timestamp;  

        IssueComment issueComment = IssueComment.builder()
            .text(text).build();
                                                
        ResponseItem responseItem = IssueSteps.createIssueComment(
            issue, 
            issueComment
        );

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(3)
    @Story("Редактирование задач")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Обновление данных существующей задачи")
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
            .customFields(Collections.singletonList(CustomFields.builder()
                .name("Priority")
                .value(Value.builder()
                    .name("Critical").build())
                .type("SingleEnumIssueCustomField").build()
            ))
            .type("Issue").build();

        ResponseItem responseItem = IssueSteps.updateIssue(
            issue, 
            updateIssue
        );

        issue.setCreatedIssueId(responseItem.getId());

        assertNotNull(responseItem.getId(), "Поле 'id' отсутствует");
        assertNotNull(responseItem.getType(), "Поле '$type' отсутствует");
    }

    @Test
    @Order(4)
    @Story("Удаление задач")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Проверка корректного удаления задачи")
    void testDeleteIssue() {
        IssueSteps.deleteIssue(issue);
        IssueSteps.waitUntilIssueIsDeleted(issue, 5);
    }

    @AfterAll
    @Step("Очистка тестовых данных")
    @Description("Удаление тестового проекта после выполнения всех тестов")
    static void deleteProject() {
        ProjectSteps.deleteProject(project);
    }
}
