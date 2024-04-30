package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.ProfileDAO;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    private ProfileDAO profileDAO;
    @Autowired
    public ProfileServiceImpl(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }
    @Override
    @Transactional
    public void saveProfile(Profile profile) {
        profileDAO.saveProfile(profile);
    }

    @Override
    public Profile getProfile(Long id) {
        try {
            return profileDAO.getProfile(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        try {
            return profileDAO.getAllProfiles();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Profile getProfileAndSkillAndProject(Long id) {
        try {
            return profileDAO.getProfileAndSkillAndProject(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateProfile(Profile profile) {
        profileDAO.updateProfile(profile);
    }

    @Override
    @Transactional
    public void deleteProfile(Long id) {
        profileDAO.deleteProfile(id);
    }
}
