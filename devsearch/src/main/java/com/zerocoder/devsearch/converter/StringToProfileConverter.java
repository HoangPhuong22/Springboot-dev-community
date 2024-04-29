package com.zerocoder.devsearch.converter;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToProfileConverter implements Converter<String, Profile> {
    @Autowired
    private ProfileService profileService;

    @Override
    public Profile convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        Long profileId = Long.parseLong(source);
        return profileService.getProfile(profileId);
    }
}