package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long message_id;
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "sender_id",nullable = false)
    private Profile sender;
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH}, fetch=FetchType.LAZY)
    @JoinColumn(name = "recipient_id",nullable = false)
    private Profile receiver;
    @Column(name = "subject", nullable = false)
    @NotNull(message = "Message subject is required")
    @Size(min = 2, message = "Subject must be at least 2 characters long")
    private String subject;
    @Column(name = "body", nullable = false)
    @NotNull(message = "Message body is required")
    private String body;
    @Column(name = "is_read")
    private Boolean is_read = false;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public Message(Long message_id, String subject, String body) {
        this.message_id = message_id;
        this.subject = subject;
        this.body = body;
    }
    public Message() {
    }
    public long getMinutesSinceCreated() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime created = LocalDateTime.ofInstant(getCreated().toInstant(), ZoneId.systemDefault());
        return Duration.between(created, now).toMinutes();
    }

    public Long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Long message_id) {
        this.message_id = message_id;
    }

    public Profile getSender() {
        return sender;
    }

    public void setSender(Profile sender) {
        this.sender = sender;
    }

    public Profile getReceiver() {
        return receiver;
    }

    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIs_read() {
        return is_read;
    }

    public void setIs_read(Boolean is_read) {
        this.is_read = is_read;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", is_read=" + is_read +
                ", created=" + created +
                '}';
    }
}
