package com.pentryyy.component;

public enum UrlPaths {
    CREATE_PROJECT_PATH("/api/admin/projects");
    
    private final String path;
    
    UrlPaths(String path) {
        this.path = path;
    }
    
    @Override
    public String toString() {
        return this.path;
    }
}
