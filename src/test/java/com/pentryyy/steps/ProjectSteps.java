package com.pentryyy.steps;

import io.qameta.allure.Step;
import java.util.List;

import com.pentryyy.api.ProjectApiClient;
import com.pentryyy.dto.request.Project;
import com.pentryyy.dto.response.ResponseItem;

public class ProjectSteps {

    @Step("Создать новый проект с валидными данными")
    public static ResponseItem createProject(Project project) {
        return ProjectApiClient.createProject(project);
    }

    @Step("Найти проект по ID")
    public static ResponseItem findProjectById(Project project) {
        return ProjectApiClient.findProjectById(project);
    }

    @Step("Получить список всех проектов")
    public static List<ResponseItem> findAllProjects() {
        return ProjectApiClient.findAllProjects();
    }

    @Step("Удалить проект по ID")
    public static void deleteProject(Project project) {
        ProjectApiClient.deleteProject(project);
    }

    @Step("Ожидание отсутствия проекта с ID (макс. {timeoutSeconds} сек)")
    public static void waitUntilProjectIsDeleted(Project project, long timeoutSeconds) {
        ProjectApiClient.waitUntilProjectIsDeleted(project, timeoutSeconds);
    }
}
