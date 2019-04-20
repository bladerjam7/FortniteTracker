package com.example.android.fortnitetracker.Objects;

public class Post {
    private String name;
    private String message;

    public Post() {
    }

    public Post(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
