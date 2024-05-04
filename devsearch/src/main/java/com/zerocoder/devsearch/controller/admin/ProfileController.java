package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.UserService;
import com.zerocoder.devsearch.utils.FileUploadUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/profiles")
public class ProfileController {
    private ProfileService profileService;
    private UserService userService;
    @Autowired
    public ProfileController(ProfileService profileService, UserService userService)
    {
        this.profileService = profileService;
        this.userService = userService;
    }
    @GetMapping("")
    public String index(Model theModel)
    {
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "admin/profile-list";
    }
    @GetMapping("/add")
    public String addProfile(Model theModel)
    {
        theModel.addAttribute("profile", new Profile());
        theModel.addAttribute("users", userService.getAllUsers());
        return "admin/profile-add-form";
    }
    @PostMapping("/add")
    public String addProfile(@Valid @ModelAttribute("profile") Profile theProfile,
                             BindingResult bindingResult,
                             @RequestParam("img")MultipartFile multipartFile,
                             Model theModel
                             )
    {
        Long id = theProfile.getUser().getUserId();
        boolean ok = true;
        for(Profile p : profileService.getAllProfiles())
        {
            if(p.getUser().getUserId() == id)
            {
                ok = false;
                break;
            }
        }

        if(bindingResult.hasErrors() || !ok || multipartFile.isEmpty())
        {
            theModel.addAttribute("users", userService.getAllUsers());
            System.out.println(multipartFile.getOriginalFilename());
            if(multipartFile.isEmpty())
                bindingResult.rejectValue("profile_image", "error.profile", "Please select an image");
            if(!ok)
                bindingResult.rejectValue("user", "error.profile", "User already has a profile");
            return "admin/profile-add-form";
        }
        try
        {
            String uploadDir = "src/main/resources/static/media/profiles";
            String fileName = FileUploadUtils.saveFile(uploadDir, multipartFile);
            theProfile.setProfile_image(fileName);
        }
        catch (IOException e) {
            theModel.addAttribute("users", userService.getAllUsers());
            bindingResult.rejectValue("profile_image", "error.profile", "Could not save file");
            return "admin/profile-add-form";
        }
        profileService.saveProfile(theProfile);
        return "redirect:/admin/profiles";
    }
    @GetMapping("/edit/{id}")
    public String editProfile(@PathVariable("id") Long id, Model theModel)
    {
        Profile profile = profileService.getProfile(id);
        theModel.addAttribute("profile", profile);
        theModel.addAttribute("users", userService.getAllUsers());
        return "admin/profile-edit-form";
    }
    @PostMapping("/edit")
    public String editProfile(@Valid @ModelAttribute("profile") Profile theProfile,
                          BindingResult bindingResult,
                          @RequestParam("img") MultipartFile multipartFile,
                          Model theModel
                          )
{
    Long id = theProfile.getUser().getUserId();
    boolean ok = true;
    for(Profile p : profileService.getAllProfiles())
    {
        if(p.getUser().getUserId() == id && p.getProfile_id() != theProfile.getProfile_id())
        {
            ok = false;
            break;
        }
    }
    if(bindingResult.hasErrors() || !ok)
    {
        theModel.addAttribute("users", userService.getAllUsers());
        if(!ok)
            bindingResult.rejectValue("user", "error.profile", "User already has a profile");
        return "admin/profile-edit-form";
    }
    // Get the managed Profile from the database
    Profile managedProfile = profileService.getProfile(theProfile.getProfile_id());
    managedProfile.setName(theProfile.getName());
    managedProfile.setHeadline(theProfile.getHeadline());
    managedProfile.setBio(theProfile.getBio());
    managedProfile.setAddress(theProfile.getAddress());
    managedProfile.setSocial_github(theProfile.getSocial_github());
    managedProfile.setSocial_facebook(theProfile.getSocial_facebook());
    managedProfile.setSocial_tiktok(theProfile.getSocial_tiktok());
    managedProfile.setSocial_twitter(theProfile.getSocial_twitter());
    managedProfile.setSocial_youtube(theProfile.getSocial_youtube());
    String fileName = managedProfile.getProfile_image();
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
            theModel.addAttribute("users", userService.getAllUsers());
            bindingResult.rejectValue("profile_image", "error.profile", "Could not save file");
            return "admin/profile-edit-form";
        }
    }
    if(newFile != null)
        managedProfile.setProfile_image(newFile);
    else managedProfile.setProfile_image(fileName);
    // Update the managed Profile
    profileService.updateProfile(managedProfile);
    return "redirect:/admin/profiles";
}
    @GetMapping("/delete/{id}")
    public String deleteProfile(@PathVariable("id") Long id)
    {
        String fileName = profileService.getProfile(id).getProfile_image();
        if(fileName != null && !fileName.equals("default_avatar.jpg"))
            FileUploadUtils.deleteFile("src/main/resources/static/media/profiles", fileName);
        profileService.deleteProfile(id);
        return "redirect:/admin/profiles";
    }
}
