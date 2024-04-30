package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SKILL")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long skill_id;
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "PROFILE_SKILL", joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private List<Profile> profile;
    @Column(name = "name", nullable = false)
    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;
    @Column(name = "description", nullable = false)
    @NotNull(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 character")
    private String description;
    public Skill() {
    }

    public Skill(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public void addProfile(Profile profile) {
        if(this.profile == null)
            this.profile = new ArrayList<>();
        this.profile.add(profile);
    }

    public Long getSkill_id() {
        return skill_id;
    }

    public void setSkill_id(Long skill_id) {
        this.skill_id = skill_id;
    }

    public List<Profile> getProfile() {
        return profile;
    }

    public void setProfile(List<Profile> profile) {
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
}
