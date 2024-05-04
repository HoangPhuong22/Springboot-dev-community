package com.zerocoder.devsearch.utils;

import com.zerocoder.devsearch.entity.Project;

import java.util.List;

public class SearchProject {
    private List<Project> projects;
    private int totalCount;
    private int totalPages;
    private int currentPage;

    public SearchProject() {
    }

    public SearchProject(List<Project> projects, int totalCount, int totalPages, int currentPage) {
        this.projects = projects;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
