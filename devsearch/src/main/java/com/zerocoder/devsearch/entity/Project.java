package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROJECT")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long project_id;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "owner_id",nullable = false)
    private Profile profile;
    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinTable(name = "PROJECT_TAG",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tag;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<Review> reviews;
    @Column(name = "title", nullable = false)
    @NotNull(message = "Please provide a title")
    @Size(min = 3, message = "Title must be at least 3 characters long")
    private String title;
    @Column(name = "description", nullable = false)
    @NotNull(message = "Please provide a description")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;
    @Column(name = "project_image")
    private String project_image;
    @Column(name = "demo_link")
    private String demo_link;
    @Column(name = "source_code")
    private String source_code;
    @Column(name = "vote_ratio")
    private Double vote_ratio;
    @Column(name = "vote_total")
    private Long vote_total;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    @PrePersist
    private void onCreate() {
        this.created = new Date();
    }
    public Project() {
    }

    public Project(String project_image, String demo_link, String source_code, Double vote_ratio, Long vote_total, Date created) {
        this.project_image = project_image;
        this.demo_link = demo_link;
        this.source_code = source_code;
        this.vote_ratio = vote_ratio;
        this.vote_total = vote_total;
        this.created = created;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review theReview){
        if(reviews == null){
            reviews = new ArrayList<>();
        }
        reviews.add(theReview);
        theReview.setProject(this);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Tag> getTag() {
        return tag;
    }
    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }
    public void addTag(Tag theTag){
        if(tag == null){
            tag = new ArrayList<>();
        }
        tag.add(theTag);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_image() {
        return project_image;
    }

    public void setProject_image(String project_image) {
        this.project_image = project_image;
    }

    public String getDemo_link() {
        return demo_link;
    }

    public void setDemo_link(String demo_link) {
        this.demo_link = demo_link;
    }

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public Double getVote_ratio() {
        return vote_ratio;
    }

    public void setVote_ratio(Double vote_ratio) {
        this.vote_ratio = vote_ratio;
    }

    public Long getVote_total() {
        return vote_total;
    }

    public void setVote_total(Long vote_total) {
        this.vote_total = vote_total;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", project_image='" + project_image + '\'' +
                ", demo_link='" + demo_link + '\'' +
                ", source_code='" + source_code + '\'' +
                ", vote_ratio=" + vote_ratio +
                ", vote_total=" + vote_total +
                ", created=" + created +
                '}';
    }
}
