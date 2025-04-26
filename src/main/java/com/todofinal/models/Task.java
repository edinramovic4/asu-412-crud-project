package com.todofinal.models;

public class Task {
    private Long id;
    private String title;
    private boolean completed;
    private Long parentId;
    private String lastUpdated;

    public Task() {
    }

    public Task(Long id, String title, boolean completed, Long parentId, String lastUpdated) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.parentId = parentId;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}