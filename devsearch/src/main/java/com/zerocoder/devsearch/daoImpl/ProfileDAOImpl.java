package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.ProfileDAO;
import com.zerocoder.devsearch.entity.Profile;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public void updateProfile(Profile profile) {
        entityManager.merge(profile);
    }

    @Override
    public void deleteProfile(Long id) {
        Profile profile = entityManager.find(Profile.class, id);
        entityManager.remove(profile);
    }
}
