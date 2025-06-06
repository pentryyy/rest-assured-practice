package com.pentryyy.component;

import org.apache.commons.math3.exception.NullArgumentException;

public enum UrlPaths {

    // ---------------------- Url пути для работы с разделом проектов ------------------------
    CREATE_PROJECT("/api/admin/projects"),
    FIND_PROJECT_BY_ID("/api/admin/projects/{id}"),
    FIND_ALL_PROJECTS("/api/admin/projects"),
    DELETE_PROJECT_BY_ID("/api/admin/projects/{id}"),

    // ------------------------ Url пути для работы с разделом задач -------------------------
    CREATE_ISSUE("/api/issues"),
    FIND_ISSUE_BY_ID("/api/issues/{id}"),
    FIND_ALL_ISSUES("/api/issues"),
    CREATE_ISSUE_COMMENT("/api/issues/{id}/comments"),
    UPDATE_ISSUE_BY_ID("/api/issues/{id}"),
    DELETE_ISSUE_BY_ID("/api/issues/{id}");

    private final String path;
    
    UrlPaths(String path) {
        this.path = path;
    }

    public String withId(String id) {
        if (id == null) {
            throw new NullArgumentException();
        }

        return this.path.replace("{id}", id);
    }
    
    @Override
    public String toString() {
        return this.path;
    }
}
