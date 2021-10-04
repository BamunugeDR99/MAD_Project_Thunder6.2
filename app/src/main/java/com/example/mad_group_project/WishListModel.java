package com.example.mad_group_project;
public class WishListModel {

    private String ItemName;
    private String Price;
    private String Ratings;
    private String Reviews;
    private String imgurl;
    private String description;


    public WishListModel() {
    }

    public WishListModel(String itemName, String price, String ratings, String reviews, String imgurl) {
        ItemName = itemName;
        Price = price;
        Ratings = ratings;
        Reviews = reviews;
        this.imgurl = imgurl;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getPrice() {
        return Price;
    }

    public String getRatings() {
        return Ratings;
    }

    public String getReviews() {
        return Reviews;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }

    public void setReviews(String reviews) {
        Reviews = reviews;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
