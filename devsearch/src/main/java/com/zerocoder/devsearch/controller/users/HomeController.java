package com.zerocoder.devsearch.controller.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String home()
    {
        return "redirect:/profiles";
    }
}
