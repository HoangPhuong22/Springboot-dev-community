package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.ProjectDAO;
import com.zerocoder.devsearch.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    private EntityManager entityManager;
    @Autowired
    public ProjectDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveProject(Project project) {
        entityManager.persist(project);
    }

    @Override
    public List<Project> getAllProjects() {
        List<Project> projects = entityManager.createQuery("from Project").getResultList();
        return projects;
    }

    @Override
    public Project getProjectById(Long id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public Project getProjectTagById(Long id) {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p left join fetch p.tag where p.id = :id", Project.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Project> getProjectAndProfileAndTag() {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p left join fetch p.profile left join fetch p.tag", Project.class);
        return query.getResultList();
    }

    @Override
    public void updateProject(Project project) {
        entityManager.merge(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = entityManager.find(Project.class, id);
        entityManager.remove(project);
    }
}
