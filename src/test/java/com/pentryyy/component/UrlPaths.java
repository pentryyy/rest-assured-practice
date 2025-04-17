package com.pentryyy.component;

import org.apache.commons.math3.exception.NullArgumentException;

public enum UrlPaths {
    CREATE_PROJECT("/api/admin/projects"),
    FIND_PROJECT_BY_ID("/api/admin/projects/{id}"),
    FIND_ALL_PROJECTS("/api/admin/projects"),
    DELETE_PROJECT_BY_ID("/api/admin/projects/{id}");

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
