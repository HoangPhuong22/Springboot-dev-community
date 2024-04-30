package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Profile;

import java.util.List;

public interface ProfileDAO {
    void saveProfile(Profile profile);
    Profile getProfile(Long id);
    List<Profile> getAllProfiles();
    Profile getProfileAndSkillAndProject(Long id);
    void updateProfile(Profile profile);
    void deleteProfile(Long id);
}
