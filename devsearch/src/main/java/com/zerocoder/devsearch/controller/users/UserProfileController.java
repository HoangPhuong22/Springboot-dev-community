package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profiles")
public class UserProfileController {
    private final ProfileService profileService;
    @Autowired
    public UserProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @GetMapping("")
    public String profile(Model theModel)
    {
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "users/profile-list";
    }
    @GetMapping("/{id}")
    public String profileDetail(@PathVariable("id") Long id, Model theModel)
    {
        Profile profile = profileService.getProfileAndSkillAndProject(id);
        theModel.addAttribute("profile", profile);
        return "users/profile-detail";
    }
}
