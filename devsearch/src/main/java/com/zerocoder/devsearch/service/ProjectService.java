package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Project;

import java.util.List;

public interface ProjectService {
    void saveProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project getProjectTagById(Long id);
    List<Project> getProjectAndProfileAndTag();
    void updateProject(Project project);
    void deleteProject(Long id);

}
