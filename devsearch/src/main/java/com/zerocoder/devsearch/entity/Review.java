package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(name = "rating", nullable = false)
    private Boolean value;
    @Column(name = "body", nullable = false)
    @NotNull(message = "Review body is required.")
    @Size(min = 10, message = "Review body must be at least 10 characters long.")
    private String body;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
    public Review() {
    }

    public Review(Long review_id, Boolean value, String body, Date created) {
        this.review_id = review_id;
        this.value = value;
        this.body = body;
        this.created = created;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
