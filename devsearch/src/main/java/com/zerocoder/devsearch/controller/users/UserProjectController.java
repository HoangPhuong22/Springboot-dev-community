package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.*;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.service.ReviewService;
import com.zerocoder.devsearch.service.TagService;
import com.zerocoder.devsearch.service.UserService;
import com.zerocoder.devsearch.utils.FileUploadUtils;
import com.zerocoder.devsearch.utils.SearchProject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class UserProjectController {
    private final ProjectService projectService;
    private final TagService tagService;
    private final UserService userService;
    private final ReviewService reviewService;
    @Autowired
    public UserProjectController(ProjectService projectService, TagService tagService, UserService userService, ReviewService reviewService)
    {
        this.projectService = projectService;
        this.tagService = tagService;
        this.userService = userService;
        this.reviewService = reviewService;
    }
    @GetMapping("")
    public String projects(Model theModel,
                           @RequestParam(defaultValue = "") String keyword,
                           @RequestParam(defaultValue = "1") int page)
    {
        try
        {
            page = Integer.parseInt(String.valueOf(page));
        }
        catch (NumberFormatException e)
        {
            page = 1;
        }
        int size = 3;
        SearchProject result = projectService.searchProject(keyword, page, size);
        List<Project> projects = result.getProjects();
        int totalPages = result.getTotalPages();
        int currentPage = result.getCurrentPage();
        int range = 5;
        int left = Math.max(1, currentPage - 2);
        int right = Math.min(currentPage + 2, totalPages);
        if (left == 1) right = Math.min(range, totalPages);
        if (right == totalPages) left = Math.max(totalPages - range + 1, 1);

        List<Integer> pages = new java.util.ArrayList<>();
        for (int i = left; i <= right; i++) {
            pages.add(i);
        }
        theModel.addAttribute("pages", pages);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("page", currentPage);
        theModel.addAttribute("keyword", keyword);
        theModel.addAttribute("projects", projects);
        return "users/project-list";
    }
    @GetMapping("/{id}")
    public String projectDetails(@PathVariable("id") Long id, Model theModel, Authentication authentication)
    {
        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        Project project = projectService.getProjectById(id);
        int ok = 1;
        for(Review r : reviewService.getAllReviews()) {
            if(r.getProfile().getProfile_id() == profile.getProfile_id() &&
                    r.getProject().getProject_id() == project.getProject_id()){
                ok = 0;
                break;
            }
        }
        if(project.getProfile().getProfile_id() == profile.getProfile_id())
            ok = 2;
        theModel.addAttribute("project", project);
        theModel.addAttribute("review", new Review());
        theModel.addAttribute("ok", ok);
        return "users/project-details";
    }
    @GetMapping("/add")
    public String addProject(Model theModel)
    {
        theModel.addAttribute("project", new Project());
        theModel.addAttribute("tags", tagService.getAllTags());
        return "users/add-project-form";
    }
    @PostMapping("/add")
    public String addProject(@Valid @ModelAttribute("project") Project project,
                             BindingResult bindingResult, Model theModel,
                             HttpServletRequest request,
                             @RequestParam("img") MultipartFile file
    )
    {
        String[] tagIds = request.getParameterValues("tagId");
        if(bindingResult.hasErrors() || tagIds == null || tagIds.length == 0 || file.isEmpty())
        {
            if(file.isEmpty())
                bindingResult.rejectValue("project_image", "error.project", "Please select an image");
            if(tagIds == null || tagIds.length == 0)
                bindingResult.rejectValue("tag", "error.project", "Please select at least one tag");
            theModel.addAttribute("tags", tagService.getAllTags());
            theModel.addAttribute("tags", tagService.getAllTags());
            return "users/add-project-form";
        }
        try
        {
            String dir = "src/main/resources/static/media/projects";
            String fileName = FileUploadUtils.saveFile(dir, file);
            project.setProject_image(fileName);
        } catch (IOException e) {
            bindingResult.rejectValue("project_image", "error.project", "Could not save file");
            theModel.addAttribute("tags", tagService.getAllTags());
            return "users/add-project-form";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        project.setProfile(profile);
        if(project.getTag() != null)
            project.getTag().clear();
        for(String tagId : tagIds)
        {
            project.addTag(tagService.getTagById(Long.parseLong(tagId)));
        }
        projectService.saveProject(project);
        return "redirect:/profiles/myaccount";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model theModel)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUserName(authentication.getName());
        Profile profile = user.getProfile();
        Project project = projectService.getProjectByProfileId(profile.getProfile_id(), id);
        theModel.addAttribute("project", project);
        List<Tag> tag = tagService.getAllTags();
        theModel.addAttribute("tags", tag);
        return "users/edit-project-form";
    }
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("project") Project project,
                       BindingResult theBindingResult,
                       Model theModel,
                       HttpServletRequest request,
                       @RequestParam("img") MultipartFile multipartFile)
    {
        String[] tagIds = request.getParameterValues("tagId");
        if(theBindingResult.hasErrors() || tagIds == null || tagIds.length == 0)
        {
            if(tagIds == null || tagIds.length == 0)
                theBindingResult.rejectValue("tag", "error.project", "Please select at least one tag");
            theModel.addAttribute("tags", tagService.getAllTags());
            return "users/edit-project-form";
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
        for(String tagId : tagIds)
        {
            project.addTag(tagService.getTagById(Long.parseLong(tagId)));
        }
        if(newFileName == null) project.setProject_image(fileName);
        else project.setProject_image(newFileName);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(username);
        Profile profile = user.getProfile();
        project.setProfile(profile);
        projectService.updateProject(project);
        return "redirect:/profiles/myaccount";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id)
    {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        Project project = projectService.getProjectByProfileId(profile.getProfile_id(), id);
        String fileName = project.getProject_image();
        String deleteDir = "src/main/resources/static/media/projects";
        projectService.deleteProject(project.getProject_id());
        FileUploadUtils.deleteFile(deleteDir, fileName);
        return "redirect:/profiles/myaccount";
    }
}
