package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    private ProjectService projectService;
    private UserService userService;
    private TagService tagService;
    private ReviewService reviewService;
    private ProfileService profileService;
    private MessageService messageService;
    private RoleService roleService;
    @Autowired
    public DashboardController(ProjectService projectService, UserService userService, TagService tagService, ReviewService reviewService, ProfileService profileService, MessageService messageService, RoleService roleService) {
        this.projectService = projectService;
        this.userService = userService;
        this.tagService = tagService;
        this.reviewService = reviewService;
        this.profileService = profileService;
        this.messageService = messageService;
        this.roleService = roleService;
    }
    @GetMapping("")
    public String dashboard() {
        return "redirect:admin/dashboard";
    }
    @GetMapping("/dashboard")
    public String index(Model theModel) {
        theModel.addAttribute("projects", projectService.getAllProjects());
        theModel.addAttribute("users", userService.getAllUsers());
        theModel.addAttribute("tags", tagService.getAllTags());
        theModel.addAttribute("reviews", reviewService.getAllReviews());
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        theModel.addAttribute("messages", messageService.getAllMessages());
        theModel.addAttribute("roles", roleService.getAllRoles());
        return "admin/dashboard";
    }
}
