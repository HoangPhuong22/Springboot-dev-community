package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.utils.SearchProfile;

import java.util.List;

public interface ProfileDAO {
    void saveProfile(Profile profile);
    Profile getProfile(Long id);
    List<Profile> getAllProfiles();
    Profile getProfileAndSkillAndProject(Long id);
    SearchProfile searchProfile(String keyword, int page, int size);
    void updateProfile(Profile profile);
    void deleteProfile(Long id);
}
