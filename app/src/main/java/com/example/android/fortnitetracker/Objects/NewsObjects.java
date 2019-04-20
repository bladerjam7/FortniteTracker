package com.example.android.fortnitetracker.Objects;

public class NewsObjects {
    private String title;
    private String body;
    private String imageUrl;

    public NewsObjects(String title, String body, String imageUrl) {
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
