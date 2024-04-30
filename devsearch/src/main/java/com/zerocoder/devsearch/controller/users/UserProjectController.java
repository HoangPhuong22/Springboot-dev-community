package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class UserProjectController {
    private final ProjectService projectService;
    @Autowired
    public UserProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping("")
    public String projects(Model theModel)
    {
        theModel.addAttribute("projects", projectService.getProjectAndProfileAndTag());
        return "users/project-list";
    }
    @GetMapping("/{id}")
    public String projectDetails(@PathVariable("id") Long id, Model theModel)
    {
        Project project = projectService.getProjectById(id);
        theModel.addAttribute("project", project);
        return "users/project-details";
    }
}
