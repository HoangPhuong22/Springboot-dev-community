package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @OneToOne(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Profile profile;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    })
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    @Column(name = "username", unique = true, nullable = false)
    @NotNull(message = "Username is required")
    @Size(min = 1, message = "Username is required")
    @Pattern(regexp = "^[\\p{L}0-9]*$", message = "Username must not contain spaces or special characters")
    private String userName;
    @Column(name = "password", nullable = false)
    @Size(min = 6, message = "Passwords must be at least 6 characters")
    private String password;
    @Column(name = "email", unique = true, nullable = false)
    @NotNull(message = "Email is required")
    @Size(min = 1, message = "Email is required")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Enter the correct email format")
    private String email;
    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name is required")
    @Size(min = 1, message = "First name is required")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Last name is required")
    @Size(min = 1, message = "Last name is required")
    private String lastName;
    @Column(name = "enable", nullable = false)
    private boolean enable = true;
    public User(){}

    public User(String userName, String password, String email, String firstName, String lastName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void addRoles(Role theRole){
        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(theRole);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enable=" + enable +
                '}';
    }
}
