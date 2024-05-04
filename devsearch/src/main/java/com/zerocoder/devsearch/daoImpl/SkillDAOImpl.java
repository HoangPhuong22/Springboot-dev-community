package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.SkillDAO;
import com.zerocoder.devsearch.entity.Skill;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public class SkillDAOImpl implements SkillDAO {
    @Autowired
    private EntityManager entityManager;
    @Override
    public void saveSkill(Skill skill) {
        entityManager.persist(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = entityManager.find(Skill.class, id);
        entityManager.remove(skill);
    }

    @Override
    public void updateSkill(Skill skill) {
        entityManager.merge(skill);
    }

    @Override
    public Skill getSkillByProfileId(Long profile_id, Long skill_id) {
        Skill skill = entityManager.find(Skill.class, skill_id);
        if (skill != null) {
            boolean hasProfile = skill.getProfile().stream().anyMatch(profile -> profile.getProfile_id().equals(profile_id));
            if (hasProfile) {
                return skill;
            }
        }
        return null;
    }

    @Override
    public Skill getSkillById(Long id) {
        return entityManager.find(Skill.class, id);
    }

    @Override
    public List<Skill> getAllSkills() {
        return entityManager.createQuery("from Skill s left join fetch s.profile", Skill.class).getResultList();
    }
}
