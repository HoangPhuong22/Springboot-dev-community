package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.SkillDAO;
import com.zerocoder.devsearch.entity.Skill;
import com.zerocoder.devsearch.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillDAO skillDAO;
    @Override
    @Transactional
    public void saveSkill(Skill skill) {
        skillDAO.saveSkill(skill);
    }

    @Override
    @Transactional
    public void deleteSkill(Long id) {
        skillDAO.deleteSkill(id);
    }

    @Override
    @Transactional
    public void updateSkill(Skill skill) {
        skillDAO.updateSkill(skill);
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillDAO.getSkillById(id);
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillDAO.getAllSkills();
    }
}
