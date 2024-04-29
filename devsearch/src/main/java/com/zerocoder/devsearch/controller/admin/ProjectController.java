package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.entity.Tag;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.service.TagService;
import com.zerocoder.devsearch.service.UserService;
import com.zerocoder.devsearch.utils.FileUploadUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/projects")
public class ProjectController {
    private UserService userService;
    private ProjectService projectService;
    private ProfileService profileService;
    private TagService tagService;
    @Autowired
    public ProjectController(UserService userService, ProjectService projectService, ProfileService profileService, TagService tagService) {
        this.userService = userService;
        this.projectService = projectService;
        this.profileService = profileService;
        this.tagService = tagService;
    }

    @GetMapping("")
    public String index(Model theModel)
    {
        theModel.addAttribute("projects", projectService.getAllProjects());
        return "admin/project-list";
    }
    @GetMapping("/add")
    public String add(Model theModel)
    {
        theModel.addAttribute("project", new Project());
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        theModel.addAttribute("tags", tagService.getAllTags());
        return "admin/project-add-form";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("project") Project project, BindingResult bindingResult, Model theModel,
                      @RequestParam("img") MultipartFile multipartFile,
                      HttpServletRequest request
    )
    {
        String[] tags = request.getParameterValues("tage");
        if(bindingResult.hasErrors() || multipartFile.isEmpty() || tags == null)
        {
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            theModel.addAttribute("tags", tagService.getAllTags());
            if(tags == null)
                bindingResult.rejectValue("tag", "error.project", "Please select tags");
            if(multipartFile.isEmpty())
                bindingResult.rejectValue("project_image", "error.project", "Please select an image");
            return "admin/project-add-form";
        }
        try {
            String uploadDir = "src/main/resources/static/media/projects";
            String fileName = FileUploadUtils.saveFile(uploadDir, multipartFile);
            project.setProject_image(fileName);
        } catch (IOException e) {
            bindingResult.rejectValue("project_image", "error.project", "Could not save file");
            return "admin/project-add-form";
        }
        for(String tagId : tags)
        {
            Tag tag = tagService.getTagById(Long.parseLong(tagId));
            project.addTag(tag);
        }
        projectService.saveProject(project);
        return "redirect:/admin/projects";
    }
    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, Model themodel)
    {
        Project project = projectService.getProjectTagById(id);
        System.out.println(project.getTag());
        themodel.addAttribute("project", project);
        themodel.addAttribute("profiles", profileService.getAllProfiles());
        themodel.addAttribute("tags", tagService.getAllTags());
        return "admin/project-edit-form";
    }
    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("project") Project project,
                         BindingResult theBindingResult,
                         Model theModel,
                         @RequestParam("img") MultipartFile multipartFile,
                         HttpServletRequest request
    ) {
        String[] tags = request.getParameterValues("tage");
        if (theBindingResult.hasErrors() || tags == null) {
            if(tags == null)
                theBindingResult.rejectValue("tag", "error.project", "Please select tags");

            theModel.addAttribute("profiles", profileService.getAllProfiles());
            theModel.addAttribute("tags", tagService.getAllTags());
            project.setProject_image(projectService.getProjectById(project.getProject_id()).getProject_image());
            return "admin/project-edit-form";
        }
        String fileName = projectService.getProjectById(project.getProject_id()).getProject_image();
        String newFileName = null;
        if (!multipartFile.isEmpty()) {
            String deleteDir = "src/main/resources/static/media/projects";
            try {
                String uploadDir = "src/main/resources/static/media/projects";
                newFileName = FileUploadUtils.saveFile(uploadDir, multipartFile);
                project.setProject_image(newFileName);
                FileUploadUtils.deleteFile(deleteDir, fileName);
            } catch (IOException e) {
                theBindingResult.rejectValue("project_image", "error.project", "Could not save file");
                return "admin/project-edit-form";
            }
        }
        if(project.getTag() != null)
            project.getTag().clear();
        for(String tagId : tags)
        {
            Tag tag = tagService.getTagById(Long.parseLong(tagId));
            project.addTag(tag);
        }

        if(newFileName == null) project.setProject_image(fileName);
        else project.setProject_image(newFileName);
        projectService.updateProject(project);
        return "redirect:/admin/projects";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id)
    {
        String fileName = projectService.getProjectById(id).getProject_image();
        String deleteDir = "src/main/resources/static/media/projects";
        FileUploadUtils.deleteFile(deleteDir, fileName);
        projectService.deleteProject(id);
        return "redirect:/admin/projects";
    }
}
