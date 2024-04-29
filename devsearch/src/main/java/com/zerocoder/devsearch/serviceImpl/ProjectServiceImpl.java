package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.ProjectDAO;
import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.service.ProjectService;
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
