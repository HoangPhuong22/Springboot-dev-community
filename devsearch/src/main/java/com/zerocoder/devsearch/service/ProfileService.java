package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.utils.SearchProfile;

import java.util.List;

public interface ProfileService {
    void saveProfile(Profile profile);
    Profile getProfile(Long id);
    List<Profile> getAllProfiles();
    SearchProfile searchProfile(String keyword, int page, int size);
    Profile getProfileAndSkillAndProject(Long id);
    void updateProfile(Profile profile);
    void deleteProfile(Long id);
}
