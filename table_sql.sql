-- DROP DATABASE devsearch;
-- CREATE DATABASE devsearch;
USE devsearch;
CREATE TABLE USER (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    last_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    enable BIT NOT NULL DEFAULT TRUE
);

CREATE TABLE ROLE (	
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE USER_ROLE (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES USER(user_id),
    FOREIGN KEY (role_id) REFERENCES ROLE(role_id)
);

CREATE TABLE PROFILE (
    profile_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    headline VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    bio TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    address VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    profile_image VARCHAR(255),
    social_github VARCHAR(255),
    social_youtube VARCHAR(255),
    social_facebook VARCHAR(255),
    social_tiktok VARCHAR(255),
    social_twitter VARCHAR(255),
    created DATETIME,
    FOREIGN KEY (user_id) REFERENCES USER(user_id)
);
CREATE TABLE SKILL
(
	skill_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL
);
CREATE TABLE PROFILE_SKILL
(
	skill_id BIGINT,
    profile_id BIGINT,
    PRIMARY KEY(skill_id, profile_id),
    FOREIGN KEY(skill_id) REFERENCES SKILL(skill_id),
    FOREIGN KEY(profile_id) REFERENCES PROFILE(profile_id)
);
CREATE TABLE TAG
(
	tag_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE PROJECT
(
	project_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    project_image VARCHAR(255),
    demo_link VARCHAR(255),
    source_code VARCHAR(255),
    vote_ratio DECIMAL(5, 2) DEFAULT 0,
    vote_total BIGINT DEFAULT 0,
    created DATETIME,
    FOREIGN KEY (owner_id) REFERENCES PROFILE(profile_id)
);
CREATE TABLE PROJECT_TAG
(
	project_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY(project_id, tag_id),
    FOREIGN KEY(project_id) REFERENCES PROJECT(project_id),
    FOREIGN KEY(tag_id) REFERENCES TAG(tag_id)
);
CREATE TABLE REVIEW
(
	review_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    rating BIT NOT NULL,
    created DATETIME,
    body TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL, 
    FOREIGN KEY(owner_id) REFERENCES PROFILE(profile_id),
    FOREIGN KEY(project_id) REFERENCES PROJECT(project_id)
);
CREATE TABLE MESSAGE
(
	message_id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    recipient_id BIGINT NOT NULL,
    subject VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    body TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    is_read BIT DEFAULT FALSE,
    created DATETIME,
    FOREIGN KEY(sender_id) REFERENCES PROFILE(profile_id),
    FOREIGN KEY(recipient_id) REFERENCES PROFILE(profile_id)
)