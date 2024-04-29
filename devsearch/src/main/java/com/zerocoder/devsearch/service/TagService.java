package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Tag;

import java.util.List;

public interface TagService {
    void saveTag(Tag tag);
    Tag getTagById(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTags();
    void deleteTag(Long id);
    void updateTag(Tag tag);
}
