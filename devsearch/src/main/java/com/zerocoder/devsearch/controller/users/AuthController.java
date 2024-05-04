package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Role;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.RoleService;
import com.zerocoder.devsearch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;
    private ProfileService profileService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public AuthController(UserService userService, ProfileService profileService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.profileService = profileService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "users/login";
    }
    @GetMapping("/register")
    public String register(Model theModel) {
        theModel.addAttribute("user", new User());
        return "users/register";
    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                               Model theModel,
                               HttpServletRequest request
    ) {
        String confirmPassword = request.getParameter("confirm-password");
        boolean ok = confirmPassword.equals(user.getPassword());
        User checkUser = userService.checkUserByUserName(user.getUserName(), -999L);
        User checkEMail = userService.checkUserByEmail(user.getEmail(), -9999L);
        if (bindingResult.hasErrors() || !ok || checkUser != null || checkEMail != null) {
            if (!ok) {
                bindingResult.rejectValue("password", "error.user", "Re-entered password is incorrect");
            }
            if(checkUser != null) {
                bindingResult.rejectValue("userName", "error.user", "Username already exists");
            }
            if(checkEMail != null) {
                bindingResult.rejectValue("email", "error.user", "Email already exists");
            }
            return "users/register";
        }
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setName(user.getFirstName() + " " + user.getLastName());
        profile.setHeadline("  ");
        profile.setBio("  ");
        profile.setAddress("  ");
        profile.setProfile_image("default_avatar.jpg");
        Role role = roleService.getRoleById(2L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProfile(profile);
        user.setEnable(true);
        user.addRoles(role);
        userService.saveUser(user);
        return "redirect:/login";
    }
}
