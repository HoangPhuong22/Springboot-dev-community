package com.zerocoder.devsearch.controller.admin;

import com.zerocoder.devsearch.entity.Tag;
import com.zerocoder.devsearch.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/tags")
public class TagController {
    private TagService tagService;
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    @GetMapping("")
    public String home(Model theModel)
    {
        theModel.addAttribute("tags", tagService.getAllTags());
        return "admin/tag-list";
    }
    @GetMapping("/add")
    public String addTag(Model theModel)
    {
        theModel.addAttribute("tag", new Tag());
        return "admin/tag-add-form";
    }
    @PostMapping("/add")
    public String addTag(@Valid @ModelAttribute("tag") Tag tag,
                         BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/tag-add-form";
        }
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id)
    {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
    @GetMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id, Model theModel)
    {
        theModel.addAttribute("tag", tagService.getTagById(id));
        return "admin/tag-update-form";
    }
    @PostMapping("/edit")
    public String update(@Valid @ModelAttribute("tag") Tag tag,
                         BindingResult bindingResult)
    {
        Tag tagCheck = tagService.getTagByName(tag.getName());
        if(bindingResult.hasErrors() || (tagCheck != null && tagCheck.getTag_id() != tag.getTag_id()))
        {
            if(tagCheck != null &&  tagCheck.getTag_id() != tag.getTag_id())
                bindingResult.rejectValue("name", "error.tag", "Tag already exists");
            return "admin/tag-update-form";
        }
        Tag updated = tagService.getTagById(tag.getTag_id());
        tagService.updateTag(updated);
        return "redirect:/admin/tags";
    }
}
