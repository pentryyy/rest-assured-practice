package com.pentryyy.steps;

import java.util.List;

import com.pentryyy.api.IssueApiClient;
import com.pentryyy.component.HttpMethod;
import com.pentryyy.component.UrlPaths;
import com.pentryyy.dto.request.Issue;
import com.pentryyy.dto.request.IssueComment;
import com.pentryyy.dto.request.UpdateIssue;
import com.pentryyy.dto.response.ResponseItem;

import io.qameta.allure.Step;

public class IssueSteps {
    
    @Step("Создать новую задачу с валидными данными")
    public static ResponseItem createIssue(Issue issue) {
        return IssueApiClient.actionWithItem(
            issue, 
            UrlPaths.CREATE_ISSUE.toString(),
            HttpMethod.POST
        );
    }

    @Step("Найти задачу по ID")
    public static ResponseItem findCurrentIssue(Issue issue) {
        return IssueApiClient.findCurrentIssue(issue);
    }

    @Step("Получить список всех задач")
    public static List<ResponseItem> findAllIssues() {
        return IssueApiClient.findAllIssues();
    }

    @Step("Создать новый комментарий к существующей задаче")
    public static ResponseItem createIssueComment(Issue issue, IssueComment issueComment) {
        return IssueApiClient.actionWithItem(
            issueComment, 
            UrlPaths.CREATE_ISSUE_COMMENT.withId(issue.getCreatedIssueId()),
            HttpMethod.POST
        );
    }

    @Step("Обновить данные уже существующей задачи")
    public static ResponseItem updateIssue(Issue issue, UpdateIssue updateIssue) {
        return IssueApiClient.actionWithItem(
            updateIssue, 
            UrlPaths.UPDATE_ISSUE_BY_ID.withId(issue.getCreatedIssueId()), 
            HttpMethod.PUT
        );
    }

    @Step("Удалить задачу по ID")
    public static void deleteIssue(Issue issue) {
        IssueApiClient.deleteIssue(issue);
    }

    @Step("Ожидание отсутствия задачи с ID (макс. {timeoutSeconds} сек)")
    public static void waitUntilIssueIsDeleted(Issue issue, long timeoutSeconds) {
        IssueApiClient.waitUntilIssueIsDeleted(issue, 5);
    }
}
