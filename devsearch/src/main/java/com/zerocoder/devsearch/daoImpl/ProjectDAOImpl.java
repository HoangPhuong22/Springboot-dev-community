package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.ProjectDAO;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.entity.Tag;
import com.zerocoder.devsearch.utils.SearchProject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

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
    public Project getProjectByProfileId(Long profile_id, Long project_id) {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p left join fetch p.profile where p.profile.profile_id = :profile_id and p.id = :project_id", Project.class);
        query.setParameter("profile_id", profile_id);
        query.setParameter("project_id", project_id);
        return query.getSingleResult();
    }

    @Override
    public List<Project> getProjectAndProfileAndTag() {
        TypedQuery<Project> query = entityManager.createQuery("select p from Project p left join fetch p.profile left join fetch p.tag", Project.class);
        return query.getResultList();
    }

    @Override
    public SearchProject searchProject(String keyword, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // First query for searching Project by title
        CriteriaQuery<Project> cq1 = cb.createQuery(Project.class);
        Root<Project> root1 = cq1.from(Project.class);
        Predicate titlePredicate = cb.like(root1.get("title"), "%" + keyword + "%");
        cq1.where(titlePredicate).distinct(true);

        // Second query for searching Project by Tag name
        CriteriaQuery<Project> cq2 = cb.createQuery(Project.class);
        Root<Project> root2 = cq2.from(Project.class);
        Join<Project, Tag> tagJoin = root2.join("tag", JoinType.LEFT);
        Predicate tagPredicate = cb.like(tagJoin.get("name"), "%" + keyword + "%");
        cq2.where(tagPredicate).distinct(true);

        // Execute both queries
        TypedQuery<Project> query1 = entityManager.createQuery(cq1);
        TypedQuery<Project> query2 = entityManager.createQuery(cq2);

        // Merge the results using a LinkedHashSet to avoid duplicates and maintain insertion order
        Set<Project> projectSet = new LinkedHashSet<>(query1.getResultList());
        projectSet.addAll(query2.getResultList());

        // Convert the Set back to a List for further processing
        List<Project> projects = new ArrayList<>(projectSet);
        projects.sort(Comparator.comparing(Project::getVote_ratio)
                .thenComparing(Project::getVote_total)
                .thenComparing(Project::getTitle)
                .reversed());
        // Pagination and other calculations
        int totalPages = projects.size() / size;
        if(projects.size() % size != 0) {
            totalPages++;
        }
        if(page > totalPages) {
            page = Math.max(1, totalPages);
        }
        int start = (page - 1) * size;
        int end = Math.min(start + size, projects.size());
        projects = projects.subList(start, end);

        // Prepare the result
        SearchProject searchProject = new SearchProject();
        searchProject.setProjects(projects);
        searchProject.setTotalCount(projects.size());
        searchProject.setTotalPages(totalPages);
        searchProject.setCurrentPage(page);

        return searchProject;
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
