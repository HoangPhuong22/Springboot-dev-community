package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Skill;

import java.util.List;

public interface SkillService {
    public void saveSkill(Skill skill);
    public void deleteSkill(Long id);
    public void updateSkill(Skill skill);
    public Skill getSkillById(Long id);
    public List<Skill> getAllSkills();
}
