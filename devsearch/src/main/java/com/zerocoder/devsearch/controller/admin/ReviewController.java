package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Project;
import com.zerocoder.devsearch.entity.Review;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.ProjectService;
import com.zerocoder.devsearch.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ProfileService profileService;
    private ProjectService projectService;
    @Autowired
    public ReviewController(ReviewService reviewService, ProfileService profileService, ProjectService projectService) {
        this.reviewService = reviewService;
        this.profileService = profileService;
        this.projectService = projectService;
    }
    @GetMapping("")
    public String index(Model theModel) {
        theModel.addAttribute("reviews", reviewService.getAllReviews());
        return "admin/review-list";
    }
    @GetMapping("/add")
    public String addReview(Model theModel) {
        theModel.addAttribute("review", new Review());
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        theModel.addAttribute("projects", projectService.getAllProjects());
        return "admin/review-add-form";
    }
    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute("review") Review theReview,
                            BindingResult bindingResult, Model theModel) {
        boolean ok = true;
        for(Review r : reviewService.getAllReviews()) {
            if(r.getProfile().getProfile_id() == theReview.getProfile().getProfile_id() &&
                    r.getProject().getProject_id() == theReview.getProject().getProject_id()){
                ok = false;
                break;
            }
        }
        if(bindingResult.hasErrors() || !ok) {
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            theModel.addAttribute("projects", projectService.getAllProjects());
            System.out.println("LOI ROI BAN OI");
            if(!ok) {
                bindingResult.rejectValue("profile", "error.review", "Profile has already reviewed this project.");
            }
            return "admin/review-add-form";
        }
        Project project = theReview.getProject();
        long total = 0;
        double ratio = 0;
        for(Review r : reviewService.getAllReviews()) {
            ++total;
            if(r.getValue() == true) ++ratio;
        }
        ratio = ratio / total;
        project.setVote_ratio(ratio);
        project.setVote_total(total);
        projectService.updateProject(project);
        reviewService.saveReview(theReview);
        return "redirect:/admin/reviews";
    }
    @GetMapping("/edit/{id}")
    public String editReview(@PathVariable("id") Long id, Model theModel) {
        theModel.addAttribute("review", reviewService.getReview(id));
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        theModel.addAttribute("projects", projectService.getAllProjects());
        return "admin/review-edit-form";
    }
    @PostMapping("/edit")
    public String editReview(@Valid @ModelAttribute("review") Review theReview,
                             BindingResult bindingResult, Model theModel) {
        boolean ok = true;
        for(Review r : reviewService.getAllReviews()) {
            if(r.getProfile().getProfile_id() == theReview.getProfile().getProfile_id() &&
                    r.getProject().getProject_id() == theReview.getProject().getProject_id() &&
                    r.getReview_id() != theReview.getReview_id()){
                ok = false;
                break;
            }
        }
        if(bindingResult.hasErrors() || !ok) {
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            theModel.addAttribute("projects", projectService.getAllProjects());
            if(!ok) {
                bindingResult.rejectValue("profile", "error.review", "Profile has already reviewed this project.");
            }
            return "admin/review-edit-form";
        }

        Project project = theReview.getProject();
        long total = 0;
        double ratio = 0;
        for(Review r : reviewService.getAllReviews()) {
            ++total;
            if(r.getValue() == true) ++ratio;
        }
        ratio = ratio / total;
        project.setVote_ratio(ratio);
        project.setVote_total(total);
        projectService.updateProject(project);

        reviewService.updateReview(theReview);
        return "redirect:/admin/reviews";
    }
    @GetMapping("delete/{id}")
    public String deleteReview(@PathVariable Long id) {
        Project project = reviewService.getReview(id).getProject();
        long total = 0;
        double ratio = 0;
        for(Review r : reviewService.getAllReviews()) {
            ++total;
            if(r.getValue() == true) ++ratio;
        }
        ratio = ratio / total * 100;
        project.setVote_ratio(ratio);
        project.setVote_total(total);
        projectService.updateProject(project);
        reviewService.deleteReview(id);
        return "redirect:/admin/reviews";
    }
}
