package com.zerocoder.devsearch.utils;

import com.zerocoder.devsearch.entity.Profile;

public class checkFieldProfile {
    public static int countEmptyFields(Profile profile) {
        int emptyFields = 0;
        if (isBlank(profile.getName())) emptyFields++;
        if (isBlank(profile.getHeadline())) emptyFields++;
        if (isBlank(profile.getBio())) emptyFields++;
        if (isBlank(profile.getAddress())) emptyFields++;
        if (profile.getSkills() == null || profile.getSkills().isEmpty()) emptyFields++;
        return emptyFields;
    }

    private static boolean isBlank(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }
}
