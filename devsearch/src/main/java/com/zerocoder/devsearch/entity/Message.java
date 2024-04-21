package com.zerocoder.devsearch.entity;

import jakarta.persistence.*;

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
    private String subject;
    @Column(name = "body", nullable = false)
    private String body;
    @Column(name = "is_read")
    private Boolean is_read = false;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    public Message(Long message_id, String subject, String body) {
        this.message_id = message_id;
        this.subject = subject;
        this.body = body;
    }
    public Message() {
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
