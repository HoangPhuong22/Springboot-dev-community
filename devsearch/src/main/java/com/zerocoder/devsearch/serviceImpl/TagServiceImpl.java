package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.TagDAO;
import com.zerocoder.devsearch.entity.Tag;
import com.zerocoder.devsearch.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private TagDAO tagDAO;
    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
    @Override
    @Transactional
    public void saveTag(Tag tag) {
        tagDAO.saveTag(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagDAO.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        try {
            return tagDAO.getTagByName(name);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDAO.getAllTags();
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagDAO.deleteTag(id);
    }

    @Override
    @Transactional
    public void updateTag(Tag tag) {
        tagDAO.updateTag(tag);
    }
}
