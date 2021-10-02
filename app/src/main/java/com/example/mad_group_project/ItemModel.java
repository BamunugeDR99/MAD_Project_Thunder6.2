package com.example.mad_group_project;

public class ItemModel {

    private String ItemName;
    private String Description;
    private String Image;
    private String Price;
    private String Ratings;

    public ItemModel() {
    }

    public ItemModel(String itemName, String description, String image, String price, String ratings) {
        ItemName = itemName;
        Description = description;
        Image = image;
        Price = price;
        Ratings = ratings;
    }


    public String getItemName() {
        return ItemName;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public String getPrice() {
        return Price;
    }

    public String getRatings() {
        return Ratings;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setRatings(String ratings) {
        Ratings = ratings;
    }
}
