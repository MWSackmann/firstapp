package com.example.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sackmann on 02.05.2016.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String message;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @Version
    private long version;

    public Post() {
    }

    public Post(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    @PrePersist
    protected void onPersist() {
        this.createdAt = now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = now();
    }

    protected Timestamp now() {
        return new Timestamp(new Date().getTime());
    }

    @Override
    public String toString() {
        return "Post ID " + id;
    }
}
