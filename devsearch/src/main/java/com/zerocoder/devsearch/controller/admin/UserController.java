package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Role;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.RoleService;
import com.zerocoder.devsearch.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("")
    public String index(Model theModel)
    {
        theModel.addAttribute("users", userService.getAllUsers());
        return "admin/user-list";
    }
    @GetMapping("/add")
    public String add(Model theModel)
    {
        User user = new User();
        theModel.addAttribute("user", user);
        return "admin/user-add-form";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("user") User user, BindingResult theBindingResult,
                       HttpServletRequest request)
    {
        User checkUsername = userService.getUserByUserName(user.getUserName());
        User checkEmail = userService.getUserByEmail(user.getEmail());
        String confirmPassword = request.getParameter("confirmPassword");
        if(theBindingResult.hasErrors() || checkUsername != null || checkEmail != null || !user.getPassword().equals(confirmPassword))
        {
            System.out.println(user.getUserName().length());
            if(checkUsername != null)
                theBindingResult.rejectValue("userName", "error.user", "Username already exists");
            if(checkEmail != null)
                theBindingResult.rejectValue("email", "error.user", "Email already exists");
            if(!user.getPassword().equals(confirmPassword))
                theBindingResult.rejectValue("password", "error.user", "Password and Confirm Password must be same");
            return "admin/user-add-form";
        }
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model theModel)
    {
        User user = userService.getUserRoleById(id);
        List<Role> allRoles = roleService.getAllRoles();
        System.out.println(allRoles.size());
        theModel.addAttribute("user", user);
        theModel.addAttribute("allRoles", allRoles);
        return "admin/user-edit-form";
    }
    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("user") User user,
                       BindingResult theBindingResult,
                       Model theModel,
                       HttpServletRequest request)
    {
        User checkUsername = userService.checkUserByUserName(user.getUserName(), user.getUserId());
        User checkEmail = userService.checkUserByEmail(user.getEmail(), user.getUserId());
        if(theBindingResult.hasErrors() || checkUsername != null || checkEmail != null)
        {
            String[] roles = request.getParameterValues("rolesID");
            if(roles != null)
            {
                if(user.getRoles() != null) user.getRoles().clear();
                for(String role : roles)
                {
                    Role theRole = roleService.getRoleById(Long.parseLong(role));
                    user.addRoles(theRole);
                }
            }
            List<Role> allRoles = roleService.getAllRoles();
            theModel.addAttribute("allRoles", allRoles);
            if(checkEmail != null)
                theBindingResult.rejectValue("email", "error.user", "Email already exists");
            return "admin/user-edit-form";
        }
        String[] roles = request.getParameterValues("rolesID");
        if(roles != null)
        {
            if(user.getRoles() != null) user.getRoles().clear();
            for(String role : roles)
            {
                Role theRole = roleService.getRoleById(Long.parseLong(role));
                user.addRoles(theRole);
            }
        }
        User userUpdated = userService.getUserRoleById(user.getUserId());
        userUpdated.setUserName(user.getUserName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setRoles(user.getRoles());
        userUpdated.setFirstName(user.getFirstName());
        userUpdated.setLastName(user.getLastName());
        userUpdated.setEnable(user.getEnable());
        userService.updateUser(userUpdated);
        return "redirect:/admin/users";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id)
    {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
