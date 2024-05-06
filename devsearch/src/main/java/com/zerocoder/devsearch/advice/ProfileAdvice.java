package com.zerocoder.devsearch.advice;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ProfileAdvice {
    private final ProfileService profileService;
    private final UserService userService;
    @Autowired
    public ProfileAdvice(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }
    @ModelAttribute("profile")
    public Profile getUserProfile()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            try
            {
                String username = authentication.getName();
                User user = userService.getUserByUserName(username);
                return profileService.getProfile(user.getProfile().getProfile_id());
            }
            catch (Exception e)
            {
                return null;

            }
        }
        return null;
    }
}
