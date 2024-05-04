package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.ProjectDAO;
import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.utils.SearchProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDAO projectDAO;
    @Override
    @Transactional
    public void saveProject(Project project) {
        projectDAO.saveProject(project);
    }

    @Override
    public List<Project> getAllProjects() {
        try
        {
            return projectDAO.getAllProjects();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public Project getProjectById(Long id) {
        return projectDAO.getProjectById(id);
    }

    @Override
    public Project getProjectTagById(Long id) {
        return projectDAO.getProjectTagById(id);
    }

    @Override
    public Project getProjectByProfileId(Long profile_id, Long project_id) {
        return projectDAO.getProjectByProfileId(profile_id, project_id);
    }

    @Override
    public List<Project> getProjectAndProfileAndTag() {
        return projectDAO.getProjectAndProfileAndTag();
    }

    @Override
    public SearchProject searchProject(String keyword, int page, int size) {
        return projectDAO.searchProject(keyword, page, size);
    }

    @Override
    @Transactional
    public void updateProject(Project project) {
        projectDAO.updateProject(project);
    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        projectDAO.deleteProject(id);
    }
}
