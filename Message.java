package com.example.do_an.model;

import com.google.gson.annotations.Expose;

public class Message {
    @Expose
    private String role; // "system" or "user"
    @Expose
    private String content;

    // This field is not serialized, used internally within the app
    private transient boolean isUser;

    // Constructors
    public Message() {
    }

    public Message(String role, String content, boolean isUser) {
        this.role = role;
        this.content = content;
        this.isUser = isUser;
    }

    // Getter and setter for role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Getter and setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Getter and setter for isUser
    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean isUser) {
        this.isUser = isUser;
    }
}
