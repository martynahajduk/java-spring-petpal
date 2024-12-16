package be.kdg.programming3.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserFormData {
    private Long id;

    private String name;
    private String email;
    private String password;

    private long feederId;


    public UserFormData() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getFeederId() {
        return feederId;
    }

    public void setFeederId(long feederId) {
        this.feederId = feederId;
    }
}


