package com.example.popcorn.models;

public class Content {
    private String title, details,image;

    public Content(){

    }

    public Content(String title, String details, String image) {
        this.title = title;
        this.details = details;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
