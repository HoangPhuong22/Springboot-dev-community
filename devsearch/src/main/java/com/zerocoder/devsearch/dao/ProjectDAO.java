package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Project;

import java.util.List;

public interface ProjectDAO {
    void saveProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project getProjectTagById(Long id);
    List<Project> getProjectAndProfileAndTag();
    void updateProject(Project project);
    void deleteProject(Long id);
}
