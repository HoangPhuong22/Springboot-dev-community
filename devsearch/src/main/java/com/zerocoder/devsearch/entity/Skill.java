package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "SKILL")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skill_id;
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false)
    private Profile profile;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    public Skill() {
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "skill_id=" + skill_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
