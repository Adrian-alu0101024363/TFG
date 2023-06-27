package com.example.myapplication;

public class Wordinfo {
    private int id;
    private String imageUrl;
    private String text;
    private int userId;
    private String translation;

    public Wordinfo(int id, String imageUrl, String text, int userId, String translation) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.text = text;
        this.userId = userId;
        this.translation = translation;
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

    public String getTranslation() {return  translation;}
}

