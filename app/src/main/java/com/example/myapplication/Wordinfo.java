package com.example.myapplication;

public class Wordinfo {
    private int id;
    private String imageUrl;
    private String text;
    private int userId;

    public Wordinfo(int id, String imageUrl, String text, int userId) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.text = text;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }
}

