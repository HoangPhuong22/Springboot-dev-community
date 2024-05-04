package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.UserService;
import com.zerocoder.devsearch.utils.FileUploadUtils;
import com.zerocoder.devsearch.utils.SearchProfile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/profiles")
public class UserProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    @Autowired
    public UserProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }
    @GetMapping("")
    public String profile(Model theModel,
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
        SearchProfile result = profileService.searchProfile(keyword, page, size);
        List<Profile> profiles = result.getProfiles();
        int totalPages = result.getTotalPages();
        int currentPage = result.getCurrentPage();
        int range = 5;
        int left = Math.max(1, currentPage - 2);
        int right = Math.min(currentPage + 2, totalPages);
        if (left == 1) right = Math.min(range, totalPages);
        if (right == totalPages) left = Math.max(totalPages - range + 1, 1);

        List<Integer> pages = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            pages.add(i);
        }

        theModel.addAttribute("pages", pages);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("page", currentPage);
        theModel.addAttribute("keyword", keyword);
        theModel.addAttribute("profiles", profiles);
        return "users/profile-list";
    }
    @GetMapping("/{id}")
    public String profileDetail(@PathVariable("id") Long id, Model theModel)
    {
        Profile profile = profileService.getProfileAndSkillAndProject(id);
        theModel.addAttribute("profile", profile);
        return "users/profile-detail";
    }
    @GetMapping("/myaccount")
    public String myProfile(Model theModel, Authentication authentication)
    {
        User user = userService.getUserByUserName(authentication.getName());
        Profile profile = profileService.getProfileAndSkillAndProject(user.getProfile().getProfile_id());
        theModel.addAttribute("profile", profile);
        return "users/my-profile";
    }
    @GetMapping("/edit")
    public String editProfile(Model theModel, Authentication authentication)
    {
        User user = userService.getUserByUserName(authentication.getName());
        Profile profile = profileService.getProfile(user.getProfile().getProfile_id());
        theModel.addAttribute("profile", profile);
        return "users/edit-profile-form";
    }
    @PostMapping("/edit")
    public String editProfile(@Valid @ModelAttribute("profile") Profile profile,
                              BindingResult bindingResult,
                              @RequestParam("profileIMG")MultipartFile multipartFile,
                              Authentication authentication)
    {
        if(bindingResult.hasErrors())
        {
            return "users/edit-profile-form";
        }
        String fileName = profile.getProfile_image();
        String newFile = null;
        if(!multipartFile.isEmpty())
        {
            try
            {
                String uploadDir = "src/main/resources/static/media/profiles";
                newFile = FileUploadUtils.saveFile(uploadDir, multipartFile);
                if(fileName != null && !fileName.equals("default_avatar.jpg"))
                    FileUploadUtils.deleteFile("src/main/resources/static/media/profiles", fileName);
            }
            catch (IOException e) {
                bindingResult.rejectValue("profile_image", "error.profile", "Could not save file");
                return "admin/profile-edit-form";
            }
        }
        if(newFile != null)
            profile.setProfile_image(newFile);
        else profile.setProfile_image(fileName);
        String username = authentication.getName();
        User user = userService.getUserByUserName(username);
        profile.setUser(user);
        profileService.updateProfile(profile);
        return "redirect:/profiles/myaccount";
    }

}
