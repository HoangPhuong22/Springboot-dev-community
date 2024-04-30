package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Skill;

import java.util.List;

public interface SkillDAO {
    public void saveSkill(Skill skill);
    public void deleteSkill(Long id);
    public void updateSkill(Skill skill);
    public Skill getSkillById(Long id);
    public List<Skill> getAllSkills();
}
