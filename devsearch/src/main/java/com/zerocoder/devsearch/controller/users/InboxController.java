package com.zerocoder.devsearch.controller.users;

import com.zerocoder.devsearch.entity.Message;
import com.zerocoder.devsearch.entity.Profile;
import com.zerocoder.devsearch.entity.User;
import com.zerocoder.devsearch.service.MessageService;
import com.zerocoder.devsearch.service.ProfileService;
import com.zerocoder.devsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/inbox")
public class InboxController {
    private MessageService messageService;
    private UserService userService;
    private ProfileService  profileService;
    @Autowired
    public InboxController(MessageService messageService, UserService userService, ProfileService profileService)
    {
        this.messageService = messageService;
        this.userService = userService;
        this.profileService = profileService;
    }
    @GetMapping("")
    public String inbox(Model theModel, Authentication authentication)
    {
        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        Profile profile = user.getProfile();
        List<Message> messages = profile.getReceiver();
        int count = 0;
        for(Message message : messages)
        {
            if(!message.getIs_read())
                count++;
        }
        messages.sort((message1, message2) -> {
            boolean read1 = message1.getIs_read();
            boolean read2 = message2.getIs_read();
            if (read1 != read2) {
                return Boolean.compare(read1, read2);
            } else {
                Date created1 = message1.getCreated();
                Date created2 = message2.getCreated();
                if (created1 == null) {
                    return (created2 == null) ? 0 : 1;
                }
                if (created2 == null) {
                    return -1;
                }
                return created2.compareTo(created1);
            }
        });
        theModel.addAttribute("messages", messages);
        theModel.addAttribute("messageCount", count);
        return "users/inbox";
    }
    @GetMapping("/add/{id}")
    public String addMessage(@PathVariable("id") Long id, Model theModel)
    {
        theModel.addAttribute("message", new Message());
        theModel.addAttribute("receiverId", id);
        return "users/message-form";
    }
    @PostMapping("/add/{id}")
    public String addMessage(@PathVariable("id") Long id, Message message, Authentication authentication)
    {
        String userName = authentication.getName();
        User user = userService.getUserByUserName(userName);
        Profile sender = user.getProfile();
        Profile receiver = profileService.getProfile(id);
        message.setSender(sender);
        message.setReceiver(receiver);
        messageService.saveMessage(message);
        return "redirect:/profiles/"+id;
    }
    @GetMapping("/view/{id}")
    public String viewMessage(@PathVariable("id") Long id, Model theModel)
    {
        Message message = messageService.getMessage(id);
        message.setIs_read(true);
        messageService.updateMessage(message);
        theModel.addAttribute("message", message);
        return "users/message";
    }
}
