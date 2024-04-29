package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Tag;

import java.util.List;

public interface TagDAO {
    void saveTag(Tag tag);
    Tag getTagById(Long id);
    Tag getTagByName(String name);
    List<Tag> getAllTags();
    void deleteTag(Long id);
    void updateTag(Tag tag);
}
