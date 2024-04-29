package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Message;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.service.MessageService;
import com.zerocoder.devsearch.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/messages")
public class MessageController {
    private MessageService messageService;
    private ProfileService profileService;

    @Autowired
    public MessageController(MessageService messageService, ProfileService profileService) {
        this.messageService = messageService;
        this.profileService = profileService;
    }

    @GetMapping("")
    public String index(Model theModel) {
        theModel.addAttribute("messages", messageService.getAllMessages());
        return "admin/message-list";
    }
    @GetMapping("/add")
    public String addMessage(Model theModel)
    {
        theModel.addAttribute("message", new Message());
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "admin/message-add-form";
    }
    @PostMapping("/add")
    public String addMessage(@Valid @ModelAttribute("message") Message theMessage,
                             BindingResult bindingResult, Model theModel
    )
    {
        if(bindingResult.hasErrors())
        {
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            return "admin/message-add-form";
        }
        messageService.saveMessage(theMessage);
        return "redirect:/admin/messages";
    }
    @GetMapping("/edit/{id}")
    public String editMessage(@PathVariable("id") Long theId, Model theModel)
    {
        theModel.addAttribute("message", messageService.getMessage(theId));
        theModel.addAttribute("profiles", profileService.getAllProfiles());
        return "admin/message-edit-form";
    }
    @PostMapping("/edit")
    public String editMessage(@Valid @ModelAttribute("message") Message theMessage,
                              BindingResult bindingResult, Model theModel)
    {
        if(bindingResult.hasErrors())
        {
            theModel.addAttribute("profiles", profileService.getAllProfiles());
            return "admin/message-edit-form";
        }
        messageService.updateMessage(theMessage);
        return "redirect:/admin/messages";
    }
    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable("id") Long theId)
    {
        messageService.deleteMessage(theId);
        return "redirect:/admin/messages";
    }
}
