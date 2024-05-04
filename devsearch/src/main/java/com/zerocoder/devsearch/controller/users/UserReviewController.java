package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.entity.Review;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.service.ReviewService;
import com.zerocoder.devsearch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
public class UserReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProjectService projectService;
    @Autowired
    public UserReviewController(ReviewService reviewService, UserService userService, ProfileService profileService, ProjectService projectService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.profileService = profileService;
        this.projectService = projectService;
    }
    @PostMapping("/add/{id}")
    public String add(@Valid @ModelAttribute("review")Review review, BindingResult bindingResult,
                      @PathVariable("id") Long id ,
                      Model theModel,
                      Authentication authentication
    ){
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
        if(bindingResult.hasErrors() || ok != 1) {
            if(ok == 0)
                bindingResult.rejectValue("profile", "error.review", "Your has already reviewed this project.");
            if(ok == 2)
                bindingResult.rejectValue("profile", "error.review", "You can't review your own project.");
            theModel.addAttribute("project", project);
            theModel.addAttribute("ok", ok);
            return "users/project-details";
        }
        review.setProject(project);
        review.setProfile(user.getProfile());
        reviewService.saveReview(review);
        Project theProject = projectService.getProjectById(review.getProject().getProject_id());
        theProject.updateVoteData();
        projectService.updateProject(theProject);
        return "redirect:/projects/" + id;
    }
}
