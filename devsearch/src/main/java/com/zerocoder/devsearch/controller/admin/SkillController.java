package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Skill;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.SkillService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/skills")
public class SkillController {
    private SkillService skillService;
    private ProfileService profileService;

    @Autowired
    public SkillController(SkillService skillService, ProfileService profileService) {
        this.skillService = skillService;
        this.profileService = profileService;
    }
    @GetMapping("")
    public String skills(Model theModel) {
        theModel.addAttribute("skills", skillService.getAllSkills());
        return "admin/skill-list";
    }
    @GetMapping("/add")
    public String addSkill(Model theModel) {
        theModel.addAttribute("skill", new Skill());
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "admin/skill-add-form";
    }
    @PostMapping("/add")
    public String addSkill(@Valid @ModelAttribute("skill") Skill skill,
                           BindingResult bindingResult, Model theModel, HttpServletRequest request) {
        String[] profileID = request.getParameterValues("profileID");
        if (bindingResult.hasErrors() || profileID == null || profileID.length == 0) {
            if(profileID == null || profileID.length == 0)
                bindingResult.rejectValue("profiles", "error.skill", "Please select at least one profile");
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            return "admin/skill-add-form";
        }
        for(String id : profileID) {
            skill.addProfile(profileService.getProfile(Long.parseLong(id)));
        }
        skillService.saveSkill(skill);
        return "redirect:/admin/skills";
    }
    @GetMapping("/edit/{id}")
    public String editSkill(@PathVariable("id") Long id, Model theModel) {
        theModel.addAttribute("skill", skillService.getSkillById(id));
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "admin/skill-edit-form";
    }
    @PostMapping("/edit")
    public String editSkill(@Valid @ModelAttribute("skill") Skill skill,
                            BindingResult bindingResult, Model theModel, HttpServletRequest request) {
        String[] profileID = request.getParameterValues("profileID");
        if (bindingResult.hasErrors() || profileID == null || profileID.length == 0) {
            if(profileID == null || profileID.length == 0)
                bindingResult.rejectValue("profiles", "error.skill", "Please select at least one profile");
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            return "admin/skill-edit-form";
        }
        if(skill.getProfile() != null)
            skill.getProfile().clear();
        for(String id : profileID) {
            skill.addProfile(profileService.getProfile(Long.parseLong(id)));
        }
        skillService.updateSkill(skill);
        return "redirect:/admin/skills";
    }
    @GetMapping("/delete/{id}")
    public String deleteSkill(@PathVariable("id") Long id) {
        skillService.deleteSkill(id);
        return "redirect:/admin/skills";
    }
}
