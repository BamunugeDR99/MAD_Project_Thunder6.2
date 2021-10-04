package com.example.mad_group_project;

public class yourreviews {
        private String name;
        private String description;
        private String imageurl;
        private String rating;

    public yourreviews() {
    }

    public yourreviews(String name, String description, String imageurl, String rating) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
