package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Skill;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.SkillService;
import com.zerocoder.devsearch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/skills")
public class UserSkillController {
    private ProfileService profileService;
    private SkillService skillService;
    private UserService userService;
    @Autowired
    public UserSkillController(ProfileService profileService, SkillService skillService, UserService userService) {
        this.profileService = profileService;
        this.skillService = skillService;
        this.userService = userService;
    }
    @GetMapping("/add")
    public String skills(Model theModel)
    {
        theModel.addAttribute("skill", new Skill());
        return "users/add-skill-form";
    }
    @PostMapping("/add")
    public String addSkill(@Valid @ModelAttribute("skill") Skill skill, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "users/add-skill-form";
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);
        Profile profile = user.getProfile();
        skill.addProfile(profile);
        skillService.saveSkill(skill);
        return "redirect:/profiles/myaccount";
    }
    @GetMapping("edit/{id}")
    public String editSkill(@ModelAttribute("id") Long id, Model theModel)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        Skill skill = skillService.getSkillByProfileId(profile.getProfile_id(), id);
        theModel.addAttribute("skill", skill);
        return "users/edit-skill-form";
    }
    @PostMapping("edit")
    public String updateSkill(@Valid @ModelAttribute("skill") Skill skill, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "users/edit-skill-form";
        }
        List<Profile> profiles = skillService.getSkillById(skill.getSkill_id()).getProfile();
        skill.setProfile(profiles);
        skillService.updateSkill(skill);
        return "redirect:/profiles/myaccount";
    }
    @GetMapping("delete/{id}")
    public String deleteSkill(@ModelAttribute("id") Long id)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        Skill skill = skillService.getSkillByProfileId(profile.getProfile_id(), id);
        skillService.deleteSkill(skill.getSkill_id());
        return "redirect:/profiles/myaccount";
    }
}
