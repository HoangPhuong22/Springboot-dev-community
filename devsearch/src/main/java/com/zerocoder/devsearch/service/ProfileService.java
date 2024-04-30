package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Profile;

import java.util.List;

public interface ProfileService {
    void saveProfile(Profile profile);
    Profile getProfile(Long id);
    List<Profile> getAllProfiles();
    Profile getProfileAndSkillAndProject(Long id);
    void updateProfile(Profile profile);
    void deleteProfile(Long id);
}
