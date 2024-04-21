package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "REVIEW")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long review_id;
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "project_id",nullable = false)
    private Project project;
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false)
    private Profile profile;
    @Column(name = "value", nullable = false)
    private Boolean value;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;
    public Review() {
    }

    public Review(Boolean value) {
        this.value = value;
    }

    public Long getReview_id() {
        return review_id;
    }

    public void setReview_id(Long review_id) {
        this.review_id = review_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Review{" +
                "review_id=" + review_id +
                ", value=" + value +
                ", created=" + created +
                '}';
    }
}
