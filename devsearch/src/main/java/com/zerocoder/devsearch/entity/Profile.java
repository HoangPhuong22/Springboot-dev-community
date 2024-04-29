package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "PROFILE")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profile_id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Skill> skills;
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Project> projects;
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> sender;
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> receiver;
    @Column(name = "name", nullable = false)
    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters")
    private String name;
    @Column(name = "headline", nullable = false)
    @NotNull(message = "Headline is required")
    @Size(min = 1, message = "Headline must be at least 1 character")
    private String headline;
    @Column(name = "bio", nullable = false)
    @NotNull(message = "Bio is required")
    @Size(min = 1, message = "Bio must be at least 1 character")
    private String bio;
    @Column(name = "address", nullable = false)
    @NotNull(message = "Address is required")
    private String address;
    @Column(name = "profile_image")
    private String profile_image;
    @Column(name = "social_github")
    private String social_github;
    @Column(name = "social_youtube")
    private String social_youtube;
    @Column(name = "social_facebook")
    private String social_facebook;
    @Column(name = "social_twitter")
    private String social_twitter;
    @Column(name = "social_tiktok")
    private String social_tiktok;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Profile() {
    }

    public Profile(String name, String headline, String bio, String address, String profile_image, String social_github, String social_youtube, String social_facebook, String social_twitter, String social_tiktok) {
        this.name = name;
        this.headline = headline;
        this.bio = bio;
        this.address = address;
        this.profile_image = profile_image;
        this.social_github = social_github;
        this.social_youtube = social_youtube;
        this.social_facebook = social_facebook;
        this.social_twitter = social_twitter;
        this.social_tiktok = social_tiktok;
    }

    public Long getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Long profile_id) {
        this.profile_id = profile_id;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    void add(Skill tempSkill)
    {
        if(skills == null)
        {
            skills = new ArrayList<>();
        }
        skills.add(tempSkill);
        tempSkill.setProfile(this);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    void addProject(Project tempProject)
    {
        if(projects == null)
        {
            projects = new ArrayList<>();
        }
        projects.add(tempProject);
        tempProject.setProfile(this);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    public void addReview(Review tempReview)
    {
        if(reviews == null)
        {
            reviews = new ArrayList<>();
        }
        reviews.add(tempReview);
        tempReview.setProfile(this);
    }

    public List<Message> getSender() {
        return sender;
    }

    public void setSender(List<Message> sender) {
        this.sender = sender;
    }

    public List<Message> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<Message> receiver) {
        this.receiver = receiver;
    }
    public void addSender(Message tempMessage)
    {
        if(sender == null)
        {
            sender = new ArrayList<>();
        }
        sender.add(tempMessage);
        tempMessage.setSender(this);
    }
    public void addReceiver(Message tempMessage)
    {
        if(receiver == null)
        {
            receiver = new ArrayList<>();
        }
        receiver.add(tempMessage);
        tempMessage.setReceiver(this);
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getSocial_github() {
        return social_github;
    }

    public void setSocial_github(String social_github) {
        this.social_github = social_github;
    }

    public String getSocial_youtube() {
        return social_youtube;
    }

    public void setSocial_youtube(String social_youtube) {
        this.social_youtube = social_youtube;
    }

    public String getSocial_facebook() {
        return social_facebook;
    }

    public void setSocial_facebook(String social_facebook) {
        this.social_facebook = social_facebook;
    }

    public String getSocial_twitter() {
        return social_twitter;
    }

    public void setSocial_twitter(String social_twitter) {
        this.social_twitter = social_twitter;
    }

    public String getSocial_tiktok() {
        return social_tiktok;
    }

    public void setSocial_tiktok(String social_tiktok) {
        this.social_tiktok = social_tiktok;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profile_id=" + profile_id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", headline='" + headline + '\'' +
                ", bio='" + bio + '\'' +
                ", address='" + address + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", social_github='" + social_github + '\'' +
                ", social_youtube='" + social_youtube + '\'' +
                ", social_facebook='" + social_facebook + '\'' +
                ", social_twitter='" + social_twitter + '\'' +
                ", social_tiktok='" + social_tiktok + '\'' +
                ", created=" + created +
                '}';
    }
}
