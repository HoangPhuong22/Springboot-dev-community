package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.utils.SearchProject;

import java.util.List;

public interface ProjectDAO {
    void saveProject(Project project);
    List<Project> getAllProjects();
    Project getProjectById(Long id);
    Project getProjectTagById(Long id);
    Project getProjectByProfileId(Long profile_id, Long project_id);
    List<Project> getProjectAndProfileAndTag();
    SearchProject searchProject(String keyword, int page, int size);
    void updateProject(Project project);
    void deleteProject(Long id);
}
