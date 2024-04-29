package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.TagDAO;
import com.zerocoder.devsearch.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {
    private EntityManager entityManager;
    @Autowired
    public TagDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveTag(Tag tag) {
        entityManager.persist(tag);
    }

    @Override
    public Tag getTagById(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag getTagByName(String name) {
        TypedQuery<Tag>query = entityManager.createQuery("from Tag where name=:name", Tag.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<Tag> getAllTags() {
        TypedQuery<Tag>query = entityManager.createQuery("from Tag", Tag.class);
        return query.getResultList();
    }

    @Override
    public void deleteTag(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        entityManager.merge(tag);
    }
}
