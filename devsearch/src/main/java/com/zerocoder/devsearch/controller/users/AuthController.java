package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.Role;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.PasswordResetTokenService;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.RoleService;
import com.zerocoder.devsearch.service.UserService;
import com.zerocoder.devsearch.serviceImpl.EmailService;
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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class AuthController {
    private UserService userService;
    private ProfileService profileService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private EmailService emailService;
    private PasswordResetTokenService passwordResetTokenService;

    public AuthController(UserService userService, ProfileService profileService, RoleService roleService, PasswordEncoder passwordEncoder, EmailService emailService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.profileService = profileService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Autowired

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
        emailService.sendSimpleMessage(user.getEmail(), "Welcome to DevSearch", "You have successfully registered to DevSearch");
        return "redirect:/login";
    }
    @GetMapping("/reset-password")
    public String resetPassword() {
        return "users/reset-password";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email, Model theModel)
    {
        User user = userService.findUserByEmail(email);
        if(user == null)
        {
            theModel.addAttribute("error", "Email not found");
            return "users/reset-password";
        }
        passwordResetTokenService.deletePasswordResetTokenByUser(user);
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, token);
        String text = "Hello " + user.getFirstName() + " " + user.getLastName() + ",\n" +
                "User: " + user.getUserName() + "\n" +
                "You have requested to reset your password.\n"+
                "Please click the link below to reset your password.\n" +
                "Link expires in 60 second, if you do not reset your password within 60 second, you will have to request a new link.\n" +
                "http://localhost:8080/change-password?id=" + user.getUserId() + "&token=" + token + "\n" +
                "If you did not request a password reset, please ignore this email.";
        emailService.sendSimpleMessage(user.getEmail(), "Password Reset Request", text);
        theModel.addAttribute("message", "Password reset link has been sent to your email");
        return "users/reset-password";
    }
    @GetMapping("/change-password")
    public String changePassword(@RequestParam("id") Long id, @RequestParam("token") String token, Model theModel)
    {
        String result = passwordResetTokenService.validatePasswordResetToken(id, token);
        if(result.equals("invalidToken"))
        {
            theModel.addAttribute("error", "The link is broken, please resend a new request to change your password");
            return "users/reset-password";
        }
        if(result.equals("expired"))
        {
            theModel.addAttribute("error", "Link is expired, please email to create a new link");
            return "users/reset-password";
        }
        theModel.addAttribute("id", id);
        theModel.addAttribute("token", token);
        return "users/change-password";
    }
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("id") Long id, @RequestParam("token") String token,
                             @RequestParam("password") String password,
                             @RequestParam("confirm-password") String cfPassword, Model theModel) {
        String result = passwordResetTokenService.validatePasswordResetToken(id, token);
        if (result.equals("invalidToken")) {
            theModel.addAttribute("error", "The link is broken, please resend a new request to change your password");
            return "users/reset-password";
        }
        if (result.equals("expired")) {
            theModel.addAttribute("error", "Link is expired, please email to create a new link");
            return "users/reset-password";
        }
        if(password.length() < 6 || !password.equals(cfPassword))
        {
            String text1 = "", text2 = "";
            theModel.addAttribute("id", id);
            theModel.addAttribute("token", token);
            if(password.length() < 6)
            {
                text1 = "Password must be at least 6 characters";
            }
            if(!password.equals(cfPassword))
            {
                text2 = "Re-entered password is incorrect";
            }
            theModel.addAttribute("error1", text1);
            theModel.addAttribute("error2", text2);
            return "users/change-password";
        }

        User user = userService.getUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        userService.updateUser(user);
        passwordResetTokenService.deletePasswordResetTokenByUser(user);
        theModel.addAttribute("message", "Password changed successfully");
        return "users/login";
    }
}
