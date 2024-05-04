package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.ProfileDAO;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Skill;
import com.zerocoder.devsearch.utils.SearchProfile;
import com.zerocoder.devsearch.utils.checkFieldProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProfileDAOImpl implements ProfileDAO {
    private EntityManager entityManager;
    @Autowired
    public ProfileDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveProfile(Profile profile) {
        entityManager.persist(profile);
    }

    @Override
    public Profile getProfile(Long id) {
        return entityManager.find(Profile.class, id);
    }

    @Override
    public List<Profile> getAllProfiles() {
        List<Profile> profiles = entityManager.createQuery("from Profile").getResultList();
        return profiles;
    }

    @Override
    @EntityGraph(attributePaths = {"skills", "projects"})
    public Profile getProfileAndSkillAndProject(Long id) {
        TypedQuery<Profile> query = entityManager.createQuery("select p from Profile p where p.profile_id = :id", Profile.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public SearchProfile searchProfile(String keyword, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // First query for searching Profile by keyword
        CriteriaQuery<Profile> cq1 = cb.createQuery(Profile.class);
        Root<Profile> root1 = cq1.from(Profile.class);
        List<Predicate> predicates1 = new ArrayList<>();
        Predicate namePredicate1 = cb.like(root1.get("name"), "%" + keyword + "%");
        Predicate headlinePredicate1 = cb.like(root1.get("headline"), "%" + keyword + "%");
        Predicate bioPredicate1 = cb.like(root1.get("bio"), "%" + keyword + "%");
        Predicate addressPredicate1 = cb.like(root1.get("address"), "%" + keyword + "%");
        predicates1.add(cb.or(namePredicate1, headlinePredicate1, bioPredicate1, addressPredicate1));
        cq1.where(predicates1.toArray(new Predicate[0])).distinct(true);

        // Second query for searching Profile by Skill name
        CriteriaQuery<Profile> cq2 = cb.createQuery(Profile.class);
        Root<Profile> root2 = cq2.from(Profile.class);
        Join<Profile, Skill> skillJoin = root2.join("skills", JoinType.LEFT);
        Predicate skillPredicate = cb.like(skillJoin.get("name"), "%" + keyword + "%");
        cq2.where(skillPredicate).distinct(true);

        // Execute both queries
        TypedQuery<Profile> query1 = entityManager.createQuery(cq1);
        TypedQuery<Profile> query2 = entityManager.createQuery(cq2);

        // Merge the results using a Set to avoid duplicates
        Set<Profile> profileSet = new LinkedHashSet<>(query1.getResultList());
        profileSet.addAll(query2.getResultList());

        List<Profile> profiles = new ArrayList<>(profileSet);

        // sort
        profiles.sort((p1, p2) -> {
            int p1EmptyFields = checkFieldProfile.countEmptyFields(p1);
            int p2EmptyFields = checkFieldProfile.countEmptyFields(p2);

            if (p1EmptyFields != p2EmptyFields) {
                return Integer.compare(p1EmptyFields, p2EmptyFields);
            } else {
                return Comparator.comparing(Profile::getName)
                        .thenComparing(Profile::getHeadline)
                        .thenComparing(Profile::getBio)
                        .thenComparing(profile -> profile.getSkills().size())
                        .thenComparing(profile -> profile.getCreated())
                        .compare(p1, p2);
            }
        });

        // Pagination and other calculations
        int totalPages = profiles.size() / size;
        if(profiles.size() % size != 0) {
            totalPages++;
        }
        if(page > totalPages) {
            page = Math.max(1, totalPages);
        }
        int start = (page - 1) * size;
        int end = Math.min(start + size, profiles.size());
        profiles = profiles.subList(start, end);

        // Prepare the result
        SearchProfile searchProfile = new SearchProfile();
        searchProfile.setProfiles(profiles);
        searchProfile.setTotalCount(profiles.size());
        searchProfile.setTotalPages(totalPages);
        searchProfile.setCurrentPage(page);

        return searchProfile;
    }

    @Override
    public void updateProfile(Profile profile) {
        entityManager.merge(profile);
    }

    @Override
    public void deleteProfile(Long id) {
        Profile profile = entityManager.find(Profile.class, id);
        entityManager.remove(profile);
    }
}
