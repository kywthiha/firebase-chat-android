package com.example.kyaw.firebasetutorial.chat.model;

public class User {
    private String id;
    private String name;
    private String profile_url;
    private String email;

    public User(String id, String name, String profile_url, String email) {
        this.id = id;
        this.name = name;
        this.profile_url = profile_url;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }
}
